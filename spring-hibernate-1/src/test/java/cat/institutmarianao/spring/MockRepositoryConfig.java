package cat.institutmarianao.spring;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cat.institutmarianao.spring.repository.UserRepository;

/**
 * Provides Mock Repository classes to the Service Tests
 *
 * @author Toni
 *
 */
@Configuration
public class MockRepositoryConfig {
	@Bean
	public UserRepository userRepository() {
		return Mockito.mock(UserRepository.class);
	}
}