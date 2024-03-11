package cat.institutmarianao.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import cat.institutmarianao.DaoTestConfig;
import cat.institutmarianao.ServicesConfig;
import cat.institutmarianao.user.User;
import cat.institutmarianao.user.UserDao;
import cat.institutmarianao.utils.Mock;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ServicesConfig.class, DaoTestConfig.class })
public class QuestionDaoTest {
	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private UserDao userDao;

	@Test
	public void saveOk() {
		/* Setup */
		Timestamp currentTime = new Timestamp(new Date().getTime());

		String username = "john";
		User user = Mock.getUser(null, username);
		user.setCreatedOn(currentTime);

		userDao.save(user);

		Question question = new Question();
		question.setText("This is a question");
		question.setUser(user);

		/* Previous verification - question has no id */
		assertNull(question.getQuestionId());

		/* Test */
		questionDao.save(question);

		/* Verification */
		assertNotNull(question.getQuestionId());

		Question questionFromDb = questionDao.getById(question.getQuestionId());

		assertEquals(question.getQuestionId(), questionFromDb.getQuestionId());
		assertEquals(username, questionFromDb.getUser().getUsername());
		assertEquals(currentTime, questionFromDb.getUser().getCreatedOn());
	}
}