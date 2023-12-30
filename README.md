# JTamaro

JTamaro is a Java educational library designed for teaching problem decomposition using graphics.

It follows the same philosphy as its sister project, PyTamaro for Python.

## Usage

There are two different ways to use JTamaro:

* The "functional" way, which does not require an understanding of inheritance
* The "object-oriented" way, which can be used to discuss inheritance

Both ways use the type `Graphic` (or `Grafik` in the German localization).
The functional way (class `jtamaro.en.Graphics`) provides static methods to construct and compose graphics.
The object-oriented way (package `jtamaro.en.graphic`) uses a class hierarchy for the different kinds of graphics
and operators on graphics.

### Functional (requires static methods)

```java
import jtamaro.en.Graphic;
import jtamaro.en.Colors;
import jtamaro.en.Graphics;

Graphic h = Graphics.rectangle(200, 60, Colors.WHITE);
Graphic v = Graphics.rectangle(60, 200, Colors.WHITE);
Graphic cross = Graphics.overlay(h, v);
```

Using static imports, we can eliminate the need for mentioning class Graphics in each call:

```java
import jtamaro.en.Graphic;
import static jtamaro.en.Colors.*;
import static jtamaro.en.Graphics.*;

Graphic h = rectangle(200, 60, WHITE);
Graphic v = rectangle(60, 200, WHITE);
Graphic cross = overlay(h, v);
```

### Object-Oriented (requires subtyping)

```java
import jtamaro.en.Graphic;
import jtamaro.en.Colors;
import jtamaro.en.graphics.Rectangle;
import jtamaro.en.graphics.Overlay;

Graphic h = new Rectangle(200, 60, Colors.WHITE);
Graphic v = new Rectangle(60, 200, Colors.WHITE);
Graphic cross = new Overlay(h, v);
```

## IO

The colors and graphics are pure.
Code that performs side-effects (e.g., showing a graphic on the screen)
is accessible through methods of class `IO`:

```java
import static jtamaro.en.Colors.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.IO.*;

show(rectangle(200, 100, RED))
```

## Implementation

The implementation is "hidden away" in packages `jtamaro.internal.**`.
Students are only confronted with the localized API in packages `jtamaro.LOCALE.**`.

The localized APIs delegate to the internal implementation.
The APIs provide classes (and methods) that have localized names,
and those classes delegate all work to the corresponding implementation classes.

Color functionality is delegated to class `ColorImpl`.
Graphic functionality is delegated to class `GraphicImpl` and its subclasses.

## Build

Compile a jar file for usage in other projects:

```bash
./gradlew jar
```

The output will be in `build/libs/jtamaro-*.jar`

## Use

### Use in BlueJ

The library can be stored as a JAR file inside the +libs directory of a BlueJ project;
this way BlueJ will pick it up and one can import names using normal import statements.

In the BlueJ Code Pad, one can enter the necessary import statements
(which is a bit painful),
and then enter any JTamaro expressions.

### Use in JShell

To use the library in JShell, using a startup script that configures the class path
(in the following example, referring to a JAR in the +libs directory)
and then imports all the necessary classes could be useful:

```jsh
/env -class-path +libs/jtamaro-1.0.0.jar
import static jtamaro.en.IO.*;
import static jtamaro.en.Color.*;
import static jtamaro.en.Colors.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.Sequences.*;
import static jtamaro.en.Pairs.*;
import static jtamaro.en.Points.*;
import jtamaro.en.Graphic;
import jtamaro.en.Color;
import jtamaro.en.Pair;
import jtamaro.en.Sequence;
import jtamaro.en.Point;
```

Then one can launch JShell as follows, and all the names will be imported already:

```sh
jshell startup.jsh
```

This way, one can immediately entire expressions like the following:

```java
showFilmStrip(map(i->rectangle(i % 10 * 10, 100, RED), range(10)), 100, 100)
```
