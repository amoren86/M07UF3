package cat.institutmarianao.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import cat.institutmarianao.answer.AnswerDAO;
import cat.institutmarianao.question.QuestionDAO;
import cat.institutmarianao.user.UserDAO;
import cat.institutmarianao.vote.VoteDAO;

/**
 * Provides Mock DAO classes to the Service Tests
 *
 * @author Toni
 *
 */
@Configuration
@Import(value = { ServicesConfig.class })
public class ServicesTestConfig {
	@Bean
	public UserDAO userDAO() {
		return Mockito.mock(UserDAO.class);
	}

	@Bean
	public QuestionDAO questionDAO() {
		return Mockito.mock(QuestionDAO.class);
	}

	@Bean
	public AnswerDAO answerDAO() {
		return Mockito.mock(AnswerDAO.class);
	}

	@Bean
	public VoteDAO voteDAO() {
		return Mockito.mock(VoteDAO.class);
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return Mockito.mock(PlatformTransactionManager.class);
	}

}