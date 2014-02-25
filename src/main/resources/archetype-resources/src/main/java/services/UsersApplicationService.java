#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services;

import ${package}.entities.UserApplication;

public interface UsersApplicationService {
	public UserApplication verifyLogin(String email, String password) throws UserNotFoundException;
	
	public void changePassword(UserApplication user, String currentPassword, String newPassword) throws UserNotFoundException;
}
