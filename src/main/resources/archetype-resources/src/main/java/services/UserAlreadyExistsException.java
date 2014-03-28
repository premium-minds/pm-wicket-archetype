#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services;

@SuppressWarnings("serial")
public class UserAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 1L;
}
