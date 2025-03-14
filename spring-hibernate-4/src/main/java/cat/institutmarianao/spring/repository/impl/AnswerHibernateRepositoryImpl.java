package cat.institutmarianao.spring.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cat.institutmarianao.spring.model.Answer;
import cat.institutmarianao.spring.repository.AnswerRepository;
import jakarta.transaction.Transactional;

@Transactional
@Repository
public class AnswerHibernateRepositoryImpl implements AnswerRepository {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Answer getById(Long questionId) {
		return getSession().get(Answer.class, questionId);
	}

	@Override
	public void save(Answer answer) {
		getSession().persist(answer);
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
