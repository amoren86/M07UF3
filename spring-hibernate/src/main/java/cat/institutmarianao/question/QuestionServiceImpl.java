package cat.institutmarianao.question;

import java.util.HashSet;
import java.util.Set;

import cat.institutmarianao.answer.Answer;
import cat.institutmarianao.user.User;
import cat.institutmarianao.user.UserDao;
import jakarta.transaction.Transactional;

@Transactional
public class QuestionServiceImpl implements QuestionService {
	private QuestionDao questionDao;
	private UserDao userDao;

	public QuestionServiceImpl(UserDao userDao, QuestionDao questionDao) {
		this.userDao = userDao;
		this.questionDao = questionDao;
	}

	@Override
	public Set<Question> getAllQuestions(Integer userId) {
		User user = userDao.getById(userId);
		return user.getQuestions();
	}

	@Override
	public Question answerQuestion(Answer answer, Integer questionId) {
		Question question = questionDao.getById(questionId);
		Set<Answer> answers = question.getAnswers();
		if (answers == null) {
			answers = new HashSet<>();
		}
		answers.add(answer);
		return questionDao.update(question);
	}

	@Override
	public void create(Question question) {
		questionDao.save(question);
	}
}