package pl.ioki.releasemonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan
@EnableAutoConfiguration
@EnableAsync
@EnableScheduling
@PropertySource("classpath:/application.properties")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
