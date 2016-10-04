package lanit.component;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.AbstractFilter;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.string.Strings;

/**
 * Фильтр, показывающий поле с выбором даты
 * 
 * @author Белов А.В.
 * @param <T>
 *            The {@link DateTextField}'s model object
 * 
 */
public class DateFilter<T> extends AbstractFilter
{
	private static final long serialVersionUID = 1L;

	private final DateTextField<T> filter;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            component id
	 * @param model
	 *            model for the underlying form component
	 * @param form
	 *            filter form this filter will be added to
	 */
	public DateFilter(final String id, final IModel<T> model, final FilterForm<?> form)
	{
		super( id, form );
		filter = new DateTextField<T>( id, model );
		filter.add( new DatePicker() {

            private static final long serialVersionUID = 1L;

            @Override
            protected boolean enableMonthYearSelection() {
                return true;
            }

			@Override
			protected boolean showOnFieldClick() {
				return true;
			}

			@Override
			protected boolean autoHide() {
				return true;
			}

			@Override
			protected String getIconStyle() {
				return "display: none;";
			}

			@Override
			public void afterRender(Component component) {
				Response response = component.getResponse();
				response.write("\n<span class=\"yui-skin-sam\"><span style=\"");

				if (renderOnLoad())
				{
					response.write("display:block;");
				}
				else
				{
					response.write("display:none;");
					response.write("position:absolute;");
				}

				response.write("z-index: 99999;\" id=\"");
				response.write(getEscapedComponentMarkupId());
				response.write("Dp\"></span><img style=\"");
				response.write(getIconStyle());
				response.write("\" id=\"");
				response.write(getIconId());
				response.write("\" src=\"");
				CharSequence iconUrl = getIconUrl();
				response.write(Strings.escapeMarkup(iconUrl != null ? iconUrl.toString() : ""));
				response.write("\" alt=\"");
				CharSequence alt = getIconAltText();
				response.write(Strings.escapeMarkup((alt != null) ? alt.toString() : ""));
				response.write("\" title=\"");
				CharSequence title = getIconTitle();
				response.write(Strings.escapeMarkup((title != null) ? title.toString() : ""));
				response.write("\"/>");

				if (renderOnLoad())
				{
					response.write("<br style=\"clear:left;\"/>");
				}
				response.write("</span>");
			}			
        } );
		enableFocusTracking( filter );
		filter.setType( String.class );
		add( filter );
	}

	/**
	 * @return underlying {@link DateTextField} form component that represents this filter
	 */
	public final DateTextField<T> getFilter() {
		return filter;
	}
}