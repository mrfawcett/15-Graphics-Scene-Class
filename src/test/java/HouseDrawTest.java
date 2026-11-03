import java.awt.Color;
import java.util.ArrayList;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class HouseDrawTest {
    private static final double TOL = 0.5;
    private static final double ROOF = Math.sqrt(3) / 2;   // height of an equilateral triangle, per unit of side

    private World world;
    private Turtle pen;

    @BeforeEach
    void setUp() {
        world = new World(500, 500);
        pen = new Turtle(world);
    }

    // ---- helpers ---------------------------------------------------------

    private static double minX(ArrayList<World.Segment> segs) {
        double m = Double.MAX_VALUE;
        for (World.Segment s : segs) m = Math.min(m, Math.min(s.x1, s.x2));
        return m;
    }
    private static double maxX(ArrayList<World.Segment> segs) {
        double m = -Double.MAX_VALUE;
        for (World.Segment s : segs) m = Math.max(m, Math.max(s.x1, s.x2));
        return m;
    }
    private static double minY(ArrayList<World.Segment> segs) {
        double m = Double.MAX_VALUE;
        for (World.Segment s : segs) m = Math.min(m, Math.min(s.y1, s.y2));
        return m;
    }
    private static double maxY(ArrayList<World.Segment> segs) {
        double m = -Double.MAX_VALUE;
        for (World.Segment s : segs) m = Math.max(m, Math.max(s.y1, s.y2));
        return m;
    }
    /** true if some segment starts or ends within TOL of (x, y) */
    private static boolean hasCorner(ArrayList<World.Segment> segs, double x, double y) {
        for (World.Segment s : segs) {
            if (Math.abs(s.x1 - x) < TOL && Math.abs(s.y1 - y) < TOL) return true;
            if (Math.abs(s.x2 - x) < TOL && Math.abs(s.y2 - y) < TOL) return true;
        }
        return false;
    }

    // ---- tests -----------------------------------------------------------

    @DisplayName("draw: one house adds exactly 6 segments (4 walls + 2 roof lines; the top wall is drawn once)")
    @Test
    void draw_Test01() {
        new House(100, 300, 80, Color.RED).draw(pen);
        assertEquals(6, world.getSegments().size(),
                "a house is 6 segments: the square (4) plus two roof lines. Lift the pen to climb the wall.");
    }

    @DisplayName("draw: every segment is drawn in the house's colour (Color.BLUE), not the pen's old colour")
    @Test
    void draw_Test02() {
        pen.setPenColor(Color.BLACK);
        new House(100, 300, 80, Color.BLUE).draw(pen);
        ArrayList<World.Segment> segs = world.getSegments();
        assertEquals(6, segs.size(), "segment count");
        for (int i = 0; i < segs.size(); i++) {
            assertEquals(Color.BLUE, segs.get(i).color,
                    "segment " + i + " should be BLUE: call t.setPenColor(color) before drawing");
        }
    }

    @DisplayName("draw: every segment has length size (80) within 0.5 px")
    @Test
    void draw_Test03() {
        new House(100, 300, 80, Color.RED).draw(pen);
        ArrayList<World.Segment> segs = world.getSegments();
        assertEquals(6, segs.size(), "segment count");
        for (int i = 0; i < segs.size(); i++) {
            assertEquals(80.0, segs.get(i).length(), TOL,
                    "segment " + i + " should be exactly size long (walls AND roof lines)");
        }
    }

    @DisplayName("draw: bounding box - left edge at x, bottom edge at y, right edge at x+size, peak size*1.866 above y")
    @Test
    void draw_Test04() {
        new House(100, 300, 80, Color.RED).draw(pen);
        ArrayList<World.Segment> segs = world.getSegments();
        assertEquals(100.0, minX(segs), TOL, "left wall should be at x = 100");
        assertEquals(300.0, maxY(segs), TOL, "bottom wall should be at y = 300 (the house rises from (x, y))");
        assertEquals(180.0, maxX(segs), TOL, "right wall should be at x + size = 180");
        assertEquals(300 - 80 - 80 * ROOF, minY(segs), TOL,
                "roof peak should be size + 0.866*size above y (turn 30 then 120, not 60)");
    }

    @DisplayName("draw: the five corners are all present - (x,y), (x,y-size), (x+size,y-size), (x+size,y) and the peak")
    @Test
    void draw_Test05() {
        new House(200, 400, 100, Color.RED).draw(pen);
        ArrayList<World.Segment> segs = world.getSegments();
        assertTrue(hasCorner(segs, 200, 400), "bottom-left corner (200, 400) should be an endpoint");
        assertTrue(hasCorner(segs, 200, 300), "top-left corner (200, 300) should be an endpoint");
        assertTrue(hasCorner(segs, 300, 300), "top-right corner (300, 300) should be an endpoint");
        assertTrue(hasCorner(segs, 300, 400), "bottom-right corner (300, 400) should be an endpoint");
        assertTrue(hasCorner(segs, 250, 300 - 100 * ROOF),
                "roof peak (250, 213.4) should be an endpoint");
    }

    @DisplayName("draw: leaves the pen the way it found it - up stays up, down stays down")
    @Test
    void draw_Test06() {
        pen.penUp();
        new House(100, 300, 80, Color.RED).draw(pen);
        assertFalse(pen.isPenDown(), "the pen was UP before draw, so it should be UP afterward");

        Turtle pen2 = new Turtle(world);
        pen2.penDown();
        new House(100, 300, 80, Color.RED).draw(pen2);
        assertTrue(pen2.isPenDown(), "the pen was DOWN before draw, so it should be DOWN afterward");
    }

    @DisplayName("draw: a pen that was turned 45 degrees first still draws an upright house (setHeading(0))")
    @Test
    void draw_Test07() {
        pen.turn(45);
        new House(100, 300, 80, Color.RED).draw(pen);
        ArrayList<World.Segment> segs = world.getSegments();
        assertEquals(6, segs.size(), "segment count");
        assertEquals(100.0, minX(segs), TOL, "left edge should still be x = 100 - point the pen up first");
        assertEquals(180.0, maxX(segs), TOL, "right edge should still be x + size = 180 - a crooked house means you forgot setHeading(0)");
        assertEquals(300.0, maxY(segs), TOL, "bottom edge should still be y = 300");
    }

    @DisplayName("draw: a pen that starts somewhere else leaves no stray line - still exactly 6 segments, all at the house")
    @Test
    void draw_Test08() {
        pen.penDown();                       // pen is down and far away at the centre (250, 250)
        new House(20, 480, 40, Color.GREEN).draw(pen);
        ArrayList<World.Segment> segs = world.getSegments();
        assertEquals(6, segs.size(), "moving to (x, y) must happen with the pen UP, or you get a 7th segment");
        assertEquals(20.0, minX(segs), TOL, "no segment should reach back toward the pen's old position");
        assertEquals(480.0, maxY(segs), TOL, "bottom edge at y");
    }

    @DisplayName("draw: the same house drawn by two different pens gives 12 segments, all in the house colour, both with their own pen width")
    @Test
    void draw_Test09() {
        House h = new House(150, 350, 60, Color.MAGENTA);
        pen.setPenWidth(3);
        Turtle other = new Turtle(world);
        other.setPenWidth(1);
        h.draw(pen);
        h.draw(other);
        ArrayList<World.Segment> segs = world.getSegments();
        assertEquals(12, segs.size(), "two draws of one house = 12 segments");
        for (int i = 0; i < 12; i++) {
            assertEquals(Color.MAGENTA, segs.get(i).color, "segment " + i + " colour should be the house colour");
        }
        for (int i = 0; i < 6; i++) {
            assertEquals(3, segs.get(i).width, "first pen has width 3 - House must not change the pen width");
        }
        for (int i = 6; i < 12; i++) {
            assertEquals(1, segs.get(i).width, "second pen has width 1 - House must not change the pen width");
        }
    }
}
