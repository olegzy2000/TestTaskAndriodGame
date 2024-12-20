package io.github.some_example_name.battleField;

import java.util.Objects;

public class RectCoordinates {
    private int xCoordinate;
    private int yCoordinate;
    private boolean isUse=false;

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RectCoordinates that = (RectCoordinates) o;
        return xCoordinate == that.xCoordinate && yCoordinate == that.yCoordinate && isUse == that.isUse;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoordinate, yCoordinate, isUse);
    }

    public boolean isUse() {
        return isUse;
    }

    public void setUse(boolean use) {
        isUse = use;
    }

    @Override
    public String toString() {
        return "RectCoordinates{" +
            "xCoordinate=" + xCoordinate +
            ", yCoordinate=" + yCoordinate +
            ", isUse=" + isUse +
            '}';
    }
}
