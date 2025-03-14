package cat.institutmarianao.spring.repository;

import cat.institutmarianao.spring.model.Answer;

public interface AnswerRepository {
	Answer getById(Long answerId);

	void save(Answer answer);
}
