package cat.institutmarianao.question;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Transactional
@Repository("questionHibernateDAO")
public class QuestionHibernateDaoImpl implements QuestionDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Question getById(Integer questionId) {
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