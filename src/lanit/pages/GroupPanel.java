package lanit.pages;

import lanit.StudentsApplication;
import lanit.dao.DaoException;
import lanit.dao.GroupDao;
import lanit.models.Group;
import lanit.providers.SortableGroupDataProvider;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.ArrayList;
import java.util.List;

public class GroupPanel extends Panel {

    private AjaxFallbackDefaultDataTable groupTable;
    private Group selectedGroup;
    private Item< Group > selectedItemGroup;

    public GroupPanel(String id) {
        super(id);
        setOutputMarkupId(true);
        StudentPanel studentPanel = new StudentPanel("studentPanel", new PropertyModel<Group>(this, "selectedGroup"));
        add(studentPanel);

        final ModalWindow correctGrouptModalWindow;
        add(correctGrouptModalWindow = new ModalWindow("correctGroupModalWindow"));

        correctGrouptModalWindow.setCookieName("modal-2");
        correctGrouptModalWindow.setTitle("Изменение группы");
        correctGrouptModalWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback()
        {
            @Override
            public void onClose(AjaxRequestTarget target)
            {
                target.add(groupTable);
            }
        });



        AjaxLink addGroupLink = new AjaxLink("addGroupLink") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                correctGrouptModalWindow.setContent(new CorrectGroupWindowPanel(correctGrouptModalWindow.getContentId(), correctGrouptModalWindow));
                correctGrouptModalWindow.show(ajaxRequestTarget);
            }
        };
        add(addGroupLink);

        AjaxLink deleteGroupLink = new AjaxLink("deleteGroupLink") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                GroupDao dao = StudentsApplication.get().getGroupDao();
                try {
                    dao.deleteGroup( getSelectedGroup().getId() );
                } catch ( DaoException e ) {
                    e.printStackTrace();
                }

                ajaxRequestTarget.add(groupTable);
            }
        };
        add(deleteGroupLink);

        AjaxLink viewAllLink = new AjaxLink("viewAllLink") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                studentPanel.reset();
                ajaxRequestTarget.add(studentPanel);
                ajaxRequestTarget.add(GroupPanel.this);
            }
        };
        add(viewAllLink);



        List<IColumn<Group, String>> groupColumns = new ArrayList<>();

//        studentColumns.add(new PropertyColumn<Student, String>(Model.of("ID"), "id"));
        groupColumns.add(new PropertyColumn<Group, String>(Model.of("Наименование"), "name", "name"));

        groupTable = new AjaxFallbackDefaultDataTable<Group, String>("groupTable", groupColumns, new SortableGroupDataProvider(), 15) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onConfigure() {
                super.onConfigure();
                selectedItemGroup = null;
            }

            @Override
            protected Item< Group > newRowItem( String id, int index, final IModel< Group > model ) {

                final Item< Group > item = super.newRowItem( id, index, model );

                item.add( AttributeModifier.replace( "style", "cursor: pointer;" ) );

                if ( selectedItemGroup == null ) {
                    selectedItemGroup = item;
                }

                if ( item.getModelObject().equals(getSelectedGroup() ) ) {
                    selectedItemGroup = item;
                }

                item.add( new AjaxEventBehavior( "click" ) {

                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onEvent( AjaxRequestTarget target ) {
                        // Если до выделения новой строки была выделена ещё какая то, то
                        // снимаем с неё выделение и обновляем её
                        if ( selectedItemGroup != null ) {
                            selectedItemGroup.add( AttributeModifier.remove( "style" ) );
                            selectedItemGroup.add(AttributeModifier.replace( "style", "cursor: pointer;" ) );
                            target.add( selectedItemGroup );
                        }
                        // Выделяем новую строку
                        item.add( AttributeModifier.replace( "style", "background-color: #80b6ed;" ) );
                        // Запоминаем новую строку (для того, чтобы с неё можно было потом
                        // снять выделение при клике на другую)
                        selectedItemGroup = item;
                        // Обновляем новую строку для отображения выделения
                        target.add( item );

                        setSelectedGroup( model.getObject() );
                        target.add(studentPanel);
                    }
                } );


                item.add( new AjaxEventBehavior( "dblclick" ) {

                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onEvent( AjaxRequestTarget target ) {
                        correctGrouptModalWindow.setContent(new CorrectGroupWindowPanel(correctGrouptModalWindow.getContentId(), correctGrouptModalWindow, getSelectedGroup()));
                        correctGrouptModalWindow.show(target);
                    }
                } );
                return item;
            }
        };

        add(groupTable);
    }

    public Group getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(Group selectedGroup) {
        this.selectedGroup = selectedGroup;
    }
}
