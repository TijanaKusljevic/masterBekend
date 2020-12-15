package etf.master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"etf.master"})
public class RecenzijeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecenzijeApplication.class, args);
	}

}