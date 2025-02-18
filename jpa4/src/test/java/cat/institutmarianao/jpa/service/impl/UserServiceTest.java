package cat.institutmarianao.jpa.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.sql.Timestamp;
import java.util.Date;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import cat.institutmarianao.jpa.model.User;
import cat.institutmarianao.jpa.service.UserService;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;

@RunWith(Arquillian.class)
public class UserServiceTest {
	@EJB
	private UserService userService;

	@Deployment
	public static JavaArchive createTestableDeployment() {
		return ShrinkWrap.create(JavaArchive.class, "jpa4.jar").addPackages(true, "cat.institutmarianao.jpa")
				.addClass(org.h2.Driver.class).addAsManifestResource("META-INF/MANIFEST.MF", "MANIFEST.MF")
				.addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
	}

	private User createTestUser(String username, String name, String email) {
		User user = new User();
		user.setUsername(username);
		user.setName(name);
		user.setEmail(email);
		user.setActive(true);
		user.setCreatedOn(new Timestamp(new Date().getTime()));
		user.setPassword("password");
		user.setRank(100);
		return user;
	}

	@Test
	public void testCreateUser() {
		User user = createTestUser("lovelace", "Ada Lovelace", "ada@lovelace.was");
		assertNull(user.getId());

		userService.create(user);
		assertNotNull(user.getId());

		User foundUser = userService.findUserByUsername("lovelace");
		assertNotNull(foundUser);
		assertEquals(user, foundUser);
	}

	@Test
	public void testEditUser() {
		User user = createTestUser("babbage", "Charles Babbage", "charles@babbage.was");
		assertNull(user.getId());

		userService.create(user);
		assertNotNull(user.getId());
		assertNotEquals("new.charles@babbage.was", user.getEmail());

		user.setEmail("new.charles@babbage.was");
		userService.edit(user);

		User updatedUser = userService.findUserByUsername("babbage");
		assertEquals("new.charles@babbage.was", updatedUser.getEmail());
	}

	@Test
	public void testRemoveUser() {
		User user = createTestUser("byron", "Lord Byron", "lord@byron.was");
		userService.create(user);

		userService.remove(user);

		assertThrows(EJBException.class, () -> userService.findUserByUsername("byron"));
	}
}