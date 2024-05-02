package dev.matheuspereira.fluxcred.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@ComponentScan(basePackages = "dev.matheuspereira.fluxcred.core")
@EnableJpaRepositories(basePackages = "dev.matheuspereira.fluxcred.core.infrastructure.jpa")
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class FluxCredApplication {

  public static void main(String[] args) {
    SpringApplication.run(FluxCredApplication.class, args);
  }

}
