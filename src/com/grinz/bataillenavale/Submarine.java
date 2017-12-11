package com.grinz.bataillenavale;

public class Submarine extends Ship {
    private int width = 3;
    private int xPosition;
    private int yPosition;

    public Submarine(int width, int xPosition, int yPosition) {
        super(width, xPosition, yPosition);
    }
}
