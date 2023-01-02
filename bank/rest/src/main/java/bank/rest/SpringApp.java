package bank.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Springboot app.
 */
@SpringBootApplication
public class SpringApp {

  public static void main(String[] args) {
    SpringApplication.run(SpringApp.class, args);
  }

  @Bean
  protected String path() {
    return "../../bank.json";
  }

}
