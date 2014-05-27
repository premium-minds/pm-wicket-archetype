#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package  ${package};

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import ${package}.entities.UserApplication;
import ${package}.entities.UserProfile;

public class TestUtils {

	public static List<UserProfile> getProfiles(int size){
		List<UserProfile> list = new ArrayList<>();
		for (int i = 0;i<size;i++){
			UserProfile profile = new UserProfile();
			profile.setDescription("description " + i);
			profile.setPermissions(new HashSet<String>(Arrays.asList("permissão 1","permissão 2")));
			list.add(profile);
		}
		return list;
	}
	
	public static List<UserApplication> getUsers(int size){
		List<UserApplication> list = new ArrayList<>();
		for (int i = 0;i<size;i++){
			UserApplication user = new UserApplication();
			user.setName("name " + i);
			user.setEmail("mail@mail.com");
			user.setEnabled(true);
			user.setProfile(getProfiles(1).get(0));
			list.add(user);
		}
		return list;
	}
}
