package com.joaquin.batch.springbatch.steps;

import com.joaquin.batch.springbatch.entities.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ItemProcessorStep implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        log.info("-------> Inicio del paso de PROCESAMIENTO <-----------");

        List<Person> personList = (List<Person>) chunkContext
                .getStepContext()
                .getStepExecution()
                .getExecutionContext()
                .get("personList");

        List<Person> personListFinal = personList.stream().map(person -> {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            person.setInsertionDate(format.format(LocalDate.now()));
            return person;
        }).toList();

        chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext()
                .put("personList", personListFinal);

        log.info("-------> Fin del paso de PROCESAMIENTO <-----------");

        return RepeatStatus.FINISHED;
    }
}
