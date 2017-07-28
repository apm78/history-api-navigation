package de.akquinet.engineering.vaadin.demo;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class HomeView implements ComponentView, View
{
    public static final String VIEW_NAME = "";

    private final VerticalLayout rootLayout = new VerticalLayout();
    private final Label label = new Label();

    public HomeView()
    {
        final Label title = new Label("Home View");
        title.setStyleName(ValoTheme.LABEL_H2);
        rootLayout.addComponent(title);
        rootLayout.addComponent(label);
    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent event)
    {
        final String parameters = event.getParameters();
        final String[] parameterArray = parameters.split("/");
        label.setValue("Parameters: " + String.join(", ", parameterArray));
    }

    @Override
    public Component getComponent()
    {
        return rootLayout;
    }
}
