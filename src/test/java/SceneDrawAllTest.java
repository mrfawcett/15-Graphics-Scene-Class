import java.awt.Color;
import java.util.ArrayList;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SceneDrawAllTest {
    private static final double TOL = 0.5;

    @DisplayName("totalSegments: a fresh scene has 0 segments")
    @Test
    void drawAll_Test01() {
        Scene s = new Scene(640, 400);
        assertEquals(0, s.totalSegments(), "nothing has been drawn yet");
    }

    @DisplayName("drawAll: with no houses it draws nothing and does not crash")
    @Test
    void drawAll_Test02() {
        Scene s = new Scene(640, 400);
        assertDoesNotThrow(() -> s.drawAll(), "drawAll on an empty scene must simply do nothing");
        assertEquals(0, s.totalSegments(), "no houses, no segments");
    }

    @DisplayName("drawAll: one house -> 6 segments")
    @Test
    void drawAll_Test03() {
        Scene s = new Scene(640, 400);
        s.addHouse(new House(100, 300, 80, Color.RED));
        s.drawAll();
        assertEquals(6, s.totalSegments(), "one house is 6 segments (does House.draw pass HouseDrawTest?)");
        assertEquals(6, s.getWorld().getSegments().size(), "totalSegments() must report world.getSegments().size()");
    }

    @DisplayName("drawAll: three houses -> 18 segments (6 per house), and totalSegments agrees")
    @Test
    void drawAll_Test04() {
        Scene s = new Scene(640, 400);
        s.addHouse(new House(40, 340, 100, Color.RED));
        s.addHouse(new House(200, 340, 60, Color.BLUE));
        s.addHouse(new House(300, 340, 60, Color.GREEN));
        s.drawAll();
        assertEquals(18, s.getWorld().getSegments().size(), "3 houses x 6 segments - did drawAll visit EVERY house?");
        assertEquals(18, s.totalSegments(), "totalSegments() should match the world's segment list");
    }

    @DisplayName("drawAll: houses are drawn in the order added - first 6 segments RED, next 6 BLUE")
    @Test
    void drawAll_Test05() {
        Scene s = new Scene(640, 400);
        s.addHouse(new House(40, 340, 60, Color.RED));
        s.addHouse(new House(200, 340, 60, Color.BLUE));
        s.drawAll();
        ArrayList<World.Segment> segs = s.getWorld().getSegments();
        assertEquals(12, segs.size(), "two houses = 12 segments");
        for (int i = 0; i < 6; i++) {
            assertEquals(Color.RED, segs.get(i).color, "segment " + i + " belongs to the first (RED) house");
        }
        for (int i = 6; i < 12; i++) {
            assertEquals(Color.BLUE, segs.get(i).color, "segment " + i + " belongs to the second (BLUE) house");
        }
    }

    @DisplayName("drawAll: each house lands at its own (x, y) - the second house's segments start at x = 200")
    @Test
    void drawAll_Test06() {
        Scene s = new Scene(640, 400);
        s.addHouse(new House(40, 340, 60, Color.RED));
        s.addHouse(new House(200, 340, 60, Color.BLUE));
        s.drawAll();
        ArrayList<World.Segment> segs = s.getWorld().getSegments();
        assertEquals(12, segs.size(), "two houses = 12 segments");
        double minX = Double.MAX_VALUE, maxX = -Double.MAX_VALUE;
        for (int i = 6; i < 12; i++) {
            minX = Math.min(minX, Math.min(segs.get(i).x1, segs.get(i).x2));
            maxX = Math.max(maxX, Math.max(segs.get(i).x1, segs.get(i).x2));
        }
        assertEquals(200.0, minX, TOL, "second house should start at its own x (200): call h.draw(pen) for each house");
        assertEquals(260.0, maxX, TOL, "second house should be its own size wide (60)");
    }

    @DisplayName("drawAll: uses ONE pen for all houses - at most one new turtle per drawAll call, not one per house")
    @Test
    void drawAll_Test07() {
        Scene s = new Scene(640, 400);
        s.addHouse(new House(40, 340, 60, Color.RED));
        s.addHouse(new House(200, 340, 60, Color.BLUE));
        s.addHouse(new House(300, 340, 60, Color.GREEN));
        int before = s.getWorld().getTurtles().size();
        s.drawAll();
        int added = s.getWorld().getTurtles().size() - before;
        assertTrue(added <= 1, "drawAll created " + added + " turtles for 3 houses - make one pen and hand it to every house");
        assertEquals(18, s.totalSegments(), "3 houses x 6 segments");
    }

    @DisplayName("drawAll: calling it twice draws everything twice (one house -> 12 segments)")
    @Test
    void drawAll_Test08() {
        Scene s = new Scene(640, 400);
        s.addHouse(new House(100, 300, 80, Color.RED));
        s.drawAll();
        s.drawAll();
        assertEquals(12, s.totalSegments(), "drawAll does not clear the world; two calls = two drawings");
    }

    @DisplayName("totalSegments: counts what YOUR pens draw too (makePen + forward = 1, then drawAll adds 6 -> 7)")
    @Test
    void drawAll_Test09() {
        Scene s = new Scene(640, 400);
        Turtle sun = s.makePen(Color.ORANGE, 5);
        sun.penDown();
        sun.forward(40);
        assertEquals(1, s.totalSegments(), "one segment from the pen you made");
        s.addHouse(new House(100, 300, 80, Color.RED));
        s.drawAll();
        assertEquals(7, s.totalSegments(), "1 from the sun pen + 6 from the house");
    }
}
