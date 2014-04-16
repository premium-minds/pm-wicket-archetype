#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package  ${package}.pages;
import static org.easymock.EasyMock.*;

import java.util.HashSet;

import org.apache.wicket.util.tester.FormTester;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import ${package}.configuration.Permissions;
import ${package}.entities.UserApplication;
import ${package}.entities.UserProfile;
import ${package}.services.UserNotFoundException;
import ${package}.services.UsersApplicationService;


public class LoginPageTest extends AbstractPageTest{

	@Inject UsersApplicationService usersService;
	@Test
	public void testRenderLoginPage() {
		replayAll();
		getTester().startPage(HomePage.class);
		getTester().assertRenderedPage(LoginPage.class);
		getTester().assertVisible("form:");
		getTester().assertVisible("form:email");
		getTester().assertVisible("form:password");
		verifyAll();
	}

	@Test
	public void testRenderLoginPageSubmitFormWithErrors() {
		replayAll();
		getTester().startPage(HomePage.class);
		getTester().assertRenderedPage(LoginPage.class);
		FormTester form = getTester().newFormTester("form");
		form.setValue("email", "invalidMail");
		form.setValue("password", "123");
		form.submit();
		verifyAll();
	}
	
	@Test
	public void testRenderLoginPageSubmit() throws UserNotFoundException {
		UserApplication userLoggedIn = new UserApplication();
		userLoggedIn.setProfile(new UserProfile());
		userLoggedIn.getProfile().setPermissions(new HashSet<String>());
		userLoggedIn.getProfile().getPermissions().add(Permissions.ACCESS_HOMEPAGE);
		
		expect(usersService.verifyLogin("validmail@mail.com", "123")).andReturn(userLoggedIn);
		replayAll();
		getTester().startPage(HomePage.class);
		getTester().assertRenderedPage(LoginPage.class);
		FormTester form = getTester().newFormTester("form");
		form.setValue("email", "validmail@mail.com");
		form.setValue("password", "123");
		form.submit();
		getTester().startPage(HomePage.class);
		getTester().assertRenderedPage(HomePage.class);
		verifyAll();
	}
	
	@Override
	public AbstractModule getModule() {
		return new AbstractModule() {
			@Override
			protected void configure() {
				bind(UsersApplicationService.class).toInstance(createMock(UsersApplicationService.class));
			}
		};
	}
}
