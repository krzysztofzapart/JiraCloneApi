package pl.kzapart.JiraClone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import pl.kzapart.JiraClone.config.SwaggerConfiguration;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class JiraCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(JiraCloneApplication.class, args);
	}

}
