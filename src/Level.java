import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Level {
    static final int cell_height = 25;
    static final int cell_width = 50;

    static final int totalWidth = 400;
    static final int levelWidth = totalWidth * cell_width;

    int[][] cells = new int[1080/cell_height][totalWidth];

    int endRow;
    int endCol;

    private PApplet sketch;

    public Level(PApplet sketch, int levelNo, ArrayList<Powerup> powerups, ArrayList<Enemy> enemies) {
        this.sketch = sketch;
        //First create floor and ceiling
        for (int col = 0; col < cells[0].length; col++) {
            cells[35][col] = 1;
            cells[0][col] = 1;
        }

        cells[35][30] = 0;
        cells[35][31] = 0;

        //powerups = new ArrayList<>();

        powerups.clear();

        setWall(0, 0, cells.length);
        setWall(0, 1, cells.length);

        setWall(0, cells[0].length - 1, cells.length);
        setWall(0, cells[0].length - 2, cells.length);

        enemies.clear();

        if (levelNo == 1) {
            setPlatform(30, 10, 10);

            setPlatform(32, 50, 100);

            System.out.println("Adding to powerups");
            powerups.add(new PowerupHealth(sketch, new PVector(20 * cell_width, 34 * cell_height)));
            powerups.add(new PowerupRecharge(sketch, new PVector(25 * cell_width, 34 * cell_height)));

            enemies.add(new EnemyBasic(sketch, new PVector(30 * cell_width, 33 * cell_height)));
            enemies.add(new EnemyBasic(sketch, new PVector(29 * cell_width, 33 * cell_height)));
            enemies.add(new EnemyBasic(sketch, new PVector(28 * cell_width, 33 * cell_height)));

            enemies.add(new EnemyMid(sketch, new PVector(25 * cell_width, 33 * cell_height)));

            enemies.add(new EnemyHard(sketch, new PVector(26 * cell_width, 33 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(24 * cell_width, 33 * cell_height)));


            endRow = 33;
            endCol = 45;
        } else if (levelNo == 2) {
            setPlatform(10, 20,5);

            powerups.add(new PowerupHealth(sketch, new PVector(20 * cell_width, 34 * cell_height)));
            powerups.add(new PowerupRecharge(sketch, new PVector(25 * cell_width, 34 * cell_height)));

            endRow = 20;
            endCol = 40;
        }
    }

    public void setPlatform(int startRow, int startCol, int length) {
        for (int i = 0; i < length; i++) {
            System.out.println(startRow + ", " + i);
            cells[startRow][startCol + i] = 1;
        }
    }

    public void setWall(int startRow, int startCol, int length) {
        for (int i = 0; i < length; i++) {
            cells[startRow + i][startCol] = 1;
        }
    }

    public boolean checkFallenOffLevel(Character character) {
        int charY = (int) character.pos.y + character.sizeY / 7;
        int charRow = charY / cell_height;
        int maxRow = sketch.displayHeight / cell_height;

        return charRow >= maxRow - 2;
    }

    public boolean collidesYDown(Character character) {

        //Gets x and y cell coords of character, using position
        int charX = (int)character.pos.x ;
        int charCol = charX/cell_width ;
        int charY = (int)character.pos.y + character.sizeY / 7 ;
        int charRow = charY/cell_height ;

        float sizeX = character.sizeX / 2f;
        float sizeY = character.sizeY / 2f;

        //System.out.println(charX);
        //System.out.println(charCol);

        //Checks 3 columns, 1 row below- to account for clipping the edge
        for (int colOffset = -1; colOffset <= 1; colOffset++) {
            int col = charCol + colOffset ;
            int row = charRow + 1 ;
            //Checks if that cell is a platform
            if (cells[row][col] == 1) {
                //Gets coords of that cell
                int blockX = col*cell_width ;
                int blockY = row*cell_height ;
                //Checks relative x position versus character size
                if (blockX - charX > sizeX)
                    continue ;
                //Checks other side
                if (charX - (blockX+cell_width) > sizeX)
                    continue ;
                //Checks relative y position versus character size
                if (blockY - charY > sizeY)
                    continue ;
                //Checks other side
                if (charY - (blockY+cell_height) > sizeY)
                    continue ;
                return true ;
            }
        }
        return false ;
    }

    public boolean collidesYUp(Character character) {

        //Gets x and y cell coords of character, using position
        int charX = (int)character.pos.x ;
        int charCol = charX/cell_width ;
        int charY = (int)character.pos.y ;
        int charRow = charY/cell_height ;

        float sizeX = character.sizeX / 2f;
        float sizeY = character.sizeY / 2f;

        //System.out.println(charX);
        //System.out.println(charCol);

        //Checks 3 columns, 1 row below- to account for clipping the edge
        for (int colOffset = -1; colOffset <= 1; colOffset++) {
            int col = charCol + colOffset ;
            int row = charRow - 1 ;
            //Checks if that cell is a platform
            if (cells[row][col] == 1) {
                //Gets coords of that cell
                int blockX = col*cell_width ;
                int blockY = row*cell_height ;
                //Checks relative x position versus character size
                if (blockX - charX > sizeX)
                    continue ;
                //Checks other side
                if (charX - (blockX+cell_width) > sizeX)
                    continue ;
                //Checks relative y position versus character size
                if (blockY - charY > sizeY)
                    continue ;
                //Checks other side
                if (charY - (blockY+cell_height) > sizeY)
                    continue ;
                return true ;
            }
        }
        return false ;
    }

    public boolean collidesXLeft(Character character) {
        //Gets x and y cell coords of character, using position
        int charX = (int)character.pos.x ;
        int charCol = charX/cell_width ;
        int charY = (int)character.pos.y ;
        int charRow = charY/cell_height ;

        float sizeX = character.sizeX / 2f;
        float sizeY = character.sizeY / 2f;

        //System.out.println(charX);
        //System.out.println(charCol);
        //System.out.println(charRow);

        //Checks 3 rows, 1 col below- to account for clipping the edge
        for (int rowOffset = -1; rowOffset <= 0; rowOffset++) {
            int row = charRow + rowOffset  ;
            int col = charCol - 1 ;
            //Checks if that cell is a platform
            if (cells[row][col] == 1) {
                //Gets coords of that cell
                int blockX = col*cell_width ;
                int blockY = row*cell_height ;
                //Checks relative x position versus character size
                if (blockX - charX > sizeX)
                    continue ;
                //Checks other side
                if (charX - (blockX+cell_width) > sizeX)
                    continue ;
                //Checks relative y position versus character size
                if (blockY - charY > sizeY)
                    continue ;
                //Checks other side
                if (charY - (blockY+cell_height) > sizeY)
                    continue ;
                return true ;
            }
        }
        return false ;
    }

    public boolean collidesXRight(Character character) {
        //Gets x and y cell coords of character, using position
        int charX = (int)character.pos.x ;
        int charCol = charX/cell_width ;
        int charY = (int)character.pos.y ;
        int charRow = charY/cell_height ;

        float sizeX = character.sizeX / 2f;
        float sizeY = character.sizeY / 2f;

        //System.out.println(charX);
        //System.out.println(charCol);
        //System.out.println(charRow);

        //Checks 3 rows, 1 col below- to account for clipping the edge
        for (int rowOffset = -1; rowOffset <= 0; rowOffset++) {
            int row = charRow + rowOffset  ;
            int col = charCol + 1 ;
            //Checks if that cell is a platform
            if (cells[row][col] == 1) {
                //Gets coords of that cell
                int blockX = col*cell_width ;
                int blockY = row*cell_height ;
                //Checks relative x position versus character size
                if (blockX - charX > sizeX)
                    continue ;
                //Checks other side
                if (charX - (blockX+cell_width) > sizeX)
                    continue ;
                //Checks relative y position versus character size
                if (blockY - charY > sizeY)
                    continue ;
                //Checks other side
                if (charY - (blockY+cell_height) > sizeY)
                    continue ;
                return true ;
            }
        }
        return false ;
    }




    public void drawLevel(float offset) {
        sketch.pushStyle();
        sketch.fill(25, 25, 112);

        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                if (cells[row][col] == 1) {
                    float xCoord = col * cell_width - offset + sketch.displayWidth / 4;
                    if (-cell_width < xCoord && xCoord < sketch.displayWidth) {
                        sketch.rect(xCoord, row * cell_height, cell_width, cell_height);
                    }
                }
            }
        }

        sketch.fill(0, 255, 255);
        sketch.rect(endCol * cell_width - offset + sketch.displayWidth / 4, endRow * cell_height, cell_width, cell_height);
        sketch.popStyle();

    }

    public boolean checkEndLevelHit(PVector pos) {
        int charX = (int)pos.x ;
        int charCol = charX/cell_width ;
        int charY = (int)pos.y ;
        int charRow = charY/cell_height ;

        return charCol == endCol && charRow == endRow;
    }


}
