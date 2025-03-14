package cat.institutmarianao.spring.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cat.institutmarianao.spring.model.Answer;
import cat.institutmarianao.spring.model.Question;
import cat.institutmarianao.spring.model.User;
import cat.institutmarianao.spring.repository.QuestionRepository;
import cat.institutmarianao.spring.repository.UserRepository;
import cat.institutmarianao.spring.service.QuestionService;
import jakarta.transaction.Transactional;

@Transactional
public class QuestionServiceImpl implements QuestionService {
	private UserRepository userRepository;
	private QuestionRepository questionRepository;

	public QuestionServiceImpl(UserRepository userRepository, QuestionRepository questionRepository) {
		this.userRepository = userRepository;
		this.questionRepository = questionRepository;
	}

	@Override
	public List<Question> getAllQuestions(Long userId) {
		User user = userRepository.getById(userId);
		if (user == null) {
			return null;
		}
		return user.getQuestions();
	}

	@Override
	public Question answerQuestion(Answer answer, Long questionId) {
		Question question = questionRepository.getById(questionId);
		Set<Answer> answers = question.getAnswers();
		if (answers == null) {
			answers = new HashSet<>();
		}
		answers.add(answer);
		return questionRepository.update(question);
	}

	@Override
	public void create(Question question) {
		questionRepository.save(question);
	}
}