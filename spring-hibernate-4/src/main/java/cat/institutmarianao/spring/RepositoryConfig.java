package cat.institutmarianao.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cat.institutmarianao.spring.repository.AnswerRepository;
import cat.institutmarianao.spring.repository.QuestionRepository;
import cat.institutmarianao.spring.repository.UserRepository;
import cat.institutmarianao.spring.repository.VoteRepository;
import cat.institutmarianao.spring.repository.impl.AnswerHibernateRepositoryImpl;
import cat.institutmarianao.spring.repository.impl.QuestionHibernateRepositoryImpl;
import cat.institutmarianao.spring.repository.impl.UserHibernateRepositoryImpl;
import cat.institutmarianao.spring.repository.impl.VoteHibernateRepositoryImpl;

/**
 * Repository beans, using Hibernate implementation
 */
@Configuration
public class RepositoryConfig {
	@Bean
	public UserRepository userRepository() {
		return new UserHibernateRepositoryImpl();
	}

	@Bean
	public QuestionRepository questionRepository() {
		return new QuestionHibernateRepositoryImpl();
	}

	@Bean
	public AnswerRepository answerRepository() {
		return new AnswerHibernateRepositoryImpl();
	}

	@Bean
	public VoteRepository voteRepository() {
		return new VoteHibernateRepositoryImpl();
	}
}
