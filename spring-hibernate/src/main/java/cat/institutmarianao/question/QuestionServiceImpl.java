package cat.institutmarianao.question;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import cat.institutmarianao.answer.Answer;
import cat.institutmarianao.user.User;
import cat.institutmarianao.user.UserDAO;

@Transactional
public class QuestionServiceImpl implements QuestionService {
	private QuestionDAO questionDAO;
	private UserDAO userDAO;

	public QuestionServiceImpl(UserDAO userDAO, QuestionDAO questionDAO) {
		this.userDAO = userDAO;
		this.questionDAO = questionDAO;
	}

	@Override
	public Set<Question> getAllQuestions(Integer userId) {
		User user = userDAO.getById(userId);
		return user.getQuestions();
	}

	@Override
	public Question answerQuestion(Answer answer, Integer questionId) {
		Question question = questionDAO.getById(questionId);
		Set<Answer> answers = question.getAnswers();
		if (answers == null) {
			answers = new HashSet<>();
		}
		answers.add(answer);
		return questionDAO.update(question);
	}

	@Override
	public void create(Question question) {
		questionDAO.save(question);
	}
}