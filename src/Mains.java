import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mains {

    public static void main(String[] args) {
        Random rnd = new Random();
        int rectsAmount = 7;
        GameEnvironment ge = new GameEnvironment();
        GUI gui = new GUI("title", 800, 600);
        List<Collidable> blocks = new ArrayList<>();
        for (int i = 0; i < rectsAmount; i++) {
            for (int j = 0; j < rectsAmount; j++) {
                blocks.add(new Block(new Rectangle(new Point(50 * j, 50 * i), 50, 20), Color.BLUE));
                ge.addCollidable(blocks.get(j + i * rectsAmount));
            }
        }
        blocks.add(new Block(new Rectangle(new Point(0, 0), 800, 600), Color.BLACK));
        ge.addCollidable(blocks.get(blocks.size() - 1));
        Ball[] balls = new Ball[rectsAmount * rectsAmount];
        for (int i = 0; i < rectsAmount; i++) {
            for (int j = 0; j < rectsAmount; j++) {
                balls[j + i * rectsAmount] = new Ball(50 * j + 25, 50 * i + 25, 5, Color.RED, ge);
                balls[j + i * rectsAmount].setVelocity(Velocity.fromAngleAndSpeed(rnd.nextInt(0, 360), 5));
            }
        }
        while (true) {
            Sleeper sleeper = new Sleeper();
            sleeper.sleepFor(20);
            DrawSurface d = gui.getDrawSurface();
            for (int i = 0; i < rectsAmount; i++) {
                for (int j = 0; j < rectsAmount; j++) {
                    d.setColor(Color.black);
                    d.drawRectangle((int) blocks.get(j + i * rectsAmount).getCollisionRectangle().getUpperLeft().getX(),
                            (int) blocks.get(j + i * rectsAmount).getCollisionRectangle().getUpperLeft().getY(),
                            (int) blocks.get(j + i * rectsAmount).getCollisionRectangle().getWidth(),
                            (int) blocks.get(j + i * rectsAmount).getCollisionRectangle().getHeight());
                    d.setColor(Color.red);
                    d.fillCircle((int) balls[j + i * rectsAmount].getCenter().getX(), (int) balls[j + i * rectsAmount].getCenter().getY(), balls[j + i * rectsAmount].getSize());
                    balls[j + i * rectsAmount].moveOneStep(ge);
                }
            }
            gui.show(d);


        }

    }
}

