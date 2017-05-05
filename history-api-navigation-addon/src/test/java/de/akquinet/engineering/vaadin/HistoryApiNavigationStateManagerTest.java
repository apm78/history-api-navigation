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
    public void before(){
        MockitoAnnotations.initMocks(this);
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
    public void testSetState()
    {
        navigationStateManager.setState("page");
        Mockito.verify(page, Mockito.times(1)).pushState("/page");

        navigationStateManager.setState("page/param1/param2");
        Mockito.verify(page, Mockito.times(1)).pushState("/page/param1/param2");

        navigationStateManager.setState(null);
        Mockito.verify(page, Mockito.times(1)).pushState("/");
    }

    @Test
    public void testGetState() throws Exception
    {
        {
            Mockito.when(page.getLocation()).thenReturn(new URI("http://localhost/page/param1/param2"));
            final String state = navigationStateManager.getState();
            Assert.assertEquals("page/param1/param2", state);
        }

        {
            Mockito.when(page.getLocation()).thenReturn(new URI("http://localhost/page/param1/param2/"));
            final String state = navigationStateManager.getState();
            Assert.assertEquals("page/param1/param2/", state);
        }

        {
            Mockito.when(page.getLocation()).thenReturn(new URI("http://localhost/page/param1?query"));
            final String state = navigationStateManager.getState();
            Assert.assertEquals("page/param1", state);
        }

        {
            Mockito.when(page.getLocation()).thenReturn(new URI("http://localhost/page"));
            final String state = navigationStateManager.getState();
            Assert.assertEquals("page", state);
        }
    }
}
