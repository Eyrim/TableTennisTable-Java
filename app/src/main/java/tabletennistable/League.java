package tabletennistable;

import java.util.ArrayList;
import java.util.List;

public class League {
    private List<LeagueRow> _rows;

    public League() {
        this._rows = new ArrayList<>();
    }

    public League(List<LeagueRow> rows) {
        _rows = rows;
    }

    public void addPlayer(String player) {
        validateName(player);
        checkPlayerIsUnique(player);

        if (areAllRowsFull()) {
            addNewRow();
        }

        bottomRow().add(player);
    }

    public List<LeagueRow> getRows() {
        return _rows;
    }

    public void recordWin(String winner, String loser) {
        checkPlayerIsInGame(winner);
        checkPlayerIsInGame(loser);

        int winnerRowIndex = findPlayerRowIndex(winner);
        int loserRowIndex = findPlayerRowIndex(loser);

        if (winnerRowIndex - loserRowIndex != 1) {
            throw new IllegalArgumentException(
                    "No changes needed as the winner of this match is already at the top of the league");
        }

        _rows.get(winnerRowIndex).swap(winner, loser);
        _rows.get(loserRowIndex).swap(loser, winner);
    }

    public String getWinner() {
        if (_rows.size() > 0) {
            return _rows.get(0).getPlayers().get(0);
        }

        return null;
    }

    private boolean areAllRowsFull() {

        return _rows.isEmpty() || bottomRow().isFull();
    }

    private void addNewRow() {
        _rows.add(new LeagueRow(_rows.size() + 1));
    }

    private LeagueRow bottomRow() {
        return _rows.get(_rows.size() - 1);
    }

    private void validateName(String player) {
        String _validNameRegex = "^\\w+$";
        if (!player.matches(_validNameRegex)) {
            throw new IllegalArgumentException("Player name " + player + " contains invalid");
        }
    }

    private void checkPlayerIsInGame(String player) {
        if (!isPlayerInGame(player)) {
            throw new IllegalArgumentException("Player " + player + " is not in the game");
        }
    }

    private void checkPlayerIsUnique(String player) {
        if (isPlayerInGame(player)) {
            throw new IllegalArgumentException("Cannot add player " + player + " because they are already in the game");
        }
    }

    private boolean isPlayerInGame(String player) {
        return findPlayerRowIndex(player) >= 0;
    }

    private int findPlayerRowIndex(String player) {
        int rowIndex = 0;
        for (LeagueRow row : _rows) {
            if (row.includes(player)) {
                return rowIndex;
            }
            rowIndex++;
        }
        return -1;
    }
}
