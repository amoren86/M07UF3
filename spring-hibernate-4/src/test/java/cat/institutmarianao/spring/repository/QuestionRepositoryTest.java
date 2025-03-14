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
import cat.institutmarianao.spring.model.Question;
import cat.institutmarianao.spring.model.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RepositoryConfig.class, HibernateConfig.class, H2DataSourceConfig.class })
public class QuestionRepositoryTest {
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void saveOk() {
		/* Setup */
		Timestamp currentTime = new Timestamp(new Date().getTime());

		String username = "john";
		User user = createUser(null, username);
		user.setCreatedOn(currentTime);

		userRepository.create(user);

		Question question = new Question();
		question.setText("This is a question");
		question.setUser(user);

		/* Previous verification - question has no id */
		assertNull(question.getId());

		/* Test */
		questionRepository.save(question);

		/* Verification */
		assertNotNull(question.getId());

		Question questionFromDb = questionRepository.getById(question.getId());

		assertEquals(question.getId(), questionFromDb.getId());
		assertEquals(username, questionFromDb.getUser().getUsername());
		assertEquals(currentTime, questionFromDb.getUser().getCreatedOn());
	}
}