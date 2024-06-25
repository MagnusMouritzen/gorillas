package unibanden.ist.gorillas.game;

import java.awt.Color;
import unibanden.ist.gameengine.*;

/**
 * Responsible: Emil Dissing s214912
 * Creates the City Map on an empty canvas
 */
public class MapCity extends Map {
	
	@Override
    public void generateLevel(int height, int width){
		//creates an array that keeps all info on structures on every pixel
        int[][] structure =  new int[height][width];
        //creates an array that keeps all information on which colors to display on every pixel
        Color[][] visuals = new Color[height][width];
        //controls the min and max x value of each building
        int minx = 3;
        int maxx = 6;
        //controls the min and max y value of each buildings
        int miny = 5;
        int maxy = 15;
        //variable to keep track of the current x and y values
        int currentx = 0;
        int currenty = 0;
        //value to keep track of the start of a building
        int lastx = 0;
        //value that choses the color
        int color = 0;
        //creates an array which controlls the window colors on all buildings on canvas
        double[][] windowColor = new double[height/16 + 1][width/16 + 1];
        for (int i = 0; i< windowColor.length; i++) {
        	for (int t = 0; t< windowColor[i].length; t++) {
        		windowColor[i][t] = Math.random();
        	}
        	
        }
        
        for (int x = 0; x < width; x++) {
        	//checks if a new building is starting
        	if (currentx <= x) {
        		lastx = currentx;
        		//choses the size of the building
        		currentx+= (int)((Math.random() * (maxx - minx) + minx))*16;
        		currenty = (int)((Math.random() * (maxy - miny) + miny))*16;
        		//choses the color of the building
        		color = (int)(Math.random()*3);
        		
        	}
        	for (int y = 0; y < height; y++) {
        		//checks if the current pixel is within the buildings height
        		if (y<currenty) {
        			//sets the state of the structure on this pixel
        			structure[y][x] = Layer.Destructible.ordinal();
        			//checks if its the outline of the building
        			if (x == lastx || x == currentx || y+1 == currenty) {
        				//selects the color
        				if (color == 0) {
            				visuals[y][x] = new Color(10, 107, 143);
            			}
            			else if (color == 1) {
            				visuals[y][x] = new Color(165, 88, 6);
            			}
            			else {
            				visuals[y][x] = new Color(67, 11, 151);
            			}
        			}
        			//checks if a window is supposed to be on this column of pixels
        			else if (x%16 > 4 && x%16 < 12 ) {
        				//checks if a window is supposed to be on this row of pixels
        				if((currenty-y)%16>4 && (currenty-y)%16<14) {
        					//sets the window color
        					if(windowColor[y/16][x/16]<0.5) {
        						visuals[y][x] = new Color(255, 240, 67);
        					}
        					else {
        						visuals[y][x] = new Color(136, 136, 136);
        					}
        					
        				}
        				else {
        					//sets building color
            				if (color == 0) {
                				visuals[y][x] = new Color(81, 184, 193);
                			}
                			else if (color == 1) {
                				visuals[y][x] = new Color(235, 131, 55);
                			}
                			else {
                				visuals[y][x] = new Color(127, 55, 235);
                			}
            			}
        				
        			}
        			else {
        				//sets building color
        				if (color == 0) {
            				visuals[y][x] = new Color(81, 184, 193);
            			}
            			else if (color == 1) {
            				visuals[y][x] = new Color(235, 131, 55);
            			}
            			else {
            				visuals[y][x] = new Color(127, 55, 235);
            			}
        			}
        		}
        		//sets nothing
        		else {
        			structure[y][x] = Layer.Nothing.ordinal();
        		}
        	}
        }

		// Make indestructible sides
		for (int x : new int[]{0, width - 1}){
			for (int y = 0; y < height; y++) {
				structure[y][x] = Layer.Indestructible.ordinal();
			}
		}

		Engine.visualiser.setBackgroundColor(new Color(7, 27, 110));
		assemble(structure, visuals);
    }

}
