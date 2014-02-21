#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.db.persistence;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.Transactional;
import com.google.inject.persist.UnitOfWork;

public class TransactionalRule implements TestRule {
	private Injector injector;
	
	public class TransactionalStatement extends Statement {
		private final Statement statement;
		private final UnitOfWork unitOfWork;
		private final PersistService persistService;
		private final Provider<EntityManager> emp;
		
		public TransactionalStatement(Statement statement, UnitOfWork unitOfWork, PersistService persistService, Provider<EntityManager> emp){
			this.statement = statement;
			this.unitOfWork = unitOfWork;
			this.persistService = persistService;
			this.emp = emp;
		}
		
		@Override
		public void evaluate() throws Throwable {
	    	persistService.start();
			unitOfWork.begin();
			emp.get().getTransaction().begin();
			try {
				statement.evaluate();
			} finally {
				if(emp.get().getTransaction().getRollbackOnly())
					emp.get().getTransaction().rollback();
				else
					emp.get().getTransaction().commit();
				unitOfWork.end();
				persistService.stop();
			}
		}
		
	}
	
	public TransactionalRule(Injector injector) {
		this.injector = injector;
	}

	@Override
	public Statement apply(Statement base, Description description) {
		if(description.getAnnotation(Transactional.class)==null) return base;
		return new TransactionalStatement(base, injector.getInstance(UnitOfWork.class), injector.getInstance(PersistService.class), injector.getProvider(EntityManager.class));
	}

}
