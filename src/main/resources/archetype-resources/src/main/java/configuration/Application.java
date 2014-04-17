#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.configuration;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.persist.PersistService;
import com.premiumminds.persistence.PersistenceTransaction;
import com.premiumminds.webapp.wicket.persistence.TransactionalRequestCycleListener;

public class Application extends WebApplication {
	@Inject
	private PersistenceTransaction persistenceTransaction; 
	@Inject
	private PersistService persistService;
	
	private static Logger log = LoggerFactory.getLogger(Application.class);
	
	@Override
	protected void init() {
		try {
			persistService.start();
	
			getRequestCycleListeners().add(new TransactionalRequestCycleListener(persistenceTransaction));
			
			super.init();
		} catch(Throwable t){
			log.error("error starting application", t);
			throw t;
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		try {
			super.finalize();
			
			persistService.stop();
		} catch(Throwable t){
			log.error("error ending application", t);
			throw t;
		}
	}
}