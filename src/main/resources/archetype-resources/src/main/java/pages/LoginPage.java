#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.pages;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.model.Model;

import ${package}.configuration.WebApplication;
import com.premiumminds.webapp.wicket.bootstrap.BootstrapFeedbackPanel;

@SuppressWarnings("serial")
public class LoginPage extends WebPage {
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		
		Form<Void> form = new StatelessForm<Void>("form"){
			private EmailTextField email = new EmailTextField("email", Model.of(""));
			private PasswordTextField password = new PasswordTextField("password", Model.of(""));
			
			{
				add(new BootstrapFeedbackPanel("feedbackError", FeedbackMessage.ERROR));
				add(email.setRequired(true));
				add(password);
			}
			
			protected void onSubmit() {
				if(((WebApplication)getWebApplication()).authenticate(email.getModelObject(), password.getModelObject())){
					continueToOriginalDestination();
					throw new RestartResponseException(getApplication().getHomePage());
				} else {
					error(getString("login.invalid"));
				}
			}
		};
		
		add(form);
	}
	
	@Override
	protected void onConfigure() {
		super.onConfigure();
		if(((WebApplication.AuthenticatedSession) getSession()).isSignedIn()){
			continueToOriginalDestination();
			throw new RestartResponseException(getApplication().getHomePage());
		}
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		// jquery required by bootstrap
		response.render(JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings()
	            .getJQueryReference())); 
	}

}
