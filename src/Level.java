import processing.core.PApplet;

public class Level {
    static final int cell_height = 10;
    static final int cell_width = 20;

    static final int totalWidth = 100;

    int[][] cells = new int[totalWidth][1080 / cell_height];

    private PApplet sketch;


    public Level(PApplet sketch, int levelNo) {
        this.sketch = sketch;
        //First draw floor
        for (int col = 0; col < cells.length; col++) {
            cells[80][col] = 1;
        }
        if (levelNo == 1) {

        }

    }


    public void drawLevel() {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                if (cells[row][col] == 1) {
                    sketch.pushStyle();
                    sketch.fill(25, 25, 112);
                    sketch.rect(col * cell_width, row * cell_height, cell_width, cell_height);
                    sketch.popStyle();
                }
            }
        }
    }
}
