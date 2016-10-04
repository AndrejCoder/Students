package lanit.dao;

import lanit.models.Group;
import lanit.models.Group;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;

import java.util.HashMap;
import java.util.List;

/**
 * @author Белов А.В.
 */
public class GroupDaoImpl extends SqlMapTemplate implements GroupDao {

	public GroupDaoImpl(SqlSessionFactory sqlMapper) {
		super(sqlMapper);
	}

	@Override
	public List<Group> getAllGroups(SortParam< String > sortParam) {
		HashMap< String, Object > params = new HashMap< String, Object >();
		params.put( "sort", sortParam );
		return (List<Group>) queryForList("Group.findAllGroups", params);
	}

	@Override
	public List<Group> getAllGroups() {
		return getAllGroups(null);
	}

	@Override
	public void createGroup(Group newGroup) throws DaoException {
		HashMap< String, Object > params = new HashMap< String, Object >();
		params.put( "group", newGroup );
		queryForObject( "Group.insertGroup", params );
	}

	@Override
	public void updateGroup(Group newGroup) throws DaoException {
		HashMap< String, Object > params = new HashMap< String, Object >();
		params.put( "group", newGroup );
		queryForObject( "Group.updateGroup", params );
	}

	@Override
	public void deleteGroup(Integer id) throws DaoException {
		HashMap< String, Object > params = new HashMap< String, Object >();
		params.put( "id", id );
		queryForObject( "Group.deleteGroup", params );
	}
}