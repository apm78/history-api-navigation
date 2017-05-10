package de.akquinet.engineering.vaadin;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.Page;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.SingleComponentContainer;
import com.vaadin.ui.UI;

/**
 * This {@link Navigator} factor provides functions to easily create a Navigator
 * that works with the HTML5 History API instead of using the URI fragments.
 *
 * @author Axel Meier, akquinet engineering GmbH
 */
public final class HistoryApiNavigatorFactory
{
    private HistoryApiNavigatorFactory()
    {
    }

    /**
     * Creates a navigator that is tracking the active view using the HTML5 History API
     * of the {@link Page} containing the given UI and replacing the contents of
     * a {@link ComponentContainer} with the active view.
     * <p>
     * All components of the container are removed each time before adding the
     * active {@link View}. Views must implement {@link Component} when using
     * this constructor.
     * <p>
     * Navigation is automatically initiated after {@code UI.init()} if a
     * navigator was created. If at a later point changes are made to the
     * navigator, {@code navigator.navigateTo(navigator.getState())} may need to
     * be explicitly called to ensure the current view matches the navigation
     * state.
     *
     * @param ui
     *            The UI to which this Navigator is attached.
     * @param container
     *            The ComponentContainer whose contents should be replaced with
     *            the active view on view change
     */
    public static Navigator createHistoryApiNavigator(final UI ui, final ComponentContainer container){
        return createHistoryApiNavigator(ui, new Navigator.ComponentContainerViewDisplay(container));
    }

    /**
     * Creates a navigator that is tracking the active view using the HTML5 History API
     * of the {@link Page} containing the given UI and replacing the contents of
     * a {@link SingleComponentContainer} with the active view.
     * <p>
     * Views must implement {@link Component} when using this constructor.
     * <p>
     * Navigation is automatically initiated after {@code UI.init()} if a
     * navigator was created. If at a later point changes are made to the
     * navigator, {@code navigator.navigateTo(navigator.getState())} may need to
     * be explicitly called to ensure the current view matches the navigation
     * state.
     *
     * @param ui
     *            The UI to which this Navigator is attached.
     * @param container
     *            The SingleComponentContainer whose contents should be replaced
     *            with the active view on view change
     */
    public static Navigator createHistoryApiNavigator(final UI ui, final SingleComponentContainer container){
        return createHistoryApiNavigator(ui, new Navigator.SingleComponentContainerViewDisplay(container));
    }

    /**
     * Creates a navigator that is tracking the active view using the HTML5 History API
     * of the {@link Page} containing the given UI.
     * <p>
     * Navigation is automatically initiated after {@code UI.init()} if a
     * navigator was created. If at a later point changes are made to the
     * navigator, {@code navigator.navigateTo(navigator.getState())} may need to
     * be explicitly called to ensure the current view matches the navigation
     * state.
     *
     * @param ui
     *            The UI to which this Navigator is attached.
     * @param display
     *            The ViewDisplay used to display the views.
     */
    public static Navigator createHistoryApiNavigator(final UI ui, final ViewDisplay display){
        return new Navigator(ui, new HistoryApiNavigationStateManager(ui.getPage()), display);
    }
}
