package lanit.dao;

/**
 * Класс для ошибок, возникающих при вызове методов DAO. Если метод вызывал серверную процедуру, то
 * в message будет содержаться сообщение, возвращенное серверной процедурой.
 * 
 * @author Белов А.В.
 */
public class DaoException extends Exception {

    /**
     * Константа.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message Сообщение об ошибке.
     */
    public DaoException( final String message ) {
        super( message );
    }

    /**
     * @param message Сообщение об ошибке.
     * @param cause Причина.
     */
    public DaoException( final String message, final Throwable cause ) {
        super( message, cause );
    }
}
