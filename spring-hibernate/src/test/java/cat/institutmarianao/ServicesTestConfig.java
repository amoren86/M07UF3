package cat.institutmarianao;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import cat.institutmarianao.answer.AnswerDao;
import cat.institutmarianao.question.QuestionDao;
import cat.institutmarianao.user.UserDao;
import cat.institutmarianao.vote.VoteDao;

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
	public UserDao userDao() {
		return Mockito.mock(UserDao.class);
	}

	@Bean
	public QuestionDao questionDao() {
		return Mockito.mock(QuestionDao.class);
	}

	@Bean
	public AnswerDao answerDao() {
		return Mockito.mock(AnswerDao.class);
	}

	@Bean
	public VoteDao voteDao() {
		return Mockito.mock(VoteDao.class);
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return Mockito.mock(PlatformTransactionManager.class);
	}

}