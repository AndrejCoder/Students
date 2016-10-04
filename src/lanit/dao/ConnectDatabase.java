/**
 * 
 */
package lanit.dao;


/**
 * @author Белов А.В.
 *
 */
public class ConnectDatabase {

	private ViewerDatabase database;
	
	public ConnectDatabase(){
		database= new ViewerDatabase();
	}
	
	public StudentDao getStudentDao() {
        return database.getStudentDao();
    }
}