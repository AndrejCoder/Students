package lanit.providers;


import lanit.StudentsApplication;
import lanit.models.Group;
import lanit.models.Student;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Iterator;
import java.util.List;

public class SortableGroupDataProvider extends SortableDataProvider<Group, String> {

    List<Group> allGroups;

    public SortableGroupDataProvider(){
        setSort( "name", SortOrder.ASCENDING );
    }

    @Override
    public Iterator<? extends Group> iterator(long first, long count) {
        return allGroups.iterator();
    }

    @Override
    public long size() {
        allGroups = StudentsApplication.get().getGroupDao().getAllGroups(getSort());
        return allGroups.size();
    }

    @Override
    public IModel<Group> model(Group student) {
       return new Model< Group >( student );
    }
}
