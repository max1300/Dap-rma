package fr.houseofcode.dap.server.rma;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
     * Instance of Logger.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Main that call Spring.
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
    //TODO RMA by Djer |Spring| Cette méthode est utile au début pour vérifier que Spring est bien configuré (affiche le contenu du conteneur IOC). Tu peux la supprimer.
    @Bean
    public CommandLineRunner commandLineRunner(final ApplicationContext ctx) {
        return args -> {
            LOG.info("Let's inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);

            for (String beanName : beanNames) {
                LOG.info(beanName);
            }
        };
    }

}
