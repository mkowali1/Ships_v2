public class GameController {
    private GameModel model;
    private GameView view;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
    }

    public void startGame() {
        model.initializeGame();
        view.showMessage("Welcome to Ships! Guess a coordinate.");
        playGame();
    }

    private void playGame() {
        while (!model.isGameOver()) {
            view.displayGrid(model.getGrid());
            String input = view.getInput();
            if (input == null || input.equalsIgnoreCase("quit")) {
                view.showMessage("Game ended by player.");
                break;
            }
            boolean hit = model.processGuess(input);
            if (hit) {
                view.showMessage("Hit at " + input + "!");
            } else {
                view.showMessage("Miss at " + input + ".");
            }
        }
        if (model.isGameOver()) {
            view.displayGrid(model.getGrid());
            view.showMessage("Game Over! You sank all ships");
        }
    }

    public void handleInput(String input) {
        model.processGuess(input);
    }

}
