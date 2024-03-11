package cat.institutmarianao.vote;

import cat.institutmarianao.answer.Answer;
import cat.institutmarianao.answer.AnswerDao;
import cat.institutmarianao.user.User;
import cat.institutmarianao.user.UserDao;
import jakarta.transaction.Transactional;

/*
 * A vote is made by an user over an answer of another user
 */
@Transactional
public class VoteServiceImpl implements VoteService {
	private VoteDao voteDao;
	private AnswerDao answerDao;
	private UserDao userDao;

	public VoteServiceImpl(VoteDao voteDao, AnswerDao answerDao, UserDao userDao) {
		this.voteDao = voteDao;
		this.answerDao = answerDao;
		this.userDao = userDao;
	}

	@Override
	public void votePositive(Integer answerId, Integer userId) {
		vote(answerId, userId, true);
	}

	@Override
	public void voteNegative(Integer answerId, Integer userId) {
		vote(answerId, userId, false);
	}

	private Vote vote(Integer userId, Integer answerId, Boolean value) {
		User user = userDao.getById(userId);

		Answer answer = answerDao.getById(answerId);

		Vote vote = new Vote();
		vote.setAnswer(answer);
		vote.setUser(user);
		vote.setVote(value);

		voteDao.save(vote);

		return vote;
	}
}