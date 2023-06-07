#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.configuration;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.wicket.resource.JQueryResourceReference;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

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

		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		try {
			Context initContext = new InitialContext();
			Context webContext = (Context)initContext.lookup("java:/comp/env");

			DataSource ds = (DataSource) webContext.lookup("jdbc/appDatasource");
			
			Flyway flyway = Flyway.configure().locations("migrations").dataSource(ds).load();
			flyway.migrate();

			persistService.start();

			getRequestCycleListeners().add(new TransactionalRequestCycleListener(persistenceTransaction));

			getCspSettings().blocking().disabled();
			getJavaScriptLibrarySettings().setJQueryReference(JQueryResourceReference.getV2());

			super.init();
		} catch(Throwable t){
			log.error("error starting application", t);
			throw new RuntimeException("could not start application", t);
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
