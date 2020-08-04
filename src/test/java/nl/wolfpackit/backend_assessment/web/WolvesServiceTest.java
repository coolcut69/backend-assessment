package nl.wolfpackit.backend_assessment.web;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import nl.wolfpackit.backend_assessment.model.WolfEntity;
import nl.wolfpackit.backend_assessment.persistence.PackRepository;
import nl.wolfpackit.backend_assessment.persistence.WolfRepository;
import nl.wolfpackit.backend_assessment.web.exception.DomainValidationException;
import nl.wolfpackit.backend_assessment.web.exception.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

class WolvesServiceTest {

    private static final Long WOLF_ID = 5L;
    private final WolvesService service;
    private final WolfRepository wolfRepository = mock(WolfRepository.class);
    private final PackRepository packRepository = mock(PackRepository.class);

    public WolvesServiceTest() {
        MockitoAnnotations.initMocks(this);
        this.service = new WolvesServiceImpl(wolfRepository, packRepository);
    }

    @Test
    @DisplayName("Validate wolf resource, name cannot be empty")
    void createWolfWithoutName() {
        //given
        WolfResource resource = new WolfResource();

        //when
        DomainValidationException exception = assertThrows(DomainValidationException.class, () -> service.createWolf(resource));

        //then
        assertThat(exception.getMessage(), equalTo("Cannot create wolf, reason: name cannot be blank"));
        verify(wolfRepository, times(0)).save(any(WolfEntity.class));
    }

    @Test
    @DisplayName("Validate wolf resource, gender should be M,F,X")
    void createWolfInvalidGender() {
        //given
        WolfResource resource = new WolfResource();
        resource.setName("valid");
        resource.setGender("BLA");

        //when
        DomainValidationException exception = assertThrows(DomainValidationException.class, () -> service.createWolf(resource));

        //then
        assertThat(exception.getMessage(), equalTo("Cannot create wolf, reason: unknown gender value BLA"));
        verify(wolfRepository, times(0)).save(any(WolfEntity.class));
    }

    @Test
    @DisplayName("Validate wolf resource, date should be in format YYYY-MM-DD")
    void updateWolfInvalidBirthDate() {
        //given
        WolfResource resource = new WolfResource();
        resource.setName("valid");
        resource.setGender("X");
        resource.setBirthDate("1978/02/11");

        //when
        DomainValidationException exception = assertThrows(DomainValidationException.class, () -> service.updateWolf(WOLF_ID, resource));

        //then
        assertThat(exception.getMessage(), equalTo("Cannot update wolf, reason: birth date should be in format YYYY-MM-DD not 1978/02/11"));
        verify(wolfRepository, times(0)).save(any(WolfEntity.class));
    }

    @Test
    @DisplayName("Delete wolf")
    void deleteWolf() {
        //given
        WolfEntity entity = new WolfEntity();
        entity.setId(WOLF_ID);
        when(wolfRepository.findById(WOLF_ID)).thenReturn(Optional.of(entity));

        //when
        service.deleteWolf(WOLF_ID);

        //then
        verify(wolfRepository, times(1)).delete(any(WolfEntity.class));
    }

    @Test
    @DisplayName("Delete unknown wolf")
    void deleteUnknownWolf() {
        //given
        when(wolfRepository.findById(WOLF_ID)).thenReturn(Optional.empty());

        //when
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            service.deleteWolf(WOLF_ID);
        });

        //then
        assertThat(exception.getMessage(), equalTo("Could not find wolf " + WOLF_ID));
    }

}
