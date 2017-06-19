package de.akquinet.engineering.vaadin;

import com.vaadin.navigator.NavigationStateManager;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.shared.Registration;

/**
 * A {@link NavigationStateManager} using the HTML5 History API to track views and enable listening to view changes.
 * <p>
 * With this class the state won't appear in fragments after a hashbang ("#!"), but as part of the path.
 * For example http://localhost:8080/#!myview/myparameters is replaced by http://localhost:8080/myview/myparameters.
 *
 * @author Axel Meier, akquinet engineering GmbH
 */
public class HistoryApiNavigationStateManager implements NavigationStateManager
{
    private final Page page;
    private final String contextPath;
    private Navigator navigator;
    private Registration popStateListenerRegistration;

    public HistoryApiNavigationStateManager(final Page page, final String contextPath)
    {
        this.page = page;
        this.contextPath = contextPath == null || contextPath.isEmpty() ? "/" : contextPath;
    }

    @Override
    public String getState()
    {
        String state = page.getLocation().getPath();

        if (state == null) {
            state ="/";
        }

        return state.substring(contextPath.length());
    }

    @Override
    public void setState(final String state)
    {
        final String newState = state != null ? state : "";
        pushState(newState.startsWith("/") ? newState.substring(1) : newState);
    }

    private void pushState(final String state) {
        page.pushState(contextPath + state);
    }

    @Override
    public void setNavigator(final Navigator navigator)
    {
        if (this.navigator == null && navigator != null)
        {
            popStateListenerRegistration = page.addPopStateListener(event -> navigator.navigateTo(getState()));
        }
        else if (this.navigator != null && navigator == null)
        {
            popStateListenerRegistration.remove();
        }
        this.navigator = navigator;
    }
}
