package lanit.pages;

import lanit.StudentsApplication;
import lanit.component.DateFilteredPropertyColumn;
import lanit.dao.DaoException;
import lanit.dao.StudentDao;
import lanit.models.Group;
import lanit.models.Student;
import lanit.providers.SortableStudentDataProvider;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.ChoiceFilteredPropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.TextFilteredPropertyColumn;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.ArrayList;
import java.util.List;


public class StudentPanel extends Panel {

    private static final long serialVersionUID = 1L;

    private AjaxFallbackDefaultDataTable studentTable;

    private Student selectedStudent;

    private Item< Student > selectedItemStudent;
    private IModel<Group> selectedGroup;

    List<String> groupNameList;

    public StudentPanel(String id, IModel<Group> selectedGroup) {
        super(id);
        setOutputMarkupId(true);
        this.selectedGroup = selectedGroup;
        final ModalWindow correctStudentModalWindow;
        add(correctStudentModalWindow = new ModalWindow("correctStudentModalWindow"));

        correctStudentModalWindow.setCookieName("modal-1");
        correctStudentModalWindow.setTitle("Изменение студента");
        correctStudentModalWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback()
        {
            @Override
            public void onClose(AjaxRequestTarget target)
            {
                target.add(studentTable);
            }
        });

        AjaxLink addLink = new AjaxLink("addLink") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                correctStudentModalWindow.setContent(new CorrectStudentWindowPanel(correctStudentModalWindow.getContentId(), correctStudentModalWindow));
                correctStudentModalWindow.show(ajaxRequestTarget);
            }
        };
        add(addLink);

        AjaxLink deleteLink = new AjaxLink("deleteLink") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                StudentDao dao = StudentsApplication.get().getStudentDao();
                try {
                    dao.deleteStudent( getSelectedStudent().getId() );
                } catch ( DaoException e ) {
                    e.printStackTrace();
                }

                ajaxRequestTarget.add(studentTable);
            }
        };
        add(deleteLink);

        List<IColumn<Student, String>> studentColumns = new ArrayList<>();

        List<Group> groupList = StudentsApplication.get().getGroupDao().getAllGroups();
        groupNameList = new ArrayList<>();
        for(Group gr: groupList){
            groupNameList.add(gr.getName());
        }

//        studentColumns.add(new PropertyColumn<Student, String>(Model.of("ID"), "id"));
        studentColumns.add(new TextFilteredPropertyColumn<Student, String, String>(Model.of("Фамилия"), "lastName", "lastName"));
        studentColumns.add(new TextFilteredPropertyColumn<Student, String, String>(Model.of("Имя"), "firstName", "firstName"));
        studentColumns.add(new TextFilteredPropertyColumn<Student, String, String>(Model.of("Отчество"), "secondName"));
        studentColumns.add(new DateFilteredPropertyColumn< Student, String, String >(Model.of("Дата рождения"), "birthday", "birthday"));
        studentColumns.add(new ChoiceFilteredPropertyColumn<Student, String, String>(Model.of("Группа"), "group.name", new PropertyModel<List<? extends String>>(this, "groupNameList")));

        SortableStudentDataProvider provider = new SortableStudentDataProvider(selectedGroup);
        FilterForm<Student> form = new FilterForm<Student>( "form", provider );

        studentTable = new AjaxFallbackDefaultDataTable<Student, String>("studentTable", studentColumns, provider, 15) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onConfigure() {
                super.onConfigure();
                selectedItemStudent = null;
            }

            @Override
            protected Item< Student > newRowItem( String id, int index, final IModel< Student > model ) {

                final Item< Student > item = super.newRowItem( id, index, model );

                item.add( AttributeModifier.replace( "style", "cursor: pointer;" ) );

                if ( selectedItemStudent == null ) {
                    selectedItemStudent = item;
                }

                if ( item.getModelObject().equals(getSelectedStudent() ) ) {
                    selectedItemStudent = item;
                }

                item.add( new AjaxEventBehavior( "click" ) {

                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onEvent( AjaxRequestTarget target ) {
                        // Если до выделения новой строки была выделена ещё какая то, то
                        // снимаем с неё выделение и обновляем её
                        if ( selectedItemStudent != null ) {
                            selectedItemStudent.add( AttributeModifier.remove( "style" ) );
                            selectedItemStudent.add(AttributeModifier.replace( "style", "cursor: pointer;" ) );
                            target.add( selectedItemStudent );
                        }
                        // Выделяем новую строку
                        item.add( AttributeModifier.replace( "style", "background-color: #80b6ed;" ) );
                        // Запоминаем новую строку (для того, чтобы с неё можно было потом
                        // снять выделение при клике на другую)
                        selectedItemStudent = item;
                        // Обновляем новую строку для отображения выделения
                        target.add( item );

                        setSelectedStudent( model.getObject() );
                    }
                } );


                item.add( new AjaxEventBehavior( "dblclick" ) {

                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onEvent( AjaxRequestTarget target ) {
                        correctStudentModalWindow.setContent(new CorrectStudentWindowPanel(correctStudentModalWindow.getContentId(), correctStudentModalWindow, getSelectedStudent()));
                        correctStudentModalWindow.show(target);
                    }
                } );
                return item;
            }
        };
        FilterToolbar searchToolbar = new FilterToolbar( studentTable, form );
        studentTable.addTopToolbar( searchToolbar );

        form.add(studentTable);
        add(form);
    }

    public AjaxFallbackDefaultDataTable getStudentTable() {
        return studentTable;
    }

    public void setStudentTable(AjaxFallbackDefaultDataTable studentTable) {
        this.studentTable = studentTable;
    }

    public Student getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public void reset(){
        this.selectedGroup.setObject(null);
    }
}
