package ${package}.pages.users;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.model.IModel;

import ${package}.configuration.Permissions;
import ${package}.entities.UserApplication;
import ${package}.entities.UserProfile;
import ${package}.pages.TemplatePage;
import ${package}.services.UsersApplicationService;
import com.premiumminds.webapp.wicket.bootstrap.BootstrapFeedbackPanel;
import com.premiumminds.webapp.wicket.bootstrap.crudifier.IObjectRenderer;
import com.premiumminds.webapp.wicket.bootstrap.crudifier.table.*;

@AuthorizeInstantiation(Permissions.ACCESS_HOMEPAGE)
@SuppressWarnings("serial")
public class ListUsersPage extends TemplatePage {
	@Inject
	private UsersApplicationService service;
	private BootstrapFeedbackPanel infoPanel = new BootstrapFeedbackPanel("feedbackSuccess", FeedbackMessage.INFO);	
	private CrudifierTable<UserApplication> table;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(infoPanel);
		infoPanel.setOutputMarkupPlaceholderTag(true);
		add(table = new CrudifierTable<UserApplication>("usersTable") {
			@Override
			protected List<UserApplication> load(int page, int maxPerPage) {
				return service.getUsers();
			}
		}); 
		table.setOutputMarkupId(true);
		table.addColumn(new PropertyColumn<UserApplication>("name"));
		table.addColumn(new PropertyColumn<UserApplication>("email"));
		table.addColumn(new PropertyColumn<UserApplication>("profile"));
		table.addColumn(new PropertyColumn<UserApplication>("enabled"));
		table.addColumn(new ButtonColumn<UserApplication>("newPassword") {
			@Override
			public void onClick(IModel<UserApplication> model,AjaxRequestTarget target) {
				service.generateNewPassword(model.getObject(),getString("mail.resetUserPassword.title"),getString("mail.resetUserPassword.message"));
				info(getString("ResetUserPasswordInfo"));
				target.add(infoPanel);
			}
		});
		table.getRenderers().put(UserProfile.class, new IObjectRenderer<UserProfile>() {
			@Override
			public String render(UserProfile object) {
				return object.getDescription();
			}
		});
	}
}
