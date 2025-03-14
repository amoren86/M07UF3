package cat.institutmarianao.spring.repository;

import static cat.institutmarianao.utils.RepositoryMock.createUser;
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

import cat.institutmarianao.spring.H2DataSourceConfig;
import cat.institutmarianao.spring.HibernateConfig;
import cat.institutmarianao.spring.RepositoryConfig;
import cat.institutmarianao.spring.model.Answer;
import cat.institutmarianao.spring.model.Question;
import cat.institutmarianao.spring.model.User;
import cat.institutmarianao.spring.model.Vote;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RepositoryConfig.class, HibernateConfig.class, H2DataSourceConfig.class })
public class VoteRepositoryTest {
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private VoteRepository voteRepository;

	@Test
	public void saveOk() {
		/* Setup */
		Timestamp currentTime = new Timestamp(new Date().getTime());

		/* userQ, which did the question */
		User userQ = createUser(null, "jack");
		userQ.setCreatedOn(currentTime);
		userRepository.create(userQ);

		Question question = new Question();
		question.setText("This is a question");
		question.setUser(userQ);
		questionRepository.save(question);

		/* userA, which did the answer related to the question before */
		User userA = createUser(null, "john");
		userA.setCreatedOn(currentTime);
		userRepository.create(userA);

		Answer answer = new Answer();
		answer.setText("This is an answer");
		answer.setUser(userA);
		answerRepository.save(answer);

		/* userV, which does the vote to the answer that userA did */
		String username = "wilson";
		User userV = createUser(null, username);
		userV.setCreatedOn(currentTime);
		userRepository.create(userV);

		Vote vote = new Vote();
		vote.setVote(true);
		vote.setUser(userV);
		vote.setAnswer(answer);

		/* Previous verification - vote has no id */
		assertNull(vote.getId());

		/* Test */
		voteRepository.save(vote);

		/* Verification */
		assertNotNull(vote.getId());

		Vote voteFromDb = voteRepository.getById(vote.getId());

		assertEquals(vote.getId(), voteFromDb.getId());
		assertEquals(username, voteFromDb.getUser().getUsername());
		assertEquals(vote.getVote(), voteFromDb.getVote());
		assertEquals(currentTime, voteFromDb.getUser().getCreatedOn());
	}
}