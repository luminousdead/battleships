package battleship;

import java.util.Scanner;

public class Player {
    public static Scanner scanner = new Scanner(System.in);
    private String name;
    private String[][] playerBoard = new String[10][10];
    private String[][] enemyBoardWithFog = new String[10][10];
    private String[][] enemyBoard = new String[10][10];
    private int hits = 0;

    Player(String name) {
        createBoards();
        this.name = name;
        showBoard(playerBoard);
        for (Ship ship : Ship.values()) {
            try {
                setShip(ship);
                System.out.println();
                showBoard(playerBoard);
            } catch (Exception e) {

            }
        }
    }

    private void setShip(Ship ship) throws Exception {
        System.out.println("\nEnter the coordinates of the " + ship.getName() + " (" + ship.getLength() + " cells):");
        String input;
        String tmpAxis;
        int[] coordinates = new int[4];
        while (true) {
            try {
                System.out.print("\n> ");
                input = scanner.nextLine();
                if (input.length() > 7 || input.length() < 5)
                    throw new Exception("Wrong input!");
                String[] tmp = input.split(" ");

                // string input to int coordinates
                coordinates[0] = "ABCDEFGHIJ".indexOf(String.valueOf(tmp[0].charAt(0)));
                coordinates[1] = Integer.parseInt(tmp[0].substring(1)) - 1; // FormatException обработать
                coordinates[2] = "ABCDEFGHIJ".indexOf(String.valueOf(tmp[1].charAt(0)));
                coordinates[3] = Integer.parseInt(tmp[1].substring(1)) - 1;

                if (coordinates[1] < 0 || coordinates[1] > 10 || coordinates[3] < 0 || coordinates[3] > 10
                        || coordinates[0] < 0 || coordinates[2] < 0){
                    throw new Exception("12345");
                }

                if (coordinates[0] == (coordinates[2])) {
                    tmpAxis = "X";
                    if (Math.abs(coordinates[1] - coordinates[3]) + 1 != ship.getLength())
                        throw new Exception("Wrong length of the " + ship.getName() + "!");
                } else if (coordinates[1] == (coordinates[3])) {
                    tmpAxis = "Y";
                    if (Math.abs(coordinates[0] - coordinates[2]) + 1 != ship.getLength())
                        throw new Exception("Wrong length of the " + ship.getName() + "!");
                } else {
                    throw new Exception("Wrong ship location!");
                }

                for (int i = Integer.min(coordinates[0], coordinates[2]) - 1; i <= Integer.max(coordinates[0], coordinates[2]) + 1; i++) {
                    for (int j = Integer.min(coordinates[1], coordinates[3]) - 1; j <= Integer.max(coordinates[1], coordinates[3]) + 1 ; j++) {
                        if (i < 0 || i > 9 || j < 0 || j > 9)
                            continue;
                        if (this.playerBoard[i][j].equals("O"))
                            throw new Exception("You placed it too close to another one.");
                    }
                }

                if (tmpAxis.equals("X")){
                    for (int i = Integer.min(coordinates[1], coordinates[3]), j = 1; i <= Integer.max(coordinates[1], coordinates[3]); i++ ,j++) {
                        this.playerBoard[coordinates[0]][i] = "O";
                    }
                } else {
                    for (int i = Integer.min(coordinates[0], coordinates[2]), j = 1; i <= Integer.max(coordinates[0], coordinates[2]); i++ ,j++) {
                        this.playerBoard[i][coordinates[1]] = "O";
                    }
                }
                break;
            } catch (Exception e){
                System.out.println("\nError! " + e.getMessage() + " Try again:");
            }
        }
    }

    private void createBoards() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.playerBoard[i][j] = "~";
                this.enemyBoardWithFog[i][j] = "~";
            }
        }
    }
    private void showBoard(String[][] board) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10 ");
        for (int i = 0; i < 10; i++) {
            System.out.print(String.valueOf((char)(65 + i)) + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public String gameTurn() {
        showBoard(enemyBoardWithFog);
        System.out.println("---------------------");
        showBoard(playerBoard);
        System.out.println("\n" + this.name + ", it's your turn:\n");
        String gameState = "";
        String input;
        int[] hitCoordinate = new int[2];
        while (true){
            try {
                System.out.print("> ");
                input = Player.scanner.nextLine();
                hitCoordinate[0] = "ABCDEFGHIJ".indexOf(String.valueOf(input.charAt(0)));
                hitCoordinate[1] = Integer.parseInt(input.substring(1)) - 1; // FormatException

                if (hitCoordinate[0] < 0 || hitCoordinate[1] < 0 || hitCoordinate[1] > 9){
                    throw new Exception("You entered wrong coordinates!");
                }

                if (enemyBoardWithFog[hitCoordinate[0]][hitCoordinate[1]].equals("X") || enemyBoardWithFog[hitCoordinate[0]][hitCoordinate[1]].equals("M")){
                    throw new Exception("You already hit this square!");
                }

                if (enemyBoard[hitCoordinate[0]][hitCoordinate[1]].equals("O")){
                    if (!enemyBoardWithFog[hitCoordinate[0]][hitCoordinate[1]].equals("X")){
                        this.hits++;
                        enemyBoardWithFog[hitCoordinate[0]][hitCoordinate[1]] = "X";
                        enemyBoard[hitCoordinate[0]][hitCoordinate[1]] = "X";
                    } else {
                        return "HIT";
                    }
                    if (this.hits == 17){
                        return "WIN";
                    } else {
                        for (int i = hitCoordinate[0] - 1; i <= hitCoordinate[0] + 1; i++) {
                            for (int j = hitCoordinate[1] - 1; j <= hitCoordinate[1] + 1; j++) {
                                if (i == hitCoordinate[0] && j == hitCoordinate[1] || i > 9 || j > 9 || i < 0 || j < 0)
                                    continue;
                                if (enemyBoard[i][j].equals("O") && !enemyBoardWithFog[i][j].equals("X"))
                                {
                                    return "HIT";
                                }
                            }
                        }
                        return "SANK";
                    }
                } else {
                    enemyBoardWithFog[hitCoordinate[0]][hitCoordinate[1]] = "M";
                    return "MISS";
                }
            } catch (Exception e){
                System.out.println("\nError! " + e.getMessage() + " Try again:");
            }
        }
    }

    public String[][] getPlayerBoard() {
        return this.playerBoard;
    }

    public void setEnemyBoard(String[][] board) {
        this.enemyBoard = board;
    }
}