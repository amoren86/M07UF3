package cat.institutmarianao.answer;

public interface AnswerDao {
	Answer getById(Integer answerId);

	void save(Answer answer);
}
