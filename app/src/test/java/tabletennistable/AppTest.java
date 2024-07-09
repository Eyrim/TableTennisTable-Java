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

    private League league;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void setup() {
        this.league = new League();
    }

    @Test
    public void testPrintsCurrentState() {
        initMocks();
        when(renderer.render(league)).thenReturn("Rendered League");

        App app = new App(league, renderer, null);

        assertEquals("Rendered League", app.sendCommand("print"));
    }
}
