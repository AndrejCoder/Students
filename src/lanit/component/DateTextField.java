package lanit.component;

import lanit.StudentsApplication;
import org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

/**
 * Компонент для выгрузки файла с диска. Использует модель с типом
 * 
 * @author Сарафанов И.В.
 */
public class DateTextField<T> extends TextField< T > implements ITextFormatProvider {

    private static final long serialVersionUID = 1L;

    public DateTextField(String id, IModel< T > model ) {
        super( id, model );
    }

    @Override
    public String getTextFormat() {
        return StudentsApplication.DATE_FORMAT;
    }
}