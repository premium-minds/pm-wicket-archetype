#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package  ${package}.pages.users;

import static org.easymock.EasyMock.expect;

import org.apache.wicket.util.tester.FormTester;
import org.junit.Test;

import static org.easymock.EasyMock.*;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import ${package}.TestUtils;
import ${package}.configuration.Permissions;
import ${package}.entities.UserApplication;
import ${package}.pages.AbstractLoggedInPageTest;
import ${package}.pages.HomePage;
import ${package}.services.UserAlreadyExistsException;
import ${package}.services.UsersApplicationService;

public class CreateUserPageTest extends AbstractLoggedInPageTest{

	@Inject UsersApplicationService usersService;
	
	@Test
	public void testRenderPage() {
		addPermission(Permissions.ACCESS_HOMEPAGE);
		expect(usersService.getProfiles()).andReturn(TestUtils.getProfiles(3));
		replayAll();
		getTester().startPage(HomePage.class);
		getTester().clickLink("usersMenu:createUsers:link");
		getTester().assertRenderedPage(CreateUserPage.class);
		getTester().assertInvisible("form:feedbackError:feedbackul:messages");
		verifyAll();
	}
	
	@Test
	public void testSubmitWithError() {
		addPermission(Permissions.ACCESS_HOMEPAGE);
		expect(usersService.getProfiles()).andReturn(TestUtils.getProfiles(3)).times(2);
		replayAll();
		getTester().startPage(HomePage.class);
		getTester().clickLink("usersMenu:createUsers:link");
		getTester().assertRenderedPage(CreateUserPage.class);
		getTester().executeAjaxEvent("form:submit", "onclick");
		getTester().assertVisible("form:feedbackError:feedbackul:messages");
		verifyAll();
	}
	
	@Test
	public void testSubmitWithError2() {
		addPermission(Permissions.ACCESS_HOMEPAGE);
		expect(usersService.getProfiles()).andReturn(TestUtils.getProfiles(3)).times(2);
		replayAll();
		getTester().startPage(HomePage.class);
		getTester().clickLink("usersMenu:createUsers:link");
		getTester().assertRenderedPage(CreateUserPage.class);
		
		FormTester form = getTester().newFormTester("form");
		form.setValue("controls:controlGroup:1:controlGroup:inputBox:input", "nome");
		form.setValue("controls:controlGroup:2:controlGroup:inputBox:input", "mail@mail.com");
		getTester().executeAjaxEvent("form:submit", "onclick");
		getTester().assertVisible("form:feedbackError:feedbackul:messages");
		verifyAll();
	}
	
	@Test
	public void testSubmitWithError3() {
		addPermission(Permissions.ACCESS_HOMEPAGE);
		expect(usersService.getProfiles()).andReturn(TestUtils.getProfiles(3)).times(2);
		replayAll();
		getTester().startPage(HomePage.class);
		getTester().clickLink("usersMenu:createUsers:link");
		getTester().assertRenderedPage(CreateUserPage.class);
		
		FormTester form = getTester().newFormTester("form");
		form.setValue("controls:controlGroup:1:controlGroup:inputBox:input", "nome");
		form.setValue("controls:controlGroup:2:controlGroup:inputBox:input", "mail");
		form.select("controls:controlGroup:3:controlGroup:inputBox:input", 1);
		getTester().executeAjaxEvent("form:submit", "onclick");
		getTester().assertVisible("form:feedbackError:feedbackul:messages");
		verifyAll();
	}
	
	@Test
	public void testSubmit() throws UserAlreadyExistsException {
		addPermission(Permissions.ACCESS_HOMEPAGE);
		expect(usersService.getProfiles()).andReturn(TestUtils.getProfiles(3)).times(2);
		expect(usersService.getUsers()).andReturn(TestUtils.getUsers(3));
		usersService.createUser(anyObject(UserApplication.class), anyObject(String.class), anyObject(String.class));
		replayAll();
		getTester().startPage(HomePage.class);
		getTester().clickLink("usersMenu:createUsers:link");
		getTester().assertRenderedPage(CreateUserPage.class);
		
		FormTester form = getTester().newFormTester("form");
		form.setValue("controls:controlGroup:1:controlGroup:inputBox:input", "nome");
		form.setValue("controls:controlGroup:2:controlGroup:inputBox:input", "mail@mail.com");
		form.select("controls:controlGroup:3:controlGroup:inputBox:input", 1);
		getTester().executeAjaxEvent("form:submit", "onclick");
		getTester().assertRenderedPage(ListUsersPage.class);
		verifyAll();
	}
	
	
	@Override
	public AbstractModule getTestModule() {
		return new AbstractModule() {
			@Override
			protected void configure() {
				bind(UsersApplicationService.class).toInstance(createMock(UsersApplicationService.class));
			}
		};
	}

}
