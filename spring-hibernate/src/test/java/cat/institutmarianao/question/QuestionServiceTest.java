package cat.institutmarianao.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cat.institutmarianao.config.ServicesTestConfig;
import cat.institutmarianao.user.User;
import cat.institutmarianao.user.UserDAO;
import cat.institutmarianao.utils.Mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServicesTestConfig.class })
public class QuestionServiceTest {
	@Autowired
	private UserDAO userDAOMock;

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