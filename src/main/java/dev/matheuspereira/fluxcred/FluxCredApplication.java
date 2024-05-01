package dev.matheuspereira.fluxcred;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableJpaAuditing
@ComponentScan(basePackages = "dev.matheuspereira.fluxcred")
@EnableJpaRepositories(basePackages = "dev.matheuspereira.fluxcred.infrastructure.jpa")
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class FluxCredApplication {

  public static void main(String[] args) {
    SpringApplication.run(FluxCredApplication.class, args);
  }

}
