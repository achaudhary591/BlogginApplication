package com.akshay.blog;

import com.akshay.blog.config.AppConstants;
import com.akshay.blog.entities.Role;
import com.akshay.blog.reporsitories.RoleRepository;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BloggingBackendApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BloggingBackendApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	/**
	 * Callback used to run the bean.
	 *
	 * @param args incoming main method arguments
	 * @throws Exception on error
	 */
	@Override
	public void run(String... args) throws Exception {
		try {
			Role role = new Role();
			role.setRoleId(AppConstants.ADMIN_USER);
			role.setRole("ROLE_ADMIN");

			Role role1 = new Role();
			role1.setRoleId(AppConstants.NORMAL_USER);
			role1.setRole("ROLE_USER");

			List<Role> savedRoles = this.roleRepository.saveAll(List.of(role, role1));
			savedRoles.forEach(role2 -> {
				System.out.println(role2.getRole());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
