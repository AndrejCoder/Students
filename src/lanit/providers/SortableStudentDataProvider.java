package lanit.providers;


import lanit.StudentsApplication;
import lanit.models.Group;
import lanit.models.Student;
import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Iterator;
import java.util.List;

public class SortableStudentDataProvider extends SortableDataProvider<Student, String>
                                         implements IFilterStateLocator<Student> {

    private List<Student> allStudents;
    private IModel<Group> group;
    private Student filterBean = new Student();

    public SortableStudentDataProvider(IModel<Group> selectedGroup){
        this.group = selectedGroup;
        setSort( "lastName", SortOrder.ASCENDING );
    }

    @Override
    public Iterator<? extends Student> iterator(long first, long count) {
        return allStudents.iterator();
    }

    @Override
    public long size() {
        Integer groupId = null;
        if (group != null && group.getObject() != null){
            groupId = group.getObject().getId();
        }
        allStudents = StudentsApplication.get().getStudentDao().getAllStudents(getSort(), getFilterState(), groupId);
        return allStudents.size();
    }

    @Override
    public IModel<Student> model(Student student) {
       return new Model< Student >( student );
    }

    @Override
    public final Student getFilterState() {
        return filterBean;
    }

    @Override
    public final void setFilterState( Student state ) {
        filterBean = state;
    }
}
