#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package  ${package}.pages;

import javax.inject.Inject;

import org.junit.Test;

import com.google.inject.AbstractModule;
import ${package}.configuration.Permissions;
import ${package}.services.UsersApplicationService;

public class HomePageTest extends AbstractLoggedInPageTest{

	@Inject UsersApplicationService usersService;
	
	@Test
	public void testRenderPage() {
		addPermission(Permissions.ACCESS_HOMEPAGE);
		replayAll();
		getTester().startPage(HomePage.class);
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
