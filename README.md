# Monitoring Example for Camunda Platform Run Version 7.18.4

## Camunda Run
Before running the example, download Camunda Platform Run at: https://camunda.com/de/download/
Look at the utilized Spring Boot Version under internal/webapps and align the following dependencies.

For Camunda Platform Run 7.18.4, Spring Boot 2.7.6 is used. 
The following dependencies are needed at configuration/userlib for Prometheus integration:

- Micrometer-Core-1.9.6.jar
- Microemter-Registry-Prometheus-1.9.6.jar
- Simpleclient_common-0.15.0.jar
- Simpleclient_tracer_otel_agent-0.15.0.jar
- Simpleclient_tracer_otel-0.15.0.jar
- Simpleclient-0.15.0.jar

Append the following lines to Camunda Platform Run configuration:

```yaml
management.endpoints.web.exposure.include: health, prometheus
management.endpoint.health.show-details: always
```

Check Actuator endpoints under:
- localhost:8080/actuator
- localhost:8080/actuator/health
- localhost:8080/actuator/prometheus

## Prometheus & Grafana
Run docker-compose.yml in Monitoring.

## Prometheus
Ensure that localhost:9090 is reachable

## Grafana
Log in on localhost:3000 with admin/admin.
Create a datasource to poll data from Prometheus under Settings -> Datasource:

```yaml
URL: http://host.docker.internal:9090
```
You can import Dashboards from the folder, but be aware that you need to replace datasource UID to your created Prometheus UID.

## Plugin
Extend and build plugin for advanced Camunda specific metrics.
Place plugin with dependencies also to userlib folder and restart application.
