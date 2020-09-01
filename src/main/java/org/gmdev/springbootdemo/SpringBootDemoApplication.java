package org.gmdev.springbootdemo;

import org.gmdev.springbootdemo.configuration.beans.Alien;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class SpringBootDemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootDemoApplication.class, args);

		// Beans test
		Alien alien1= context.getBean(Alien.class);
		alien1.getLaptop().compile();

		Alien alien2= context.getBean(Alien.class);
		alien2.getLaptop().compile();
	}

	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

}
