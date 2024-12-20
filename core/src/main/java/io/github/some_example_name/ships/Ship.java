package io.github.some_example_name.ships;

import java.util.List;

import io.github.some_example_name.battleField.RectCoordinates;

public class Ship {
    private List<RectCoordinates> shipsRect;
    private Rotation rotation;

    public Rotation getRotation() {
        return rotation;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public List<RectCoordinates> getShipsRect() {
        return shipsRect;
    }

    public void setShipsRect(List<RectCoordinates> shipsRect) {
        this.shipsRect = shipsRect;
    }
}
