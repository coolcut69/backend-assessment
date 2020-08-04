package nl.wolfpackit.backend_assessment.web;

import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import nl.wolfpackit.backend_assessment.model.Gender;
import nl.wolfpackit.backend_assessment.model.PackEntity;
import nl.wolfpackit.backend_assessment.model.WolfEntity;
import nl.wolfpackit.backend_assessment.persistence.PackRepository;
import nl.wolfpackit.backend_assessment.persistence.WolfRepository;
import nl.wolfpackit.backend_assessment.web.exception.DomainValidationException;
import nl.wolfpackit.backend_assessment.web.exception.EntityNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WolvesServiceImpl implements WolvesService {

    private final WolfRepository wolfRepository;
    private final PackRepository packRepository;

    public WolvesServiceImpl(WolfRepository wolfRepository, PackRepository packRepository) {
        this.wolfRepository = wolfRepository;
        this.packRepository = packRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WolfEntity> getWolves(int size, int page, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return wolfRepository.findAll(pageable).getContent();
    }

    @Override
    public List<PackEntity> getPacks(int size, int page, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return packRepository.findAll(pageable).getContent();
    }

    @Override
    @Transactional
    public WolfEntity createWolf(WolfResource resource) {
        validateResource(resource, "Cannot create wolf, reason:");

        WolfEntity entity = new WolfEntity();
        entity.setName(resource.getName());
        entity.setGender(Gender.byLabel(resource.getGender()));
        entity.setBirthDate(DateUtilities.parseISO8601Date(resource.getBirthDate()));

        return wolfRepository.save(entity);
    }

    private void validateResource(WolfResource resource, String context) {
        if (StringUtils.isBlank(resource.getName())) {
            throw new DomainValidationException(context + " name cannot be blank");
        }

        if (StringUtils.isBlank(resource.getGender())) {
            throw new DomainValidationException(context + " gender cannot be blank");
        } else {
            Arrays.stream(Gender.values()).
                    filter(gender -> gender.getLabel().equals(resource.getGender())).
                    findAny().
                    orElseThrow(() -> new DomainValidationException(context + " unknown gender value " + resource.getGender()));
        }

        if (StringUtils.isBlank(resource.getBirthDate())) {
            throw new DomainValidationException(context + " birth date cannot be blank");
        } else {
            try {
                DateUtilities.parseISO8601Date(resource.getBirthDate());
            } catch (IllegalArgumentException | DateTimeParseException e) {
                throw new DomainValidationException(context + " birth date should be in format YYYY-MM-DD not " + resource.getBirthDate());
            }
        }
    }

    @Override
    @Transactional
    public WolfEntity updateWolf(Long id, WolfResource resource) {
        validateResource(resource, "Cannot update wolf, reason:");

        WolfEntity entity = wolfRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "wolf"));
        entity.setName(resource.getName());
        entity.setGender(Gender.byLabel(resource.getGender()));
        entity.setBirthDate(DateUtilities.parseISO8601Date(resource.getBirthDate()));

        return wolfRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteWolf(Long id) {
        WolfEntity entity = wolfRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "wolf"));

        if (packRepository.findAllByWolfId(id).size() == 0) {
            wolfRepository.delete(entity);
        } else {
            throw new DomainValidationException("Cannot delete wolf, reason: is member of a pack");
        }
    }

    @Override
    public void addWolfToPack(Long packId, Long wolfId) {
        PackEntity pack = packRepository.findById(packId).orElseThrow(() -> new EntityNotFoundException(packId, "pack"));
        WolfEntity wolf = wolfRepository.findById(wolfId).orElseThrow(() -> new EntityNotFoundException(wolfId, "wolf"));

        pack.getWolves().add(wolf);
        packRepository.save(pack);

    }

    @Override
    public void deleteWolfFromPack(Long packId, Long wolfId) {
        PackEntity pack = packRepository.findById(packId).orElseThrow(() -> new EntityNotFoundException(packId, "pack"));
        WolfEntity wolf = wolfRepository.findById(wolfId).orElseThrow(() -> new EntityNotFoundException(wolfId, "wolf"));

        pack.getWolves().remove(wolf);
        packRepository.save(pack);
    }
}
