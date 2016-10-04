package lanit.dao;

import lanit.models.Student;
import lanit.util.ResultHolder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;

import java.util.HashMap;
import java.util.List;

/**
 * @author Белов А.В.
 */
public class StudentDaoImpl extends SqlMapTemplate implements StudentDao {

	public StudentDaoImpl(SqlSessionFactory sqlMapper) {
		super(sqlMapper);
	}


	@Override
	public List<Student> getAllStudents(SortParam< String > sortParam, Student filter) {
		return getAllStudents(sortParam, filter, null);
	}

	@Override
	public List<Student> getAllStudents(SortParam< String > sortParam, Student filter, Integer groupId) {
		HashMap< String, Object > params = new HashMap< String, Object >();
		HashMap< String, Object > filters = new HashMap< String, Object >();
		filters.put("last_name", filter.getLastName());
		filters.put("first_name", filter.getFirstName());
		filters.put("second_name", filter.getSecondName());
		filters.put("birthday", filter.getBirthday());
		if (filter.getGroup() != null) {filters.put("name", filter.getGroup().getName());}
		params.put( "filters", filters);
		params.put( "group_id", groupId);
		params.put( "sort", sortParam );
		return (List<Student>) queryForList("Student.findAllStudents", params);
	}

	@Override
	public void createStudent(Student newStudent) throws DaoException {
		HashMap< String, Object > params = new HashMap< String, Object >();
		params.put( "student", newStudent );
		queryForObject( "Student.insertStudent", params );
	}

	@Override
	public void updateStudent(Student newStudent) throws DaoException {
		HashMap< String, Object > params = new HashMap< String, Object >();
		params.put( "student", newStudent );
		queryForObject( "Student.updateStudent", params );
	}

	@Override
	public void deleteStudent(Integer id) throws DaoException {
		HashMap< String, Object > params = new HashMap< String, Object >();
		params.put( "id", id );
		queryForObject( "Student.deleteStudent", params );
	}
}