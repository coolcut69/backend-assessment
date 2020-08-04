package nl.wolfpackit.backend_assessment.web;

import java.util.List;

import nl.wolfpackit.backend_assessment.model.PackEntity;
import nl.wolfpackit.backend_assessment.model.WolfEntity;

public interface WolvesService {
    List<WolfEntity> getWolves(int size, int page, String sort);

    List<PackEntity> getPacks(int size, int page, String sort);

    WolfEntity createWolf(WolfResource resource);

    WolfEntity updateWolf(Long id, WolfResource resource);

    void deleteWolf(Long id);

    void addWolfToPack(Long packId, Long wolfId);

    void deleteWolfFromPack(Long packId, Long wolfId);
}
