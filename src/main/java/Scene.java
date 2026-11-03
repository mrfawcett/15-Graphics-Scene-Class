import java.awt.Color;
import java.util.ArrayList;

/** READ FIRST
 * A Scene OWNS things. It has-a World (the canvas) and it has-a list of
 * Houses. That is composition: the fields are objects, and the constructor
 * builds them. Nothing here is new syntax - the field types are class names
 * instead of int.
 *
 *   Scene s = new Scene(640, 400);
 *   s.addHouse(new House(60, 300, 80, Color.RED));
 *   s.addHouse(new House(200, 300, 120, Color.BLUE));
 *   s.drawAll();                 // one pen draws every house
 *   s.totalSegments();           // 12 - six per house
 *   s.getLargest();              // the blue one
 *   s.show();                    // opens a window if there is a screen
 *
 * THE #1 CRASH: an object field you never constructed is null, and the
 * NullPointerException is reported on the line where you USED it, not where
 * you forgot it. Both fields below start as null. Your constructor must
 * give each of them a new.
 *
 * This is has-a, not is-a. Do not write "extends World". Inheritance is
 * Unit 4, in January, and you do not need it here.
 */
public class Scene {

    // Declared for you. Both are null until the constructor runs new.
    private World world;                // the canvas every pen draws on
    private ArrayList<House> houses;    // the houses in this scene, in the order added

    /** COMPLETE THIS METHOD
     * Precondition: width > 0 and height > 0
     * Builds a World of the given size and an empty list of houses.
     * Example: new Scene(640, 400) has getWorld().getWidth() 640,
     * getWorld().getHeight() 400 and getHouseCount() 0.
     */
    public Scene(int width, int height) {
        // Insert your code below

    }

    /** COMPLETE THIS METHOD
     * Returns the World this scene owns (the same object every time).
     */
    public World getWorld() {
        // Insert your code below

        return null;
    }

    /** COMPLETE THIS METHOD
     * Precondition: h is not null
     * Adds h to the end of the list. Adding the same House twice stores it
     * twice.
     */
    public void addHouse(House h) {
        // Insert your code below

    }

    /** COMPLETE THIS METHOD
     * Returns how many houses have been added.
     */
    public int getHouseCount() {
        // Insert your code below

        return 0;
    }

    /** COMPLETE THIS METHOD
     * Precondition: 0 <= i < getHouseCount()
     * Returns the house at position i, in the order they were added
     * (the first house added is getHouse(0)).
     */
    public House getHouse(int i) {
        // Insert your code below

        return null;
    }

    /** COMPLETE THIS METHOD
     * Precondition: width >= 1
     * Builds and RETURNS a brand-new Turtle that lives in this scene's world,
     * configured as a pen: pen colour c, pen width width, pen UP, hidden.
     * Every call makes a new Turtle - two calls give two different objects.
     * Example: Turtle red = s.makePen(Color.RED, 4);  red.getPenWidth() is 4,
     * red.isPenDown() is false, red.getWorld() is s.getWorld().
     * Hint: the return type slot holds Turtle exactly where int would go.
     */
    public Turtle makePen(Color c, int width) {
        // Insert your code below

        return null;
    }

    /** COMPLETE THIS METHOD
     * Draws every house in the list, in order, using ONE pen for all of them:
     * make a single pen (makePen(Color.BLACK, 1) is fine - each House sets
     * its own colour) and hand that same pen to every house's draw. Do not
     * make a pen per house. With no houses this does nothing.
     * Example: two houses -> 12 new segments, the first 6 in the first
     * house's colour and the next 6 in the second house's colour.
     */
    public void drawAll() {
        // Insert your code below

    }

    /** COMPLETE THIS METHOD
     * Returns the house with the biggest size, or null if there are no
     * houses. If several houses tie for biggest, returns the one that was
     * added FIRST.
     * Example: sizes 60, 120, 120 -> the house at index 1.
     * Hint: start with the first house as the best so far and only replace it
     * when you find something strictly bigger (>, not >=).
     */
    public House getLargest() {
        // Insert your code below

        return null;
    }

    /** COMPLETE THIS METHOD
     * Returns how many line segments have been drawn in this scene's world so
     * far - by drawAll AND by any pen you made with makePen.
     * Hint: world.getSegments() is an ArrayList; ask it its size.
     */
    public int totalSegments() {
        // Insert your code below

        return 0;
    }

    /** PROVIDED - do not change
     * Opens a window showing the scene when this computer has a screen, and
     * does nothing on a machine without one (like the autograder).
     */
    public void show() {
        world.show();
    }
}
