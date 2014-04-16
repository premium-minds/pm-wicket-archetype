#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services;

import java.util.HashSet;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import org.junit.Before;
import org.junit.Test;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.persist.Transactional;
import ${package}.entities.UserApplication;
import ${package}.entities.UserProfile;
import ${package}.services.impl.UsersApplicationServiceImpl;
import com.premiumminds.webapp.utils.mailer.AbstractMailer;
import com.premiumminds.webapp.utils.mailer.SimpleMailer;

public class UsersApplicationServiceTest extends AbstractServiceTest{

	@Inject 
	private UsersApplicationService userService;
	@Inject
	private Provider<EntityManager> emp;

	@Before 
	public void initialize(){
		UserProfile profile = new UserProfile();
		profile.setDescription("Default");
		profile.setPermissions(new HashSet<String>());
		emp.get().persist(profile);
		UserApplication user = new UserApplication();
		user.setName("John Doe");
		user.setEmail("johnDoe@mail.com");
		user.setCipheredPassword("$2a$10$7QsqZ6HFowLBN/xWf8Q.AOY46b.6ETdxQfgarSC.z0IX0ByHPTofO");
		user.setProfile(profile);
		user.setEnabled(true);
		emp.get().persist(user);
	}

	@Test(expected=UserNotFoundException.class)
	@Transactional
	public void WrongUserAndPass() throws UserNotFoundException{
		userService.verifyLogin("mail", "pass");
	}

	@Test(expected=UserNotFoundException.class)
	@Transactional
	public void EmptyUserAndWrongPass() throws UserNotFoundException{
		userService.verifyLogin(null, "pass");
	}

	@Test(expected=UserNotFoundException.class)
	@Transactional
	public void WrongUserAndEmptyPass() throws UserNotFoundException{
		userService.verifyLogin("mail", null);
	}

	@Test(expected=UserNotFoundException.class)
	@Transactional
	public void EmptyUserAndEmptyPass() throws UserNotFoundException{
		userService.verifyLogin(null, null);
	}

	@Test
	@Transactional
	public void okUser() throws UserNotFoundException{
		userService.verifyLogin("johnDoe@mail.com", "test");
	}

	@Override
	public Module getModule() {
		return new AbstractModule() {

			@Override
			protected void configure() {
				bind(UsersApplicationService.class).to(UsersApplicationServiceImpl.class);
				bind(AbstractMailer.class).to(SimpleMailer.class);
			}
		};
	}
}
