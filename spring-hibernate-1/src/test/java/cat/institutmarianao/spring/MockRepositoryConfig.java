package cat.institutmarianao.spring;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cat.institutmarianao.spring.repository.UserRepository;

/**
 * Repository beans, using Mockito mocks
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