/**
 * Ass3Game class that runs the game.
 */
public class Ass3Game {
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