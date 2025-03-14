package cat.institutmarianao.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cat.institutmarianao.spring.repository.UserRepository;
import cat.institutmarianao.spring.service.UserService;
import cat.institutmarianao.spring.service.impl.UserServiceImpl;

/**
 * Services beans
 */
@Configuration
public class ServicesConfig {
	@Bean
	public UserService userService(UserRepository userRepository) {
		return new UserServiceImpl(userRepository);
	}
}
