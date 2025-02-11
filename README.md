# JTamaro

JTamaro is a Java educational library designed for teaching problem
decomposition using graphics.

It follows the same philosophy as its sister project,
[PyTamaro](https://github.com/LuceResearchLab/pytamaro) for Python.

## Components

### `lib/data`

Provides implementation for the following types:

- `Function{0-4}`: interfaces for function objects with up to 4 arguments
- `Option`: the Option(al) / Maybe monad. Used to handle errors
- `Pair`: tuple of two elements
- `Sequence`: ordered list of elements of a certain type

### `lib/graphic`

There are two different ways to use JTamaro:

- The "functional" way, which does not require an understanding of inheritance
- The "object-oriented" way, which can be used to discuss inheritance

Both ways use the type `Graphic`.
The functional way (class `jtamaro.graphic.Graphics`) provides static methods to
construct and compose graphics.
The object-oriented way (package `jtamaro.graphic`) uses a class hierarchy
for the different kinds of graphics
and operators on graphics.

#### Functional (requires static methods)

```java
import jtamaro.graphic.Graphic;
import jtamaro.graphic.Colors;
import jtamaro.graphic.Graphics;

Graphic h = Graphics.rectangle(200, 60, Colors.WHITE);
Graphic v = Graphics.rectangle(60, 200, Colors.WHITE);
Graphic cross = Graphics.overlay(h, v);
```

Using static imports, we can eliminate the need for mentioning class Graphics in
each call:

```java
import jtamaro.graphic.Graphic;

import static jtamaro.graphic.Colors.*;
import static jtamaro.graphic.Graphics.*;

Graphic h = rectangle(200, 60, WHITE);
Graphic v = rectangle(60, 200, WHITE);
Graphic cross = overlay(h, v);
```

#### Object-Oriented (requires subtyping)

```java
import jtamaro.graphic.Graphic;
import jtamaro.graphic.Colors;
import jtamaro.graphic.Rectangle;
import jtamaro.graphic.Overlay;

Graphic h = new Rectangle(200, 60, Colors.WHITE);
Graphic v = new Rectangle(60, 200, Colors.WHITE);
Graphic cross = new Overlay(h, v);
```

### `lib/interaction`

Interaction allows to develop an interactive application.

### `lib/io`

The colors and graphics are pure.
Code that performs side effects (e.g., showing a graphic on the screen)
is accessible through methods of class `IO`:

```java
import static jtamaro.graphic.Colors.*;
import static jtamaro.graphic.Graphics.*;
import static jtamaro.io.IO.*;

show(rectangle(200, 100, RED))
```

## Build

This project uses Gradle.
It contains multiple subprojects:

* `lib` - the main jtamaro module. Includes the data structure, graphics and I/O
* `music` - midi-based music library that allows to play notes and chords
* `example` - an application that contains some demos that use of the library

To build everything and run the demo app:

```bash
./gradlew :example:run
```

To run individual demos in the demo app, run the corresponding classes
with `main` methods in the `example` directory
from within an IDE that understands gradle (e.g., VS Code)
and thus will find (and if needed build) the library.

To just build a library's jar file for usage in other projects:

```bash
./gradlew :lib:jar
```

The output will be in `lib/build/libs/lib-*.jar`
