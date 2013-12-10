#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.configuration;

import com.google.inject.AbstractModule;
import ${package}.services.UsersApplicationService;
import ${package}.services.impl.UsersApplicationServiceImpl;

public class ServicesModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UsersApplicationService.class).to(UsersApplicationServiceImpl.class);
	}

}
