#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.impl;

import javax.inject.Singleton;
import javax.persistence.NoResultException;

import org.mindrot.jbcrypt.BCrypt;

import ${package}.entities.UserApplication;
import ${package}.services.UserNotFoundException;
import ${package}.services.UsersApplicationService;

@Singleton
public class UsersApplicationServiceImpl extends AbstractServiceImpl implements UsersApplicationService {
	private static String generatePasswordHash(String password){
		return BCrypt.hashpw(password, BCrypt.gensalt(10));
	}
	
	/*
	 * Self running code to generate hash for passwords
	 */
	public static void main(String[] args){
		if(args.length!=1){
			System.out.println("Usage: password");
			System.out.println("Prints an hash to the password");
		} else {
			System.out.println("Hash: "+generatePasswordHash(args[0]));
		}
		
	}

	public UserApplication verifyLogin(String email, String password) throws UserNotFoundException {
		try {
			UserApplication user = getEntityManager().createQuery(
					"SELECT u FROM "+UserApplication.class.getSimpleName()+" u WHERE u.email=:email AND u.enabled=TRUE", 
					UserApplication.class)
				.setParameter("email", email)
				.getSingleResult();
			fetch(user.getProfile().getPermissions()); // load permissions from database
			if(BCrypt.checkpw(password, user.getCipheredPassword())) return detach(user);
			throw new UserNotFoundException();
		} catch(NoResultException e){
			throw new UserNotFoundException();
		}
	}

}
