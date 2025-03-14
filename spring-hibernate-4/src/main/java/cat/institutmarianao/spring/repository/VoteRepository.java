package cat.institutmarianao.spring.repository;

import cat.institutmarianao.spring.model.Vote;

public interface VoteRepository {
	Vote getById(Long voteId);

	void save(Vote vote);
}
