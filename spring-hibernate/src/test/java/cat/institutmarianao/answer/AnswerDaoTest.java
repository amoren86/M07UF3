package cat.institutmarianao.answer;

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
import cat.institutmarianao.question.Question;
import cat.institutmarianao.question.QuestionDao;
import cat.institutmarianao.user.User;
import cat.institutmarianao.user.UserDao;
import cat.institutmarianao.utils.Mock;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ServicesConfig.class, DaoTestConfig.class })
public class AnswerDaoTest {
	@Autowired
	private AnswerDao answerDao;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private UserDao userDao;

	@Test
	public void saveOk() {
		/* Setup */
		Timestamp currentTime = new Timestamp(new Date().getTime());

		/* userQ, which did the question */
		User userQ = Mock.getUser(null, "jack");
		userQ.setCreatedOn(currentTime);
		userDao.save(userQ);

		Question question = new Question();
		question.setText("This is a question");
		question.setUser(userQ);
		questionDao.save(question);

		/* userA, which does the answer related to the question before */
		String username = "wilson";

		User userA = Mock.getUser(null, username);
		userA.setCreatedOn(currentTime);
		userDao.save(userA);

		Answer answer = new Answer();
		answer.setText("This is an answer");
		answer.setUser(userA);

		/* Previous verification - answer has no id */
		assertNull(answer.getAnswerId());

		/* Test */
		answerDao.save(answer);

		/* Verification */
		assertNotNull(answer.getAnswerId());

		Answer answerFromDb = answerDao.getById(answer.getAnswerId());

		assertEquals(answer.getAnswerId(), answerFromDb.getAnswerId());
		assertEquals(username, answerFromDb.getUser().getUsername());
		assertEquals(currentTime, answerFromDb.getUser().getCreatedOn());
	}
}