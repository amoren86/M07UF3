package cat.institutmarianao.spring.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import cat.institutmarianao.spring.MockRepositoryConfig;
import cat.institutmarianao.spring.ServicesConfig;
import cat.institutmarianao.spring.model.Question;
import cat.institutmarianao.spring.model.User;
import cat.institutmarianao.spring.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ServicesConfig.class, MockRepositoryConfig.class })
public class QuestionServiceTest {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private QuestionService questionService;

	@Test
	public void getAllQuestionsOk() {
		/* Setup */
		Long userId = 1L;
		User user = mock(User.class);

		@SuppressWarnings("unchecked")
		List<Question> questions = mock(List.class);

		when(userRepository.getById(userId)).thenReturn(user);
		when(user.getQuestions()).thenReturn(questions);

		/* Test */
		List<Question> questionsFromDb = questionService.getAllQuestions(userId);

		/* Verifiation */
		assertSame(questions, questionsFromDb);
		verify(userRepository, times(1)).getById(userId);
	}
}