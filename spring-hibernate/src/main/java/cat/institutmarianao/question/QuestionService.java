package cat.institutmarianao.question;

import java.util.Set;

import cat.institutmarianao.answer.Answer;

public interface QuestionService {
	Set<Question> getAllQuestions(Integer userId);

	Question answerQuestion(Answer answer, Integer questionId);

	void create(Question question);
}