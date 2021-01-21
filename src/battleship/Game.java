package battleship;

import static battleship.Player.scanner;

public class Game {
    public static void start() {
        System.out.println("Player 1, place your ships on the game field");
        Player player1 = new Player("Player 1");
        changeTurn();

        System.out.println("Player 2, place your ships on the game field");
        Player player2 = new Player("Player 2");
        changeTurn();

        player1.setEnemyBoard(player2.getPlayerBoard());
        player2.setEnemyBoard(player1.getPlayerBoard());

        String gameStats = "";
        while (true) {
            gameStats = player1.gameTurn();
            switch (gameStats) {
                case "HIT":
                    System.out.println("You hit a Ship!");
                    changeTurn();
                    break;
                case "MISS":
                    System.out.println("You missed!");
                    changeTurn();
                    break;
                case "SANK":
                    System.out.println("You sank a Ship!");
                    changeTurn();
                    break;
            }
            if (gameStats.equals("WIN")){
                System.out.println("You sank the last ship. You won. Congratulations!");
                break;
            }

            gameStats = player2.gameTurn();
            switch (gameStats) {
                case "HIT":
                    System.out.println("You hit a Ship!");
                    changeTurn();
                    break;
                case "MISS":
                    System.out.println("You missed!");
                    changeTurn();
                    break;
                case "SANK":
                    System.out.println("You sank a Ship!");
                    changeTurn();
                    break;
            }
            if (gameStats.equals("WIN")){
                System.out.println("You sank the last ship. You won. Congratulations!");
                break;
            }
        }
    }

    private static void changeTurn() {
        System.out.println("\nPress Enter and pass the move to another player");
        scanner.nextLine();
    }
}