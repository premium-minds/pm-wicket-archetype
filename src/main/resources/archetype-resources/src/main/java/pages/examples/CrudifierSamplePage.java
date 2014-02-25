#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.pages.examples;

import org.apache.wicket.model.Model;

import ${package}.entities.UserApplication;
import ${package}.pages.TemplatePage;
import com.premiumminds.webapp.wicket.bootstrap.crudifier.form.CrudifierForm;

@SuppressWarnings("serial")
public class CrudifierSamplePage extends TemplatePage {
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(new CrudifierForm<UserApplication>("form", Model.of(new UserApplication())));
	}
}
