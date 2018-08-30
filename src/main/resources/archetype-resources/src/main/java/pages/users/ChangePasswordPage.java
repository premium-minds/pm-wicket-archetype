package ${package}.pages.users;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.Model;

import ${package}.configuration.Permissions;
import ${package}.pages.TemplatePage;
import ${package}.services.UserNotFoundException;
import ${package}.services.UsersApplicationService;
import com.premiumminds.webapp.wicket.bootstrap.BootstrapControlGroupFeedback;
import com.premiumminds.webapp.wicket.bootstrap.BootstrapFeedbackPanel;

@AuthorizeInstantiation(Permissions.ACCESS_HOMEPAGE)
@SuppressWarnings("serial")
public class ChangePasswordPage extends TemplatePage {
	@Inject
	private UsersApplicationService service;

	public ChangePasswordPage(){
		final BootstrapFeedbackPanel errorFeedback = new BootstrapFeedbackPanel("feedbackError", FeedbackMessage.ERROR);
		final BootstrapFeedbackPanel successFeedback = new BootstrapFeedbackPanel("feedbackSuccess", FeedbackMessage.SUCCESS);
		
		final PasswordTextField currentPassword = new PasswordTextField("current-password", Model.of(""));
		final PasswordTextField newPassword = new PasswordTextField("new-password", Model.of(""));
		PasswordTextField repeatPassword = new PasswordTextField("repeat-password", Model.of(""));
		
		Form<Void> form = new Form<>("form");
		
		form.add(errorFeedback);
		form.add(successFeedback);
		
		form.add(new BootstrapControlGroupFeedback("current-password").add(currentPassword));
		form.add(new BootstrapControlGroupFeedback("new-password").add(newPassword));
		form.add(new BootstrapControlGroupFeedback("repeat-password").add(repeatPassword));
		
		form.add(new EqualPasswordInputValidator(newPassword, repeatPassword));
		
		form.add(new AjaxSubmitLink("submit") {
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				target.add(getForm());
				
				try {
					service.changePassword(getLoggedInUser(), currentPassword.getModelObject(), newPassword.getModelObject());
					success(form.getString("password-changed"));
				} catch(UserNotFoundException e){
					error(form.getString("current-password.WrongPassword"));
				}
			}
			
			@Override
			protected void onError(AjaxRequestTarget target) {
				target.add(getForm());
			}
		});
		
		add(form);
	}
}
