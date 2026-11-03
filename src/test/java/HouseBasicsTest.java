import java.awt.Color;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class HouseBasicsTest {
    @DisplayName("House: constructor stores x and y; getX()/getY() return them (100, 300)")
    @Test
    void house_Test01() {
        House h = new House(100, 300, 80, Color.RED);
        assertEquals(100, h.getX(), "getX() should return the x passed to the constructor");
        assertEquals(300, h.getY(), "getY() should return the y passed to the constructor");
    }

    @DisplayName("House: getSize() returns the size passed in (80)")
    @Test
    void house_Test02() {
        House h = new House(100, 300, 80, Color.RED);
        assertEquals(80, h.getSize(), "getSize() should return the size passed to the constructor");
    }

    @DisplayName("House: getColor() returns the colour passed in (Color.RED, then a custom colour)")
    @Test
    void house_Test03() {
        House h = new House(100, 300, 80, Color.RED);
        assertEquals(Color.RED, h.getColor(), "getColor() should return Color.RED");
        House k = new House(0, 0, 10, new Color(10, 20, 30));
        assertEquals(new Color(10, 20, 30), k.getColor(),
                "getColor() should return exactly the Color object handed to the constructor");
    }

    @DisplayName("House: two houses keep their own values (fields are per object, not static)")
    @Test
    void house_Test04() {
        House a = new House(10, 20, 30, Color.RED);
        House b = new House(40, 50, 60, Color.BLUE);
        assertEquals(10, a.getX(), "house a x");
        assertEquals(40, b.getX(), "house b x");
        assertEquals(20, a.getY(), "house a y");
        assertEquals(50, b.getY(), "house b y");
        assertEquals(30, a.getSize(), "house a size");
        assertEquals(60, b.getSize(), "house b size");
        assertEquals(Color.RED, a.getColor(), "house a colour");
        assertEquals(Color.BLUE, b.getColor(), "house b colour");
    }

    @DisplayName("House: x and y are not mixed up - new House(7, 250, ...) has getX() 7 and getY() 250")
    @Test
    void house_Test05() {
        House h = new House(7, 250, 15, Color.GREEN);
        assertEquals(7, h.getX(), "getX() must be the FIRST constructor argument");
        assertEquals(250, h.getY(), "getY() must be the SECOND constructor argument");
        assertEquals(15, h.getSize(), "getSize() must be the THIRD constructor argument");
    }

    @DisplayName("House: getters can be called repeatedly and keep returning the same values")
    @Test
    void house_Test06() {
        House h = new House(100, 300, 80, Color.RED);
        h.getX(); h.getY(); h.getSize(); h.getColor();
        assertEquals(100, h.getX(), "getX() should not change between calls");
        assertEquals(300, h.getY(), "getY() should not change between calls");
        assertEquals(80, h.getSize(), "getSize() should not change between calls");
        assertEquals(Color.RED, h.getColor(), "getColor() should not change between calls");
    }
}
