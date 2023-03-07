package cat.institutmarianao.answer;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Transactional
@Repository("answerHibernateDAO")
public class AnswerHibernateDAOImpl implements AnswerDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Answer getById(Integer questionId) {
		return getSession().get(Answer.class, questionId);
	}

	@Override
	public void save(Answer answer) {
		getSession().saveOrUpdate(answer);
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
