package ${package}.pages.users;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;

import com.premiumminds.webapp.wicket.bootstrap.BootstrapModal;

public abstract class ConfirmModal extends BootstrapModal{
	private static final long serialVersionUID = 1L;
	
	public ConfirmModal(String id) {
		super(id);
	}

	@Override 
	public void onInitialize(){
		super.onInitialize();

		/**
		 * Confirm buttom
		 */
		AjaxLink<Void> confirmBtn = new AjaxLink<Void>("closeOk") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				hide(target);
				callOnSubmit(target);
			}
		};
		add(confirmBtn);
	}

	public abstract void callOnSubmit(AjaxRequestTarget target);

}
