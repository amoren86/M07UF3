package cat.institutmarianao.spring.service;

public interface VoteService {
	void votePositive(Long answerId, Long userId);

	void voteNegative(Long answerId, Long userId);
}
