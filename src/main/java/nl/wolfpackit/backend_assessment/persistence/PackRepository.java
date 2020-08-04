package nl.wolfpackit.backend_assessment.persistence;

import java.util.List;

import nl.wolfpackit.backend_assessment.model.PackEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PackRepository extends PagingAndSortingRepository<PackEntity, Long> {

    @Query("select p from PackEntity p join p.wolves w where w.id  = ?1")
    List<PackEntity> findAllByWolfId(Long wolfId);
}
