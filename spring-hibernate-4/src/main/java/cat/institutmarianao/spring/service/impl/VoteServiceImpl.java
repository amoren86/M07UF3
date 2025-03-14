package cat.institutmarianao.spring.service.impl;

import cat.institutmarianao.spring.model.Answer;
import cat.institutmarianao.spring.model.User;
import cat.institutmarianao.spring.model.Vote;
import cat.institutmarianao.spring.repository.AnswerRepository;
import cat.institutmarianao.spring.repository.UserRepository;
import cat.institutmarianao.spring.repository.VoteRepository;
import cat.institutmarianao.spring.service.VoteService;
import jakarta.transaction.Transactional;

@Transactional
public class VoteServiceImpl implements VoteService {
	private VoteRepository voteRepository;
	private AnswerRepository answerRepository;
	private UserRepository userRepository;

	public VoteServiceImpl(VoteRepository voteRepository, AnswerRepository answerRepository,
			UserRepository userRepository) {
		this.voteRepository = voteRepository;
		this.answerRepository = answerRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void votePositive(Long answerId, Long userId) {
		vote(answerId, userId, true);
	}

	@Override
	public void voteNegative(Long answerId, Long userId) {
		vote(answerId, userId, false);
	}

	private Vote vote(Long userId, Long answerId, Boolean value) {
		User user = userRepository.getById(userId);

		Answer answer = answerRepository.getById(answerId);

		Vote vote = new Vote();
		vote.setAnswer(answer);
		vote.setUser(user);
		vote.setVote(value);

		voteRepository.save(vote);

		return vote;
	}
}