package com.grinz.bataillenavale;

public class Torpedo extends Ship {
    private int width = 2;
    private int xPosition;
    private int yPosition;

    public Torpedo(int width, int xPosition, int yPosition) {
        super(width, xPosition, yPosition);
    }
}
