package com.biel.dominatorarena.model.repositories;

import com.biel.dominatorarena.model.entities.Executor;
import com.biel.dominatorarena.model.entities.WorkBlock;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Biel on 4/12/2016.
 */
public interface WorkBlockRepository extends CrudRepository<WorkBlock, Long> {
    List<WorkBlock> findByExecutor(Executor executor);
    Optional<WorkBlock> findOneByExecutor_Id(Long id);
    Optional<WorkBlock> findOneByExecutor_IdAndExecutorIsNull(Long id);
    List<WorkBlock> findByExecutorIsNullAndExecutedIsFalse();
}
