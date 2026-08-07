package be.charleshornick.supra.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "be.charleshornick.supra")
public class Application {

    static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
