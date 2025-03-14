package cat.institutmarianao.spring.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cat.institutmarianao.spring.model.Vote;
import cat.institutmarianao.spring.repository.VoteRepository;
import jakarta.transaction.Transactional;

@Transactional
@Repository
public class VoteHibernateRepositoryImpl implements VoteRepository {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Vote getById(Long voteId) {
		return getSession().get(Vote.class, voteId);
	}

	@Override
	public void save(Vote vote) {
		getSession().persist(vote);
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
