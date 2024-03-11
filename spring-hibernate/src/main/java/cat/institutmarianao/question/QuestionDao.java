package cat.institutmarianao.question;

public interface QuestionDao {
	Question getById(Integer questionId);

	void save(Question question);

	Question update(Question question);
}
