#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.configuration;

import javax.inject.Inject;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.Request;

import ${package}.entities.UserApplication;
import ${package}.pages.HomePage;
import ${package}.pages.LoginPage;
import ${package}.services.UserNotFoundException;
import ${package}.services.UsersApplicationService;

@SuppressWarnings("serial")
public class WebApplication extends AuthenticatedWebApplication {
	@Inject
	private UsersApplicationService usersService;

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return LoginPage.class;
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return AuthenticatedSession.class;
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}
	
	@Override
	protected void init() {
		super.init();
		
		mountPage("login", LoginPage.class);
	}

	public boolean authenticate(String email, String password){
		try {
			AuthenticatedSession.get().loggedInUser = usersService.verifyLogin(email, password);
			return true;
		} catch(UserNotFoundException e){
			AuthenticatedSession.get().loggedInUser = null;
			return false;
		}
	}
	
	public static class AuthenticatedSession extends AbstractAuthenticatedWebSession {
		private UserApplication loggedInUser;
		
		public static AuthenticatedSession get()
		{
			return (AuthenticatedSession)Session.get();
		}
		
		public AuthenticatedSession(Request request) {
			super(request);
		}

		@Override
		public Roles getRoles() {
			if(isSignedIn()) return new Roles(loggedInUser.getProfile().getPermissions().toArray(new String[0]));
			else return null;
		}

		@Override
		public boolean isSignedIn() {
			return loggedInUser!=null;
		}

	}
}
