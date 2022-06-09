package ua.martishyn.app;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import ua.martishyn.app.repositories.RouteRepository;

@SpringBootApplication
@ComponentScan(basePackages = "ua.martishyn.app")
public class TrainReservationApplication {

	@Bean
	ModelMapper modelMapper(){
		return new ModelMapper();
	}



	public static void main(String[] args) {
		SpringApplication.run(TrainReservationApplication.class, args);
	}
}
