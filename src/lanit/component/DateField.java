package lanit.component;

import lanit.StudentsApplication;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.HashMap;

/**
 * Поле дата, с возможностью прямого ввода текста.
 * 
 * @author Белов А.В, Сарафанов И.В.
 */
public class DateField extends AbstractLabeledField< String > {

    private static final long serialVersionUID = 1L;

    private final DateTextField field;

    /**
     * Конструктор.
     * 
     * @param id - идентификатор Wicket
     * @param model - модель
     */
    public DateField( String id, IModel< String > model ) {
        super( id, model );

        field = new DateTextField( "field", new Model< String >() );
        field.setRequired( false );
        add( field );

        field.add( new DatePicker() {

            private static final long serialVersionUID = 1L;

            @Override
            protected boolean enableMonthYearSelection() {
                return true;
            }

        } );
    }

    @Override
    protected final void configureParameters( HashMap< String, String > parameters ) {

    }

    @Override
    public void convertInput() {

        String convertedInput = field.getConvertedInput();
        setConvertedInput( convertedInput );
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();

        String value = this.getModelObject();
        if ( value != null ) {
            field.setModelObject( value );
        }
    }

    /**
     * Поле с возмостью ввода дат и текста.
     * 
     * @author Сарафанов И.В.
     */
    private class DateTextField extends TextField< String > implements ITextFormatProvider {

        private static final long serialVersionUID = 1L;

        public DateTextField( String id, IModel< String > model ) {
            super( id, model );
        }

        @Override
        public String getTextFormat() {
            return StudentsApplication.DATE_FORMAT;
        }

    }

}
