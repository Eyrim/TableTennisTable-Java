package tabletennistable;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Welcome to the table tennis table");

            League league = new League();
            LeagueRenderer leagueRenderer = new LeagueRenderer();
            FileService fileService = new FileService();
            App game = new App(league, leagueRenderer, fileService);

            boolean isGameActive = true;

            while (isGameActive) {
                System.out.println("> ");
                String command = in.nextLine();

                if (command.equals("quit")) {
                    isGameActive = false;
                } else {
                    System.out.println(game.sendCommand(command));
                }
            }
        }
    }
}
