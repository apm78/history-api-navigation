package de.akquinet.engineering.vaadin;

import com.vaadin.navigator.NavigationStateManager;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.shared.Registration;

/**
 * A {@link NavigationStateManager} using the HTML5 history API to track views and enable listening to view changes.
 * <p>
 * With this class the state won't appear in fragments after a hashbang ("#!"), but as part of the path.
 * For example http://localhost:8080/#!myview/myparameters is replaced by http://localhost:8080/myview/myparameters.
 *
 * @author Axel Meier, akquinet engineering GmbH
 */
public class HistoryApiNavigationStateManagerTest implements NavigationStateManager
{
    private final Page page;
    private Navigator navigator;
    private Registration popStateListenerRegistration;

    public HistoryApiNavigationStateManagerTest(final Page page)
    {
        this.page = page;
    }

    @Override
    public String getState()
    {
        final String state = page.getLocation().getPath();
        if (state != null && state.startsWith("/"))
        {
            return state.substring(1);
        }
        return "";
    }

    @Override
    public void setState(final String state)
    {
        page.pushState("/" + (state != null ? state : ""));
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
