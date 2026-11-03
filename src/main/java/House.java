import java.awt.Color;

/** READ FIRST
 * A House is DATA: where it stands, how big it is, what colour its walls are.
 * It does not own a Turtle. When it is time to draw, a pen is handed in as a
 * parameter - the House borrows it, uses it, and gives it back.
 *
 *   House h = new House(100, 300, 80, Color.RED);
 *   h.draw(somePen);        // any Turtle will do
 *
 * (x, y) is the BOTTOM-LEFT corner of the walls. Remember (0, 0) is the
 * top-left of the World and y grows DOWNWARD, so the house rises from (x, y)
 * toward smaller y values.
 *
 * The shape is the 6-segment house from Turtle Basics, and every segment has
 * length size:
 *
 *        /\            segments 5-6: the roof, an equilateral triangle
 *       /  \           whose base is the top wall (drawn once, not twice)
 *      +----+  <-- (x, y - size)      (x + size, y - size)
 *      |    |          segments 1-4: the walls, a square
 *      +----+  <-- (x, y)             (x + size, y)
 *
 * The peak is at (x + size/2, y - size - 0.866 * size).
 *
 * This file uses only Unit 2 Java: fields, a constructor, getters, and one
 * method with a loop. No arrays, no ArrayList, no extends.
 */
public class House {

    // The fields are declared for you. int fields start at 0 and object
    // fields start at null until the constructor sets them.
    private int x;          // bottom-left corner, pixels from the left edge
    private int y;          // bottom-left corner, pixels from the top edge
    private int size;       // side length of the walls (and of each roof line)
    private Color color;    // wall colour, used as the pen colour when drawing

    /** COMPLETE THIS METHOD
     * Precondition: size > 0
     * Stores the four values in the four fields.
     * Example: new House(100, 300, 80, Color.RED) has getX() 100, getY() 300,
     * getSize() 80 and getColor() Color.RED.
     * Hint: this.x = x; - the parameter names shadow the fields, so say this.
     */
    public House(int x, int y, int size, Color color) {
        // Insert your code below

    }

    /** COMPLETE THIS METHOD
     * Returns the x of the bottom-left corner.
     */
    public int getX() {
        // Insert your code below

        return 0;
    }

    /** COMPLETE THIS METHOD
     * Returns the y of the bottom-left corner.
     */
    public int getY() {
        // Insert your code below

        return 0;
    }

    /** COMPLETE THIS METHOD
     * Returns the side length.
     */
    public int getSize() {
        // Insert your code below

        return 0;
    }

    /** COMPLETE THIS METHOD
     * Returns the wall colour.
     */
    public Color getColor() {
        // Insert your code below

        return null;
    }

    /** COMPLETE THIS METHOD
     * Precondition: t is not null
     * Draws this house with the borrowed pen t: exactly 6 segments, all of
     * length size, all in this house's colour, with the bottom-left corner of
     * the walls at (x, y). Leaves the pen in the same up/down state it was in
     * when draw was called. Where the turtle ends up does not matter.
     *
     * Plan:
     *   0. boolean wasDown = t.isPenDown();       remember the pen state
     *   1. t.penUp(); t.moveTo(x, y);             fly to the corner, no ink
     *      t.setHeading(0);                      face UP - a turtle keeps its
     *                                            heading between calls, so a
     *                                            pen someone else turned would
     *                                            draw a crooked house
     *      t.setPenColor(color); t.penDown();
     *   2. four times: t.forward(size); t.turnRight();      the walls
     *      (you are back at (x, y), facing up)
     *   3. t.penUp(); t.forward(size); t.penDown();         climb to the top-left
     *   4. t.turn(30); t.forward(size);                     left roof line
     *      t.turn(120); t.forward(size);                    right roof line
     *   5. if (!wasDown) t.penUp();                          give the pen back
     *                                                       the way you found it
     *
     * Example: new House(100, 300, 80, Color.RED).draw(pen) adds 6 red
     * segments to the pen's World. Their endpoints are (100,300), (100,220),
     * (180,220), (180,300) and the peak (140, 150.7).
     */
    public void draw(Turtle t) {
        // Insert your code below

    }
}
