package org.gmdev.setup;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class PostgresSetUp {

    @Container
    private static final PostgreSQLContainer<?> database =
            new PostgreSQLContainer<>("postgres:14.2")
                    .withInitScript("setup-postgresql.sql")
                    .withUsername("docker")
                    .withPassword("12345")
                    .withExposedPorts(5432)
                    .withReuse(true);

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.test.database.replace=", () -> "none");
        registry.add("spring.datasource.url=", database::getJdbcUrl);
        registry.add("spring.datasource.username=", database::getUsername);
        registry.add("spring.datasource.password=", database::getPassword);
    }

}
