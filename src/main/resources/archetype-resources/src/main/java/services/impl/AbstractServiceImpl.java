#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.impl;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

public class AbstractServiceImpl {
	@Inject
	private Provider<EntityManager> emp;

	public EntityManager getEntityManager(){
		return emp.get();
	}
	
	public <T> T detach(T obj){
		emp.get().detach(obj);
		return obj;
	}
	
	public <T extends Collection<?>> T fetch(T obj){
		obj.size(); // hack to fetch collection
		return obj;
	}
}
