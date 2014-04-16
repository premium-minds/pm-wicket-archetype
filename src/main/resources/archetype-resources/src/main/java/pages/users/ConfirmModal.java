package ${package}.pages.users;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;

public abstract class ConfirmModal extends Panel{
	private static final long serialVersionUID = 1L;

	public ConfirmModal(String id) {
		super(id);
	}

	@Override 
	public void onInitialize(){
		super.onInitialize();
		/**
		 * Cancel button
		 */
		AjaxLink<Void> closeBtn = new AjaxLink<Void>("closeCancel") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				callSubmit(target);
			}
		};

		/**
		 * Confirm buttom
		 */
		AjaxLink<Void> confirmBtn = new AjaxLink<Void>("closeOk") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				callSubmit(target);
				callOnSubmit(target);
			}
		};
		add(closeBtn);
		add(confirmBtn);
	}

	protected void callSubmit(AjaxRequestTarget target) {
		target.prependJavaScript("$('#"+ConfirmModal.this.getMarkupId()+"').modal('hide'); $('.modal-backdrop').remove();");		
	}

	public abstract void callOnSubmit(AjaxRequestTarget target);

}
