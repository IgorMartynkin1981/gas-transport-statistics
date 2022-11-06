package ru.alrosa.transport.gastransportstatistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.alrosa.transport.gastransportstatistics.users.services.UserService;
import ru.alrosa.transport.gastransportstatistics.users.services.UserServiceImpl;

@SpringBootApplication
public class GastransportstatisticsApplication {


	public static void main(String[] args) {
		SpringApplication.run(GastransportstatisticsApplication.class, args);
	}

}
