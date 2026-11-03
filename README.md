# Graphics Scene Class

**Unit 2 — Graphics, GUI & Applied Class Design** · Pairs with lecture 2.8 Designing Your Own Graphics Class

Until now the turtle was the point. Today it is a part. You will write a
`Scene` that *owns* a `World` and a list of `House` objects, a `House` that
knows where it stands and what colour it is but borrows a pen to draw itself,
and a `Street` that fills a scene with a row of houses. Nothing here is new
syntax — every line is Unit 2.1 syntax with a class name in the slot where an
`int` used to go. The idea is new; the code is not.

## Has-a, not is-a

There are two ways one class can be built from another. **Has-a**
(composition) means an object keeps another object as an instance variable: a
`Scene` has-a `World`, a `Scene` has-a list of `House`s, a `House` has-a
`Color`. You write it as a field, you build it in the constructor with `new`,
and it is all you need this semester. **Is-a** (inheritance) means one class
`extends` another and gets its methods for free: a `Dog` is-a `Animal`, a
`JPanel` is-a `Component`. That is Unit 4, in January. Do not write
`extends World` or `extends Turtle` in this assignment — you do not need it,
the tests do not expect it, and most real designs are has-a anyway.

## What you are given

| File | Status | Purpose |
|---|---|---|
| `src/main/java/House.java` | **you complete this** | position, size, colour; `draw(Turtle t)` |
| `src/main/java/Scene.java` | **you complete this** | owns a `World` and an `ArrayList<House>`; makes pens; draws everything |
| `src/main/java/Street.java` | **you complete this** | adds a row of alternating houses to a `Scene` |
| `src/main/java/Turtle.java` | provided | the turtle from Turtle Basics — do not edit |
| `src/main/java/World.java` | provided | the canvas; `getSegments()` is how the tests see your drawing |
| `src/main/java/Main.java` | provided | builds a neighbourhood and calls `show()` — run it to look at your work |
| `src/test/java/*Test.java` | provided | the autograder's tests — read them |
| `pom.xml`, `grading.json` | provided | build and grading setup — do not edit |

The fields of all three classes are declared for you. They start as `0` or
`null`. **Every object field gets a `new` in the constructor** — that is the
#1 crash from 2.8, and the `NullPointerException` will point at the line where
you *used* the field, not the constructor where you forgot it.

## What to write

| Rubric line | Points | What it does |
|---|---|---|
| `House` constructor + getters | 10 | store `x, y, size, color`; `getX() getY() getSize() getColor()` |
| `House.draw(Turtle t)` | 25 | draws the 6-segment house at `(x, y)` in `color` with the borrowed pen |
| `Scene` has-a: constructor, `getWorld`, `addHouse`, `getHouseCount`, `getHouse`, `getLargest` | 15 | the scene owns its parts |
| `Scene.makePen(Color c, int width)` | 10 | **returns** a new, configured `Turtle` |
| `Scene.drawAll()`, `Scene.totalSegments()` | 20 | one pen draws every house; count what is on the canvas |
| `Street` constructor, `getScene`, `getCount` | 20 | passes a `Scene` in and fills it |

### `House`

`(x, y)` is the **bottom-left corner of the walls**. `(0, 0)` is the top-left
of the world and y grows *downward*, so the house rises from `(x, y)` toward
smaller y. A `House` has **no Turtle field** — it is data, and a pen arrives as
a parameter only at the moment it is drawn.

`draw(Turtle t)` produces the same 6-segment house you drew in Turtle Basics,
every segment exactly `size` long:

```
       /\          roof: two sides of an equilateral triangle
      /  \         (the top wall is its base — drawn once, not twice)
     +----+   (x, y - size)          (x + size, y - size)
     |    |        walls: a square
     +----+   (x, y)                 (x + size, y)
```

The plan, in order:

```java
boolean wasDown = t.isPenDown();     // 0. remember how the pen arrived
t.penUp();  t.moveTo(x, y);          // 1. fly to the corner - no ink
t.setHeading(0);                     //    face UP (see the trap below)
t.setPenColor(color);  t.penDown();
for (int i = 0; i < 4; i++) {        // 2. the walls
    t.forward(size);  t.turnRight();
}                                    //    back at (x, y), facing up
t.penUp();  t.forward(size);  t.penDown();     // 3. climb to the top-left
t.turn(30);  t.forward(size);        // 4. left roof line, up to the peak
t.turn(120); t.forward(size);        //    right roof line, down to the corner
if (!wasDown) { t.penUp(); }         // 5. hand the pen back as you found it
```

The peak lands at `(x + size/2, y - size - 0.866*size)` — 0.866 is the height
of an equilateral triangle per unit of side. Where the turtle ends up does not
matter; how it *left the pen* does.

**The trap:** a `Turtle` keeps its heading between calls. If the pen you are
handed was last used to draw a roof, it is facing 150°, and a house drawn from
there comes out crooked. `t.setHeading(0)` before the first `forward` fixes it.
There is a test for exactly this — it hands you a pen that has been turned 45°.

### `Scene`

```
Scene
  private World world              the canvas
  private ArrayList<House> houses  in the order added
```

| Method | Rule |
|---|---|
| `Scene(int width, int height)` | `world = new World(width, height); houses = new ArrayList<House>();` |
| `World getWorld()` | the one world this scene owns — same object every call |
| `void addHouse(House h)` | append to the list |
| `int getHouseCount()` | `houses.size()` |
| `House getHouse(int i)` | `houses.get(i)`; `getHouse(0)` is the first one added |
| `Turtle makePen(Color c, int width)` | `new Turtle(world)`, then `setPenColor(c)`, `setPenWidth(width)`, `penUp()`, `hide()`, and **return it**. Every call makes a new turtle. |
| `void drawAll()` | make **one** pen, hand that same pen to every house's `draw`. Not one pen per house. |
| `House getLargest()` | biggest `getSize()`; `null` if no houses; ties go to the house added **first** |
| `int totalSegments()` | `world.getSegments().size()` — everything drawn, by `drawAll` or by any pen you made |

`makePen` is the "a method can return an object" slide. The return type slot
holds `Turtle` exactly where `int` would go, and the caller gets something
ready to use: `Turtle red = s.makePen(Color.RED, 4); red.penDown(); red.forward(150);`

**The trap in `getLargest`:** start with the first house as the best so far
and replace it only when you find something *strictly* bigger (`>`). With `>=`
a tie goes to the later house, and that test fails.

### `Street`

`Street(Scene scene, int count, int startX, int y, int size, int gap)` stores
`scene` and `count`, then adds `count` houses to `scene`. House `i` stands at
`x = startX + i * (size + gap)`, all with the same `y` and `size`. Even `i` is
`Color.RED`, odd `i` is `Color.BLUE`. Houses already in the scene stay where
they are; yours go after them.

This is the "what gets copied when you pass an object" slide made concrete.
Java copies the **reference**, so `scene.addHouse(...)` inside `Street`
changes the very scene the caller is holding. `getScene()` must return that
same object — the test checks with `assertSame`, not `assertEquals`.

## Examples

```java
Scene s = new Scene(640, 400);
s.getHouseCount();                       // 0
s.totalSegments();                       // 0

s.addHouse(new House(100, 300, 80, Color.RED));
s.addHouse(new House(200, 300, 120, Color.BLUE));
s.getHouse(1).getSize();                 // 120
s.getLargest() == s.getHouse(1);         // true

s.drawAll();
s.totalSegments();                       // 12  (6 per house)
s.getWorld().getSegments().get(0).color; // Color.RED  - first house first
s.getWorld().getSegments().get(6).color; // Color.BLUE

Turtle sun = s.makePen(Color.ORANGE, 5); // pen up, hidden, orange, width 5
sun.penDown(); sun.forward(40);
s.totalSegments();                       // 13

new Street(s, 3, 300, 300, 40, 10);      // x = 300, 350, 400 ; RED, BLUE, RED
s.getHouseCount();                       // 5
```

The single house `new House(100, 300, 80, Color.RED)` draws segments whose
endpoints are `(100,300) (100,220) (180,220) (180,300)` and the peak
`(140, 150.7)`; its bounding box runs from x = 100 to 180 and from
y = 150.7 to 300.

## Running the tests

`mvn test` runs everything; `mvn test -Dtest=HouseDrawTest` runs one rubric line.

| Test class | Rubric line | Points |
|---|---|---|
| `HouseBasicsTest` | House: constructor and getters | 10 |
| `HouseDrawTest` | House.draw(Turtle) borrows a pen | 25 |
| `SceneHousesTest` | Scene has-a World and houses; getLargest | 15 |
| `SceneMakePenTest` | Scene.makePen returns a configured Turtle | 10 |
| `SceneDrawAllTest` | Scene.drawAll and totalSegments | 20 |
| `StreetTest` | Street fills a Scene by reference | 20 |

The autograder awards a rubric line only when **every** test in that class
passes. The tests never open a window; they read `world.getSegments()` and
compare coordinates with a tolerance of 0.5 pixels.

## Suggested order

1. `House` constructor and the four getters. Run `HouseBasicsTest`. Five minutes.
2. `House.draw`. Follow the plan above line by line. Run `HouseDrawTest` and
   read each failure message — it names the corner or the count it expected.
3. `Scene` constructor, `getWorld`, `addHouse`, `getHouseCount`, `getHouse`.
   Run `SceneHousesTest`; the `getLargest` tests will still be red.
4. `getLargest`. Watch the tie test.
5. `makePen`. Run `SceneMakePenTest`. Remember `penUp()` — a new turtle
   starts pen *down*.
6. `drawAll` and `totalSegments`. Run `SceneDrawAllTest`. `drawAll` is two
   lines once `makePen` and `House.draw` exist.
7. `Street`. Run `StreetTest`, then run `Main` and look at the picture.

## Rules of the road

- AP Java subset only: fields, constructors, methods, `if`, `for`, `%`,
  `ArrayList<House>` with `add`, `get`, `size`. No arrays of houses, no
  `Collections`, no streams, no lambdas.
- **No `extends`.** This assignment is has-a. Is-a is January.
- Do not change method headers, the field declarations, or the provided
  `show()`. Do not edit `Turtle.java` or `World.java`.
- Do not touch `src/test`, `pom.xml`, `grading.json`, or `.github`. The
  autograder checks that they are byte-identical to the template before it
  runs a single test; if they differ it stops and awards nothing, and the
  change shows up in the roster.
- `Main` calls `scene.show()` last so a window opens on your laptop. The tests
  never call it, and on the grader it does nothing — so nothing you draw in
  `Main` affects your grade, and nothing in the tests needs a screen.
