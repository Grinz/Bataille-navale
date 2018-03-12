package Models.Ships;

public class Ship {
    private int width;
    private int xPosition;
    private int yPosition;
    private String direction;
    private int life;

    public Ship() {

    }

    public Ship (int width, int xPosition, int yPosition, String direction, int life) {
        this.width = width;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.direction = direction;
        this.life = life;
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
