#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package  ${package}.pages;

import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.util.tester.WicketTester;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import  ${package}.configuration.WebApplication;

public abstract class AbstractPageTest extends EasyMockSupport {
	private WicketTester tester;
	private Injector injector;
	private WebApplication wicketApp;
	private static final Logger log = LoggerFactory.getLogger(AbstractPageTest.class);

	public  AbstractPageTest(){
		Module module = Modules.override(new AbstractModule(){
			@Override
			protected void configure() {
				bind(org.apache.wicket.protocol.http.WebApplication.class).to(WebApplication.class);
			}
		}).with(getModule());
		injector = Guice.createInjector(module);
		injector.injectMembers(this);
		wicketApp = injector.getInstance(WebApplication.class);
		wicketApp.getComponentInstantiationListeners().add(
				new GuiceComponentInjector(wicketApp, injector));
	}

	@Before
	public void setUp() {
		tester = new WicketTester(wicketApp);
		System.setProperty("wicket.configuration", "DEPLOYMENT");
	}

	public WicketTester getTester() {
		return tester;
	}

	public WebApplication getWebApplication(){
		return wicketApp;
	}

	public abstract Module getModule();

	public static Logger getLog() {
		return log;
	}
}
