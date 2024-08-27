package net.gafah.auth_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServerApplication  {

//	@Autowired
//	private BCryptPasswordEncoder encoder;
	
	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		System.out.println("Password: " + encoder.encode("secret"));
//		
//	}

}
