package cat.institutmarianao.user;

public class UserServiceImpl implements UserService {
	private UserDao userDao;

	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User findUserByUsername(String username) {
		return userDao.findUserByUsername(username);
	}
}
