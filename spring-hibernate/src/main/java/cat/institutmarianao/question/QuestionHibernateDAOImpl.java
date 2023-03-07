package cat.institutmarianao.question;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Transactional
@Repository("questionHibernateDAO")
public class QuestionHibernateDAOImpl implements QuestionDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Question getById(Integer questionId) {
		return getSession().get(Question.class, questionId);
	}

	@Override
	public void save(Question question) {
		getSession().saveOrUpdate(question);
	}

	@Override
	public Question update(Question question) {
		return (Question) getSession().merge(question);
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}