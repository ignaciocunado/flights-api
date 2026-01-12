package server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"commons", "server"})
public class FlightsApiApplication {

	/**
	 * Main entry point for the application.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(FlightsApiApplication.class, args);
	}

}
