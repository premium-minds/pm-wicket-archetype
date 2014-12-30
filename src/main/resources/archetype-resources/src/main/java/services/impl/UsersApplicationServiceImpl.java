#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.inject.Singleton;
import javax.persistence.NoResultException;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ${package}.entities.UserApplication;
import ${package}.entities.UserProfile;
import ${package}.services.UserAlreadyExistsException;
import ${package}.services.UserNotFoundException;
import ${package}.services.UsersApplicationService;

import com.premiumminds.webapp.utils.mailer.AbstractMailer;
import com.premiumminds.webapp.utils.mailer.MailerException;

@Singleton
public class UsersApplicationServiceImpl extends AbstractServiceImpl implements UsersApplicationService {
	
	private static final Logger logger = LoggerFactory.getLogger(UsersApplicationServiceImpl.class);

	@Inject AbstractMailer mailer;	

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
			if(BCrypt.checkpw(password, user.getCipheredPassword())){
				user.setCipheredPassword("");
				return detach(user);
			}
			throw new UserNotFoundException();
		} catch(NoResultException e){
			throw new UserNotFoundException();
		}
	}

	@Override
	public void changePassword(UserApplication user, String currentPassword, String newPassword) throws UserNotFoundException {
		try {
			UserApplication userDb = getEntityManager().find(UserApplication.class, user.getId());
			if(!BCrypt.checkpw(currentPassword, userDb.getCipheredPassword())) throw new UserNotFoundException();
			userDb.setCipheredPassword(generatePasswordHash(newPassword));
			getEntityManager().flush();
		} catch(NoResultException e){
			throw new UserNotFoundException();
		}
		
	}

	@Override
	public List<UserProfile> getProfiles() {
		List<UserProfile> p=new ArrayList<>();
		try{	
			p =	getEntityManager().createQuery(
					"SELECT DISTINCT p FROM " + UserProfile.class.getSimpleName() + " p ", 
					UserProfile.class).getResultList();
		} catch (NoResultException e){
			logger.error("There are no profiles on the system");
		}
		return p;
	}
	@Override
	public List<UserApplication> getUsers() {
		List<UserApplication> users=new ArrayList<>();
		try{	
			users =	getEntityManager().createQuery(
					"SELECT DISTINCT u FROM " + UserApplication.class.getSimpleName() + "  u ORDER BY u.name ", 
					UserApplication.class).getResultList();
		} catch (NoResultException e){
			logger.error("There are no users on the system");
		}
		return users;
	}
	
	@Override
	public void createUser(UserApplication newUser,String newPasswordMailTitle,String newPasswordMailMessage) throws UserAlreadyExistsException{

		if (haveAnotherUsersWithThisMail(newUser))
			throw new UserAlreadyExistsException();
		
		String newPassword = generateRandomString(8);
		newUser.setCipheredPassword(generatePasswordHash(newPassword));
		
		try {
			mailer.send(Arrays.asList(newUser.getEmail()), null, null, newPasswordMailTitle, newPasswordMailMessage + newPassword);
		} catch (MailerException e) {
			throw new RuntimeException("error sending email to user", e);
		}
		
		getEntityManager().persist(newUser);
	}


	@Override
	public void saveUser(UserApplication user) throws UserAlreadyExistsException{
		if (haveAnotherUsersWithThisMail(user))
			throw new UserAlreadyExistsException();
		getEntityManager().merge(user);
	}

	@Override
	public void generateNewPassword(UserApplication user,String resetPasswordMailTitle,String resetPasswordMailMessage) {
		String newPassword = generateRandomString(8);
		try {
			mailer.send(Arrays.asList(user.getEmail()), null, null, resetPasswordMailTitle, resetPasswordMailMessage + newPassword);
		} catch (MailerException e) {
			throw new RuntimeException("error sending email to user", e);
		}
		user.setCipheredPassword(generatePasswordHash(newPassword));
		getEntityManager().merge(user);
	}

	private boolean haveAnotherUsersWithThisMail(UserApplication newUser) {
		int count;
		String query="";
		query="SELECT u FROM " + UserApplication.class.getSimpleName() + " u WHERE u.email=:email";
		if (newUser.getId()>0)
			query+=" and NOT u.id="+newUser.getId();
		count = getEntityManager().createQuery(query, UserApplication.class).setParameter("email", newUser.getEmail()).getResultList().size();
		return (count > 0) ? true : false;
	}


	
	//Generate random string of a given size
	public String generateRandomString(int size){
		Random ran = new Random();
		char data = ' ';
		StringBuilder dat = new StringBuilder("");
		for (int i=0; i<size; i++) {
			data = (char)(ran.nextInt(25)+97);dat.append(data);
		}
		return dat.toString();
	}

}
