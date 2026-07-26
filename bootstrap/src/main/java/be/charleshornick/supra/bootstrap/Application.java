package be.charleshornick.supra.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "be.charleshornick.supra")
public class Application {

    void main() {
        SpringApplication.run(Application.class);
    }
}
