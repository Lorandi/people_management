package com.lorandi.peoplemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@EnableScheduling
@SpringBootApplication
public class PeopleManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeopleManagementApplication.class, args);
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
	}

}
