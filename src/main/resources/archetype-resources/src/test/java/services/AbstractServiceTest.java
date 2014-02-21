#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Rule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.persist.jpa.JpaPersistModule;
import ${package}.db.persistence.TransactionalRule;

public abstract class AbstractServiceTest extends EasyMockSupport{
	private Injector injector;
	@Rule
	public TransactionalRule rule;

	public AbstractServiceTest() {	
		injector = Guice.createInjector(new JpaPersistModule("app-data-unit-test"), getModule());
		injector.injectMembers(this);
		rule = new TransactionalRule(injector);
	}

	public abstract Module getModule();
	
	@Before
	public void setUp(){

	}	
}
