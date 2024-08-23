package byow.Core;

import byow.Core.TileMap.Coordinate;

import java.io.Serializable;
import java.util.ArrayList;


public class Lights implements Serializable {

    private final Coordinate center;
    private final Rooms room;
    private final ArrayList<Coordinate> layers;
    private final int height;

    public Lights(Rooms r) {
        room = r;
        center = r.getCenter();
        height = Math.max(r.getHeight(), r.getWidth()) / 2;
        layers = fillLayers();
    }

    public ArrayList<Coordinate> fillLayers() {
        ArrayList<Coordinate> l = new ArrayList<>();
        for (int x = center.getX() - 1; x <= center.getX() + 1; x++) {
            for (int y = center.getY() - 1; y <= center.getY() + 1; y++) {
                Coordinate around = new Coordinate(x, y);
                if (!around.equals(center)) {
                    l.add(around);
                }
            }
        }
        return l;
    }

    public Rooms getRoom() {
        return room;
    }

    public ArrayList<Coordinate> getLayers() {
        return layers;
    }

    public Coordinate getCenter() {
        return center;
    }

    public int getHeight() {
        return height;
    }


}
