package lanit.dao;


import lanit.models.Student;
import lanit.util.ResultHolder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;

import java.util.HashMap;
import java.util.List;

/**
 * @author Белов А.В.
 */
public interface StudentDao {
	
	List<Student> getAllStudents(SortParam< String > sortParam, Student filter);
	List<Student> getAllStudents(SortParam< String > sortParam, Student filter, Integer groupId);

	void createStudent(Student newStudent) throws DaoException;
	void updateStudent(Student newStudent) throws DaoException;
	void deleteStudent(Integer id) throws DaoException;
}