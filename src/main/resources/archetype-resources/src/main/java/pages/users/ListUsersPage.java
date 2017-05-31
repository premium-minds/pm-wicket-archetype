package ${package}.pages.users;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.RestartResponseException;
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
import com.premiumminds.wicket.crudifier.IObjectRenderer;
import com.premiumminds.wicket.crudifier.table.ButtonColumn;
import com.premiumminds.wicket.crudifier.table.CrudifierTable;
import com.premiumminds.wicket.crudifier.table.PropertyColumn;

@AuthorizeInstantiation(Permissions.ACCESS_HOMEPAGE)
@SuppressWarnings("serial")
public class ListUsersPage extends TemplatePage {
	@Inject
	private UsersApplicationService service;
	private UserApplication selectedUser = null;
	private BootstrapFeedbackPanel infoPanel = new BootstrapFeedbackPanel("feedbackSuccess", FeedbackMessage.INFO);	
	private CrudifierTable<UserApplication> table;
	private ConfirmModal modal;

	private boolean isModalShowing = false;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(infoPanel);
		infoPanel.setOutputMarkupPlaceholderTag(true);
		
		add(modal = new ConfirmModal("confirmModal") {
			@Override
			public void callOnSubmit(AjaxRequestTarget target) {
				service.generateNewPassword(selectedUser,getString("mail.resetUserPassword.title"),getString("mail.resetUserPassword.message"));
				info(getString("ResetUserPasswordInfo"));
				target.add(infoPanel);
			}
		});
		
		add(table = new CrudifierTable<UserApplication>("usersTable") {
			@Override
			protected List<UserApplication> load(int page, int maxPerPage) {
				return service.getUsers();
			}
			@Override
			protected void onSelected(AjaxRequestTarget target, IModel<UserApplication> model){
				if (isModalShowing) {
					isModalShowing = false;
					return;
				}
				throw new RestartResponseException(new CreateUserPage(model.getObject()));
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
				selectedUser=model.getObject();
				modal.show();
				isModalShowing = true;
				target.add(modal);
			}
		});
		
		table.getRenderers().put(UserProfile.class, new IObjectRenderer<UserProfile>() {
			@Override
			public String render(UserProfile object) {
				return object.getDescription();
			}
		});
		table.setClickable(true);
	}
}
