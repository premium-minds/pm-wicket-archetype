package ${package}.pages.users;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;

import ${package}.configuration.Permissions;
import ${package}.entities.UserApplication;
import ${package}.entities.UserProfile;
import ${package}.pages.TemplatePage;
import ${package}.services.UserAlreadyExistsException;
import ${package}.services.UsersApplicationService;
import com.premiumminds.wicket.crudifier.IObjectRenderer;
import com.premiumminds.wicket.crudifier.form.CrudifierEntitySettings;
import com.premiumminds.wicket.crudifier.form.CrudifierForm;
import com.premiumminds.wicket.crudifier.form.CrudifierFormSettings;
import com.premiumminds.wicket.crudifier.form.EntityProvider;

@AuthorizeInstantiation(Permissions.ACCESS_HOMEPAGE)
@SuppressWarnings("serial")
public class CreateUserPage extends TemplatePage {
	@Inject
	private UsersApplicationService service;

	@SuppressWarnings("unused")
	private CrudifierForm<UserApplication> form;
	private UserApplication user;
	private boolean editMode = false;

	public CreateUserPage() {
		super();
		user =  new UserApplication();
	}
	public CreateUserPage(UserApplication u) {
		super();
		user = u;
		editMode = true;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		CrudifierEntitySettings settings = new CrudifierEntitySettings(){
			@Override
			public Set<String> getHiddenFields() {
				HashSet<String> hidden = new HashSet<String>();
				hidden.add("cipheredPassword");
				hidden.add("id");
				if	(! isEditMode()) 
					hidden.add("enabled");
				return hidden;
			}
			@Override
			public Set<String> getOrderOfFields() {
				LinkedHashSet<String> order = new LinkedHashSet<String>();
				order.add("name");
				order.add("email");
				order.add("profile");
				if	(isEditMode()) 
					order.add("enabled");
				return order;
			}
		};

		Map<Class<?>, IObjectRenderer<?>> renderers = new HashMap<>();
		renderers.put(UserProfile.class, new IObjectRenderer<UserProfile>() {
			@Override
			public String render(UserProfile object) {
				return object.getDescription();
			}
		});

		add(form = new CrudifierForm<UserApplication>("form", new PropertyModel<UserApplication>(this,"user"),settings,new CrudifierFormSettings(), renderers){
			@Override
			protected EntityProvider<?> getEntityProvider(String name) {
				return new EntityProvider<UserProfile>() {
					@Override
					public List<UserProfile> load() {
						return service.getProfiles();
					}
				};
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				try {
					if (isEditMode()){
						service.saveUser(user);
					} else {
						((UserApplication) form.getDefaultModelObject()).setEnabled(true);
						service.createUser((UserApplication) form.getDefaultModelObject(),getString("mail.createUser.title"),getString("mail.createUser.message"));
					}
				} catch (UserAlreadyExistsException e) {
					error(getString("createUser.duplicateEmail"));
					target.add(form);
					return;
				}
				throw new RestartResponseException(ListUsersPage.class); 
			}
		});
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

}
