package cat.institutmarianao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cat.institutmarianao.answer.AnswerDAO;
import cat.institutmarianao.question.QuestionDAO;
import cat.institutmarianao.question.QuestionService;
import cat.institutmarianao.question.QuestionServiceImpl;
import cat.institutmarianao.user.UserDAO;
import cat.institutmarianao.user.UserServiceImpl;
import cat.institutmarianao.vote.VoteDAO;
import cat.institutmarianao.vote.VoteService;
import cat.institutmarianao.vote.VoteServiceImpl;

/**
 * Services configuration, that return implementing instances
 */
@Configuration
public class ServicesConfig {
	@Bean
	public QuestionService questionService(UserDAO userDAO, QuestionDAO questionDAO) {
		return new QuestionServiceImpl(userDAO, questionDAO);
	}

	@Bean
	public UserServiceImpl userService(UserDAO userDAO) {
		return new UserServiceImpl(userDAO);
	}

	@Bean
	public VoteService voteService(VoteDAO voteDAO, AnswerDAO answerDAO, UserDAO userDAO) {
		return new VoteServiceImpl(voteDAO, answerDAO, userDAO);
	}
}
