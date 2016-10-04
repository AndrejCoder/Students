package lanit.component;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.feedback.FeedbackCollector;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Класс для работы с сообщениями об ошибке.
 */
public class FeedbackLabel extends Label {

    private static final long serialVersionUID = 1L;

    private final FormComponent<?> component;
    private IModel<?> text = null;

    /**
     * Конструктор.
     * @param id
     *            Идентификатор.
     * @param component
     *            Компонент формы, к которому привязано сообщение.
     */
    public FeedbackLabel(String id, FormComponent<?> component ) {
        super( id );
        this.setOutputMarkupId( true );
        this.component = component;
    }

    /**
     * Конструктор.
     * @param id
     *            Идентификатор.
     * @param component
     *            Компонент формы, к которому привязано сообщение.
     * @param text
     *            Текстовое сообщение.
     */
    public FeedbackLabel(String id, FormComponent<?> component, String text ) {
        this( id, component, extracted( text ) );
    }

    /**
     * Конструктор.
     * @param id
     *            Идентификатор.
     * @param component
     *            Компонент формы.
     * @param iModel
     *            Модель.
     */
    public FeedbackLabel(String id, FormComponent<?> component, IModel<?> iModel ) {
        super( id );
        this.setOutputMarkupId( true );
        this.component = component;
        this.text = iModel;
    }

    private static Model<String> extracted(String text ) {
        return new Model<String>( text );
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        this.setDefaultModel( null );

        FeedbackCollector fbc = new FeedbackCollector( component );
        List<FeedbackMessage> feedbacks = fbc.collect();
        StringBuilder output = new StringBuilder();

        if ( feedbacks.size() > 0 ) {

            output.append( feedbacks.get( 0 ).getMessage() );

            // for ( FeedbackMessage feedback : feedbacks ) {
            // output.append( feedback.getMessage() );
            // }

            if ( this.text != null ) {
                this.setDefaultModel( text );
            } else {
                this.setDefaultModel( new Model<Serializable>( output ) );
            }
            this.add( AttributeModifier.replace( "class", new Model<String>( "feedbacklabel "
                    + "ERROR" ) ) );
        } else {
            this.setDefaultModel( null );
        }
    }
}
