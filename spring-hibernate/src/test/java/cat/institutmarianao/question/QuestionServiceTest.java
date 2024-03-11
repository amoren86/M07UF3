package cat.institutmarianao.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import cat.institutmarianao.ServicesTestConfig;
import cat.institutmarianao.user.User;
import cat.institutmarianao.user.UserDao;
import cat.institutmarianao.utils.Mock;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ServicesTestConfig.class })
public class QuestionServiceTest {
	@Autowired
	private UserDao userDAOMock;

	@Autowired
	private QuestionService questionService;

	@Test
	public void getAllQuestionsOk() {
		/* Setup */
		Question question0 = Mock.getQuestion0();
		question0.setQuestionId(1);

		Question question1 = Mock.getQuestion1();
		question1.setQuestionId(2);

		Integer userId = 1;
		User user = Mock.getUser0();
		user.setUserId(userId);
		Set<Question> questions = new HashSet<>();
		questions.add(question0);
		questions.add(question1);
		user.setQuestions(questions);

		when(userDAOMock.getById(userId)).thenReturn(user);

		/* Test */
		Set<Question> questionsFromDb = questionService.getAllQuestions(userId);

		/* Verifiation */
		verify(userDAOMock, timeout(1)).getById(userId);
		assertEquals(2, questionsFromDb.size());
		assertTrue(questionsFromDb.contains(question0));
		assertTrue(questionsFromDb.contains(question1));
		verify(userDAOMock, times(1)).getById(userId);
	}
}