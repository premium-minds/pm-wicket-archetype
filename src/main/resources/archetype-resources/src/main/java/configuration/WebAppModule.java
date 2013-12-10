#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.configuration;

import org.apache.wicket.protocol.http.WebApplication;

import com.google.inject.AbstractModule;

public class WebAppModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new PersistenceModule());
		install(new ServicesModule());
		
		bind(WebApplication.class).to(Application.class);
	}

}
