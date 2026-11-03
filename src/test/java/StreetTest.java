import java.awt.Color;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class StreetTest {
    @DisplayName("Street: new Street(s, 4, ...) adds exactly 4 houses to the scene it was given")
    @Test
    void street_Test01() {
        Scene s = new Scene(640, 400);
        new Street(s, 4, 50, 300, 60, 20);
        assertEquals(4, s.getHouseCount(), "the houses must go into the caller's scene via scene.addHouse");
    }

    @DisplayName("Street: x positions step by size + gap (50, 130, 210, 290 for size 60, gap 20)")
    @Test
    void street_Test02() {
        Scene s = new Scene(640, 400);
        new Street(s, 4, 50, 300, 60, 20);
        assertEquals(4, s.getHouseCount(), "house count");
        assertEquals(50, s.getHouse(0).getX(), "house 0 stands at startX");
        assertEquals(130, s.getHouse(1).getX(), "house 1 stands at startX + (size + gap)");
        assertEquals(210, s.getHouse(2).getX(), "house 2 stands at startX + 2 * (size + gap)");
        assertEquals(290, s.getHouse(3).getX(), "house 3 stands at startX + 3 * (size + gap)");
    }

    @DisplayName("Street: every house has the same y (300) and size (60)")
    @Test
    void street_Test03() {
        Scene s = new Scene(640, 400);
        new Street(s, 4, 50, 300, 60, 20);
        for (int i = 0; i < 4; i++) {
            assertEquals(300, s.getHouse(i).getY(), "house " + i + " y");
            assertEquals(60, s.getHouse(i).getSize(), "house " + i + " size");
        }
    }

    @DisplayName("Street: colours alternate RED, BLUE, RED, BLUE starting with RED")
    @Test
    void street_Test04() {
        Scene s = new Scene(640, 400);
        new Street(s, 4, 50, 300, 60, 20);
        assertEquals(Color.RED, s.getHouse(0).getColor(), "house 0 is RED");
        assertEquals(Color.BLUE, s.getHouse(1).getColor(), "house 1 is BLUE");
        assertEquals(Color.RED, s.getHouse(2).getColor(), "house 2 is RED (even index)");
        assertEquals(Color.BLUE, s.getHouse(3).getColor(), "house 3 is BLUE (odd index)");
    }

    @DisplayName("Street: a gap of 0 packs houses edge to edge (x = 100, 140, 180 for size 40)")
    @Test
    void street_Test05() {
        Scene s = new Scene(640, 400);
        new Street(s, 3, 100, 250, 40, 0);
        assertEquals(3, s.getHouseCount(), "house count");
        assertEquals(100, s.getHouse(0).getX(), "house 0");
        assertEquals(140, s.getHouse(1).getX(), "house 1 - step is size + gap = 40");
        assertEquals(180, s.getHouse(2).getX(), "house 2");
    }

    @DisplayName("Street: count 0 adds nothing; count 1 adds a single RED house at startX")
    @Test
    void street_Test06() {
        Scene s = new Scene(640, 400);
        new Street(s, 0, 50, 300, 60, 20);
        assertEquals(0, s.getHouseCount(), "count 0 must add no houses");

        new Street(s, 1, 75, 300, 60, 20);
        assertEquals(1, s.getHouseCount(), "count 1 adds one house");
        assertEquals(75, s.getHouse(0).getX(), "the single house stands at startX");
        assertEquals(Color.RED, s.getHouse(0).getColor(), "the first house is always RED");
    }

    @DisplayName("Street: houses already in the scene stay; the street's houses come after them")
    @Test
    void street_Test07() {
        Scene s = new Scene(640, 400);
        House existing = new House(5, 100, 10, Color.GREEN);
        s.addHouse(existing);
        new Street(s, 2, 200, 300, 60, 20);
        assertEquals(3, s.getHouseCount(), "1 existing + 2 from the street");
        assertSame(existing, s.getHouse(0), "the existing house is still first");
        assertEquals(200, s.getHouse(1).getX(), "first street house at startX");
        assertEquals(280, s.getHouse(2).getX(), "second street house at startX + 80");
        assertEquals(Color.RED, s.getHouse(1).getColor(), "the street's own numbering starts at RED");
    }

    @DisplayName("Street: getScene() is the very same Scene object that was passed in (a reference, not a copy)")
    @Test
    void street_Test08() {
        Scene s = new Scene(640, 400);
        Street st = new Street(s, 2, 50, 300, 60, 20);
        assertSame(s, st.getScene(), "getScene() must return the Scene handed to the constructor");
        assertEquals(2, st.getScene().getHouseCount(), "and it already holds the houses the street added");
    }

    @DisplayName("Street: getCount() reports how many houses this street added (5)")
    @Test
    void street_Test09() {
        Scene s = new Scene(640, 400);
        Street st = new Street(s, 5, 20, 300, 30, 10);
        assertEquals(5, st.getCount(), "getCount()");
        assertEquals(5, s.getHouseCount(), "and the scene agrees");
    }

    @DisplayName("Street: the whole thing draws - drawAll after a 3-house street gives 18 segments")
    @Test
    void street_Test10() {
        Scene s = new Scene(640, 400);
        new Street(s, 3, 100, 340, 50, 25);
        s.drawAll();
        assertEquals(18, s.totalSegments(), "3 houses x 6 segments");
        assertEquals(Color.BLUE, s.getWorld().getSegments().get(6).color, "the middle house is BLUE");
    }
}
