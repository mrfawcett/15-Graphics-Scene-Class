import java.awt.Color;

/** READ FIRST
 * A Street fills a Scene with a row of houses. It is the smallest possible
 * example of PASSING AN OBJECT INTO A CONSTRUCTOR: the Scene arrives as a
 * parameter, the Street calls addHouse on it, and the caller's scene now has
 * more houses in it - because there is only ONE scene. Java copied the
 * reference, not the object.
 *
 *   Scene s = new Scene(640, 400);
 *   Street row = new Street(s, 4, 50, 300, 60, 20);
 *   s.getHouseCount();      // 4 - the houses went into s, not into a copy
 *   row.getScene() == s;    // true - same object
 *
 * Layout of the 4 houses above (size 60, gap 20, so each step is 80):
 *
 *   index | x   | y   | size | colour
 *   ------+-----+-----+------+-------
 *     0   |  50 | 300 |  60  | RED
 *     1   | 130 | 300 |  60  | BLUE
 *     2   | 210 | 300 |  60  | RED
 *     3   | 290 | 300 |  60  | BLUE
 */
public class Street {

    // Declared for you. scene is null and count is 0 until the constructor runs.
    private Scene scene;    // the scene this street was built into (same object the caller has)
    private int count;      // how many houses this street added

    /** COMPLETE THIS METHOD
     * Precondition: scene is not null, count >= 0, size > 0, gap >= 0
     * Stores scene and count, then adds count houses to scene. House i
     * (i from 0) stands at x = startX + i * (size + gap), all at the same y
     * and size. Colours alternate: even i is Color.RED, odd i is Color.BLUE.
     * count 0 adds nothing. Houses already in the scene stay where they are;
     * the new ones go after them.
     * Example: new Street(s, 3, 100, 250, 40, 10) adds RED at x=100,
     * BLUE at x=150, RED at x=200, all with y 250 and size 40.
     * Hint: i % 2 == 0 picks the even houses.
     */
    public Street(Scene scene, int count, int startX, int y, int size, int gap) {
        // Insert your code below

    }

    /** COMPLETE THIS METHOD
     * Returns the Scene this street was built into - the very same object
     * that was passed to the constructor, not a copy.
     */
    public Scene getScene() {
        // Insert your code below

        return null;
    }

    /** COMPLETE THIS METHOD
     * Returns how many houses this street added.
     */
    public int getCount() {
        // Insert your code below

        return 0;
    }
}
