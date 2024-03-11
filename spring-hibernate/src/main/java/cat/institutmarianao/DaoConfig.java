package cat.institutmarianao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cat.institutmarianao.answer.AnswerDao;
import cat.institutmarianao.answer.AnswerHibernateDaoImpl;
import cat.institutmarianao.question.QuestionDao;
import cat.institutmarianao.question.QuestionHibernateDaoImpl;
import cat.institutmarianao.user.UserDao;
import cat.institutmarianao.user.UserHibernateDaoImpl;
import cat.institutmarianao.vote.VoteDao;
import cat.institutmarianao.vote.VoteHibernateDaoImpl;

/**
 * DAO configuration, using Hibernate DAOs
 */
@Configuration
public class DaoConfig {
	@Bean
	public AnswerDao answerDao() {
		return new AnswerHibernateDaoImpl();
	}

	@Bean
	public UserDao userDao() {
		return new UserHibernateDaoImpl();
	}

	@Bean
	public QuestionDao questionDao() {
		return new QuestionHibernateDaoImpl();
	}

	@Bean
	public VoteDao voteDao() {
		return new VoteHibernateDaoImpl();
	}
}
