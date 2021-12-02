package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.bean.MyBean;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.component.ComponentDependency;
import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.pojo.UserPojo;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import com.fundamentos.springboot.fundamentos.service.UserService;
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
	private UserService userService;

	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean, MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties myBeanWithProperties, UserPojo userPojo, UserRepository userRepository, UserService userService) {
		this.componentDependency = componentDependency;
        this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;
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
		saveWhithErrorTransactional();
	}

	private void saveWhithErrorTransactional() {
		User test1 = new User("TestTransactional1", "TestTransactional1@domain.com", LocalDate.now());
		User test2 = new User("TestTransactional2", "TestTransactional2@domain.com", LocalDate.now());
		User test3 = new User("TestTransactional3", "TestTransactional3@domain.com", LocalDate.now());
		User test4 = new User("TestTransactional4", "TestTransactional4@domain.com", LocalDate.now());

		List<User> users = Arrays.asList(test1, test2, test3,  test4);
		try {
			userService.saveTransactional(users);
		} catch (Exception e) {
			LOGGER.info("Esta es un excepcion dentro del metodo transaccinal " + e);
		}
		userService.getAllUsers()
				.stream()
				.forEach(user -> LOGGER.info("Usuario dentro del metodo transacional: " + user));
	}

	private void getInformationJpqlFromUser() {
		/*LOGGER.info("Get User By Email: " + userRepository.findByUserEmail("juan@me.com").orElseThrow(() -> new RuntimeException("No se encotro usuario")));

		// ascending
		userRepository.findAndSort("al", Sort.by("id").descending())
				.stream()
				.forEach(user -> LOGGER.info("Usuario sort: " + user));

		userRepository.findByName("Oscar")
				.stream()
				.forEach(user -> LOGGER.info("Usuario con query methon: " + user));

		LOGGER.info("Usrt by email and name: " + userRepository.findByEmailAndName("Test2@domain.com", "Daniela")
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado")));

		userRepository.findByNameLike("%Test%")
				.stream()
				.forEach(user -> LOGGER.info("User findByNameLike: " + user));

		userRepository.findByNameOrEmail(null, "Test5@domain.com")
				.stream()
				.forEach(user -> LOGGER.info("User findByNameOrEmail: " + user));*/

		/*userRepository.findByBirthDateBetween(LocalDate.of(2021, 03, 01), LocalDate.of(2022, 12, 01))
				.stream()
				.forEach(user -> LOGGER.info("Usuer by date: " + user));*/

		/*userRepository.findByNameLikeOrderByIdDesc("%Test%")
				.stream()
				.forEach(user -> LOGGER.info("Order Des: " + user));*/

		userRepository.findByNameContainingOrderByIdDesc("Test")
				.stream()
				.forEach(user -> LOGGER.info("Order Des: " + user));

		LOGGER.info("User a partir de named parameter: " + userRepository.getAllBirthDateAndEmail(LocalDate.of(2022, 06, 05), "juan@me.com")
				.orElseThrow(() -> new RuntimeException("No see encontro usuario a partir del named parameter")));
	}

	private void saveUserInDataBase() {
		User user1 = new User("Alex", "alex@me.com", LocalDate.of(2021, 06, 05));
		User user2 = new User("Juan", "juan@me.com", LocalDate.of(2022, 06, 05));
		User user4 = new User("Oscar", "oscar@domain.com", LocalDate.now());
		User user5 = new User("Test1", "Test1@domain.com", LocalDate.now());
		User user6 = new User("Daniela", "Test2@domain.com", LocalDate.now());
		User user7 = new User("Test3", "Test3@domain.com", LocalDate.now());
		User user8 = new User("Test4", "Test4@domain.com", LocalDate.now());
		User user9 = new User("Test5", "Test5@domain.com", LocalDate.now());
		User user10 = new User("Test6", "Test6@domain.com", LocalDate.now());
		User user11 = new User("Test7", "Test7@domain.com", LocalDate.now());
		User user12 = new User("Test8", "Test8@domain.com", LocalDate.now());
		User user13 = new User("Test9", "Test9@domain.com", LocalDate.now());

		List<User> list = Arrays.asList(user1, user2, user4, user5, user6, user7, user8, user9, user10, user11, user12, user13);
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
