package de.akquinet.engineering.vaadin.demo;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.Component;
import com.vaadin.ui.SingleComponentContainer;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class CustomViewDisplay implements ViewDisplay
{
    private final SingleComponentContainer contentNode;

    public CustomViewDisplay(final SingleComponentContainer contentNode)
    {
        this.contentNode = contentNode;
    }

    @Override
    public void showView(final View view)
    {
        if (view instanceof ComponentView)
        {
            final Component newContent = ((ComponentView) view)
                    .getComponent();
            contentNode.setContent(newContent);
        }
        else
        {
            throw new IllegalArgumentException("View is not a de.akquinet.engineering.notebook.ui.View: "
                    + view);
        }
    }
}
