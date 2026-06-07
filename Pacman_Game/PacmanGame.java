/*Make a Pacman game with the following characteristics:
 1- Can move around
 2- Eat food
 3- Add enemies
 4- Score every time eats food
 5- Dies when contact with enemy, game end and score showed on the screne.
 */
import java.util.Scanner;
import java.util.Random;


// CLASS 1: PacmanGame

public class PacmanGame {
    public static void main(String[] args) {
        GameEngine myGame = new GameEngine();
        myGame.startPlaying();
    }
}

// CLASS 2: Player

class Player {
    int currentRow;
    int currentColumn;
    int totalScore;
    boolean isStillAlive;
    char appearance;

    public Player(int startRow, int startColumn) {
        this.currentRow = startRow;
        this.currentColumn = startColumn;
        this.totalScore = 0;
        this.isStillAlive = true;
        this.appearance = 'P';
    }
}


//  CLASS 3: Monster (Previously Enemy)


class Monster {
    int currentRow;
    int currentColumn;
    char appearance;

    // This remembers what the monster stepped on so it can put it back
    char hiddenItemUnderneath;

    public Monster(int startRow, int startColumn) {
        this.currentRow = startRow;
        this.currentColumn = startColumn;
        this.appearance = 'M';
        this.hiddenItemUnderneath = '.';
    }
}


//  CLASS 4: GameBoard (Previously GameMap)


class GameBoard {
    char[][] maze;

    public GameBoard() {
        maze = new char[][] {
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '.', '#', '#', '#', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '.', '#', '.', '#', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '.', '#', '.', '#', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
        };
    }

    public void showBoardOnScreen() {
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                System.out.print(maze[row][col] + " ");
            }
            System.out.println();
        }
    }

    public char lookAtSpot(int targetRow, int targetColumn) {
        return maze[targetRow][targetColumn];
    }

    public void drawItemAt(int targetRow, int targetColumn, char newItem) {
        maze[targetRow][targetColumn] = newItem;
    }
}


// CLASS 5: GameEngine (Previously GameRunner)


class GameEngine {
    GameBoard board;
    Player hero;
    Monster[] monsters;
    Scanner keyboardReader;
    Random dice;

    public GameEngine() {
        board = new GameBoard();
        keyboardReader = new Scanner(System.in);
        dice = new Random();

        // 1. Setup Hero
        hero = new Player(5, 1);
        board.drawItemAt(hero.currentRow, hero.currentColumn, hero.appearance);

        // 2. Setup Monsters
        monsters = new Monster[3];
        monsters[0] = new Monster(1, 6);
        monsters[1] = new Monster(3, 2);
        monsters[2] = new Monster(6, 6);

        // 3. Draw monsters on the board
        for (int i = 0; i < monsters.length; i++) {
            monsters[i].hiddenItemUnderneath = board.lookAtSpot(monsters[i].currentRow, monsters[i].currentColumn);
            board.drawItemAt(monsters[i].currentRow, monsters[i].currentColumn, monsters[i].appearance);
        }
    }

    public void startPlaying() {
        System.out.println("Welcome! Use W/A/S/D to move.");

        while (hero.isStillAlive) {
            System.out.println("\nScore: " + hero.totalScore);
            board.showBoardOnScreen();


            // PART 1: HERO MOVES

            System.out.print("Enter move (W/A/S/D): ");
            char directionTyped = keyboardReader.next().toUpperCase().charAt(0);

            int futureRow = hero.currentRow;
            int futureColumn = hero.currentColumn;

            if (directionTyped == 'W') futureRow--;
            else if (directionTyped == 'S') futureRow++;
            else if (directionTyped == 'A') futureColumn--;
            else if (directionTyped == 'D') futureColumn++;
            else {
                System.out.println("Invalid input!");
                continue;
            }

            char itemAtFutureSpot = board.lookAtSpot(futureRow, futureColumn);

            if (itemAtFutureSpot == '#') {
                System.out.println("You hit a wall!");
                continue;
            }

            if (itemAtFutureSpot == 'M') {
                System.out.println("Oh no! You walked into a monster!");
                hero.isStillAlive = false;
                break;
            }

            if (itemAtFutureSpot == '.') {
                hero.totalScore++;

            }
            if (hero.totalScore==49){
                hero.isStillAlive = false;
            }

            board.drawItemAt(hero.currentRow, hero.currentColumn, ' ');
            board.drawItemAt(futureRow, futureColumn, hero.appearance);
            hero.currentRow = futureRow;
            hero.currentColumn = futureColumn;


            // PART 2: MONSTERS MOVE

            for (int i = 0; i < monsters.length; i++) {
                Monster currentMonster = monsters[i];

                int monsterFutureRow = currentMonster.currentRow;
                int monsterFutureColumn = currentMonster.currentColumn;

                int randomChoice = dice.nextInt(4);

                if (randomChoice == 0) monsterFutureRow--;
                else if (randomChoice == 1) monsterFutureRow++;
                else if (randomChoice == 2) monsterFutureColumn--;
                else if (randomChoice == 3) monsterFutureColumn++;

                char itemInFrontOfMonster = board.lookAtSpot(monsterFutureRow, monsterFutureColumn);

                if (itemInFrontOfMonster != '#' && itemInFrontOfMonster != 'M') {

                    if (itemInFrontOfMonster == 'P') {
                        System.out.println("Oh no! A monster caught you!");
                        hero.isStillAlive = false;
                        break;
                    }

                    board.drawItemAt(currentMonster.currentRow, currentMonster.currentColumn, currentMonster.hiddenItemUnderneath);

                    currentMonster.hiddenItemUnderneath = itemInFrontOfMonster;

                    board.drawItemAt(monsterFutureRow, monsterFutureColumn, currentMonster.appearance);
                    currentMonster.currentRow = monsterFutureRow;
                    currentMonster.currentColumn = monsterFutureColumn;
                }
            }
        }

        System.out.println("\n--- GAME OVER ---");
        System.out.println("Final Score: " + hero.totalScore);
        keyboardReader.close();
    }
}