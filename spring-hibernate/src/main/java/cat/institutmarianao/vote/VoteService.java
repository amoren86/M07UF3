package cat.institutmarianao.vote;

public interface VoteService {
    void votePositive(Integer answerId, Integer userId);
    void voteNegative(Integer answerId, Integer userId);
}
