package cat.institutmarianao.vote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import cat.institutmarianao.DaoTestConfig;
import cat.institutmarianao.ServicesConfig;
import cat.institutmarianao.answer.Answer;
import cat.institutmarianao.answer.AnswerDao;
import cat.institutmarianao.question.Question;
import cat.institutmarianao.question.QuestionDao;
import cat.institutmarianao.user.User;
import cat.institutmarianao.user.UserDao;
import cat.institutmarianao.utils.Mock;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ServicesConfig.class, DaoTestConfig.class })
public class VoteDaoTest {
	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private AnswerDao answerDao;

	@Autowired
	private VoteDao voteDao;

	@Test
	public void saveOk() {
		/* Setup */
		Timestamp currentTime = new Timestamp(new Date().getTime());

		/* userQ, which did the question */
		User userQ = Mock.getUser(null, "jack");
		userQ.setCreatedOn(currentTime);
		userDao.save(userQ);

		Question question = new Question();
		question.setText("This is a question");
		question.setUser(userQ);
		questionDao.save(question);

		/* userA, which did the answer related to the question before */
		User userA = Mock.getUser(null, "john");
		userA.setCreatedOn(currentTime);
		userDao.save(userA);

		Answer answer = new Answer();
		answer.setText("This is an answer");
		answer.setUser(userA);
		answerDao.save(answer);

		/* userV, which does the vote to the answer that userA did */
		String username = "wilson";
		User userV = Mock.getUser(null, username);
		userV.setCreatedOn(currentTime);
		userDao.save(userV);

		Vote vote = new Vote();
		vote.setVote(true);
		vote.setUser(userV);
		vote.setAnswer(answer);

		/* Previous verification - vote has no id */
		assertNull(vote.getVoteId());

		/* Test */
		voteDao.save(vote);

		/* Verification */
		assertNotNull(vote.getVoteId());

		Vote voteFromDb = voteDao.getById(vote.getVoteId());

		assertEquals(vote.getVoteId(), voteFromDb.getVoteId());
		assertEquals(username, voteFromDb.getUser().getUsername());
		assertEquals(vote.getVote(), voteFromDb.getVote());
		assertEquals(currentTime, voteFromDb.getUser().getCreatedOn());
	}
}