package com.akshay.blog;

import com.akshay.blog.reporsitories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BloggingBackendApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	public void repoTest(){
		String className = this.userRepository.getClass().getName();
		String packName = this.userRepository.getClass().getPackageName();

		System.out.println(className);
		System.out.println(packName);
	}

}
