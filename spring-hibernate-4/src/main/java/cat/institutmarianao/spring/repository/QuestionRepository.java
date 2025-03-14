package cat.institutmarianao.spring.repository;

import cat.institutmarianao.spring.model.Question;

public interface QuestionRepository {
	Question getById(Long questionId);

	void save(Question question);

	Question update(Question question);

}
