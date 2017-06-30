package de.akquinet.engineering.vaadin;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.shared.Registration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.net.URI;

public class HistoryApiNavigationStateManagerTest
{
    @Mock
    private Page page;

    @Mock
    private Navigator navigator;

    @Mock
    private Registration popStateListenerRegistration;

    @InjectMocks
    private HistoryApiNavigationStateManager navigationStateManager;

    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException
    {
        MockitoAnnotations.initMocks(this);

        setField(navigationStateManager, "contextPath", "/context-path");
    }

    private static void setField(final Object target, final String fieldName, final Object value) throws NoSuchFieldException, IllegalAccessException
    {
        final Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void testSetNavigator()
    {
        navigationStateManager.setNavigator(null);
        Mockito.verifyZeroInteractions(popStateListenerRegistration);
        Mockito.verifyZeroInteractions(page);

        Mockito.when(page.addPopStateListener(ArgumentMatchers.any(Page.PopStateListener.class)))
                .thenReturn(popStateListenerRegistration);
        navigationStateManager.setNavigator(navigator);
        Mockito.verify(page)
                .addPopStateListener(ArgumentMatchers.any(Page.PopStateListener.class));

        navigationStateManager.setNavigator(null);
        Mockito.verify(popStateListenerRegistration).remove();
    }

    @Test
    public void testSetState() throws Exception
    {
        // context path is '/'
        {
            setField(navigationStateManager, "contextPath", "/");

            navigationStateManager.setState("page");
            Mockito.verify(page, Mockito.times(1)).pushState("/page");

            navigationStateManager.setState("page/param1/param2");
            Mockito.verify(page, Mockito.times(1)).pushState("/page/param1/param2");

            navigationStateManager.setState("/param1");
            Mockito.verify(page, Mockito.times(1)).pushState("/param1");

            navigationStateManager.setState("/param1/param2");
            Mockito.verify(page, Mockito.times(1)).pushState("/param1/param2");

            navigationStateManager.setState(null);
            Mockito.verify(page, Mockito.times(1)).pushState("/");
        }

        // context path is '/context-path'
        {
            setField(navigationStateManager, "contextPath", "/context-path");

            navigationStateManager.setState("page");
            Mockito.verify(page, Mockito.times(1)).pushState("/context-path/page");

            navigationStateManager.setState("page/param1/param2");
            Mockito.verify(page, Mockito.times(1)).pushState("/context-path/page/param1/param2");

            navigationStateManager.setState("/param1");
            Mockito.verify(page, Mockito.times(1)).pushState("/context-path/param1");

            navigationStateManager.setState("/param1/param2");
            Mockito.verify(page, Mockito.times(1)).pushState("/context-path/param1/param2");

            navigationStateManager.setState(null);
            Mockito.verify(page, Mockito.times(1)).pushState("/context-path/");
        }
    }

    @Test
    public void testStateToPath()
    {
        Assert.assertEquals("/context-path/page/param1", HistoryApiNavigationStateManager
                .stateToPath("/context-path/", "page/param1"));

        Assert.assertEquals("/context-path/page/param1", HistoryApiNavigationStateManager
                .stateToPath("/context-path/", "/page/param1"));

        Assert.assertEquals("/context-path/page/param1", HistoryApiNavigationStateManager
                .stateToPath("/context-path", "page/param1"));

        Assert.assertEquals("/context-path/page", HistoryApiNavigationStateManager
                .stateToPath("/context-path/", "/page"));

        Assert.assertEquals("/page", HistoryApiNavigationStateManager
                .stateToPath("/", "page"));
    }

    @Test
    public void testGetState() throws Exception
    {
        {
            Mockito.when(page.getLocation()).thenReturn(new URI("http://localhost/context-path/page/param1/param2"));
            final String state = navigationStateManager.getState();
            Assert.assertEquals("page/param1/param2", state);
        }

        {
            Mockito.when(page.getLocation()).thenReturn(new URI("http://localhost/context-path/page/param1/param2/"));
            final String state = navigationStateManager.getState();
            Assert.assertEquals("page/param1/param2/", state);
        }

        {
            Mockito.when(page.getLocation()).thenReturn(new URI("http://localhost/context-path/page/param1?query"));
            final String state = navigationStateManager.getState();
            Assert.assertEquals("page/param1", state);
        }

        {
            Mockito.when(page.getLocation()).thenReturn(new URI("http://localhost/context-path/page"));
            final String state = navigationStateManager.getState();
            Assert.assertEquals("page", state);
        }
    }
}
