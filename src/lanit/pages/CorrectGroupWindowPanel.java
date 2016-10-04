package lanit.pages;

import lanit.StudentsApplication;
import lanit.dao.DaoException;
import lanit.dao.GroupDao;
import lanit.models.Group;
import lanit.util.FeedbackLabel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SimpleFormComponentLabel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;


public class CorrectGroupWindowPanel extends Panel {

    private static final long serialVersionUID = 1L;

    private Group newGroup;

    public CorrectGroupWindowPanel(String id, final ModalWindow window) {
        this(id, window, new Group());
    }

    public CorrectGroupWindowPanel(String id, final ModalWindow window, Group group) {
        super(id);

        this.newGroup = group;

        Form<Group> form = new Form<Group>( "form", new Model<Group>(newGroup) );
        add( form );

        TextField<String> nameField = new TextField<String>("nameField", new PropertyModel< String >( this, "newGroup.name" ));
        nameField.setRequired(true);
        nameField.setLabel(Model.of("Наименование"));
        form.add(nameField);
        form.add( new SimpleFormComponentLabel( "name-label", nameField ) );

        final FeedbackLabel nameFeedbackLabel = new FeedbackLabel( "nameFeedbackLabel", nameField );
        nameFeedbackLabel.setOutputMarkupId( true );
        form.add( nameFeedbackLabel );


        form.add(new AjaxSubmitLink("closeOK") {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                GroupDao dao = StudentsApplication.get().getGroupDao();
                try {
                    if (newGroup.getId() == null) {
                        dao.createGroup(newGroup);
                    } else {
                        dao.updateGroup(newGroup);
                    }
                } catch ( DaoException e ) {
                    e.printStackTrace();
                }
                window.close(target);
            }

            @Override
            protected void onError( AjaxRequestTarget target, Form<?> form ) {
                target.add( nameFeedbackLabel );
            }
        });

        form.add(new AjaxLink<Void>("closeCancel") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                window.close(target);
            }
        });
    }
}
