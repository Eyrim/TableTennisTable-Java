package tabletennistable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class IntegrationTest {
    private static final String TEST_FILE = "gryffindor";

    @AfterAll
    public static void tearDown() {
        Path path = Paths.get(TEST_FILE);

        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.out.println(TEST_FILE + " doesn't exist");
        }

    }

    @Test
    public void testCanSavePlayersToTestFile() {
        final App app = CreateApp();

        addPlayersToGivenApp(app, new String[]{"Harry", "Ron", "Hermione"});

        app.sendCommand("save " + TEST_FILE);

        List<String> expectedContent = List.of("Harry", "Ron,Hermione");

        try {
            List<String> fileContent = Files.readAllLines(Paths.get(TEST_FILE));
            Assertions.assertThat(expectedContent).isEqualTo(fileContent);
        } catch (IOException e) {
            Assertions.fail(TEST_FILE + " NOT FOUND");
        }

    }

    @Test
    public void testCanSavePlayersByMockFileService() {
        FileService fileService = mock(FileService.class);

        LeagueRenderer leagueRenderer = new LeagueRenderer();

        League league = new League();

        App app = new App(league, leagueRenderer, fileService);

        addPlayersToGivenApp(app, new String[]{"Harry", "Ron", "Hermione"});

        app.sendCommand("save " + TEST_FILE);

        verify(fileService).save(TEST_FILE, league);

        List<List<String>> players = league.getRows().stream().map(LeagueRow::getPlayers).collect(Collectors.toList());

        List<List<String>> expectedPlayers = List.of(List.of("Harry"), List.of("Ron", "Hermione"));

        Assertions.assertThat(players).isEqualTo(expectedPlayers);
    }

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

        Assertions.assertThat(league.getRows()).hasSize(2).extracting(LeagueRow::getPlayers).containsExactlyInAnyOrder(List.of("Alice"), List.of("Bob"));
    }

    private void addPlayersToGivenApp(App app, String[] players) {
        for (String player : players) {
            app.sendCommand("add player " + player);
        }
    }

    private App CreateApp() {
        return new App(new League(), new LeagueRenderer(), new FileService());
    }

    private App CreateApp(final League league) {
        return new App(league, new LeagueRenderer(), new FileService());
    }

}