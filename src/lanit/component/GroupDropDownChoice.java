package lanit.component;

import lanit.models.Group;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.util.List;

public class GroupDropDownChoice extends DropDownChoice<Group> {

    public GroupDropDownChoice( String id, IModel< Group > selectionModel,
                           List< Group > choices ) {
        super( id, selectionModel, choices );
        super.setChoiceRenderer( new ElementRenderer() );
    }

    /**
     * Компонент для отображения вариантов выбора.
     */
    private class ElementRenderer implements IChoiceRenderer< Group > {

        private static final long serialVersionUID = 1L;

        @Override
        public Object getDisplayValue( final Group obj ) {
            return obj.getName();
        }

        @Override
        public String getIdValue( final Group obj, final int position ) {
            return String.valueOf( obj.getId() );
        }

        @Override
        public Group getObject(String s, IModel<? extends List<? extends Group>> iModel) {
            for (Group group: iModel.getObject()) {
                if (String.valueOf(group.getId()).equals(s)) {
                    return group;
                }
            }
            return null;
        }
    }
}
