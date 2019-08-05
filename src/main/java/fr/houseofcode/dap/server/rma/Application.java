package fr.houseofcode.dap.server.rma;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author rma
 * 9 juil. 2019
 */

@SpringBootApplication
public class Application {

    /**
     * main that call Spring.
     * Objective is to run the server
     * @param args accept arguments of method main.
     * @since 1.0
     */
    public static void main(final String[] args) {
        SpringApplication app = new SpringApplication(Application.class);

        app.run(args);

    }

    /**
     * Bean from Spring boot.
     * @param ctx from Spring
     * @return an object from Spring
     */
    @Bean
    public CommandLineRunner commandLineRunner(final ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans +"
                    + "provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

}
