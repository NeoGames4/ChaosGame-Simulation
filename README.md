# ChaosGame-Simulation

A Java simulation of the Chaos Game. The Chaos Game works by…
> “[…] iteratively creating a sequence of points, starting with the initial random point, in which each point in the sequence is a given fraction of the distance between the previous point and one of the vertices of the polygon; the vertex is chosen at random in each iteration.”

– Wikipedia ([Chaos game](https://en.wikipedia.org/wiki/Chaos_game))

https://github.com/NeoGames4/ChaosGame-Simulation/assets/48923122/94dc1c3c-4735-4cad-ac33-32e8faf2a37b

## Create Own Games (Edit Vertices, “Nodes”)
1. Open Frame.java.
2. Scroll down to the constructor: `public Frame() {…}`, “GAME CREATION”.
3. Replace `ChaosGameSetups.getEqualateralTriangle(400)` either with a custom `Node[]`-array or one of the other templates of the `ChaosGameSetups.java` class.

Please check `ChaosGameSetups.java` or the [javadocs](https://neogames4.github.io/docs/ChaosGame/java/index.html) for more information.
Use `ChaosGameSetups.getCircle(…)` to generate any [regular polygon](https://en.wikipedia.org/wiki/Regular_polygon).
Take a look [here](https://neogames4.github.io/docs/ChaosGame/java/game/ChaosGameSetups.html) to see all provided starting arrangements.

## Controls
* W, A, S or D – Move around
* X or Y – Zoom in or out
* Shift – Hold to accelerate navigation
* R – Reset position and zoom
* Space – Pause or continue
* E	– Continue one step
* Arrow Up – Increase steps per frame
* Arrow Down – Decrease steps per frame
* I or O – Decrease or increase dot size
* F1 – Toggle the UI
* F2 – Toggle connecting lines
* H – Hold to show help menu
