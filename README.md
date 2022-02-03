# JTamaro

JTamaro is a Java educational library designed for teaching problem decomposition using graphics.

It follows the same philosphy as its sister project, PyTamaro for Python.

## Usage
There are two different ways to use JTamaro:

* The "functional" way, which does not require an understanding of inheritance
* The "object-oriented" way, which can be used to discuss inheritance

Both ways use the type `Graphic` (or `Grafik` in the German localization).
The functional way (package `jtamaro.fun`) provides static methods to construct and compose graphics.
The object-oriented way (package `jtamaro.oo`) uses a class hierarchy for the different kinds of graphics
and operators on graphics.

### Functional (requires static methods)

```java
import jtamaro.en.Graphic;
import jtamaro.en.Color;
import jtamaro.en.fun.Op;

Graphic h = Op.rectangle(200, 60, Color.WHITE);
Graphic v = Op.rectangle(60, 200, Color.WHITE);
Graphic cross = Op.overlay(h, v);
```

### Object-Oriented (requires subtyping)

```java
import jtamaro.en.Graphic;
import jtamaro.en.Color;
import jtamaro.en.oo.Rectangle;
import jtamaro.en.oo.Overlay;

Graphic h = new Rectangle(200, 60, Color.WHITE);
Graphic v = new Rectangle(60, 200, Color.WHITE);
Graphic cross = new Overlay(h, v);
```

## Implementation

The implementation is "hidden away" in packages `jtamaro.internal.**`.
Students are only confronted with the localized API in packages `jtamaro.LOCALE.**`.

The localized APIs delegate to the internal implementation.
The APIs provide classes (and methods) that have localized names,
and those classes delegate all work to the corresponding implementation classes.

Color functionality is delegated to class `ColorImpl`.
Graphic functionality is delegated to class `GraphicImpl` and its subclasses.
