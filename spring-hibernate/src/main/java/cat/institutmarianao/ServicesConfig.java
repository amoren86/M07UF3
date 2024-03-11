package cat.institutmarianao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cat.institutmarianao.answer.AnswerDao;
import cat.institutmarianao.question.QuestionDao;
import cat.institutmarianao.question.QuestionService;
import cat.institutmarianao.question.QuestionServiceImpl;
import cat.institutmarianao.user.UserDao;
import cat.institutmarianao.user.UserServiceImpl;
import cat.institutmarianao.vote.VoteDao;
import cat.institutmarianao.vote.VoteService;
import cat.institutmarianao.vote.VoteServiceImpl;

/**
 * Services configuration, that return implementing instances
 */
@Configuration
public class ServicesConfig {
	@Bean
	public QuestionService questionService(UserDao userDao, QuestionDao questionDao) {
		return new QuestionServiceImpl(userDao, questionDao);
	}

	@Bean
	public UserServiceImpl userService(UserDao userDao) {
		return new UserServiceImpl(userDao);
	}

	@Bean
	public VoteService voteService(VoteDao voteDao, AnswerDao answerDao, UserDao userDao) {
		return new VoteServiceImpl(voteDao, answerDao, userDao);
	}
}
