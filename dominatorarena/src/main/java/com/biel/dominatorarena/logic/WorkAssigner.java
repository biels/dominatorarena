package com.biel.dominatorarena.logic;

import com.biel.dominatorarena.model.entities.Executor;
import com.biel.dominatorarena.model.entities.StatisticBattle;
import com.biel.dominatorarena.model.entities.WorkBlock;
import com.biel.dominatorarena.model.repositories.ExecutorRepository;
import com.biel.dominatorarena.model.repositories.WorkBlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by Biel on 4/12/2016.
 */
@Component
public class WorkAssigner {
    @Autowired
    ExecutorRepository executorRepository;
    @Autowired
    WorkBlockRepository workBlockRepository;
    @Autowired
    RandomWorkGenerator randomWorkGenerator;
    public Optional<WorkBlock> assignWorkToExecutor(Executor executor){
        int n = executor.getCpuCoreCount() * 40 + 20;
        Optional<WorkBlock> workBlockOptional = randomWorkGenerator.generateNextWorkBlock(n);
        if(!workBlockOptional.isPresent()) return Optional.empty();
        WorkBlock workBlock = workBlockOptional.get();
        workBlock.setExecutor(executor);
        return Optional.ofNullable(workBlockRepository.save(workBlock));
    }
    public void assignWorkToIdleExecutors(){
        executorRepository.findAll().stream().filter(executor -> executor.getWorkBlocks().size() == 0)
                .forEach(executor -> assignWorkToExecutor(executor));
    }
}
