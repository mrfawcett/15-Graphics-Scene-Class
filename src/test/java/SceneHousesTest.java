import java.awt.Color;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SceneHousesTest {
    @DisplayName("Scene: constructor builds a World of the requested size (640 x 400) - has-a World")
    @Test
    void scene_Test01() {
        Scene s = new Scene(640, 400);
        assertNotNull(s.getWorld(), "getWorld() is null - the constructor must run new World(...)");
        assertEquals(640, s.getWorld().getWidth(), "world width should be the first constructor argument");
        assertEquals(400, s.getWorld().getHeight(), "world height should be the second constructor argument");
    }

    @DisplayName("Scene: getWorld() returns the same World object every time, and each Scene has its own")
    @Test
    void scene_Test02() {
        Scene s = new Scene(300, 200);
        assertSame(s.getWorld(), s.getWorld(), "getWorld() should hand back the one World the scene owns, not a new one");
        Scene other = new Scene(300, 200);
        assertNotSame(s.getWorld(), other.getWorld());
    }

    @DisplayName("Scene: a new scene has 0 houses")
    @Test
    void scene_Test03() {
        Scene s = new Scene(640, 400);
        assertEquals(0, s.getHouseCount(),
                "getHouseCount() on a fresh scene should be 0 - construct the ArrayList in the constructor");
    }

    @DisplayName("Scene: addHouse grows getHouseCount() one at a time (0 -> 1 -> 2 -> 3)")
    @Test
    void scene_Test04() {
        Scene s = new Scene(640, 400);
        s.addHouse(new House(10, 100, 20, Color.RED));
        assertEquals(1, s.getHouseCount(), "after one addHouse");
        s.addHouse(new House(50, 100, 20, Color.BLUE));
        assertEquals(2, s.getHouseCount(), "after two addHouse calls");
        s.addHouse(new House(90, 100, 20, Color.GREEN));
        assertEquals(3, s.getHouseCount(), "after three addHouse calls");
    }

    @DisplayName("Scene: getHouse(i) returns the SAME House objects that were added, in order")
    @Test
    void scene_Test05() {
        Scene s = new Scene(640, 400);
        House a = new House(10, 100, 20, Color.RED);
        House b = new House(50, 100, 30, Color.BLUE);
        s.addHouse(a);
        s.addHouse(b);
        assertSame(a, s.getHouse(0), "getHouse(0) should be the first house added (the same object, not a copy)");
        assertSame(b, s.getHouse(1), "getHouse(1) should be the second house added");
    }

    @DisplayName("Scene: two scenes do not share houses (the list belongs to each object)")
    @Test
    void scene_Test06() {
        Scene s1 = new Scene(640, 400);
        Scene s2 = new Scene(640, 400);
        s1.addHouse(new House(10, 100, 20, Color.RED));
        s1.addHouse(new House(50, 100, 20, Color.RED));
        assertEquals(2, s1.getHouseCount(), "s1 has two houses");
        assertEquals(0, s2.getHouseCount(), "s2 should still be empty - is your list static?");
    }

    @DisplayName("getLargest: empty scene -> null")
    @Test
    void scene_Test07() {
        Scene s = new Scene(640, 400);
        assertNull(s.getLargest(), "with no houses getLargest() must return null, not crash");
    }

    @DisplayName("getLargest: picks the biggest house wherever it is in the list (sizes 60, 120, 90 -> the 120)")
    @Test
    void scene_Test08() {
        Scene s = new Scene(640, 400);
        House small = new House(10, 300, 60, Color.RED);
        House big = new House(100, 300, 120, Color.BLUE);
        House mid = new House(300, 300, 90, Color.GREEN);
        s.addHouse(small);
        s.addHouse(big);
        s.addHouse(mid);
        assertSame(big, s.getLargest(), "the size-120 house should win");

        Scene t = new Scene(640, 400);
        House only = new House(10, 300, 5, Color.RED);
        t.addHouse(only);
        assertSame(only, t.getLargest(), "with one house, that house is the largest");

        Scene u = new Scene(640, 400);
        House first = new House(10, 300, 40, Color.RED);
        House last = new House(10, 300, 200, Color.RED);
        u.addHouse(first);
        u.addHouse(new House(10, 300, 100, Color.RED));
        u.addHouse(last);
        assertSame(last, u.getLargest(), "the largest can be the LAST house added");
    }

    @DisplayName("getLargest: a tie goes to the house added FIRST (sizes 60, 120, 120 -> index 1)")
    @Test
    void scene_Test09() {
        Scene s = new Scene(640, 400);
        House a = new House(10, 300, 60, Color.RED);
        House b = new House(100, 300, 120, Color.BLUE);
        House c = new House(300, 300, 120, Color.GREEN);
        s.addHouse(a);
        s.addHouse(b);
        s.addHouse(c);
        assertSame(b, s.getLargest(), "on a tie return the earlier house - compare with >, not >=");
    }
}
