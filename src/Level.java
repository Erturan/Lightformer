import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.ArrayList;

public class Level {
    static final int cell_height = 25;
    static final int cell_width = 50;

    static final int totalWidth = 100;
    static final int levelWidth = totalWidth * cell_width;

    int[][] cells; //= new int[1080/cell_height + 1][totalWidth];

    int endRow;
    int endCol;

    private PApplet sketch;

    public Level(PApplet sketch, int levelNo, ArrayList<Powerup> powerups, ArrayList<Enemy> enemies) {
        this.sketch = sketch;
        cells = new int[sketch.displayHeight / cell_height + 1][totalWidth];
        //First create floor and ceiling
        for (int col = 0; col < cells[0].length; col++) {
            cells[35][col] = 1;
            cells[0][col] = 1;
        }

        //Create the walls bounding the level
        setWall(0, 0, cells.length);
        setWall(0, 1, cells.length);

        setWall(0, cells[0].length - 1, cells.length);
        setWall(0, cells[0].length - 2, cells.length);

        //Clear enemies and powerups from previous level
        enemies.clear();
        powerups.clear();

        if (levelNo == 1) {
            setHole(35, 30, 7);
            setWall(30, 37, 5);

            setPlatform(30, 11, 4);

            setPlatform(25, 18, 3);

            setPlatform(23, 22, 2);

            setPlatform(30, 50, 10);
            enemies.add(new EnemyBasic(sketch, new PVector(61 * cell_width, 33 * cell_height)));
            enemies.add(new EnemyBasic(sketch, new PVector(62 * cell_width, 33 * cell_height)));
            powerups.add(new PowerupHealth(sketch, new PVector(58 * cell_width, 33 * cell_height)));

            setPlatform(25, 73, 1);

            powerups.add(new PowerupRecharge(sketch, new PVector(73 * cell_width, 33 * cell_height)));
            powerups.add(new PowerupHealth(sketch, new PVector(20 * cell_width, 33 * cell_height)));
            powerups.add(new PowerupRecharge(sketch, new PVector(25 * cell_width, 33 * cell_height)));

            enemies.add(new EnemyBasic(sketch, new PVector(30 * cell_width, 33 * cell_height)));
            enemies.add(new EnemyBasic(sketch, new PVector(29 * cell_width, 33 * cell_height)));
            enemies.add(new EnemyBasic(sketch, new PVector(28 * cell_width, 33 * cell_height)));
            enemies.add(new EnemyBasic(sketch, new PVector(23 * cell_width, 20 * cell_height)));
            enemies.add(new EnemyMid(sketch, new PVector(85 * cell_width, 20 * cell_height)));

            endRow = 33;
            endCol = 85;
        } else if (levelNo == 2) {
            setPlatform(10, 20,5);
            enemies.add(new EnemyBasic(sketch, new PVector(21 * cell_width, 8 * cell_height)));
            enemies.add(new EnemyBasic(sketch, new PVector(22 * cell_width, 8 * cell_height)));
            enemies.add(new EnemyBasic(sketch, new PVector(23 * cell_width, 8 * cell_height)));
            enemies.add(new EnemyBasic(sketch, new PVector(24 * cell_width, 8 * cell_height)));
            enemies.add(new EnemyBasic(sketch, new PVector(25 * cell_width, 8 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(22 * cell_width, 8 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(24 * cell_width, 8 * cell_height)));
            enemies.add(new EnemyMid(sketch, new PVector(3 * cell_width, 29 * cell_height)));
            enemies.add(new EnemyMid(sketch, new PVector(3.5f * cell_width, 28.5f * cell_height)));
            enemies.add(new EnemyMid(sketch, new PVector(4 * cell_width, 30 * cell_height)));
            enemies.add(new EnemyMid(sketch, new PVector(4.5f * cell_width, 29.5f * cell_height)));
            enemies.add(new EnemyBasic(sketch, new PVector(30 * cell_width, 33 * cell_height)));

            setWall(30, 32, 5);
            setHole(35, 37, 3);
            setHole(35, 45, 5);
            setWall(32, 52, 3);

            enemies.add(new EnemyHard(sketch, new PVector(68 * cell_width, 33 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(69 * cell_width, 33 * cell_height)));

            setPlatform(30, 65, 5);
            setPlatform(25, 72, 4);

            enemies.add(new EnemyBasic(sketch, new PVector(73 * cell_width, 23 * cell_height)));
            enemies.add(new EnemyBasic(sketch, new PVector(74 * cell_width, 23 * cell_height)));

            setPlatform(20, 62, 6);
            setPlatform(15, 62, 6);

            enemies.add(new EnemyHard(sketch, new PVector(63 * cell_width, 18 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(65 * cell_width, 18 * cell_height)));

            setPlatform(23, 43, 6);
            setPlatform(18, 44, 3);
            setPlatform(13, 48, 2);
            setPlatform(11, 35, 5);

            powerups.add(new PowerupRecharge(sketch, new PVector(95 * cell_width, 33 * cell_height)));
            powerups.add(new PowerupRecharge(sketch, new PVector(96 * cell_width, 33 * cell_height)));
            powerups.add(new PowerupRecharge(sketch, new PVector(97 * cell_width, 33 * cell_height)));
            powerups.add(new PowerupHealth(sketch, new PVector(20 * cell_width, 33 * cell_height)));
            powerups.add(new PowerupRecharge(sketch, new PVector(25 * cell_width, 33 * cell_height)));

            endRow = 8;
            endCol = 22;
        } else if (levelNo == 3) {
            setWall(30, 15, 5);
            setWall(30, 16, 5);

            enemies.add(new EnemyHard(sketch, new PVector(17 * cell_width, 33 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(19 * cell_width, 33 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(21 * cell_width, 33 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(23 * cell_width, 33 * cell_height)));

            setWall(30, 25, 5);
            setHole(35, 26, 2);
            setHole(35, 33, 10);

            powerups.add(new PowerupRecharge(sketch, new PVector(43 * cell_width, 33 * cell_height)));
            powerups.add(new PowerupRecharge(sketch, new PVector(46 * cell_width, 33 * cell_height)));
            powerups.add(new PowerupHealth(sketch, new PVector(44 * cell_width, 33 * cell_height)));
            powerups.add(new PowerupHealth(sketch, new PVector(45 * cell_width, 33 * cell_height)));

            enemies.add(new EnemyHard(sketch, new PVector(44 * cell_width, 33 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(46 * cell_width, 33 * cell_height)));
            enemies.add(new EnemyMid(sketch, new PVector(50 * cell_width, 22 * cell_height)));
            enemies.add(new EnemyMid(sketch, new PVector(52 * cell_width, 22 * cell_height)));
            enemies.add(new EnemyMid(sketch, new PVector(54 * cell_width, 22 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(60 * cell_width, 26 * cell_height)));

            setHole(35, 47, 10);
            setPlatform(24, 32, 20);
            setPlatform(28, 59, 2);
            setWall(15, 61, 20);
            setPlatform(19, 40, 4);
            setPlatform(15, 34, 4);

            enemies.add(new EnemyBasic(sketch, new PVector(35 * cell_width, 13 * cell_height)));
            enemies.add(new EnemyBasic(sketch, new PVector(36 * cell_width, 13 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(35 * cell_width, 13 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(36 * cell_width, 13 * cell_height)));

            setPlatform(11, 43, 10);
            setPlatform(26, 63, 3);
            setWall(29, 75, 6);

            enemies.add(new EnemyMid(sketch, new PVector(71 * cell_width, 30 * cell_height)));
            enemies.add(new EnemyMid(sketch, new PVector(73 * cell_width, 30 * cell_height)));
            enemies.add(new EnemyMid(sketch, new PVector(75 * cell_width, 30 * cell_height)));
            enemies.add(new EnemyMid(sketch, new PVector(77 * cell_width, 30 * cell_height)));
            enemies.add(new EnemyMid(sketch, new PVector(79 * cell_width, 30 * cell_height)));
            enemies.add(new EnemyMid(sketch, new PVector(81 * cell_width, 30 * cell_height)));
            enemies.add(new EnemyMid(sketch, new PVector(83 * cell_width, 30 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(97 * cell_width, 22 * cell_height)));

            setPlatform(25, 85, 3);
            setPlatform(24, 96, 2);

            endRow = 22;
            endCol = 97;

        } else if (levelNo == 4) {
            enemies.add(new EnemyBasic(sketch, new PVector(2 * cell_width, 33 * cell_height)));
            enemies.add(new EnemyBasic(sketch, new PVector(3 * cell_width, 33 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(17 * cell_width, 27 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(19 * cell_width, 27 * cell_height)));
            enemies.add(new EnemyMid(sketch, new PVector(3 * cell_width, 21 * cell_height)));
            enemies.add(new EnemyMid(sketch, new PVector(4 * cell_width, 21 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(17 * cell_width, 17 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(19 * cell_width, 17 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(21 * cell_width, 17 * cell_height)));

            setHole(35, 7, 85);
            setPlatform(29, 15, 6);
            setPlatform(23, 3, 4);
            setPlatform(19, 15, 8);
            setPlatform(30, 35, 2);
            setPlatform(26, 40, 6);
            setPlatform(25, 48, 6);
            setPlatform(25, 57, 6);

            enemies.add(new EnemyBasic(sketch, new PVector(52 * cell_width, 23 * cell_height)));
            enemies.add(new EnemyBasic(sketch, new PVector(54 * cell_width, 23 * cell_height)));
            enemies.add(new EnemyBasic(sketch, new PVector(59 * cell_width, 23 * cell_height)));
            enemies.add(new EnemyBasic(sketch, new PVector(61 * cell_width, 23 * cell_height)));
            enemies.add(new EnemyBasic(sketch, new PVector(63 * cell_width, 23 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(58 * cell_width, 23 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(59 * cell_width, 23 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(60 * cell_width, 23 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(61 * cell_width, 23 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(62 * cell_width, 23 * cell_height)));
            enemies.add(new EnemyHard(sketch, new PVector(62.5f * cell_width, 23 * cell_height)));
            powerups.add(new PowerupRecharge(sketch, new PVector(97 * cell_width, 33 * cell_height)));

            setPlatform(20, 70, 4);
            setPlatform(30, 80, 10);
            setPlatform(30, 96, 1);
            setPlatform(25, 96, 1);
            setPlatform(20, 96, 1);
            setPlatform(15, 96, 1);

            endCol = 97;
            endRow = 10;

        }
    }

    public void setPlatform(int startRow, int startCol, int length) {
        for (int i = 0; i < length; i++) {
            cells[startRow][startCol + i] = 1;
        }
    }

    public void setWall(int startRow, int startCol, int length) {
        for (int i = 0; i < length; i++) {
            cells[startRow + i][startCol] = 2;
        }
    }

    public void setHole(int startRow, int startCol, int length) {
        for (int i = 0; i < length; i++) {
            cells[startRow][startCol + i] = 0;
        }
    }

    public boolean checkFallenOffLevel(Character character) {
        int charY = (int) character.pos.y + character.sizeY;
        int charRow = charY / cell_height;
        int maxRow = sketch.displayHeight / cell_height;

        return charRow >= maxRow - 2;
    }

    public boolean collidesYDown(Character character) {
        //TODO: If colliding down, move character above instead of letting it become wedged

        //Gets x and y cell coords of character, using position
        int charX = (int)character.pos.x;
        int charCol = charX/cell_width + 1;
        //Add sizeY / 3 to account for the size of the character
        int charY = (int)character.pos.y + character.sizeY / 3;
        int charRow = charY/cell_height ;
        float sizeX = character.sizeX / 2f;
        float sizeY = character.sizeY / 2f;

        //Checks 3 columns, 1 row below- to account for clipping the edge
        for (int colOffset = -1; colOffset <= 1; colOffset++) {
            int col = charCol + colOffset;
            int row = charRow + 1;
            //Checks if that cell is a platform
            if (cells[row][col] != 0) {
                //Gets coords of that cell
                int blockX = col*cell_width;
                int blockY = row*cell_height;
                //Checks relative x position versus character size
                if (blockX - charX > sizeX)
                    continue;
                //Checks other side
                if (charX - (blockX+cell_width) > sizeX)
                    continue;
                //Checks relative y position versus character size
                if (blockY - charY > sizeY)
                    continue;
                //Checks other side
                if (charY - (blockY+cell_height) > sizeY)
                    continue;
                return true;
            }
        }
        return false;
    }

    public boolean collidesYUp(Character character) {

        //Gets x and y cell coords of character, using position
        int charX = (int)character.pos.x;
        int charCol = charX/cell_width + 1;
        int charY = (int)character.pos.y;
        int charRow = charY/cell_height;

        float sizeX = character.sizeX / 2f;
        float sizeY = character.sizeY / 2f;

        if (charRow == 1) {return true;}

        //Checks 3 columns, 1 row below- to account for clipping the edge
        for (int colOffset = -1; colOffset <= 1; colOffset++) {
            int col = charCol + colOffset;
            int row = charRow - 1;
            //Checks if that cell is a platform
            if (cells[row][col] != 0) {
                //Gets coords of that cell
                int blockX = col*cell_width;
                int blockY = row*cell_height;
                //Checks relative x position versus character size
                if (blockX - charX > sizeX)
                    continue;
                //Checks other side
                if (charX - (blockX+cell_width) > sizeX)
                    continue;
                //Checks relative y position versus character size
                if (blockY - charY > sizeY)
                    continue;
                //Checks other side
                if (charY - (blockY+cell_height) > sizeY)
                    continue;
                return true;
            }
        }
        return false;
    }

    public boolean collidesXLeft(Character character) {
        //Gets x and y cell coords of character, using position
        int charX = (int)character.pos.x;
        int charCol = charX/cell_width + 1;
        int charY = (int)character.pos.y;
        int charRow = charY/cell_height;

        float sizeX = character.sizeX / 2f;
        float sizeY = character.sizeY / 2f;

        //Checks 3 rows, 1 col below- to account for clipping the edge
        for (int rowOffset = -1; rowOffset <= 0; rowOffset++) {
            int row = charRow + rowOffset;
            int col = charCol - 1;
            //Checks if that cell is a platform
            if (cells[row][col] != 0) {
                //Gets coords of that cell
                int blockX = col*cell_width;
                int blockY = row*cell_height;
                //Checks relative x position versus character size
                if (blockX - charX > sizeX)
                    continue;
                //Checks other side
                if (charX - (blockX+cell_width) > sizeX)
                    continue;
                //Checks relative y position versus character size
                if (blockY - charY > sizeY)
                    continue;
                //Checks other side
                if (charY - (blockY+cell_height) > sizeY)
                    continue;
                return true;
            }
        }
        return false;
    }

    public boolean collidesXRight(Character character) {
        //Gets x and y cell coords of character, using position
        int charX = (int)character.pos.x;
        int charCol = charX/cell_width;
        int charY = (int)character.pos.y;
        int charRow = charY/cell_height;

        float sizeX = character.sizeX / 2f;
        float sizeY = character.sizeY / 2f;

        //Checks 3 rows, 1 col below- to account for clipping the edge
        for (int rowOffset = -1; rowOffset <= 0; rowOffset++) {
            int row = charRow + rowOffset;
            int col = charCol + 1;
            //Checks if that cell is a platform
            if (cells[row][col] != 0) {
                //Gets coords of that cell
                int blockX = col*cell_width;
                int blockY = row*cell_height;
                //Checks relative x position versus character size
                if (blockX - charX > sizeX)
                    continue;
                //Checks other side
                if (charX - (blockX+cell_width) > sizeX)
                    continue;
                //Checks relative y position versus character size
                if (blockY - charY > sizeY)
                    continue;
                //Checks other side
                if (charY - (blockY+cell_height) > sizeY)
                    continue;
                return true;
            }
        }
        return false;
    }

    //Already know there's a left collision, checking if it's a wall to slide down
    public boolean collidesWallLeft(Character character) {
        int charX = (int)character.pos.x;
        int charCol = charX/cell_width;
        int charY = (int)character.pos.y;
        int charRow = charY/cell_height;

        //Coordinates showing with two cells- collision with a wall
        if (cells[charRow][charCol] != 0 && cells[charRow + 1][charCol] != 0) {
            System.out.println("slide to the left");
            return cells[charRow + 2][charCol + 1] == 0;
        }
        return false;
    }

    public boolean collidesWallRight(Character character) {
        int charX = (int)character.pos.x;
        int charCol = charX/cell_width;
        int charY = (int)character.pos.y;
        int charRow = charY/cell_height;

        //Coordinates showing with two cells- collision with a wall
        if (cells[charRow][charCol] != 0 && cells[charRow + 1][charCol] != 0) {
            System.out.println("slide to the right");
            return cells[charRow + 2][charCol - 1] == 0;
        }
        return false;
    }

    /*public void adjustY(Character character) {
        //Prevents character falling part-way through a wall during the frame where the collision happens
        int charY = (int)character.pos.y + character.sizeY / 3;
        int charRow = charY / cell_height ;
        float sizeY = character.sizeY / 2f;

        character.pos.y = charRow * cell_height - sizeY / 2;
    }*/

    public void drawLevel(float offset) {
        sketch.pushStyle();
        sketch.imageMode(PConstants.CENTER);
        sketch.fill(25, 25, 112);

        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                if (cells[row][col] == 1) {
                    float xCoord = col * cell_width - offset + sketch.displayWidth / 4 + 10;
                    if (-cell_width < xCoord && xCoord < sketch.displayWidth + cell_width) {
                        sketch.image(Main.imgFloor, xCoord, row * cell_height);
                        //sketch.rect(xCoord, row * cell_height, cell_width, cell_height);
                    }
                } else if (cells[row][col] == 2) {
                    float xCoord = col * cell_width - offset + sketch.displayWidth / 4 + 10;
                    if (-cell_width < xCoord && xCoord < sketch.displayWidth) {
                        //sketch.rect(xCoord, row * cell_height, cell_width, cell_height);
                        sketch.image(Main.imgWall, xCoord, row * cell_height);
                    }
                }
            }
        }

        //sketch.rectMode(PConstants.CENTER);
        //sketch.fill(0, 255, 255);
        sketch.image(Main.imgLanterLevelEnd, endCol * cell_width - offset + sketch.displayWidth / 4, endRow * cell_height);
        //sketch.rect(endCol * cell_width - offset + sketch.displayWidth / 4, endRow * cell_height, cell_width, cell_height);
        //sketch.image(Main.imgFloor, endCol * cell_width - offset + sketch.displayWidth / 4, endRow * cell_height)
        sketch.popStyle();

    }

    public boolean checkEndLevelHit(PVector pos) {
        //int charX = (int)pos.x ;
        //int charCol = charX/cell_width + 1 ;
        //int charY = (int)pos.y ;
        //int charRow = charY/cell_height ;

        //System.out.println("Target: " + endCol + "," + endRow);
        //System.out.println("Actual: " + charCol + ", " + charRow);
        //return charCol == endCol && charRow == endRow;

        PVector lanternPos = new PVector(endCol * cell_width, endRow * cell_height);
        PVector distance = PVector.sub(pos, lanternPos);
        return distance.mag() < 50;
    }

}
