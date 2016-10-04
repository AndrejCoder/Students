package lanit;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * Сессия приложения.
 * 
 * @author Дусенбаев Д.В.
 */
public class StudentsSession extends WebSession {

    /**
     * Константа.
     */
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger( StudentsSession.class );

    /**
     * Конструктор.
     *
     * @param request The current request object
     */
    public StudentsSession(final Request request ) {
        super( request );

    }

    @Override
    public Session setLocale(Locale locale) {
        return super.setLocale( Locale.FRANCE );
    }

    /**
     * Возвращает текущую сессию.
     * 
     * @return Ссылка на сессию.
     */
    public static StudentsSession get() {
        return (StudentsSession) Session.get();
    }

}
