package cat.institutmarianao.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cat.institutmarianao.spring.repository.AnswerRepository;
import cat.institutmarianao.spring.repository.QuestionRepository;
import cat.institutmarianao.spring.repository.UserRepository;
import cat.institutmarianao.spring.repository.VoteRepository;
import cat.institutmarianao.spring.service.QuestionService;
import cat.institutmarianao.spring.service.UserService;
import cat.institutmarianao.spring.service.VoteService;
import cat.institutmarianao.spring.service.impl.QuestionServiceImpl;
import cat.institutmarianao.spring.service.impl.UserServiceImpl;
import cat.institutmarianao.spring.service.impl.VoteServiceImpl;

/**
 * Services beans
 */
@Configuration
public class ServicesConfig {
	@Bean
	public UserService userService(UserRepository userRepository) {
		return new UserServiceImpl(userRepository);
	}

	@Bean
	public QuestionService questionService(UserRepository userRepository, QuestionRepository questionRepository) {
		return new QuestionServiceImpl(userRepository, questionRepository);
	}

	@Bean
	public VoteService voteService(VoteRepository voteRepository, AnswerRepository answerRepository,
			UserRepository userRepository) {
		return new VoteServiceImpl(voteRepository, answerRepository, userRepository);
	}
}
