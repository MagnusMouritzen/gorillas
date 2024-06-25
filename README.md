# Gorillas
School project to make a version of the classic Gorillas game in JavaFX. It was made by Emil Dissing, William Lohmann, and Magnus Mouritzen. The code comments describe the work distribution. Emil made all the front-end files where responsibility is not explicitly stated.

## File structure
The source code is split into three parts, all lying in src/main/java/unibanden/ist/.
- gameengine/: These are the game-agnostic systems that are used to build the actual game. It was inspired by the structure of Unity.
- gorillas/game/: These make up the game itself and is independent of JavaFX.
- gorillas/frontend/: This connect the game to JavaFX and displays everything.

## Notes for playing
The program can not be smaller than 900x600, so if your screen is too small - or if you have zoomed in on the computer - you can not see the entire program. 

If you run the program on Linux, there is a mistake making so the image can not be updated when you change window in the menu. You must move the window around every time you have clicled a button.

When you have been to options in the menu for the first time, a settings.ser file will be generated outside of the folder of the project. Delete the file to reset the settings.
