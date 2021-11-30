package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.bean.MyBean;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.component.ComponentDependency;
import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.pojo.UserPojo;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);

//	Dependencia para inyectar
	private ComponentDependency componentDependency;
    private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;

	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean, MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties myBeanWithProperties, UserPojo userPojo, UserRepository userRepository) {
		this.componentDependency = componentDependency;
        this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	// throws Exception
	public void run(String... args) {
//		ejemplosAnteriones();
		saveUserInDataBase();
		getInformationJpqlFromUser();
	}

	private void getInformationJpqlFromUser() {
		LOGGER.info("Get User By Email: " + userRepository.findByUserEmail("juan@me.com").orElseThrow(() -> new RuntimeException("No se encotro usuario")));

		// ascending
		userRepository.findAndSort("al", Sort.by("id").descending())
				.stream()
				.forEach(user -> LOGGER.info("Usuario sort: " + user));
	}

	private void saveUserInDataBase() {
		User user1 = new User("Alex", "alex@me.com", LocalDate.of(2021, 06, 05));
		User user2 = new User("Juan", "juan@me.com", LocalDate.of(2022, 06, 05));
		User user4 = new User("Oscar", "oscar@domain.com", LocalDate.now());
		User user5 = new User("Test1", "Test1@domain.com", LocalDate.now());
		User user6 = new User("Test2", "Test2@domain.com", LocalDate.now());
		User user7 = new User("Test3", "Test3@domain.com", LocalDate.now());
		User user8 = new User("Test4", "Test4@domain.com", LocalDate.now());
		User user9 = new User("Test5", "Test5@domain.com", LocalDate.now());
		User user10 = new User("Test6", "Test6@domain.com", LocalDate.now());
		User user11 = new User("Test7", "Test7@domain.com", LocalDate.now());
		User user12 = new User("Test8", "Test8@domain.com", LocalDate.now());
		User user13 = new User("Test9", "Test9@domain.com", LocalDate.now());

		List<User> list = Arrays.asList(user1, user2);
//		se registra en base de dats
		list.stream().forEach(userRepository::save);
	}

	private void ejemplosAnteriones() {
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependendy();
		System.out.println( myBeanWithProperties.function() );
		System.out.println(userPojo.getEmail() + " - " + userPojo.getPassword());
		try {
			int division = 10 / 0;
			LOGGER.debug("Mi valor es: " + division);
		} catch (Exception e) {
			LOGGER.error("ESTO ES UN ERROR DEL APLICATIVO: " + e.getMessage());
		}
	}
}
