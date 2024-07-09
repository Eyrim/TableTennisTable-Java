package tabletennistable;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class IntegrationTest {
    @Test
    public void testPrintsEmptyGame() {
        App app = CreateApp();

        Assert.assertEquals("No players yet", app.sendCommand("print"));
    }

    private App CreateApp() {
        return new App(new League(), new LeagueRenderer(), new FileService());
    }
}
