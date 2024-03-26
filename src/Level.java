import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PSketch;
import processing.core.PVector;

import java.util.ArrayList;

public class Level {
    static final int cell_height = 25;
    static final int cell_width = 50;

    static final int totalWidth = 100;
    static final int levelWidth = totalWidth * cell_width;

    PVector gravity = new PVector(0f, 0.2f);
    PVector endLevelPos;

    int[][] cells; //= new int[1080/cell_height + 1][totalWidth];
    int[] cellYPixel; //Maps cell number to starting X pixel coordinate
    int[] cellXPixel; //Maps cell number to starting Y pixel coordinate

    int maxRow;

    //From the start of the screen, the player is displayed at a specific pixel value
    static float playerScreenXPos;

    private PApplet sketch;

    public Level(PApplet sketch, int levelNo, ArrayList<Powerup> powerups, ArrayList<Enemy> enemies) {
        this.sketch = sketch;
        playerScreenXPos = sketch.displayWidth / 4f;
        maxRow = sketch.displayHeight / cell_height;
        int numRows = sketch.displayHeight / cell_height + 1;
        int numCols = totalWidth;
        cells = new int[numRows][numCols];

        //Create cellYPixel and cellXPixel, mapping cell numbers onto pixel positions
        cellYPixel = new int[numRows];
        cellXPixel = new int[numCols];
        for (int row = 1; row < cells.length; row++) {
            cellYPixel[row] = cellYPixel[row - 1] + cell_height;
        }
        for (int col = 1; col < cells[0].length; col++) {
            cellXPixel[col] = cellXPixel[col - 1] + cell_width;
        }

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
            setLevel1(powerups, enemies);
        } else if (levelNo == 2) {
            setLevel2(powerups, enemies);
        } else if (levelNo == 3) {
            setLevel3(powerups, enemies);
        } else if (levelNo == 4) {
            setLevel4(powerups, enemies);
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
        return charY > getYPixelFromRow(maxRow);
    }

    public boolean collidesYDown(Character character) {
        //Add sizeY / 2 to account for the size of the character
        int charY = (int)character.pos.y + character.halfSizeY;

        //Check 3 columns, 1 row below- to account for clipping the edge
        for (int colOffset = -1; colOffset <= 1; colOffset++) {
            int col = (int)character.gridPos.x + colOffset;
            int row = (int)character.gridPos.y + 1;
            //Checks if that cell is a platform
            if (cells[row][col] != 0) {
                //Gets coords of the start of that cell
                int blockX = getXPixelFromCol(col);
                int blockY = getYPixelFromRow(row);
                //Checks relative x position versus character size
                if (blockX - character.pos.x > character.halfSizeX)
                    continue;
                //Checks other side
                if (character.pos.x - (blockX+cell_width) > character.halfSizeX)
                    continue;
                //Checks relative y position versus character size
                if (blockY - charY > character.halfSizeY)
                    continue;
                //Checks other side
                if (charY - (blockY+cell_height) > character.halfSizeY)
                    continue;
                return true;
            }
        }
        return false;
    }

    public boolean collidesYUp(Character character) {
        int charY = (int)character.pos.y;

        if (character.gridPos.y == 1) {return true;}

        //Checks 3 columns, 1 row below- to account for clipping the edge
        for (int colOffset = -1; colOffset <= 1; colOffset++) {
            int col = (int)character.gridPos.x + colOffset;
            int row = (int)character.gridPos.y - 1;
            //Checks if that cell is a platform
            if (cells[row][col] != 0) {
                //Gets coords of the start of that cell
                int blockX = getXPixelFromCol(col);
                int blockY = getYPixelFromRow(row);
                //Checks relative x position versus character size
                if (blockX - character.pos.x > character.halfSizeX)
                    continue;
                //Checks other side
                if (character.pos.x - (blockX+cell_width) > character.halfSizeX)
                    continue;
                //Checks relative y position versus character size
                if (blockY - charY > character.halfSizeY)
                    continue;
                //Checks other side
                if (charY - (blockY+cell_height) > character.halfSizeY)
                    continue;
                return true;
            }
        }
        return false;
    }

    public boolean collidesXLeft(Character character) {
        //Checks 3 rows, 1 col below- to account for clipping the edge
        for (int rowOffset = -1; rowOffset <= 0; rowOffset++) {
            int row = (int)character.gridPos.y + rowOffset;
            int col = (int)character.gridPos.x - 1;
            //Checks if that cell is a platform
            if (cells[row][col] != 0) {
                //Gets coords of the start of that cell
                int blockX = getXPixelFromCol(col);
                int blockY = getYPixelFromRow(row);
                //Checks relative x position versus character size
                if (blockX - character.pos.x > character.halfSizeX)
                    continue;
                //Checks other side
                if (character.pos.x - (blockX+cell_width) > character.halfSizeX)
                    continue;
                //Checks relative y position versus character size
                if (blockY - character.pos.y > character.halfSizeY)
                    continue;
                //Checks other side
                if (character.pos.y - (blockY+cell_height) > character.halfSizeY)
                    continue;
                return true;
            }
        }
        return false;
    }

    public boolean collidesXRight(Character character) {
        //Checks 3 rows, 1 col below- to account for clipping the edge
        for (int rowOffset = -1; rowOffset <= 0; rowOffset++) {
            int row = (int)character.gridPos.y + rowOffset;
            int col = (int)character.gridPos.x;
            //Checks if that cell is a platform
            if (cells[row][col] != 0) {
                //Gets coords of the start of that cell
                int blockX = getXPixelFromCol(col);
                int blockY = getYPixelFromRow(row);
                //Checks relative x position versus character size
                if (blockX - character.pos.x > character.halfSizeX)
                    continue;
                //Checks other side
                if (character.pos.x - (blockX+cell_width) > character.halfSizeX)
                    continue;
                //Checks relative y position versus character size
                if (blockY - character.pos.y > character.halfSizeY)
                    continue;
                //Checks other side
                if (character.pos.y - (blockY+cell_height) > character.halfSizeY)
                    continue;
                return true;
            }
        }
        return false;
    }

    //Already know there's a left collision, checking if it's a wall to slide down
    public boolean collidesWallLeft(Character character) {
        int row = (int)character.gridPos.y;
        int col = (int)character.gridPos.x - 1;

        //Coordinates showing with two cells- collision with a wall
        if (cells[row][col] != 0 && cells[row + 1][col] != 0) {
            System.out.println("slide to the left");
            return cells[row + 2][col + 1] == 0;
        }
        return false;
    }

    public boolean collidesWallRight(Character character) {
        int row = (int)character.gridPos.y;
        int col = (int)character.gridPos.x - 1;

        //Coordinates showing with two cells- collision with a wall
        if (cells[row][col] != 0 && cells[row + 1][col] != 0) {
            System.out.println("slide to the right");
            return cells[row + 2][col - 1] == 0;
        }
        return false;
    }

    public void drawLevel(float offset) {
        sketch.pushStyle();
        sketch.imageMode(PConstants.CENTER);
        sketch.fill(25, 25, 112);

        for (int row = 0; row < cells.length; row++) {
            float yCoord = getYPixelFromRow(row);
            for (int col = 0; col < cells[row].length; col++) {
                float xCoord = getXPixelFromCol(col) - offset + playerScreenXPos + 10;
                if (cells[row][col] == 1) {
                    if (-cell_width < xCoord && xCoord < sketch.displayWidth + cell_width) {
                        sketch.image(Main.imgFloor, xCoord, yCoord);
                    }
                } else if (cells[row][col] == 2) {
                    if (-cell_width < xCoord && xCoord < sketch.displayWidth) {
                        sketch.image(Main.imgWall, xCoord, yCoord);
                    }
                }
            }
        }
        sketch.image(Main.imgLanternLevelEnd, endLevelPos.x - offset + playerScreenXPos, endLevelPos.y);
        sketch.popStyle();
    }

    public boolean checkEndLevelHit(PVector pos) {
        if (Math.abs(pos.x - endLevelPos.x) < 50) {
            PVector distance = PVector.sub(pos, endLevelPos);
            return distance.mag() < 50;
        }
        return false;
    }

    public void setLevel1(ArrayList<Powerup> powerups, ArrayList<Enemy> enemies) {
        setHole(35, 30, 7);
        setWall(30, 37, 5);

        setPlatform(30, 11, 4);

        setPlatform(25, 18, 3);

        setPlatform(23, 22, 2);

        setPlatform(30, 50, 10);
        addEnemy(Enemy.EnemyType.BASIC, enemies, 61, 33);
        addEnemy(Enemy.EnemyType.BASIC, enemies, 62, 33);
        powerups.add(new PowerupHealth(sketch, new PVector(58 * cell_width, 33 * cell_height)));

        setPlatform(25, 73, 1);

        addPowerup(Powerup.PowerupType.COIN, powerups, 18, 33);
        addPowerup(Powerup.PowerupType.COIN, powerups, 19, 33);
        addPowerup(Powerup.PowerupType.RECHARGE, powerups, 73, 33);
        addPowerup(Powerup.PowerupType.HEALTH, powerups, 20, 33);
        addPowerup(Powerup.PowerupType.RECHARGE, powerups, 25, 33);
        addPowerup(Powerup.PowerupType.SPEED, powerups, 27, 33);

        addEnemy(Enemy.EnemyType.BASIC, enemies, 30, 33);
        addEnemy(Enemy.EnemyType.BASIC, enemies, 29, 33);
        addEnemy(Enemy.EnemyType.BASIC, enemies, 28, 33);
        addEnemy(Enemy.EnemyType.BASIC, enemies, 23, 20);
        addEnemy(Enemy.EnemyType.MID, enemies, 85, 20);

        setLantern(85, 33);
    }

    public void setLevel2(ArrayList<Powerup> powerups, ArrayList<Enemy> enemies) {
        setPlatform(10, 20,5);
        addEnemy(Enemy.EnemyType.BASIC, enemies, 21, 8);
        addEnemy(Enemy.EnemyType.BASIC, enemies, 22, 8);
        addEnemy(Enemy.EnemyType.BASIC, enemies, 23, 8);
        addEnemy(Enemy.EnemyType.BASIC, enemies, 24, 8);
        addEnemy(Enemy.EnemyType.BASIC, enemies, 25, 8);
        addEnemy(Enemy.EnemyType.HARD, enemies, 22, 8);
        addEnemy(Enemy.EnemyType.HARD, enemies, 24, 8);
        addEnemy(Enemy.EnemyType.MID, enemies, 3, 29);
        addEnemy(Enemy.EnemyType.MID, enemies, 3.5f,  28.5f);
        addEnemy(Enemy.EnemyType.MID, enemies, 4, 30);
        addEnemy(Enemy.EnemyType.MID, enemies, 4.5f, 29.5f);
        addEnemy(Enemy.EnemyType.BASIC, enemies, 30, 33);

        setWall(30, 32, 5);
        setHole(35, 37, 3);
        setHole(35, 45, 5);
        setWall(32, 52, 3);

        addEnemy(Enemy.EnemyType.HARD, enemies, 68, 33);
        addEnemy(Enemy.EnemyType.HARD, enemies, 69, 33);

        setPlatform(30, 65, 5);
        setPlatform(25, 72, 4);

        addEnemy(Enemy.EnemyType.BASIC, enemies, 73, 23);
        addEnemy(Enemy.EnemyType.BASIC, enemies, 74, 23);

        setPlatform(20, 62, 6);
        setPlatform(15, 62, 6);

        addEnemy(Enemy.EnemyType.HARD, enemies, 63, 18);
        addEnemy(Enemy.EnemyType.HARD, enemies, 65, 18);

        setPlatform(23, 43, 6);
        setPlatform(18, 44, 3);
        setPlatform(13, 48, 2);
        setPlatform(11, 35, 5);

        addPowerup(Powerup.PowerupType.RECHARGE, powerups, 95, 33);
        addPowerup(Powerup.PowerupType.RECHARGE, powerups, 96, 33);
        addPowerup(Powerup.PowerupType.RECHARGE, powerups, 97, 33);
        addPowerup(Powerup.PowerupType.HEALTH, powerups, 20, 33);
        addPowerup(Powerup.PowerupType.RECHARGE, powerups, 25, 33);

        setLantern(22, 8);
    }

    public void setLevel3(ArrayList<Powerup> powerups, ArrayList<Enemy> enemies) {
        setWall(30, 15, 5);
        setWall(30, 16, 5);

        addEnemy(Enemy.EnemyType.HARD, enemies, 17, 33);
        addEnemy(Enemy.EnemyType.HARD, enemies, 19, 33);
        addEnemy(Enemy.EnemyType.HARD, enemies, 21, 33);
        addEnemy(Enemy.EnemyType.HARD, enemies, 23, 33);

        setWall(30, 25, 5);
        setHole(35, 26, 2);
        setHole(35, 33, 10);

        addPowerup(Powerup.PowerupType.RECHARGE, powerups, 43, 33);
        addPowerup(Powerup.PowerupType.RECHARGE, powerups, 46, 33);
        addPowerup(Powerup.PowerupType.HEALTH, powerups, 44, 33);
        addPowerup(Powerup.PowerupType.HEALTH, powerups, 45, 33);

        addEnemy(Enemy.EnemyType.HARD, enemies, 44, 33);
        addEnemy(Enemy.EnemyType.HARD, enemies, 46, 33);
        addEnemy(Enemy.EnemyType.MID, enemies, 50, 22);
        addEnemy(Enemy.EnemyType.MID, enemies, 52, 22);
        addEnemy(Enemy.EnemyType.MID, enemies, 54, 22);
        addEnemy(Enemy.EnemyType.HARD, enemies, 60, 26);

        setHole(35, 47, 10);
        setPlatform(24, 32, 20);
        setPlatform(28, 59, 2);
        setWall(15, 61, 20);
        setPlatform(19, 40, 4);
        setPlatform(15, 34, 4);

        addEnemy(Enemy.EnemyType.BASIC, enemies, 35, 13);
        addEnemy(Enemy.EnemyType.BASIC, enemies, 36, 13);
        addEnemy(Enemy.EnemyType.HARD, enemies, 35, 13);
        addEnemy(Enemy.EnemyType.HARD, enemies, 36, 13);

        setPlatform(11, 43, 10);
        setPlatform(26, 63, 3);
        setWall(29, 75, 6);

        addEnemy(Enemy.EnemyType.MID, enemies, 71, 30);
        addEnemy(Enemy.EnemyType.MID, enemies, 73, 30);
        addEnemy(Enemy.EnemyType.MID, enemies, 75, 30);
        addEnemy(Enemy.EnemyType.MID, enemies, 77, 30);
        addEnemy(Enemy.EnemyType.MID, enemies, 79, 30);
        addEnemy(Enemy.EnemyType.MID, enemies, 81, 30);
        addEnemy(Enemy.EnemyType.MID, enemies, 83, 30);
        addEnemy(Enemy.EnemyType.HARD, enemies, 63, 22);

        setPlatform(25, 85, 3);
        setPlatform(24, 96, 2);

        setLantern(97, 22);
    }

    public void setLevel4(ArrayList<Powerup> powerups, ArrayList<Enemy> enemies) {
        addEnemy(Enemy.EnemyType.BASIC, enemies, 2, 33);
        addEnemy(Enemy.EnemyType.BASIC, enemies, 3, 33);
        addEnemy(Enemy.EnemyType.HARD, enemies, 17, 27);
        addEnemy(Enemy.EnemyType.HARD, enemies, 19, 27);
        addEnemy(Enemy.EnemyType.MID, enemies, 3, 21);
        addEnemy(Enemy.EnemyType.MID, enemies, 4, 21);
        addEnemy(Enemy.EnemyType.HARD, enemies, 17, 17);
        addEnemy(Enemy.EnemyType.HARD, enemies, 19, 17);
        addEnemy(Enemy.EnemyType.HARD, enemies, 21, 17);

        setHole(35, 7, 85);
        setPlatform(29, 15, 6);
        setPlatform(23, 3, 4);
        setPlatform(19, 15, 8);
        setPlatform(30, 35, 2);
        setPlatform(26, 40, 6);
        setPlatform(25, 48, 6);
        setPlatform(25, 57, 6);

        addEnemy(Enemy.EnemyType.BASIC, enemies, 52, 23);
        addEnemy(Enemy.EnemyType.BASIC, enemies, 54, 23);
        addEnemy(Enemy.EnemyType.BASIC, enemies, 59, 23);
        addEnemy(Enemy.EnemyType.BASIC, enemies, 61, 23);
        addEnemy(Enemy.EnemyType.BASIC, enemies, 63, 23);
        addEnemy(Enemy.EnemyType.HARD, enemies, 58, 23);
        addEnemy(Enemy.EnemyType.HARD, enemies, 59, 23);
        addEnemy(Enemy.EnemyType.HARD, enemies, 60, 23);
        addEnemy(Enemy.EnemyType.HARD, enemies, 61, 23);
        addEnemy(Enemy.EnemyType.HARD, enemies, 62, 23);
        addEnemy(Enemy.EnemyType.HARD, enemies, 62.5f, 23);

        addPowerup(Powerup.PowerupType.RECHARGE, powerups, 97, 33);

        setPlatform(20, 70, 4);
        setPlatform(30, 80, 10);
        setPlatform(30, 96, 1);
        setPlatform(25, 96, 1);
        setPlatform(20, 96, 1);
        setPlatform(15, 96, 1);

        setLantern(97,  10);
    }

    //Returns the cell coordinates in a PVector, from a pixel PVector
    public PVector getCellCoordsFromPos(PVector pos) {
        PVector cellCoords = new PVector();
        boolean xhit = false;
        boolean yhit = true;
        for (int i = 0; i < cellXPixel.length; i++) {
            if (pos.x < cellXPixel[i]) {
                xhit = true;
                cellCoords.x = i - 1;
                break;
            }
        }
        for (int i = 0; i < cellYPixel.length; i++) {
            if (pos.y < cellYPixel[i]) {
                yhit = true;
                cellCoords.y = i - 1;
                break;
            }
        }
        if (!xhit || !yhit) {
            System.exit(1);
        }
        return cellCoords;
    }

    //Checks if a given cell coordinate is a floor or a wall
    public boolean checkCellBlocked(PVector coord) {
        return cells[Math.round(coord.y)][Math.round(coord.x)] != 0;
    }

    public int getYPixelFromRow(int row) {
        return cellYPixel[row];
    }

    public int getXPixelFromCol(int col) {
        return cellXPixel[col];
    }

    public void addEnemy(Enemy.EnemyType enemyType, ArrayList<Enemy> enemies, float col, float row) {
        PVector startPos = new PVector(col * cell_width, row * cell_height);
        switch (enemyType) {
            case BASIC:
                enemies.add(new EnemyBasic(sketch, startPos));
                break;
            case MID:
                enemies.add(new EnemyMid(sketch, startPos));
                break;
            case HARD:
                enemies.add(new EnemyHard(sketch, startPos));
        }
    }

    public void addPowerup(Powerup.PowerupType powerupType, ArrayList<Powerup> powerups, float col, float row) {
        PVector startPos = new PVector(col *  cell_width, row * cell_height);
        switch(powerupType) {
            case COIN:
                powerups.add(new PowerupCoin(sketch, startPos));
                break;
            case HEALTH:
                powerups.add(new PowerupHealth(sketch, startPos));
                break;
            case RECHARGE:
                powerups.add(new PowerupRecharge(sketch, startPos));
                break;
            case SPEED:
                powerups.add(new PowerupSpeed(sketch, startPos));
        }
    }

    public void setLantern(int endCol, int endRow) {
        endLevelPos = new PVector(endCol * cell_width, endRow * cell_height);
    }
}
