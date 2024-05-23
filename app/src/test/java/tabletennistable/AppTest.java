package tabletennistable;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AppTest {
    @Mock
    LeagueRenderer renderer;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPrintsCurrentState() {
        initMocks();
        League league = new League();
        when(renderer.render(league)).thenReturn("Rendered League");

        App app = new App(league, renderer, null);

        assertEquals("Rendered League", app.sendCommand("print"));
    }
}
