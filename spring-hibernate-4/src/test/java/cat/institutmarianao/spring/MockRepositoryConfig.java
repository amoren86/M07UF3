package cat.institutmarianao.spring;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cat.institutmarianao.spring.repository.AnswerRepository;
import cat.institutmarianao.spring.repository.QuestionRepository;
import cat.institutmarianao.spring.repository.UserRepository;
import cat.institutmarianao.spring.repository.VoteRepository;

/**
 * Repository beans, using Mockito mocks
 */
@Configuration
public class MockRepositoryConfig {
	@Bean
	public UserRepository userRepository() {
		return Mockito.mock(UserRepository.class);
	}

	@Bean
	public QuestionRepository questionRepository() {
		return Mockito.mock(QuestionRepository.class);
	}

	@Bean
	public AnswerRepository answerRepository() {
		return Mockito.mock(AnswerRepository.class);
	}

	@Bean
	public VoteRepository voteRepository() {
		return Mockito.mock(VoteRepository.class);
	}
}