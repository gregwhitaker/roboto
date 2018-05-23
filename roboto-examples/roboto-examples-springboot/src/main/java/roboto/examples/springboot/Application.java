package roboto.examples.springboot;

import com.github.gregwhitaker.roboto.spring.annotation.EnableRobots;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRobots
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}
