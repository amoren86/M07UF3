package cat.institutmarianao.spring.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cat.institutmarianao.spring.model.Question;
import cat.institutmarianao.spring.repository.QuestionRepository;
import jakarta.transaction.Transactional;

@Transactional
@Repository
public class QuestionHibernateRepositoryImpl implements QuestionRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Question getById(Long questionId) {
		return getSession().get(Question.class, questionId);
	}

	@Override
	public void save(Question question) {
		getSession().persist(question);
	}

	@Override
	public Question update(Question question) {
		return getSession().merge(question);
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}