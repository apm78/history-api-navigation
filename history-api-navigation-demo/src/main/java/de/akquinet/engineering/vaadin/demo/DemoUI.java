package de.akquinet.engineering.vaadin.demo;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import de.akquinet.engineering.vaadin.HistoryApiNavigatorFactory;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.annotation.WebServlet;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Theme("demo_theme")
@Title("HistoryApiNavigationStateManager Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI
{

    @WebServlet(value = { "/demo/*", "/VAADIN/*" }, asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends VaadinServlet
    {
    }

    @Override
    protected void init(VaadinRequest request)
    {
        setLocale(Locale.US);

        final HorizontalLayout rootLayout = new HorizontalLayout();
        rootLayout.setSpacing(false);
        rootLayout.setSizeFull();

        final VerticalLayout navigationLayout = new VerticalLayout();
        navigationLayout.setWidth(null);
        final Label title = new Label("HTML5 History API<br>Navigation", ContentMode.HTML);
        title.setStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);
        navigationLayout.addComponent(title);
        rootLayout.addComponent(navigationLayout);

        final TextField param1Field = new TextField("Parameter 1");
        final TextField param2Field = new TextField("Parameter 2");

        final Button homeButton = new Button("Home View",
                event -> getNavigator().navigateTo(HomeView.VIEW_NAME + "/" +
                        getParameters(param1Field.getValue(), param2Field.getValue())));
        navigationLayout.addComponent(homeButton);

        final Button parameterButton = new Button("Other View",
                event -> getNavigator().navigateTo(OtherView.VIEW_NAME + "/" +
                        getParameters(param1Field.getValue(), param2Field.getValue())));
        navigationLayout.addComponents(parameterButton, param1Field, param2Field);

        final Panel contentPanel = new Panel();
        contentPanel.setSizeFull();
        rootLayout.addComponent(contentPanel);
        rootLayout.setExpandRatio(contentPanel, 1.0f);

        setNavigator(HistoryApiNavigatorFactory.createHistoryApiNavigator(this, new CustomViewDisplay(contentPanel)));

        final HomeView homeView = new HomeView();
        getNavigator().addView(HomeView.VIEW_NAME, homeView);
        getNavigator().addView(OtherView.VIEW_NAME, new OtherView());

        setContent(rootLayout);
    }

    private static String getParameters(final String param1, final String param2)
    {
        return Stream.of(param1, param2)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining("/"));
    }
}
