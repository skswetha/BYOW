package byow.Core;

import byow.Core.TileMap.Coordinate;

import java.io.Serializable;
import java.util.ArrayList;


public class Rooms implements Serializable {


    private final Coordinate center;
    private final int height;
    private final int width;
    private final ArrayList<Coordinate> floor;
    private final ArrayList<Coordinate> walls;
    private boolean lighton;
    private Coordinate toprightF;
    private Coordinate topleftF;
    private Coordinate bottomrightF;
    private ArrayList<Coordinate> topwall;
    private ArrayList<Coordinate> bottomwall;
    private ArrayList<Coordinate> rightwall;
    private ArrayList<Coordinate> leftwall;


    /* for hallways, have an arraylist (?) of the wall that you want
       (like from top to bottom or left to right) and set those things to wall
     */
    private Coordinate bottomleftF;
    private Coordinate toprightW;
    private Coordinate topleftW;
    private Coordinate bottomrightW;
    private Coordinate bottomleftW;

    public Rooms(Coordinate c, int h, int w) {
        this.center = c;
        this.height = h;
        this.width = w;
        findFloorPoints();
        findWallPoints();
        floor = findFloor();
        walls = findWalls();
        lighton = false;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Coordinate getCenter() {
        return center;
    }

    public boolean getLightOn() {
        return lighton;
    }

    public void setLightOn(boolean l) {
        lighton = l;
    }

    public Coordinate getToprightF() {
        return toprightF;
    }

    public Coordinate getBottomrightF() {
        return bottomrightF;
    }

    public Coordinate getTopleftF() {
        return topleftF;
    }

    public Coordinate getBottomleftF() {
        return bottomleftF;
    }

    public Coordinate getToprightW() {
        return toprightW;
    }

    public Coordinate getBottomrightW() {
        return bottomrightW;
    }

    public Coordinate getTopleftW() {
        return topleftW;
    }

    public Coordinate getBottomleftW() {
        return bottomleftW;
    }


    public ArrayList<Coordinate> getFloor() {
        return floor;
    }

    public ArrayList<Coordinate> getWalls() {
        return walls;
    }

    public void findFloorPoints() {
        int trX = center.getX() + (width / 2);
        int trY = center.getY() + (height / 2);
        toprightF = new Coordinate(trX, trY);

        int tlX = center.getX() - (width / 2);
        int tlY = center.getY() + (height / 2);
        topleftF = new Coordinate(tlX, tlY);

        int brX = center.getX() + (width / 2);
        int brY = center.getY() - (height / 2);
        bottomrightF = new Coordinate(brX, brY);

        int blX = center.getX() - (width / 2);
        int blY = center.getY() - (height / 2);
        bottomleftF = new Coordinate(blX, blY);
    }

    public ArrayList<Coordinate> findFloor() {
        ArrayList<Coordinate> toreturn = new ArrayList<>();
        for (int x = bottomleftF.getX(); x <= bottomrightF.getX(); x++) {
            for (int y = bottomleftF.getY(); y <= topleftF.getY(); y++) {
                toreturn.add(new Coordinate(x, y));
            }
        }
        return toreturn;
    }

    public void findWallPoints() {
        int topY = topleftF.getY() + 1;
        int bottomY = bottomleftF.getY() - 1;
        int leftX = bottomleftF.getX() - 1;
        int rightX = bottomrightF.getX() + 1;

        toprightW = new Coordinate(rightX, topY);
        topleftW = new Coordinate(leftX, topY);
        bottomrightW = new Coordinate(rightX, bottomY);
        bottomleftW = new Coordinate(leftX, bottomY);
    }


    public ArrayList<Coordinate> findWalls() {
        ArrayList<Coordinate> toreturn = new ArrayList<>();
        int topY = topleftF.getY() + 1;
        int bottomY = bottomleftF.getY() - 1;
        int leftX = bottomleftF.getX() - 1;
        int rightX = bottomrightF.getX() + 1;
        /*top wall*/
        for (int x = leftX; x <= rightX; x++) {
            toreturn.add(new Coordinate(x, topY));
        }
        /* bottom wall*/
        for (int x = leftX; x <= rightX; x++) {
            toreturn.add(new Coordinate(x, bottomY));
        }
        /*left wall*/
        for (int y = bottomY; y <= topY; y++) {
            toreturn.add(new Coordinate(leftX, y));
        }
        /* right wall*/
        for (int y = bottomY; y <= topY; y++) {
            toreturn.add(new Coordinate(rightX, y));
        }
        return toreturn;
    }


}
