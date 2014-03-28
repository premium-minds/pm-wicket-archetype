#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services;

import java.util.List;

import ${package}.entities.UserApplication;
import ${package}.entities.UserProfile;

public interface UsersApplicationService {
	public UserApplication verifyLogin(String email, String password) throws UserNotFoundException;

	public void changePassword(UserApplication user, String currentPassword, String newPassword) throws UserNotFoundException;

	List<UserProfile> getProfiles();

	void createUser(UserApplication newUser, String newPasswordMaAilTitle,String newPasswordMailMessage) throws UserAlreadyExistsException;

	List<UserApplication> getUsers();

	public void generateNewPassword(UserApplication user,String resetPasswordMailTitle,String resetPasswordMailMessage);

}
