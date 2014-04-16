#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package  ${package}.pages;

import static org.easymock.EasyMock.expect;

import java.io.ByteArrayInputStream;
import java.util.HashSet;

import javax.inject.Inject;

import org.junit.Before;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import ${package}.entities.UserApplication;
import ${package}.entities.UserProfile;
import ${package}.services.UserNotFoundException;
import ${package}.services.UsersApplicationService;
import com.premiumminds.webapp.utils.ApplicationContextConfiguration;

public abstract class AbstractLoggedInPageTest extends AbstractPageTest {
	
	@Inject
	private UsersApplicationService usersService;
	private UserApplication userLoggedIn;
	
    @Before
	public void setUp() {
		super.setUp();
		ApplicationContextConfiguration.configure(new ByteArrayInputStream(new byte[] {}));
		userLoggedIn = new UserApplication();
		userLoggedIn.setProfile(new UserProfile());
		userLoggedIn.getProfile().setPermissions(new HashSet<String>());
		try {
			expect(usersService.verifyLogin("test", "test")).andReturn(userLoggedIn);
		} catch (UserNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		replayAll();
		
		getWebApplication().authenticate("test", "test");
		
		verifyAll();
		resetAll();
	}
	
	public void addPermission(String permission){
		userLoggedIn.getProfile().getPermissions().add(permission);
	}
	@Override
	public Module getModule() {
		return Modules.override(new AbstractModule(){
			@Override
			protected void configure() {
				bind(UsersApplicationService.class).toInstance(createMock(UsersApplicationService.class));
			}
		}).with(getTestModule());
	}
	
	public abstract Module getTestModule();
}
