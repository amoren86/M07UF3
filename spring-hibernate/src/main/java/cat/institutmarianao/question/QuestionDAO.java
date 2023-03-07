package cat.institutmarianao.question;

public interface QuestionDAO {
	Question getById(Integer questionId);

	void save(Question question);

	Question update(Question question);
}
