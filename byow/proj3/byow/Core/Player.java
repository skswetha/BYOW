package byow.Core;

import byow.Core.TileMap.Coordinate;

import java.io.Serializable;


public class Player implements Serializable {

    private Coordinate location;
    private int currX;
    private int currY;

    public Player(Coordinate xy) {
        location = xy;
        currX = xy.getX();
        currY = xy.getY();

    }

    public Coordinate getW() {
        return new Coordinate(currX, currY + 1);
    }

    public Coordinate getA() {
        return new Coordinate(currX - 1, currY);
    }

    public Coordinate getS() {
        return new Coordinate(currX, currY - 1);
    }

    public Coordinate getD() {
        return new Coordinate(currX + 1, currY);
    }

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate newXY) {
        location = newXY;
        currX = newXY.getX();
        currY = newXY.getY();
    }


}
