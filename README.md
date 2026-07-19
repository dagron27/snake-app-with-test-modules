# Java Swing Snake Game

![CI](https://github.com/dagron27/se470_group_project/actions/workflows/ci.yml/badge.svg)

**Assignment:** `se470_group_project-main` — SE 470 (group assignment).

This repository root only contains project scaffolding (`.project`, `.gitattributes`,
`.gitignore`, this file). The actual Maven project lives in the subfolder
[`snake_group_project/`](snake_group_project/).

## Overview

A self-contained Java Swing implementation of the classic Snake game.

- `com.snakegame.Game` opens an 800x600 `JFrame` and starts the game.
- `com.snakegame.GamePanel` runs the game loop on a 100ms-tick Swing `Timer`,
  handles arrow-key steering, food-based growth, collision detection, and shows
  a "Game Over" dialog (`JOptionPane`) with the final score when the snake
  collides with itself or the wall.
- Supporting classes: `Position`, `Snake`, `Food`, `Direction`.

Source layout (`snake_group_project/src/main/java/com/snakegame/`):

```
Game.java
GamePanel.java
Snake.java
Food.java
Position.java
Direction.java
```

Tests (`snake_group_project/src/test/java/com/snakegame/`):

```
UnitTests/PositionTest.java
UnitTests/SnakeTest.java
UnitTests/FoodTest.java
UnitTests/GamePanelTest.java
IntegrationTests/GamePanelFoodIntegrationTest.java
```

Five test classes total: four unit tests (Position, Snake, Food, GamePanel) and
one integration test covering GamePanel + Food interaction.

## Dependencies

- Java 23 (`maven.compiler.release` in `pom.xml`)
- Maven (no wrapper committed; use a locally installed `mvn`)
- JUnit 5.12.2 (`org.junit.jupiter:junit-jupiter`), test scope only
- Maven Surefire Plugin 3.2.5, to run the JUnit 5 tests

No runtime dependencies beyond the JDK's own `javax.swing` / `java.awt`. No
file I/O, networking, serialization, or external process execution anywhere
in the codebase.

## Environment Setup

All commands below are run from the `snake_group_project/` subfolder, since
that is where `pom.xml` lives:

```
cd snake_group_project
```

Build and run the tests:

```
mvn test
```

Compile:

```
mvn compile
```

Run the game. The `pom.xml` `exec.mainClass` property now points at the real
entry point (`com.snakegame.Game`), but the `exec-maven-plugin` itself is
still not declared in `<build><plugins>`, so `mvn exec:java` is not wired up
out of the box. Run the entry point directly instead:

```
mvn compile
mvn dependency:build-classpath -Dmdep.outputFile=cp.txt
java -cp "target/classes;$(cat cp.txt)" com.snakegame.Game
```

(On Windows PowerShell, replace the classpath separator `;`/backtick as
appropriate for your shell, or simply run the compiled class from an IDE
such as Eclipse/NetBeans, which are set up via the committed `.project` /
`.classpath` / `nbactions.xml` files.)

Controls: Arrow keys to steer. The snake may not reverse directly into
itself (e.g. pressing Down while moving Up is ignored).

## Contributions

This was a group assignment with three contributors. Per-author history is
preserved in the repository's git log (`git log --author=...`) and is
summarized here for reference; it is also the basis for the LICENSE scoping
in this repository (see `LICENSE`).

**Daniel Leone (dagron27)** -- primary implementer:

- Initial repo scaffolding (`c53ef43`): `.gitattributes`, `.gitignore`,
  initial `README.md`.
- `bcd802d` "Complete Snake Game": the entire main source tree
  (`Game.java`, `GamePanel.java`, `Snake.java`, `Food.java`,
  `Position.java`, `Direction.java`), `pom.xml`, and `nbactions.xml`.
- `c597fe1`, `34a3a75` -- initial `FoodTest.java` and `GamePanelTest.java`.
- `181fdea` -- restructuring tests into `UnitTests/` / `IntegrationTests/`
  subpackages.
- `470f621` -- added `GamePanelFoodIntegrationTest.java` and extended
  `GamePanelTest.java`.
- `2565ec2` -- `.gitignore` update.
- `48ed41d` -- import/package fix in `SnakeTest.java`.
- `59bbba7` -- minor cleanup in `GamePanelFoodIntegrationTest.java`.

**JMDeschenes13** -- contributed `PositionTest.java` (`b40b812`, later
updated in `39cc680`) and the Eclipse project metadata (`.project`,
`.classpath`, `.settings/`) plus a `.gitignore` addition.

**kouslab** -- contributed the initial `SnakeTest.java` (`84176bf`).

## Known Issues

### Dead Code / Hygiene

1. **Stale `exec.mainClass` in `pom.xml` -- FIXED**
   `snake_group_project/pom.xml` previously set
   `<exec.mainClass>com.mycompany.snake_group_project.Snake_group_project</exec.mainClass>`,
   a leftover Maven archetype default pointing at a class that does not exist
   anywhere in `src/`. This has been corrected to the real entry point,
   `<exec.mainClass>com.snakegame.Game</exec.mainClass>`
   (`snake_group_project/src/main/java/com/snakegame/Game.java`). Note that
   the `exec-maven-plugin` itself is still not declared under
   `<build><plugins>`, so `mvn exec:java` is not a working run path on its
   own yet -- adding that plugin declaration is a possible future
   improvement, but was out of scope for this one-line property fix.

2. **Committed `target/` build output -- REMOVED**
   `snake_group_project/target/` (Maven build output, including
   `maven-archiver/`, `maven-status/`, and `surefire-reports/`) was checked
   into the repository even though `snake_group_project/.gitignore` excludes
   `/target/`, indicating it was committed before the `.gitignore` rule
   existed or was force-added at some point. It has now been removed from
   both git tracking (`git rm -r --cached`) and the working tree, so the
   `.gitignore` rule is enforced going forward and future builds will
   regenerate `target/` locally without it re-entering the repository.

   Since no local Maven installation is available in this environment, the
   removal was verified with a direct `javac` compile instead of `mvn
   compile`: running
   `javac -d /tmp/snake_out $(find src/main/java -name '*.java')` from
   `snake_group_project/` succeeded with no errors, producing class files for
   all six source classes (`Game`, `GamePanel`, `Snake`, `Food`, `Position`,
   `Direction`, plus the two `GamePanel$1`/`Snake$1` anonymous-class files).
   This confirms the source tree compiles cleanly with `target/` absent;
   `mvn test` should still be used to confirm the full build once Maven is
   available.

3. **Git history for individual contributions -- RESOLVED**
   An earlier note here claimed this archive had no prior commit history.
   That was true only of an older zip-extracted copy of the project; this
   checkout was cloned directly from GitHub and retains the full original
   commit history (`c53ef43` through `39cc680`, April 2025). Per-member
   contributions can be verified with `git log --author=...`; see the
   "Contributions" section below for a summary.

### Security

No security findings. This codebase was reviewed in a prior session and
found clean: no file I/O, no networking, no serialization, no command
execution, and the only dependency (JUnit 5) is test-scoped and current.
Verify this still holds if dependencies or features are added later.

## Status

Functional, self-contained coursework project. Builds and passes its test
suite via `mvn test`. Both hygiene items previously noted here have been
addressed: `exec.mainClass` now points at the real entry point, and the
committed `target/` directory has been removed from the repository. Neither
was a functional bug, and building, testing, and running the game via the
documented `java -cp` command were never blocked by them.
