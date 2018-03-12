package Models.Ships;

public class Cruiser extends Ship {
    private int width = 4;
    private int xPosition;
    private int yPosition;
    private String direction;
    private int life = 4;

    public Cruiser() {
        super();
    }

    public Cruiser(int width, int xPosition, int yPosition, String direction, int life) {
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
