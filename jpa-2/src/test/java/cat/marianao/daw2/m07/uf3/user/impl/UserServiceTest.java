package cat.marianao.daw2.m07.uf3.user.impl;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import cat.marianao.daw2.m07.uf3.domain.User;
import cat.marianao.daw2.m07.uf3.service.UserService;
import cat.marianao.daw2.m07.uf3.service.impl.UserServiceImpl;
import cat.marianao.daw2.m07.uf3.user.mock.Mock;

@RunWith(Arquillian.class)
public class UserServiceTest {
	@Inject
	private UserService userService;

	@Deployment(testable = true)
	public static JavaArchive createTestableDeployment() {
		final JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "example.jar")
				.addClasses(UserService.class, UserServiceImpl.class)
				.addAsManifestResource("META-INF/persistence-test.xml", "persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
		return jar;
	}

	@Test
	public void findAllUsers() {

		User user0 = Mock.getUser0();
		User user1 = Mock.getUser1();

		userService.create(user0);
		userService.create(user1);

		List<User> users = userService.getAllUsers();
		Assert.assertEquals(2, users.size());

		// TODO - Make the needed changes to active the following lines
		// userService.remove(user0);
		// userService.remove(user1);
	}
}
