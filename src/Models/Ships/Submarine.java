package Models.Ships;

public class Submarine extends Ship {
    private int width = 3;
    private int xPosition;
    private int yPosition;
    private String direction;
    private int life = 3;

    public Submarine() {
        super();
    }

    public Submarine(int width, int xPosition, int yPosition, String direction, int life) {
        super(width, xPosition, yPosition, direction, life);
    }

    public int GetWidth() {
        return width;
    }

    public void SetXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int GetXPosition() {
        return xPosition;
    }

    public void SetYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int GetYPosition() {
        return yPosition;
    }

    public void SetDirection(String direction) {
        this.direction = direction;
    }

    public String GetDirection() {
        return direction;
    }

    public void SetLife(int life) {
        this.life = life;
    }

    public int GetLife() {
        return this.life;
    }
}
