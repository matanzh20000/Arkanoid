import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;

/**
 * The Game class represents the game logic and the main game loop.
 * It manages the sprites and collidables, initializes the game objects, and runs the game loop.
 */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private biuoop.GUI gui;

    /**
     * Adds a collidable object to the game environment.
     *
     * @param c the collidable object to add
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Adds a sprite object to the sprite collection.
     *
     * @param s the sprite object to add
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initializes a new game: creates the blocks, balls, and paddle,
     * and adds them to the game.
     */
    public void initialize() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        gui = new biuoop.GUI("Arkanoid", 800, 600);
        biuoop.KeyboardSensor keyboard = gui.getKeyboardSensor();
        Block[] blocks = new Block[20];

        // Create the blocks
        int maxWidth = 800;
        int maxHeight = 600;
        int borderSize = 20;
        int minWidth = 0;
        int minHeight = 0;
        Block screen = new Block(new Rectangle(new Point(minWidth, minHeight), maxWidth, maxHeight), Color.BLUE);
        Block borderLeft = new Block(new Rectangle(new Point(minWidth, minHeight), borderSize, maxHeight), Color.GRAY);
        Block borderRight = new Block(new Rectangle(new Point(maxWidth - borderSize,
                minHeight), borderSize, maxHeight), Color.GRAY);
        Block borderTop = new Block(new Rectangle(new Point(minWidth, minHeight),
                maxWidth, borderSize), Color.GRAY);
        Block borderBottom = new Block(new Rectangle(new Point(minWidth, maxHeight - borderSize),
                maxWidth, borderSize), Color.GRAY);
        Paddle paddle = new Paddle(new Rectangle(new Point((double) maxWidth / 2, maxHeight - 2 * borderSize),
                100, 20), Color.ORANGE, keyboard);

        screen.addToGame(this);
        borderLeft.addToGame(this);
        borderRight.addToGame(this);
        borderTop.addToGame(this);
        borderBottom.addToGame(this);
        paddle.addToGame(this);

        for (int i = 0; i < 5; i++) {
            Color randColor = new Color((int) (Math.random() * 0x10000000));
            for (int j = i; j <= 12; j++) {
                Rectangle rectangle = new Rectangle(new Point((50 * j) + 150 - borderSize,
                        100 + i * borderSize), 50, 20);
                blocks[j] = new Block(rectangle, randColor);
                blocks[j].addToGame(this);
            }
        }

        // Create the balls
        for (int i = 0; i < 2; i++) {
            Ball ball = new Ball(400, 300, 6, Color.WHITE, this.environment);
            ball.setVelocity(1 + i, 3);
            ball.addToGame(this);
        }
    }

    /**
     * Runs the game by starting the animation loop.
     * The game loop continues until the user closes the game window.
     */
    public void run() {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();
            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
