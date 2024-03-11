package cat.institutmarianao.vote;

public interface VoteDao {
	Vote getById(Integer voteId);

	void save(Vote vote);
}
