package cat.institutmarianao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cat.institutmarianao.answer.AnswerDAO;
import cat.institutmarianao.answer.AnswerHibernateDAOImpl;
import cat.institutmarianao.question.QuestionDAO;
import cat.institutmarianao.question.QuestionHibernateDAOImpl;
import cat.institutmarianao.user.UserDAO;
import cat.institutmarianao.user.UserHibernateDAOImpl;
import cat.institutmarianao.vote.VoteDAO;
import cat.institutmarianao.vote.VoteHibernateDAOImpl;

/**
 * DAO configuration, using Hibernate DAOs
 */
@Configuration
public class DAOConfig {
	@Bean
	public AnswerDAO answerDAO() {
		return new AnswerHibernateDAOImpl();
	}

	@Bean
	public UserDAO userDAO() {
		return new UserHibernateDAOImpl();
	}

	@Bean
	public QuestionDAO questionDAO() {
		return new QuestionHibernateDAOImpl();
	}

	@Bean
	public VoteDAO voteDAO() {
		return new VoteHibernateDAOImpl();
	}
}
