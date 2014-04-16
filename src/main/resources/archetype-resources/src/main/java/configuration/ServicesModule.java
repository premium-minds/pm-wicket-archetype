#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.configuration;

import com.google.inject.AbstractModule;
import ${package}.services.UsersApplicationService;
import ${package}.services.impl.UsersApplicationServiceImpl;
import com.premiumminds.webapp.utils.mailer.AbstractMailer;
import com.premiumminds.webapp.utils.mailer.SimpleMailer;

public class ServicesModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UsersApplicationService.class).to(UsersApplicationServiceImpl.class);
		bind(AbstractMailer.class).to(SimpleMailer.class);
	}

}
