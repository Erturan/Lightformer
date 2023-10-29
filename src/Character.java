import processing.core.PApplet;
import processing.core.PVector;

public class Character {

    PApplet sketch;
    PVector pos;
    PVector vel;

    int maxXVel;

    int sizeX = 30;
    int sizeY = 30;

    public void adjustY() {
        int charY = (int)pos.y + sizeY / 3;
        int charRow = charY / Level.cell_height;
        pos.y = charRow * Level.cell_height - sizeY / 4f;
    }

    public void adjustXLeft() {
        int charX = (int)pos.x + sizeX / 2;
        int charCol = charX / Level.cell_width;
        pos.x = charCol * Level.cell_width;
    }

    public void adjustXRight() {
        int charX = (int)pos.x + sizeX / 2;
        int charCol = charX / Level.cell_width;
        pos.x = charCol * Level.cell_width - sizeX / 2f;
    }
}
