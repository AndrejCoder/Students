package lanit.component;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.TextFilteredPropertyColumn;
import org.apache.wicket.model.IModel;

import java.util.HashMap;

/**
 * @author Сарафанов И.В.
 * @param <T>
 * @param <F>
 * @param <S>
 */
public class DateFilteredPropertyColumn< T, F, S > extends
        TextFilteredPropertyColumn< T, F, S > {

    private static final long serialVersionUID = 1L;

    /**
     * Конструктор.
     * 
     * @param displayModel Наименование столбца
     * @param propertyExpression Поле класса модели данных, для отображения.
     */
    public DateFilteredPropertyColumn(IModel< String > displayModel, final S sortProperty, String propertyExpression ) {
        super( displayModel, sortProperty, propertyExpression );
    }
    
    @Override
	public Component getFilter(final String componentId, final FilterForm<?> form)
	{
		return new DateFilter<F>(componentId, getFilterModel(form), form);
		
	}
}