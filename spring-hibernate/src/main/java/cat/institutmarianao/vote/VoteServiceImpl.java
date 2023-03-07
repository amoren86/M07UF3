package cat.institutmarianao.vote;

import javax.transaction.Transactional;

import cat.institutmarianao.answer.Answer;
import cat.institutmarianao.answer.AnswerDAO;
import cat.institutmarianao.user.User;
import cat.institutmarianao.user.UserDAO;

/*
 * A vote is made by an user over an answer of another user
 */
@Transactional
public class VoteServiceImpl implements VoteService {
	private VoteDAO voteDAO;
	private AnswerDAO answerDAO;
	private UserDAO userDAO;

	public VoteServiceImpl(VoteDAO voteDAO, AnswerDAO answerDAO, UserDAO userDAO) {
		this.voteDAO = voteDAO;
		this.answerDAO = answerDAO;
		this.userDAO = userDAO;
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
		User user = userDAO.getById(userId);

		Answer answer = answerDAO.getById(answerId);

		Vote vote = new Vote();
		vote.setAnswer(answer);
		vote.setUser(user);
		vote.setVote(value);

		voteDAO.save(vote);

		return vote;
	}
}