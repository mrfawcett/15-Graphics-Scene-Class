import java.awt.Color;

/**
 * PROVIDED - you do not need to change this file.
 *
 * Builds a small neighbourhood and shows it. Run it from your IDE once your
 * classes work; the autograder never runs Main, it only runs the tests.
 * On a machine with no screen (like the grader) show() does nothing and the
 * program simply prints its report and exits.
 */
public class Main {
    public static void main(String[] args) {
        Scene scene = new Scene(640, 400);

        // One house by hand ...
        scene.addHouse(new House(40, 340, 100, Color.RED));

        // ... and a street of four. The Street adds its houses to OUR scene,
        // because it received a reference to it, not a copy.
        new Street(scene, 4, 200, 340, 60, 40);

        scene.drawAll();

        // A pen of our own, for a sun. makePen hands back a ready-to-use Turtle.
        Turtle sun = scene.makePen(Color.ORANGE, 6);
        sun.moveTo(560, 70);
        sun.penDown();
        for (int i = 0; i < 36; i++) {
            sun.forward(6);
            sun.turn(10);
        }

        System.out.println("Houses in scene: " + scene.getHouseCount());
        House big = scene.getLargest();
        if (big != null) {
            System.out.println("Largest house has size " + big.getSize()
                    + " at (" + big.getX() + ", " + big.getY() + ")");
        }
        System.out.println("Segments drawn: " + scene.totalSegments());

        scene.show();
    }
}
