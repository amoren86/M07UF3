package cat.institutmarianao.spring.service;

import java.util.List;

import cat.institutmarianao.spring.model.Answer;
import cat.institutmarianao.spring.model.Question;

public interface QuestionService {
	List<Question> getAllQuestions(Long userId);

	Question answerQuestion(Answer answer, Long questionId);

	void create(Question question);
}