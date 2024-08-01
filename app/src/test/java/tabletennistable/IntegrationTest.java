package tabletennistable;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class IntegrationTest {
    @Test
    public void testPrintsEmptyGame() {
        App app = CreateApp();

        Assert.assertEquals("No players yet", app.sendCommand("print"));
    }

    @Test
    public void testCanAddPlayers() {
        final League league = new League();
        final App app = CreateApp(league);

        app.sendCommand("add player Alice");
        app.sendCommand("add player Bob");

        Assertions.assertThat(league.getRows())
                .hasSize(2)
                .extracting(LeagueRow::getPlayers)
                .containsExactlyInAnyOrder(
                        List.of("Alice"),
                        List.of("Bob"));
    }

    private App CreateApp() {
        return new App(new League(), new LeagueRenderer(), new FileService());
    }

    private App CreateApp(final League league) {
        return new App(league, new LeagueRenderer(), new FileService());
    }
}
