package lanit;

import lanit.dao.GroupDao;
import lanit.dao.StudentDao;
import lanit.dao.ViewerDatabase;
import lanit.pages.HomePage;
import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.time.Duration;

import java.lang.ref.WeakReference;
import java.util.Locale;


public class StudentsApplication extends WebApplication {

    private static final int TIMEOUT_DURATION_MINUTES = 10;

    /**
     * Константа с форматом даты.
     */
    public static final String DATE_FORMAT = "dd.MM.yyyy";

    private final WeakReference< Class< ? extends WebSession>> webSessionClassRef;

    /**
     * Корневая папка для отчетов.
     */
//    public static final String REPORTS_ROOT = "/WEB-INF/reports";

    private ViewerDatabase database;

    public StudentsApplication() {
        super();
        webSessionClassRef =
                new WeakReference< Class< ? extends WebSession >>(
                        getWebSessionClass() );
    }

    protected final Class< ? extends WebSession > getWebSessionClass() {
        return StudentsSession.class;
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected final void init() {

        super.init();

        database = new ViewerDatabase();

        Locale locale = new Locale( "ru", "RU" );
        Locale.setDefault( locale );

        getMarkupSettings().setDefaultMarkupEncoding( "utf-8" );

//        mountPage( REDIRECT_AFTER_LOGIN_PATH, RedirectAfterLoginPage.class );
//        mountPage( REDIRECT_BUSINESS_PAGE_PATH, BusinessPage.class );

//        getAjaxRequestTargetListeners().add( AjaxLinkedComponentListener.get() );
//        getRequestCycleListeners().add( ErrorListener.get() );

        getRequestCycleSettings().setTimeout( Duration.minutes( TIMEOUT_DURATION_MINUTES ) );

//        // Замена стандартной страницы ошибки InternalErrorPage
//        getApplicationSettings().setInternalErrorPage( CustomInternalErrorPage.class );
//
//        // Замена стандартной страницы ошибки AccessDeniedPage
//        getApplicationSettings().setAccessDeniedPage( CustomAccessDeniedErrorPage.class );
//
//        // Замена стандартной страницы ошибки PageExpiredErrorPage
//        getApplicationSettings().setPageExpiredErrorPage( CustomPageExpiredErrorPage.class );
    }

    /**
     * @return Вызов метода get у класса Application (null, если приложение не найдено).
     */
    public static StudentsApplication get() {
        return (StudentsApplication) Application.get();
    }

    @Override
    public Session newSession(final Request request, final Response response ) {

        try {
            return webSessionClassRef.get().getDeclaredConstructor( Request.class )
                    .newInstance( request );
        } catch ( Exception e ) {
            throw new WicketRuntimeException( "Unable to instantiate web session "
                    + webSessionClassRef.get(), e );
        }
    }

    /**
     * @return Возвращает объект ProcessDao (бизнес-процесс).
     */
    public StudentDao getStudentDao() {
        return database.getStudentDao();
    }
    public GroupDao getGroupDao() { return database.getGroupDao(); }
}
