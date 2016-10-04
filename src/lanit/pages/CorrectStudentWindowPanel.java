package lanit.pages;

import lanit.StudentsApplication;
import lanit.component.DateField;
import lanit.component.GroupDropDownChoice;
import lanit.dao.DaoException;
import lanit.dao.StudentDao;
import lanit.models.Group;
import lanit.models.Student;
import lanit.util.FeedbackLabel;
import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SimpleFormComponentLabel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.Date;
import java.util.List;


public class CorrectStudentWindowPanel extends Panel {

    private static final long serialVersionUID = 1L;

    private Student newStudent;

    public CorrectStudentWindowPanel(String id, final ModalWindow window) {
        this(id, window, new Student());
    }

    public CorrectStudentWindowPanel(String id, final ModalWindow window, Student student) {
        super(id);
        this.newStudent = student;

        List<Group> groupList = StudentsApplication.get().getGroupDao().getAllGroups();

        Form<Student> form = new Form<Student>( "form", new Model<Student>( newStudent ) );
        add( form );

        GroupDropDownChoice formChoice =
                new GroupDropDownChoice( "formChoice", new PropertyModel< Group >( this,
                        "newStudent.group" ), groupList );
        formChoice.setLabel(Model.of("Группа"));
        form.add(formChoice);
        form.add( new SimpleFormComponentLabel( "formChoice-label", formChoice ) );

        TextField<String> lastNameField = new TextField<String>("lastNameField", new PropertyModel< String >( this, "newStudent.lastName" ));
        lastNameField.setRequired(true);
        lastNameField.setLabel(Model.of("Фамилия"));
        form.add(lastNameField);
        form.add( new SimpleFormComponentLabel( "last-name-label", lastNameField ) );

        final FeedbackLabel lastNameFeedbackLabel = new FeedbackLabel( "lastNameFeedbackLabel", lastNameField );
        lastNameFeedbackLabel.setOutputMarkupId( true );
        form.add( lastNameFeedbackLabel );

        TextField<String> firstNameField = new TextField<String>("firstNameField", new PropertyModel< String >( this, "newStudent.firstName" ));
        firstNameField.setRequired(true);
        firstNameField.setLabel(Model.of("Имя"));
        form.add(firstNameField);
        form.add( new SimpleFormComponentLabel( "first-name-label", firstNameField ) );

        final FeedbackLabel firstNameFeedbackLabel = new FeedbackLabel( "firstNameFeedbackLabel", firstNameField );
        firstNameFeedbackLabel.setOutputMarkupId( true );
        form.add( firstNameFeedbackLabel );

        TextField<String> secondNameField = new TextField<String>("secondNameField", new PropertyModel< String >( this, "newStudent.secondName" ));
        secondNameField.setLabel(Model.of("Отчество"));
        form.add(secondNameField);
        form.add( new SimpleFormComponentLabel( "second-name-label", secondNameField ) );


        final FeedbackLabel secondNameFeedbackLabel = new FeedbackLabel( "secondNameFeedbackLabel", secondNameField );
        secondNameFeedbackLabel.setOutputMarkupId( true );
        form.add( secondNameFeedbackLabel );




        DateField birthdayField = new DateField("birthdayField", new PropertyModel< String >( this, "newStudent.birthday" ));
        birthdayField.setLabel(Model.of("Дата рождения"));
        form.add(birthdayField);
//        form.add( new SimpleFormComponentLabel( "birthday-label", birthdayField ) );


        final FeedbackLabel birthdayFeedbackLabel = new FeedbackLabel( "birthdayFeedbackLabel", birthdayField );
        birthdayFeedbackLabel.setOutputMarkupId( true );
        form.add( birthdayFeedbackLabel );

//        final Component feedback = new FeedbackPanel( "feedback" );
//        feedback.setOutputMarkupId( true );
//        form.add( feedback );


        form.add(new AjaxSubmitLink("closeOK") {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                StudentDao dao = StudentsApplication.get().getStudentDao();
                try {
                    if (newStudent.getId() == null) {
                        dao.createStudent(newStudent);
                    } else {
                        dao.updateStudent(newStudent);
                    }
                } catch ( DaoException e ) {
                    e.printStackTrace();
                }
                window.close(target);
            }

            @Override
            protected void onError( AjaxRequestTarget target, Form<?> form ) {
//                target.add( feedback );
                target.add( lastNameFeedbackLabel );
                target.add( firstNameFeedbackLabel );
                target.add( secondNameFeedbackLabel );
                target.add( birthdayFeedbackLabel );
//                target.add( nameFeedBackLabel );
//                target.add( parent );
//                form.clearInput();
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
