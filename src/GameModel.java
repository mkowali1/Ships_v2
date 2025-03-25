import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {

    private int[][] grid;
    private List<Ship> ships;
    private int hits;
    private int misses;
    private static final int GRID_SIZE = 10;
    private static final int[] SHIP_LENGTHS = {5, 4, 3, 3, 2};

    public GameModel() {
        grid = new int[GRID_SIZE][GRID_SIZE];
        ships = new ArrayList<>();
        hits = 0;
        misses = 0;
    }

    void initializeGame(){
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = 0;
            }
        }
        ships.clear();
        hits = 0;
        misses = 0;
        placeShipsRandomly();
    }

    void placeShipsRandomly(){
        Random rand = new Random();
        for (int shipLength : SHIP_LENGTHS) {
            boolean placed = false;
            while (!placed) {
                int row = rand.nextInt(GRID_SIZE);
                int col = rand.nextInt(GRID_SIZE);
                boolean horizontal = rand.nextBoolean();
                if (canPlaceShip(row, col, shipLength, horizontal)) {
                    placeShip(row, col, shipLength, horizontal);
                    placed = true;
                }
            }
        }
    }

    private boolean canPlaceShip(int row, int col, int length, boolean horizontal) {
        if (horizontal) {
            if (col + length > GRID_SIZE) return false;
            for (int c = col; c < col + length; c++) {
                if (grid[row][c] != 0) return false;
            }
        } else {
            if (row + length > GRID_SIZE) return false;
            for (int r = row; r < row + length; r++) {
                if (grid[r][col] != 0) return false;
            }
        }
        return true;
    }

    private void placeShip(int row, int col, int length, boolean horizontal) {
        Ship ship = new Ship(row, col, length, horizontal);
        ships.add(ship);
        if (horizontal) {
            for (int c = col; c < col + length; c++) {
                grid[row][c] = 1;
            }
        } else {
            for (int r = row; r < row + length; r++) {
                grid[r][col] = 1;
            }
        }
    }

    public void loadShipsFromFile(String file){

    }

    private void markSunkShip(Ship ship) {

    }

    public boolean processGuess(String guess){
        int row = guess.charAt(0) - 'A';
        int col = Integer.parseInt(guess.substring(1)) - 1;
        if (row < 0 || row >= GRID_SIZE || col < 0 || col >= GRID_SIZE || grid[row][col] > 1) {
            return false;
        }
        if (grid[row][col] == 1) {
            grid[row][col] = 2;
            hits++;
            for (Ship ship : ships) {
                if (ship.isHit(row, col)) {
                    if (ship.isSunk()) {
                        markSunkShip(ship);
                    }
                    break;
                }
            }
            return true;
        } else {
            grid[row][col] = 3;
            misses++;
            return false;
        }
    }

    public boolean isGameOver() {
        return hits == ships.stream().mapToInt(Ship::getLength).sum();
    }

    int[][] getGrid(){
        return grid;
    }

}
