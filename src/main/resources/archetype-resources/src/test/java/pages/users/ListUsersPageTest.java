#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package  ${package}.pages.users;

import static org.easymock.EasyMock.expect;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import ${package}.TestUtils;
import ${package}.configuration.Permissions;
import ${package}.pages.AbstractLoggedInPageTest;
import ${package}.pages.HomePage;
import ${package}.services.UserAlreadyExistsException;
import ${package}.services.UsersApplicationService;

public class ListUsersPageTest extends AbstractLoggedInPageTest {
	@Inject UsersApplicationService usersService;

	@Test
	public void testRender() throws UserAlreadyExistsException {
		addPermission(Permissions.ACCESS_HOMEPAGE);
		expect(usersService.getUsers()).andReturn(TestUtils.getUsers(3));
		replayAll();
		getTester().startPage(HomePage.class);
		getTester().clickLink("usersMenu:listUsers:link");
		getTester().assertRenderedPage(ListUsersPage.class);
		
		verifyAll();
	}

	@Test
	public void testRenderAndClick() throws UserAlreadyExistsException {
		addPermission(Permissions.ACCESS_HOMEPAGE);
		expect(usersService.getUsers()).andReturn(TestUtils.getUsers(3)).times(2);
		expect(usersService.getProfiles()).andReturn(TestUtils.getProfiles(3));

		replayAll();
		getTester().startPage(HomePage.class);
		getTester().clickLink("usersMenu:listUsers:link");
		getTester().assertRenderedPage(ListUsersPage.class);
		getTester().executeAjaxEvent("usersTable:table:list:0", "click");
		getTester().assertRenderedPage(CreateUserPage.class);
		verifyAll();
	}

	@Test
	public void testGenerateNewPassWord() throws UserAlreadyExistsException {
		addPermission(Permissions.ACCESS_HOMEPAGE);
		expect(usersService.getUsers()).andReturn(TestUtils.getUsers(3)).times(1);
		
		replayAll();
		getTester().startPage(HomePage.class);
		getTester().clickLink("usersMenu:listUsers:link");
		getTester().assertRenderedPage(ListUsersPage.class);
		getTester().executeAjaxEvent("usersTable:table:list:0:columns:5:button", "onclick");
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
