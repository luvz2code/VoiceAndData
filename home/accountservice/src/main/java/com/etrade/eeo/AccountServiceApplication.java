package com.etrade.eeo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Created by rayyar on 3/9/18.
 */
@SpringBootApplication
@EnableJpaAuditing
public class AccountServiceApplication {
    public static void main(String[] args) {
            SpringApplication.run(AccountServiceApplication.class, args);
        }
}
