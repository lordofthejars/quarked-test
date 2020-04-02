package org.acme;

import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MariaDBContainer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class InfrastructureTestResource implements QuarkusTestResourceLifecycleManager {

    private MariaDBContainer<?> mariaDbContainer;
    private KafkaContainer kafkaContainer;

    @Override
    public Map<String, String> start() {

        this.mariaDbContainer = new MariaDBContainer<>("mariadb:10.4.4");
        this.mariaDbContainer.start();

        this.kafkaContainer = new KafkaContainer();
        this.kafkaContainer.start();

        return configurationParameters();
    }

    private Map<String, String> configurationParameters() {
        final Map<String, String> conf = new HashMap<>();

        conf.put("quarkus.datasource.url", this.mariaDbContainer.getJdbcUrl());
        conf.put("quarkus.datasource.username", this.mariaDbContainer.getUsername());
        conf.put("quarkus.datasource.password", this.mariaDbContainer.getPassword());
        // conf.put("quarkus.datasource.driver", "org.mariadb.jdbc.Driver");
        // conf.put("quarkus.hibernate-orm.dialect", "MariaDBDialect");

        conf.put("mp.messaging.outgoing.delivery.bootstrap.servers",  kafkaContainer.getContainerIpAddress() + ":" + kafkaContainer.getMappedPort(KafkaContainer.KAFKA_PORT));
        return conf;
    }

    @Override
    public void stop() {
        if (this.mariaDbContainer != null) {
            this.mariaDbContainer.stop();
        }
    }

}