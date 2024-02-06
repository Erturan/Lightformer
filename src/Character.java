import processing.core.PApplet;
import processing.core.PVector;

public class Character {

    PApplet sketch;
    PVector pos;
    PVector gridPos;
    PVector vel;

    int maxXVel;
    int stepCount = 0;

    int sizeX = 30;
    int sizeY = 30;

    int halfSizeX = sizeX / 2;
    int halfSizeY = sizeY / 2;

    int thirdSizeY = sizeY / 3;
    int quarterSizeY = sizeY / 4;

    final int stepSwitch = 8;
    final int stepReset = 15;

    public void adjustY(Level level) {
        pos.y += thirdSizeY;
        int charRow = (int)level.getCellCoordsFromPos(pos).y;
        pos.y = level.getYPixelFromRow(charRow) - quarterSizeY;
    }

    public void adjustXLeft(Level level) {
        pos.x += halfSizeX;
        int charCol = (int)level.getCellCoordsFromPos(pos).x;
        pos.x  = level.getXPixelFromCol(charCol);
    }

    public void adjustXRight(Level level) {
        pos.x += halfSizeX;
        int charCol = (int)level.getCellCoordsFromPos(pos).x;
        pos.x = level.getXPixelFromCol(charCol) - halfSizeX;
    }

    public void updateGridPos() {
        int charCol = (int)pos.x / Level.cell_width + 1;
        int charRow = (int)pos.y / Level.cell_height + 1;
        gridPos = new PVector(charCol, charRow);
    }

    public void updateFractionalSizes() {
        halfSizeX = sizeX / 2;
        halfSizeY = sizeY / 2;
        thirdSizeY = sizeY / 3;
        quarterSizeY = sizeY / 4;
    }
}
