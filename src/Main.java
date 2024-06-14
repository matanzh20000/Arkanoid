/**
 * Main class that runs the game.
 */
public class Main {
    /**
     * Main method that runs the game.
     * @param args command line arguments.
     */
        public static void main(String[] args) {
            Game game = new Game();
            game.initialize();
            game.run();
        }
}