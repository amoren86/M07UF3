package cat.institutmarianao.utils;

import org.mockito.Mockito;

import cat.institutmarianao.answer.Answer;
import cat.institutmarianao.question.Question;
import cat.institutmarianao.user.User;

public class Mock {

	public static User getUser(Integer userId, String username) {
		User user = new User();
		user.setUserId(userId);
		user.setUsername(username);
		user.setActive(true);
		return user;
	}

	public static User getUser0() {
		return getUser(null, "John Doe");
	}

	public static User getMockUser() {
		return Mockito.mock(User.class);
	}

	public static Question getQuestion(Integer questionId, String text) {
		Question question = new Question();
		question.setQuestionId(questionId);
		question.setText(text);
		return question;
	}

	public static Question getQuestion0() {
		return getQuestion(null, "Do you think that earth is flat?");
	}

	public static Question getQuestion1() {
		return getQuestion(null, "What was first the chicken or the egg?");
	}

	public static Answer getAnswer(Integer answerId, String text) {
		Answer answer = new Answer();
		answer.setAnswerId(answerId);
		answer.setText(text);
		return answer;
	}
}
