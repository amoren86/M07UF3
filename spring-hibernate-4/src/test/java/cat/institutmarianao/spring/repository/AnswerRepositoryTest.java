package cat.institutmarianao.spring.repository;

import static cat.institutmarianao.utils.RepositoryMock.createUser;
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

import cat.institutmarianao.spring.H2DataSourceConfig;
import cat.institutmarianao.spring.HibernateConfig;
import cat.institutmarianao.spring.RepositoryConfig;
import cat.institutmarianao.spring.model.Answer;
import cat.institutmarianao.spring.model.Question;
import cat.institutmarianao.spring.model.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RepositoryConfig.class, HibernateConfig.class, H2DataSourceConfig.class })
public class AnswerRepositoryTest {
	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void saveOk() {
		/* Setup */
		Timestamp currentTime = new Timestamp(new Date().getTime());

		/* userQ, which did the question */
		User userQ = createUser(null, "jack");
		userQ.setCreatedOn(currentTime);
		userRepository.create(userQ);

		Question question = new Question();
		question.setText("This is a question");
		question.setUser(userQ);
		questionRepository.save(question);

		/* userA, which does the answer related to the question before */
		String username = "wilson";

		User userA = createUser(null, username);
		userA.setCreatedOn(currentTime);
		userRepository.create(userA);

		Answer answer = new Answer();
		answer.setText("This is an answer");
		answer.setUser(userA);

		/* Previous verification - answer has no id */
		assertNull(answer.getId());

		/* Test */
		answerRepository.save(answer);

		/* Verification */
		assertNotNull(answer.getId());

		Answer answerFromDb = answerRepository.getById(answer.getId());

		assertEquals(answer.getId(), answerFromDb.getId());
		assertEquals(username, answerFromDb.getUser().getUsername());
		assertEquals(currentTime, answerFromDb.getUser().getCreatedOn());
	}
}