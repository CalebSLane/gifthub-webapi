package ca.csl.gifthub.web.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
@ComponentScan(basePackages = { "ca.csl.gifthub.web.api", "ca.csl.gifthub.core" })
@EnableJpaRepositories({ "ca.csl.gifthub.web.api", "ca.csl.gifthub.core" })
@EntityScan({ "ca.csl.gifthub.web.api", "ca.csl.gifthub.core" })
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        context.getBean(DispatcherServlet.class).setThrowExceptionIfNoHandlerFound(true);
    }
}
