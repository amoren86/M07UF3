package cat.institutmarianao.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cat.institutmarianao.spring.repository.UserRepository;
import cat.institutmarianao.spring.repository.impl.UserHibernateRepositoryImpl;

/**
 * Repository beans, using Hibernate implementation
 */
@Configuration
public class RepositoryConfig {
	@Bean
	public UserRepository userRepository() {
		return new UserHibernateRepositoryImpl();
	}
}
