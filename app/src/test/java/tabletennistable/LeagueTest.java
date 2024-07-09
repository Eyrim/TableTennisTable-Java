package tabletennistable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileWriter;
import java.util.List;
import org.hamcrest.core.IsCollectionContaining;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

public class LeagueTest {
    private League league;
    private final String BOB = "Bob";
    private final String SARAH = "Sarah";

    @BeforeEach
    private void setup() {
        this.league = new League();
    }

    @Test
    public void testAddPlayer() {
        // When
        this.league.addPlayer(this.BOB);

        // Then
        List<LeagueRow> rows = this.league.getRows();
        Assert.assertEquals(1, rows.size());
        List<String> firstRowPlayers = rows.get(0).getPlayers();
        Assert.assertEquals(1, firstRowPlayers.size());
        assertThat(firstRowPlayers, IsCollectionContaining.hasItem(this.BOB));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "+",
            "Al!ce",
            "alice-smith"
    })
    public void addPlayerShouldntAddPlayerWithInvalidName(final String name) {
        assertThrows(IllegalArgumentException.class, () -> this.league.addPlayer(name));
    }

    @Test
    public void testRecordWinCanRecordWin() {
        this.league.addPlayer(this.BOB);
        this.league.addPlayer(this.SARAH);

        this.league.recordWin(this.SARAH, this.BOB);

        System.out.println(this.league.getRows().toString());
        System.out.println("TEST");

        assertThat(
                this.league.getWinner(),
                equalTo(this.SARAH));
    }

    @Test
    public void testRecordWinCanRecordWinWithPlayersAddedInInverseOrder() {
        this.league.addPlayer(this.BOB);
        this.league.addPlayer(this.SARAH);

        final Exception exception = assertThrows(IllegalArgumentException.class,
                () -> this.league.recordWin(this.BOB, this.SARAH));

        assertTrue(exception.getMessage().contains(
                "No changes needed as the winner of this match is already at the top of the league"));
    }

    @Test
    public void getWinnerReturnsOnlyPlayerInTableIfThereIsOnlyOnePlayer() {
        this.league.addPlayer(this.BOB);

        assertTrue(this.league.getWinner().equals(this.BOB));
    }

    @Test
    public void winnerAndLoserAreSwappedWhenWinRecorded() {
        // Bob will be on top (row 0) as added first
        this.league.addPlayer(this.BOB);
        this.league.addPlayer(this.SARAH);

        this.league.recordWin(this.SARAH, this.BOB);

        assertTrue(this.league.getRows().get(0).getPlayers().equals(List.of(this.SARAH)));
        assertTrue(this.league.getRows().get(1).getPlayers().equals(List.of(this.BOB)));
    }

    @Test
    public void cannotAddDuplicateNames() {
        this.league.addPlayer(this.BOB);

        final Exception exception = assertThrows(IllegalArgumentException.class, () ->
            this.league.addPlayer(this.BOB));

        assertTrue(exception.getMessage().contains(""));
    }

    @Test
    public void canSaveAGame() {
    }
}
