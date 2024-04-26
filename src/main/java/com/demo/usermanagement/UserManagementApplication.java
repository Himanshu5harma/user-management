package com.demo.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The UserManagementApplication class represents the entry point of the application.
 */
@SpringBootApplication
public class UserManagementApplication {

	/**
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}

}
