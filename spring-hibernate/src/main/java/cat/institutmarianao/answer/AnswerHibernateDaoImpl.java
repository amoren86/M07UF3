package cat.institutmarianao.answer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Transactional
@Repository("answerHibernateDAO")
public class AnswerHibernateDaoImpl implements AnswerDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Answer getById(Integer questionId) {
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
