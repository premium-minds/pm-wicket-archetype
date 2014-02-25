package ${package}.pages.examples;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

import ${package}.configuration.Permissions;
import ${package}.pages.TemplatePage;

@SuppressWarnings("serial")
@AuthorizeInstantiation(Permissions.ACCESS_HOMEPAGE)
public class SamplePage extends TemplatePage {

}
