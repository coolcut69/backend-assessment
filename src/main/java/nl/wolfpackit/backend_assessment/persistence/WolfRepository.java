package nl.wolfpackit.backend_assessment.persistence;

import nl.wolfpackit.backend_assessment.model.WolfEntity;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface WolfRepository extends PagingAndSortingRepository<WolfEntity, Long> {
}
