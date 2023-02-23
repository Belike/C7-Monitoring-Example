package org.camunda.bpm.run.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.camunda.bpm.engine.ExternalTaskService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfiguration {

    @Autowired
    MeterRegistry registry;

    @Autowired
    ManagementService managementService;

    @Autowired
    ExternalTaskService externalTaskService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    HistoryService historyService;

    @Bean
    public Gauge getActiveIncidents(){
        Query query = runtimeService.createIncidentQuery();

        return Gauge.builder("incidents.count", query::count)
                .description("Incidents that are active")
                .register(registry);
    }

    @Bean
    public Gauge getActiveProcessInstances(){
        Query query = runtimeService.createProcessInstanceQuery().active();

        return Gauge.builder("processinstances.count", query::count)
                .description("Process Instances that are active")
                .register(registry);
    }

    @Bean
    public Gauge getCompletedProcessInstances(){
        Query query = historyService.createHistoricProcessInstanceQuery().completed();

        return Gauge.builder("processinstances.completed", query::count)
                .description("Process Instnaces that have been completed")
                .register(registry);
    }

    @Bean
    public Gauge getTimer(){
        Query query = managementService.createJobQuery().timers();

        return Gauge.builder("timer.count", query::count)
                .description("Timers that are active")
                .register(registry);
    }

    @Bean
    public Gauge getExecutableTimer(){
        Query query = managementService.createJobQuery().timers().executable();

        return Gauge.builder("timer.executable", query::count)
                .description("Timers that are active and executable")
                .register(registry);
    }

    @Bean
    public Gauge getActiveExternalTasks() {
        Query query = externalTaskService.createExternalTaskQuery().active();

        return Gauge.builder("externaltask.active", query::count)
                .description("ExternalTasks that are active")
                .register(registry);
    }

    @Bean
    public Gauge getActiveLockedExternalTasks() {
        Query query = externalTaskService.createExternalTaskQuery().active().locked();

        return Gauge.builder("externaltask.locked", query::count)
                .description("ExternalTasks that are locked")
                .register(registry);
    }

    @Bean
    public Gauge getActiveNotLockedExternalTasks() {
        Query query = externalTaskService.createExternalTaskQuery().active().notLocked();

        return Gauge.builder("externaltask.notlocked", query::count)
                .description("ExternalTasks that are not locked")
                .register(registry);
    }

    @Bean
    public Gauge getNoRetriesLeftExternalTasks() {
        Query query = externalTaskService.createExternalTaskQuery().noRetriesLeft();

        return Gauge.builder("externaltask.noretries", query::count)
                .description("ExternalTasks that have no retries left")
                .register(registry);
    }

    @Bean
    public Gauge getHighPriorityExternalTasks() {
        Query query = externalTaskService.createExternalTaskQuery().priorityHigherThanOrEquals(1);

        return Gauge.builder("externaltask.highpriority", query::count)
                .description("ExternalTasks with high priority")
                .register(registry);
    }
}
