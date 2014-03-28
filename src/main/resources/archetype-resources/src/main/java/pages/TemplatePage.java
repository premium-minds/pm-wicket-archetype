#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.pages;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.resource.JQueryResourceReference;

import ${package}.configuration.WebApplication;
import ${package}.entities.UserApplication;
import ${package}.pages.examples.CrudifierSamplePage;
import ${package}.pages.examples.Option1Page;
import ${package}.pages.examples.Option2Page;
import ${package}.pages.examples.Option3Page;
import ${package}.pages.examples.SamplePage;
import ${package}.pages.users.ChangePasswordPage;
import ${package}.pages.users.CreateUserPage;
import ${package}.pages.users.ListUsersPage;

@SuppressWarnings("serial")
public abstract class TemplatePage extends WebPage {
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(new DebugBar("debug"));
		add(new BookmarkablePageLink<WebPage>("home", HomePage.class));
		
		add(new Menu("sample", CrudifierSamplePage.class));
		Menu menu = new Menu("usersMenu");
		menu.add(new Menu("createUsers", CreateUserPage.class));
		menu.add(new Menu("listUsers", ListUsersPage.class));
		menu.add(new Menu("submenu3", Option3Page.class));
		add(menu);
		
		// change password
		add(new BookmarkablePageLink<ChangePasswordPage>("user.change-password", ChangePasswordPage.class));
		// logout link
		add(new Link<Void>("user.logout") {

			@Override
			public void onClick() {
				getSession().invalidate();
			}
		});
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(JQueryResourceReference.get())));
	}
	
	/*
	 * Class to define the menus
	 */
	public class Menu extends WebMarkupContainer {
		private Class<? extends WebPage> pageClass;

		public Menu(String id){
			this(id, null);
		}
		
		public Menu(String id, Class<? extends WebPage> pageClass) {
			super(id);
			this.pageClass = pageClass;
		}
		
		@Override
		protected void onInitialize() {
			super.onInitialize();

			if(pageClass!=null){
				AbstractLink link = new BookmarkablePageLink<WebPage>("link", pageClass);
			
				add(link);
			}
			
			add(new Behavior() {
				@Override
				public void onComponentTag(Component component, ComponentTag tag) {
					super.onComponentTag(component, tag);
					if(isActive()) tag.append("class", "active", " ");
				}
			});
		}
		
		private boolean isActive(){
			boolean ret = false;
			for(Component submenu : visitChildren(Menu.class)){
				ret |= ((Menu) submenu).isActive(); 
			}
			return ret || TemplatePage.this.getClass().equals(pageClass);
		}
	}
	
	public UserApplication getLoggedInUser(){
		return WebApplication.AuthenticatedSession.get().getLoggedInUser();
	}
}
