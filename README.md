# Chrome-Dino-Java

# Table of Contents
1. [Problem Description](#problem-description)
   1. [The Chrome Dino Game](#the-chrome-dino-game)
   2. [The Chrome Dino Application](#the-chrome-dino-application)
2. [Requirements Specification](#requirements-specification)
3. [Project Design](#project)
   1. [Software System Architecture](#software-system-architecture)
   2. [Logic](#logic)
   3. [View](#view)
4. [Issues Encountered](#issues-encountered)
5. [Potential Extensions and Customizations](#extension-and-customization-possibilities)
6. [How to Run](#how-to-run)

# 1 Problem Description

The aim of this work is to develop a desktop application, called Chrome Dino, which creates a simplified version of the video game of the same name.
The application will be implemented using JFC/Swing technology in order to facilitate a wide portability on different operating systems (platforms), reducing to a minimum any changes to the source code.

Below is a brief description of the original Chrome Dino video game, followed by a description of the simplified version that is intended to be made.

## 1.1 The Chrome Dino Game

One of the most famous Easter Eggs in Google Chrome is Chrome Dino, which appears when you try to visit a website while disconnected from the Internet.
This game, also known as The Dinosaur Game, is a browser game developed by Google and integrated into the Google Chrome web browser.
The player guides a pixelated Tyrannosaurus Rex through a side-scrolling desert landscape, trying to avoid cactus obstacles to get the highest possible score.

The T-rex was designed and introduced in 2014 by Sebastien Gabriel, a visual designer also known for designing other Chrome icons, with the idea of ​​introducing an error message of no Internet connection that the user hopefully never sees. If unfortunately it were to happen, the attempt is made to make this inconvenience as pleasant as possible by distracting the user, so that the frustration gives way to a moment of pleasant relaxation.

The choice of the Tyrannosaurus as the protagonist of the game is not accidental, in fact the underlying message is: when you lose your Internet connection it is like being catapulted back to the age of the dinosaurs.

The game is an infinite runner that has no ending or time limit. While trying to go on as long as possible, the user forgets that he is actually facing one of the biggest problems of our time: the absence of the internet.

There are currently 270 million players each month, both on laptops and mobile devices. Unsurprisingly, most of the users come from areas with unreliable or expensive internet connections, such as India, Brazil, Mexico, or Indonesia.
The game became so popular that some students intentionally disconnected their school Chromebooks from the internet, allowing the dinosaur game to load within the Chrome web browser, without any restrictions.
Google developers took this issue very seriously and developed policies to disable the game on school Chromebooks, and later did the same for other platforms.

An accessible URL, `chrome://dino`, was later created to allow play without requiring offline status, featuring an "arcade mode" for a fullscreen experience.

## 1.2 The Chrome Dino Application

The game can be started by pressing the space bar. During gameplay, the T-Rex runs continuously from left to right through a desert landscape, and the player must avoid oncoming obstacles by jumping or ducking. Pressing the space bar makes the dinosaur jump, and pressing the down arrow key (↓) makes it duck. The game ends when the dinosaur collides with an obstacle, showing a "Game Over" message. Pressing the space bar allows the game to restart.

---

# 2 Requirements Specification

The Chrome Dino application will meet the following requirements:

1. The program opens a simple graphical window with the dinosaur character and the text "CHROME DINO" and "Press SPACE BAR to start."
2. The game begins when the space bar is pressed.
3. During gameplay, the character moves continuously from left to right in a desert landscape with clouds in the background.
4. Animation includes character running, specific frames for death, and moving ground and background.
5. The player can jump by pressing the space bar or duck by pressing the down arrow key (↓).
6. A sound plays each time the character jumps.
7. The score increases by 20 points with each obstacle avoided.
8. A sound plays when the score reaches 100 or its multiples.
9. The game ends when the dinosaur collides with an obstacle, displaying "Game Over" on the screen along with a replay icon suggesting to restart the game.
10. Pressing the space bar restarts the game.

---

# 3 Project Design

This section describes the structure of the application, detailing its software architecture and functional blocks.

## 3.1 Software System Architecture

For the realization of Chrome Dino, we chose to base ourselves on the Logic View (LV) programming pattern.
This particular software architecture can be obtained from another architecture, Model View Controller (MVC), by combining Model and Controller.

The **Logic** block is responsible for representing the data managed by the application and manages the operating logic of the program.

The **View** block is responsible for representing data to the user by collecting their input: in this case it is made up of the graphical interface.

In addition to the classes belonging to Logic and View, we have three other classes contained in the “utils” package: **Animation**, **Resource** and **AudioPlayer**.
The **Animation** class manages the game animations, the **Resource** class manages the various images contained in the “data” folder, while the **AudioPlayer** class manages the reproduction of sound effects represented by audio files, also contained in the “data” folder.

The Logic and View modules and their classes are described in detail below.

---

## 3.2 Logic

Classes in the **Logic** block of Chrome Dino are located in the "logic" package. Key classes include:

- **Enemy**: An abstract class representing generic enemies with four abstract methods: `draw(Graphics g)`, `update()`, `getBound()`, and `isOutOfScreen()`.
- **Cactus**: extends the `Enemy` class, it represents the enemy of the main character. It stores the coordinate on the X axis, the height, the width of the cactus and its bound, that is, the imaginary rectangle in which it is contained, whose collision with the dinosaur causes the “Game Over”. It has the `isOutOfScreen()` method, aimed at verifying whether the enemy has been correctly overcome. It also has a `getBound()` method that returns the size of the cactus’s bound. The `draw(Graphics g)` and `update()` methods are used to draw and update the cactus on the screen, respectively.
- **EnemiesManager**: is the class that manages the enemies, that is, the cacti. It takes care of creating (`createEnemy()`), drawing (`draw(Graphics g)`), updating (`update()`), resetting (`reset()`) the enemies on the screen and checking if a collision with the main character has occurred (hasCollided()), an event that leads to “Game Over”. There are two cactus images in the “data” folder that EnemiesManager draws from using the `getResourceImage(String path)` method of the Resource class in the “utils” package. The different types of cactus are created and drawn on the screen randomly.
- **Ground**: is the class that represents the ground on which the dinosaur runs. There are three images of ground in the “data“ folder that Ground draws from using the `getResourceImage(String path)` method of the Resource class. The `draw(Graphics g)` and `update()` methods are used to draw and update the ground on the screen, respectively. The different types of ground are drawn randomly and are represented by a list.
- **Clouds**: is the class that represents the clouds in the background. There is a cloud image in the “data” folder that Clouds draws from using the `getResourceImage(String path)` method of the Resource class. The `draw(Graphics g)` and `update()` methods are used to draw and update the clouds on the screen, respectively. The different clouds are drawn randomly on the background and are represented by a list.
- **Dino**: is the class that represents the main character. To represent the dinosaur, you need its spatial coordinates, its speed, its “bound”, its score, the various frames that are updated to obtain a smooth animation and sound effects. For the dinosaur animation, Dino uses the Animation class of the “utils” package, taking the various frames present in the “data” folder using the `getResourceImage(String path)` method of the Resource class. Dino manages the dinosaur's jump, ducking, death and update. In particular, using the `draw(Graphics g)` method, the class draws the character in the various states: Run, Down Run, Jumping and Death. The `update()` method is used to update the character's various frames, thus introducing a fluid animation. The `jump()` method manages the dinosaur's jump, also reproducing the related sound effect. The `getBound()` method returns the imaginary rectangle containing the main character, useful for managing any collisions. The `upScore()` method updates the score for each obstacle overcome and plays a sound effect when a score of 100 or a multiple of it is reached. The `reset()` method resets the dinosaur and the score in the event of "Game Over".

---

## 3.3 View

Classes in the **View** block are located in the "view" package. Key classes include:

- **GameWindow**: extends `JFrame` and constitutes the main window of the graphical application. Inside it there is a panel, an instance of GameScreen. It contains the main and is responsible for starting the game using the `startGame()` method.
- **GameScreen**: extends `JPanel` and implements `Runnable` and `KeyListener`. This class contains the `startGame()` method, invoked by `GameWindow`, which allows you to start the graphical application on a specific thread. The class draws and updates the graphical panel with all its components, namely the dinosaur, the cacti, the clouds and the ground, through the methods `paint(Graphics g)` and `gameUpdate()`. The latter is invoked by the `run()` method which represents the “Game Loop” of the video game. GameScreen implements `KeyListener`, so it is able to handle all events related to any user input via the keyboard. In the specific case of this application, the user can only use the space bar and the down arrow key (↓). The `keyPressed(KeyEvent e)` and `keyReleased(KeyEvent e)` methods allow you to specify the behavior of the program when a key is pressed or released respectively. In particular, if the application has just been launched, you can start playing by pressing the space bar; instead if the game has started you can make the dinosaur jump with the space bar and you can make it lower by pressing the down arrow key (↓). In case of "Game Over", you can restart playing by pressing the space bar. Finally, the `resetGame()` method allows you to reset the game in case of "Game Over".

---

# 4 Issues Encountered

Chrome Dino is quite simple from a logical point of view: both the modeling of the processed data and the management of the application logic did not cause any particular implementation problems.

The aspect that required the most time and effort was certainly the animation management. The various frames must be constantly updated following an appropriate time cadence to obtain a fluid animation that gives a sensation of movement to the reproduced scene. As for the dinosaur animation, the Dino class uses the `Animation` class of the "utils" package, taking the various frames present in the "data" folder using the `getResourceImage(String path)` method of the `Resource` class.

Sound effects management also caused some problems. First of all, some audio files, despite being in the correct format (`.wav`), led to several complications. Some clips caused errors during compilation, while others could not be read and therefore used.
Another problem related to audio was the reproduction of sound effects related to the dinosaur's close jumps. In fact, using a single `Clip` object, the audio clip was not played if the space bar was pressed immediately after having already pressed it.
It was therefore decided to use an array of `Clip` objects instead of a single object. The idea is to use the first object for the first jump event, the second object for the second and so on, proceeding in a circular order, that is, once you reach the last object in the array, you have to start again from the first.

---

# 5 Potential Extensions and Customizations

The application developed represents a minimally simplified version of the Chrome Dino video game, which, not being particularly complex, does not leave much room for possible expansion, except by introducing a real customization not present in the real game.

A possible extension for the application, not foreseen in the real game, could be a musical background that is played during the game, interrupted by a possible “Game Over”.
In this case, however, it is not recommended to use the `.wav` format, already used for the reproduction of sound effects represented by audio clips of a few seconds. This particular type of format is in fact not compressed, so it could require a significant amount of memory. A musical background, unlike a sound effect, can have a duration of several minutes and not a few seconds. Therefore, if you want to add a musical background to the video game, you must use compressed audio formats, in particular the `.mp3` format, and use libraries that allow, if possible, its reproduction in streaming mode.
A possible alternative instead is to loop a short audio file, thus creating a background of significant length. In this case, it is possible to use the `.wav` format, following the approach already used for short audio clips.

Another possible extension, also not foreseen in the real game, is to allow the dinosaur to move left and right, in addition to only jumping and ducking. In this case, however, the entire dynamics of the game would have to be changed. In the developed application, in fact, the main character can jump and duck, but is fixed on an area of ​​the screen. It is the various enemies, the clouds in the background and the ground that move towards him and not the other way around.

---

# 6 How to Run

To start the Chrome Dino application correctly, there are two possible procedures:

### Procedure 1
1. Double-click on the `ChromeDino.jar` file contained in the "dist" folder.

### Procedure 2
1. Open the command prompt.
2. Navigate to the "dist" folder.
3. Enter the following command and press Enter:
   ```bash
   java -jar ChromeDino.jar

