#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.pages;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

import ${package}.configuration.Permissions;

@SuppressWarnings("serial")
@AuthorizeInstantiation(Permissions.ACCESS_HOMEPAGE)
public class HomePage extends TemplatePage {

}
