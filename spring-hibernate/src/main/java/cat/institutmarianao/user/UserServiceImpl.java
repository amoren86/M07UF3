package cat.institutmarianao.user;

public class UserServiceImpl implements UserService {
	private UserDAO userDAO;

	public UserServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public User findUserByUsername(String username) {
		return userDAO.findUserByUsername(username);
	}
}
