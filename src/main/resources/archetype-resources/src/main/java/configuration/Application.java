#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.configuration;

import javax.inject.Inject;

import com.google.inject.persist.PersistService;
import com.premiumminds.persistence.PersistenceTransaction;
import com.premiumminds.webapp.wicket.persistence.TransactionalRequestCycleListener;

public class Application extends WebApplication {
	@Inject
	private PersistenceTransaction persistenceTransaction; 
	@Inject
	private PersistService persistService;
	
	@Override
	protected void init() {
		persistService.start();

		getRequestCycleListeners().add(new TransactionalRequestCycleListener(persistenceTransaction));
		
		super.init();
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		
		persistService.stop();
	}
}
