#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.configuration;

import javax.persistence.EntityManager;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.persist.UnitOfWork;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.premiumminds.persistence.JpaGuicePersistenceTransaction;
import com.premiumminds.persistence.PersistenceTransaction;

public class PersistenceModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new JpaPersistModule("app-data-unit"));
	}

	@Provides
	public PersistenceTransaction providePersistenceTransaction(UnitOfWork unitOfWork, Provider<EntityManager> emp){
		return new JpaGuicePersistenceTransaction(unitOfWork, emp);
	}
}
