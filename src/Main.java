//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        //GUIGameView guiGameView = new GUIGameView();

        GameModel model = new GameModel();
        GameView view = new CLIGameView();
        GameController controller = new GameController(model, view);
        controller.startGame();
        //CLIGameView cliGameView = new CLIGameView();
    }
}