import processing.core.PVector;

public class Bolt {
    PVector startPos;
    PVector direction;

    int frame = 0;
    boolean active = true;

    public Bolt(PVector startPos, PVector target) {
        this.startPos = startPos;
        this.direction = PVector.sub(target, startPos).normalize().mult(1000);
    }

    void incFrame() {
        frame++;
        if (frame >= 60) {
            active = false;
        }
    }
}
