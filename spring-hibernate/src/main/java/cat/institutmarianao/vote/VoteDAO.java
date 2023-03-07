package cat.institutmarianao.vote;

public interface VoteDAO {
	Vote getById(Integer voteId);

	void save(Vote vote);
}
