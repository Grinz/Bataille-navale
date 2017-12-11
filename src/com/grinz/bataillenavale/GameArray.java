package com.grinz.bataillenavale;

public class GameArray {
    ShipsArray[][] shipsArray;
    ShotsArray[][] shotsArray;

    public GameArray(ShipsArray[][] shipsArray, ShotsArray[][] shotsArray) {
        this.shipsArray = shipsArray;
        this.shotsArray = shotsArray;
    }
}
