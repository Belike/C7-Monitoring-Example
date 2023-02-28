package com.camunda.consulting.externalTasks;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@ExternalTaskSubscription("Sleeper")
@Slf4j
public class SleepingHandler implements ExternalTaskHandler {

    @SneakyThrows
    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        int sleepingTime = ThreadLocalRandom.current().nextInt(1, 101);
        log.info("Sleeping for {}", sleepingTime);
        Thread.sleep(sleepingTime * 1000);

        int failure = ThreadLocalRandom.current().nextInt(1,11);
        if(failure > 8){
            externalTaskService.handleFailure(externalTask, "Failure triggered", "Failure triggered", 0, 0);
        }else{
            externalTaskService.complete(externalTask);
        }
    }
}
