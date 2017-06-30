package de.akquinet.engineering.vaadin;

import com.vaadin.navigator.NavigationStateManager;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.shared.Registration;
import org.apache.commons.lang3.StringUtils;

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
    private static final String SEPARATOR = "/";

    private final Page page;
    private final String contextPath;
    private Navigator navigator;
    private Registration popStateListenerRegistration;

    public HistoryApiNavigationStateManager(final Page page, final String contextPath)
    {
        this.page = page;
        this.contextPath = StringUtils.isEmpty(contextPath) ? SEPARATOR : contextPath;
    }

    @Override
    public String getState()
    {
        return pathToState(page.getLocation().getPath());
    }

    private String pathToState(final String path){
        if (path == null) {
            return "";
        }

        final String state = path.substring(contextPath.length());
        if (state.startsWith(SEPARATOR))
        {
            return state.substring(1);
        }

        return state;
    }

    static String stateToPath(final String contextPath, final String state)
    {
        final String newState = state != null ? state : "";
        final String path = newState.startsWith(SEPARATOR) ? newState.substring(1) : newState;
        return contextPath.endsWith(SEPARATOR)
               ? contextPath + path
               : contextPath + SEPARATOR + path;
    }

    @Override
    public void setState(final String state)
    {
        page.pushState(stateToPath(contextPath, state));
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
