package cat.institutmarianao;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import cat.institutmarianao.dao.User;
import cat.institutmarianao.dao.UserDAO;

public class JdbcUserManagement {

	private static final String INVALID_OPTION = "Invalid option";

	private static final String ENTER_OPTION = "Option";

	private static final String USERNAME = "Username";
	private static final String NAME = "Name";
	private static final String EMAIL = "Email";
	private static final String RANK = "Rank";

	private static final String PASSWORD = "Password";
	private static final String CONFIRM = "Confirm password";

	private static final String MENU_EXIT = "EXIT";
	private static final String MENU_LIST_ACTIVE_USERS = "List active users";
	private static final String MENU_FIND_USER = "Find user";
	private static final String MENU_ADD_USER = "Add user";
	private static final String MENU_DELETE_USER = "Delete user";

	private static final String FIND_MENU_BACK = "BACK";
	private static final String FIND_MENU_FIND_BY = "Find by";

	private static final String USER_NOT_FOUND = "User not found";
	private static final String USER_CREATED = "User created";

	private static final String CONFIRM_STRING = "Y";
	private static final String CONFIRM_DELETE = "Wold you like to delete this user? (Enter [" + CONFIRM_STRING
			+ "] to delete)";
	private static final String USER_DELETED = "User deleted";
	private static final String USER_NOT_DELETED = "User not deleted";
	private static final String NUMBER_EXPECTED = "Number expected";
	private static final String PASS_DONT_MATCH = "Password don't match";
	private static final String BYE = "Bye";

	private static Scanner scanner;
	private static UserDAO userDao;

	private static Map<Integer, String> menu = new HashMap<>();
	private static Map<Integer, String> findMenu = new HashMap<>();

	static {
		menu.put(0, MENU_EXIT);
		menu.put(1, MENU_LIST_ACTIVE_USERS);
		menu.put(2, MENU_FIND_USER);
		menu.put(3, MENU_ADD_USER);
		menu.put(4, MENU_DELETE_USER);

		findMenu.put(0, FIND_MENU_BACK);
		findMenu.put(1, FIND_MENU_FIND_BY + " " + USERNAME);
		findMenu.put(2, FIND_MENU_FIND_BY + " " + EMAIL);
	}

	public static void main(String[] args) throws Exception {

		DBConnection dbConnection = new DBConnection("db.properties");

		userDao = new UserDAO(dbConnection);
		scanner = new Scanner(System.in);

		int option = -1;
		do {
			option = getOptionFromMenu(menu);

			switch (option) {
			case 0:
				System.out.println(BYE);
				System.exit(0);
				return;
			case 1:
				listActiveUsers();
				break;
			case 2:
				User user = findUser();
				if (user == null) {
					System.out.println(USER_NOT_FOUND);
				} else {
					System.out.println(user);
				}
				break;
			case 3:
				createUser();
				System.out.println(USER_CREATED);
				break;
			case 4:
				User userToDelete = findUser();
				if (userToDelete == null) {
					System.out.println(USER_NOT_FOUND);
				} else {
					deleteUser(userToDelete);
				}
				break;
			default:
			}
		} while (option != 0);
		scanner.close();
	}

	private static int getOptionFromMenu(Map<Integer, String> menu) {
		do {
			System.out.println();
			System.out.println(new String(new char[25]).replace("\0", ":"));

			for (Map.Entry<Integer, String> menuEntry : menu.entrySet()) {
				System.out.println(String.format("%1$-2d - %2$s", menuEntry.getKey(), menuEntry.getValue()));
			}

			System.out.println(new String(new char[25]).replace("\0", ":"));

			int option = readIntFromScanner("\n" + ENTER_OPTION);
			if (menu.containsKey(option)) {
				String optionName = menu.get(option);
				System.out.println("\n" + optionName);
				System.out.println(new String(new char[optionName.length()]).replace("\0", "-"));
				return option;
			}
			System.out.println(INVALID_OPTION);
		} while (true);
	}

	private static void listActiveUsers() throws Exception {
		String tableFmt = "    %1$-20s    %2$-20s    %3$-25s    %4$5s     ";
		System.out.println(String.format(tableFmt, USERNAME, NAME, EMAIL, RANK));
		System.out.println(new String(new char[90]).replace("\0", "-"));

		for (User user : userDao.findActiveUsers()) {
			System.out.println(
					String.format(tableFmt, user.getUsername(), user.getName(), user.getEmail(), user.getRank()));
		}
	}

	private static User findUser() throws Exception {
		int option = getOptionFromMenu(findMenu);
		switch (option) {
		case 1:
			String username = readStringFromScanner(USERNAME);
			return userDao.findUserByUsername(username);
		case 2:
			String email = readStringFromScanner(EMAIL);
			return userDao.findUserByEmail(email);
		}
		return null;
	}

	private static User createUser() throws Exception {
		String username = readStringFromScanner(USERNAME);
		String name = readStringFromScanner(NAME);
		String email = readStringFromScanner(EMAIL);
		String password = readPasswordFromScanner(PASSWORD, CONFIRM);

		return userDao.createUser(username, name, email, password);
	}

	private static void deleteUser(User userToDelete) throws Exception {

		System.out.println(userToDelete);
		if (confirmFromScanner(CONFIRM_DELETE, "Y")) {
			userDao.deleteUser(userToDelete);
			System.out.println(USER_DELETED);
		} else {
			System.out.println(USER_NOT_DELETED);
		}
	}

	private static String readStringFromScanner(String optionOut) {
		System.out.print(String.format("%1$s: ", optionOut));
		return scanner.nextLine();
	}

	private static int readIntFromScanner(String optionOut) {
		do {
			System.out.print(String.format("%1$s: ", optionOut));

			try {
				return Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println(NUMBER_EXPECTED);
			}
		} while (true);
	}

	private static String readPasswordFromScanner(String passwordOut, String confirmOut) {
		do {
			System.out.print(String.format("%1$s: ", passwordOut));
			String password = scanner.nextLine();

			System.out.print(String.format("%1$s: ", confirmOut));
			if (password.equals(scanner.nextLine())) {
				return password;
			}
			System.out.println(PASS_DONT_MATCH);
		} while (true);
	}

	private static boolean confirmFromScanner(String message, String yes) {
		System.out.print(message);
		return yes.equalsIgnoreCase(scanner.nextLine());
	}
}
