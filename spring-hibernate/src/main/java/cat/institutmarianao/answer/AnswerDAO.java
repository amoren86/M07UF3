package cat.institutmarianao.answer;

public interface AnswerDAO {
	Answer getById(Integer answerId);

	void save(Answer answer);
}
