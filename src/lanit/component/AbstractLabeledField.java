package lanit.component;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.HashMap;

/**
 * Базовый класс, прародитель всех полей в системе.
 * 
 * @param <T> - базовый тип
 * @author Сарафанов И.В.
 */
public abstract class AbstractLabeledField< T > extends FormComponentPanel< T > {

    private static final long serialVersionUID = 1L;

    private final Label redStar;
    private final Label label;
    private final FeedbackLabel feedback;

    // параметры
    private final HashMap< String, String > params = new HashMap< String, String >();

    /**
     * Конструктор.
     * 
     * @param id - идентификатор Wicket
     * @param model - модель
     */
    public AbstractLabeledField( String id, IModel< T > model ) {
        super( id, model );
        setOutputMarkupId( true );

        configureParameters( params );

        label = new Label( "label", new Model< String >() );
        add( label );

        redStar = new Label( "redStar", "*" );
        add( redStar );

        feedback = new FeedbackLabel( "feedback", this );
        add( feedback );

    }

    protected abstract void configureParameters( HashMap< String, String > parameters );

    protected HashMap< String, String > getParams() {
        return params;
    }

    @Override
    protected void onConfigure() {

        redStar.setVisible( isRequired() );

    }

    @Override
    public void convertInput() {

    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();

        IModel< String > labelModel = this.getLabel();
        if ( labelModel != null ) {
            label.setDefaultModel( labelModel );
        }
    }

    /**
     * @return возвращает фидбек для компонента
     */
    public final FeedbackLabel getFeedback() {
        return feedback;
    }

}
