package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class TileMap implements Serializable {
    private final int mapheight;
    private final int mapwidth;
    private final TETile[][] tilemap;
    private final ArrayList<Rooms> totalrooms;
    private final ArrayList<Lights> totallights;
    private final Player avatar;
    private final Player monster;
    private int numrooms;

    /**
     * Creates an empty tile map.
     *
     * @param height int
     * @param width  int
     */
    public TileMap(int width, int height) {
        mapheight = height;
        mapwidth = width;
        this.tilemap = new TETile[mapwidth][mapheight];
        for (int w = 0; w < mapwidth; w++) {
            for (int h = 0; h < mapheight; h++) {
                tilemap[w][h] = Tileset.NOTHING;
            }
        }
        numrooms = 0;
        totalrooms = new ArrayList<>();
        totallights = new ArrayList<>();
        avatar = new Player(new Coordinate(0, 0));
        monster = new Player(new Coordinate(mapwidth, mapheight));
    }

    /*
     New tilemap constructor.
     */
    public TileMap(TETile[][] tm, Player av, ArrayList<Rooms> tr,
                   ArrayList<Lights> tl, Player mon) {
        mapheight = tm[0].length;
        mapwidth = tm.length;
        tilemap = new TETile[mapwidth][mapheight];
        for (int w = 0; w < mapwidth; w++) {
            for (int h = 0; h < mapheight; h++) {
                setCoordinate(new Coordinate(w, h), tm[w][h]);
            }
        }
        avatar = new Player(av.getLocation());
        monster = new Player(mon.getLocation());

        totalrooms = new ArrayList<Rooms>();
        for (Rooms r : tr) {
            totalrooms.add(r);
        }
        totallights = new ArrayList<Lights>();
        for (Lights l : tl) {
            totallights.add(l);
        }
    }


    public TETile[][] getTileMap() {
        return tilemap;
    }

    public int getHeight() {
        return mapheight;
    }

    public int getWidth() {
        return mapwidth;
    }

    public Player getAvatar() {
        return avatar;
    }

    public Player getMonster() {
        return monster;
    }

    public ArrayList<Lights> getTotalLights() {
        return totallights;
    }


    public TETile getCoordinate(Coordinate xy) {
        if (validCoordinate(xy)) {
            return tilemap[xy.getX()][xy.getY()];
        } else {
            System.out.println("Not a valid coordinate.");
            return null;
        }

    }

    public void setCoordinate(Coordinate xy, TETile toadd) {
        if (validCoordinate(xy)) {
            tilemap[xy.getX()][xy.getY()] = toadd;
        } else {
            System.out.println("Not a valid coordinate.");
            return;
        }
    }

    public boolean validCoordinate(Coordinate xy) {
        int width = xy.getX();
        int height = xy.getY();
        return width >= 0 && width <= mapwidth - 1
                && height >= 0 && height <= mapheight - 1;
    }

    public void setRoom(Rooms addroom) {
        ArrayList<Coordinate> floor = addroom.getFloor();
        ArrayList<Coordinate> walls = addroom.getWalls();
        for (int i = 0; i < floor.size(); i++) {
            setCoordinate(floor.get(i), Tileset.FLOOR);
            Random rand = new Random();
            int randi = rand.nextInt(4);
            if (randi == 1) {
                setCoordinate(floor.get(i), Tileset.floor1);
            } else if (randi == 3) {
                setCoordinate(floor.get(i), Tileset.floor3);
            } else if (randi == 4) {
                setCoordinate(floor.get(i), Tileset.floor4);
            }
        }
        numrooms++;
        totalrooms.add(addroom);

        /* for top wall */
        for (int x = addroom.getTopleftW().getX() + 1; x < addroom.getToprightW().getX(); x++) {
            Coordinate curr = new Coordinate(x, addroom.getTopleftW().getY());
            setCoordinate(curr, Tileset.topwall);
        }
        /* for bottom wall */
        for (int x = addroom.getBottomleftW().getX() + 1;
             x < addroom.getBottomrightW().getX(); x++) {
            Coordinate curr = new Coordinate(x, addroom.getBottomleftW().getY());
            setCoordinate(curr, Tileset.bottomwall);
        }
        /* for left wall */
        for (int y = addroom.getBottomleftW().getY() + 1; y <= addroom.getTopleftW().getY(); y++) {
            Coordinate curr = new Coordinate(addroom.getBottomleftW().getX(), y);
            setCoordinate(curr, Tileset.leftwall);
        }
        /* for right wall */
        for (int y = addroom.getBottomrightW().getY() + 1;
             y <= addroom.getToprightW().getY(); y++) {
            Coordinate curr = new Coordinate(addroom.getBottomrightW().getX(), y);
            setCoordinate(curr, Tileset.rightwall);
        }
        setCoordinate(addroom.getBottomleftW(), Tileset.blwall);
        setCoordinate(addroom.getBottomrightW(), Tileset.brwall);
    }


    public void setLightsOn(Rooms room) {
        Lights light = new Lights(room);
        room.setLightOn(true);

        ArrayList<Coordinate> layers = light.getLayers();
        setCoordinate(light.getCenter(), Tileset.LIGHTBULB);

        /* top right */
        Coordinate tr = new Coordinate(light.getCenter().getX() + 1, light.getCenter().getY() + 1);
        if (!avatar.getLocation().equals(tr)) {
            setCoordinate(tr, Tileset.trlight);
        }
        /* top left */
        Coordinate tl = new Coordinate(light.getCenter().getX() - 1, light.getCenter().getY() + 1);
        if (!avatar.getLocation().equals(tl)) {
            setCoordinate(tl, Tileset.tllight);
        }
        /* top */
        Coordinate t = new Coordinate(light.getCenter().getX(), light.getCenter().getY() + 1);
        if (!avatar.getLocation().equals(t)) {
            setCoordinate(t, Tileset.tlight);
        }
        /* bottom */
        Coordinate b = new Coordinate(light.getCenter().getX(), light.getCenter().getY() - 1);
        if (!avatar.getLocation().equals(b)) {
            setCoordinate(b, Tileset.blight);
        }
        /* right */
        Coordinate r = new Coordinate(light.getCenter().getX() + 1, light.getCenter().getY());
        if (!avatar.getLocation().equals(r)) {
            setCoordinate(r, Tileset.rlight);
        }
        /* left */
        Coordinate l = new Coordinate(light.getCenter().getX() - 1, light.getCenter().getY());
        if (!avatar.getLocation().equals(l)) {
            setCoordinate(l, Tileset.llight);
        }
        /* bottom left */
        Coordinate bl = new Coordinate(light.getCenter().getX() - 1, light.getCenter().getY() - 1);
        if (!avatar.getLocation().equals(bl)) {
            setCoordinate(bl, Tileset.bllight);
        }
        /* bottom right */
        Coordinate br = new Coordinate(light.getCenter().getX() + 1, light.getCenter().getY() - 1);
        if (!avatar.getLocation().equals(br)) {
            setCoordinate(br, Tileset.brlight);
        }
    }

    public void addLightbulb(Rooms room) {
        Lights light = new Lights(room);
        setCoordinate(light.getCenter(), Tileset.OFFLIGHTBULB);
        totallights.add(light);
    }

    public void turnLightsOff(Rooms room) {
        for (int x = room.getBottomleftF().getX(); x < room.getBottomrightF().getX() + 1; x++) {
            for (int y = room.getBottomleftF().getY(); y < room.getTopleftF().getY() + 1; y++) {
                if (avatar.getLocation().equals(new Coordinate(x, y))) {
                    setCoordinate(new Coordinate(x, y), Tileset.AVATAR);
                }
                if (getCoordinate(new Coordinate(x, y)).equals(Tileset.tllight)
                        || getCoordinate(new Coordinate(x, y)).equals(Tileset.trlight)
                        || getCoordinate(new Coordinate(x, y)).equals(Tileset.rlight)
                        || getCoordinate(new Coordinate(x, y)).equals(Tileset.tlight)
                        || getCoordinate(new Coordinate(x, y)).equals(Tileset.llight)
                        || getCoordinate(new Coordinate(x, y)).equals(Tileset.blight)
                        || getCoordinate(new Coordinate(x, y)).equals(Tileset.brlight)
                        || getCoordinate(new Coordinate(x, y)).equals(Tileset.bllight)) {
                    setCoordinate(new Coordinate(x, y), Tileset.floor1);
                }
            }
        }
        setCoordinate(room.getCenter(), Tileset.OFFLIGHTBULB);
        room.setLightOn(false);
    }


    public boolean toggleLight(char key) {
        boolean possible = false;
        if (key == 'o') {
            for (int i = 0; i < totallights.size(); i++) {
                Lights l = totallights.get(i);
                Coordinate left = new Coordinate(l.getCenter().getX() - 1, l.getCenter().getY());
                Coordinate right = new Coordinate(l.getCenter().getX() + 1, l.getCenter().getY());
                Coordinate top = new Coordinate(l.getCenter().getX(), l.getCenter().getY() + 1);
                Coordinate bottom = new Coordinate(l.getCenter().getX(), l.getCenter().getY() - 1);
                ArrayList<Coordinate> surround = new ArrayList<>();
                surround.add(left);
                surround.add(right);
                surround.add(top);
                surround.add(bottom);
                for (Coordinate each : surround) {
                    if (avatar.getLocation().equals(each)) {
                        if (l.getRoom().getLightOn()) {
                            turnLightsOff(l.getRoom());
                        } else {
                            setLightsOn(l.getRoom());
                        }
                        possible = true;
                    }
                }
            }
        }
        return possible;
    }

    public ArrayList<Rooms> getTotalRooms() {
        return totalrooms;
    }

    public void addPlayer() {
        avatar.setLocation(totalrooms.get(0).getCenter());
        setCoordinate(totalrooms.get(0).getCenter(), Tileset.down1);

        monster.setLocation(totalrooms.get(totalrooms.size() - 1).getCenter());
        setCoordinate(totalrooms.get(totalrooms.size() - 1).getCenter(), Tileset.monster1);
    }

    public void movePlayer(Coordinate newxy, Coordinate oldxy) {
        setCoordinate(newxy, Tileset.down1);
        setCoordinate(oldxy, Tileset.FLOOR);
        Random rand = new Random();
        int randi = rand.nextInt(4);
        if (randi == 1) {
            setCoordinate(oldxy, Tileset.floor1);
        } else if (randi == 3) {
            setCoordinate(oldxy, Tileset.floor3);
        } else if (randi == 4) {
            setCoordinate(oldxy, Tileset.floor4);
        }
    }

    public void moveMonster(Coordinate newxy, Coordinate oldxy) {
        setCoordinate(newxy, Tileset.monster1);
        setCoordinate(oldxy, Tileset.FLOOR);
        Random rand = new Random();
        int randi = rand.nextInt(4);
        if (randi == 1) {
            setCoordinate(oldxy, Tileset.floor1);
        } else if (randi == 3) {
            setCoordinate(oldxy, Tileset.floor3);
        } else if (randi == 4) {
            setCoordinate(oldxy, Tileset.floor4);
        }
    }

    public void moveMonster(char direction) {
        Rooms curr = null;
        for (Rooms each : totalrooms) {
            for (Coordinate xy : each.getFloor()) {
                if (monster.getLocation().equals(xy)) {
                    curr = each;
                }
            }
        }
        if (monster == null) {
            return;
        }
        Coordinate old = monster.getLocation();
        Coordinate move = monster.getLocation();
        if (direction == 'w') {
            move = monster.getW();
        }

        if (direction == 'a') {
            move = monster.getA();
        }
        if (direction == 's') {
            move = monster.getS();
        }
        if (direction == 'd') {
            move = monster.getD();
        }
        TETile tmove = getCoordinate(move);
        if (validCoordinate(move)) {
            if (!(tmove.description().equals("wall"))
                    && !(tmove.equals(Tileset.LIGHTBULB))
                    && !(tmove.equals(Tileset.OFFLIGHTBULB))
                    && !(tmove.equals(Tileset.NOTHING))
                    && !(tmove == null)) {
                monster.setLocation(move);
                moveMonster(move, old);
                for (Rooms each : totalrooms) {
                    for (Coordinate xy : each.getFloor()) {
                        if (move.equals(xy)) {
                            curr = each;
                        }
                    }
                }
            }
        }
        if (curr != null) {
            if (curr.getLightOn()) {
                setLightsOn(curr);
            }
        }
    }

    public void movePlayer(char direction) {
        Rooms curr = null;
        for (Rooms each : totalrooms) {
            for (Coordinate xy : each.getFloor()) {
                if (avatar.getLocation().equals(xy)) {
                    curr = each;
                }
            }
        }
        if (avatar == null) {
            return;
        }
        Coordinate old = avatar.getLocation();
        Coordinate move = avatar.getLocation();
        if (direction == 'w') {
            move = avatar.getW();
        }

        if (direction == 'a') {
            move = avatar.getA();
        }
        if (direction == 's') {
            move = avatar.getS();
        }
        if (direction == 'd') {
            move = avatar.getD();
        }
        TETile tmove = getCoordinate(move);
        if (validCoordinate(move)) {
            if (!(tmove.description().equals("wall"))
                    && !(tmove.equals(Tileset.LIGHTBULB))
                    && !(tmove.equals(Tileset.OFFLIGHTBULB))
                    && !(tmove.equals(Tileset.NOTHING))
                    && !(tmove == null)) {
                avatar.setLocation(move);
                movePlayer(move, old);
                for (Rooms each : totalrooms) {
                    for (Coordinate xy : each.getFloor()) {
                        if (move.equals(xy)) {
                            curr = each;
                        }
                    }
                }
            }
        }
        if (curr != null) {
            if (curr.getLightOn()) {
                setLightsOn(curr);
            }
        }
    }


    public void connectRooms(Rooms room1, Rooms room2) {
        Coordinate center1 = room1.getCenter();
        Coordinate center2 = room2.getCenter();
        int x1 = center1.getX();
        int x2 = center2.getX();
        int y1 = center1.getY();
        int y2 = center2.getY();
        ArrayList<Coordinate> pathFloor = new ArrayList<>();
        ArrayList<Coordinate> pathWall = new ArrayList<>();
        if (x1 < x2) {
            for (int x = x1; x < x2 + 1; x++) {
                Coordinate toX = new Coordinate(x, y1);
                pathFloor.add(toX);
                pathWall.add(new Coordinate(x, y1 + 1));
                pathWall.add(new Coordinate(x, y1 - 1));
            }
            if (y1 < y2) {
                for (int y = y1; y < y2 + 1; y++) {
                    Coordinate toY = new Coordinate(x2, y);
                    pathFloor.add(toY);
                    pathWall.add(new Coordinate(x2 + 1, y));
                    pathWall.add(new Coordinate(x2 - 1, y));
                }
            } else {
                for (int y = y2; y < y1 + 1; y++) {
                    Coordinate toY = new Coordinate(x2, y);
                    pathFloor.add(toY);
                    pathWall.add(new Coordinate(x2 + 1, y));
                    pathWall.add(new Coordinate(x2 - 1, y));
                }
            }
        } else {
            for (int x = x2; x < x1 + 1; x++) {
                Coordinate toX = new Coordinate(x, y2);
                pathFloor.add(toX);
                pathWall.add(new Coordinate(x, y2 + 1));
                pathWall.add(new Coordinate(x, y2 - 1));
            }
            if (y1 < y2) {
                for (int y = y1; y < y2 + 1; y++) {
                    Coordinate toY = new Coordinate(x1, y);
                    pathFloor.add(toY);
                    pathWall.add(new Coordinate(x1 + 1, y));
                    pathWall.add(new Coordinate(x1 - 1, y));
                }
            } else {
                for (int y = y2; y < y1 + 1; y++) {
                    Coordinate toY = new Coordinate(x1, y);
                    pathFloor.add(toY);
                    pathWall.add(new Coordinate(x1 + 1, y));
                    pathWall.add(new Coordinate(x1 - 1, y));
                }
            }
        }
        setTilePixels(pathFloor, pathWall);
    }

    public void setTilePixels(ArrayList<Coordinate> pathFloor, ArrayList<Coordinate> pathWall) {
        for (Coordinate xy : pathFloor) {
            setCoordinate(xy, Tileset.FLOOR);
            Random rand = new Random();
            int randi = rand.nextInt(4);
            if (randi == 1) {
                setCoordinate(xy, Tileset.floor1);
            } else if (randi == 3) {
                setCoordinate(xy, Tileset.floor3);
            } else if (randi == 4) {
                setCoordinate(xy, Tileset.floor4);
            }
        }
        for (Coordinate xy : pathWall) {
            if (!getCoordinate(xy).equals(Tileset.FLOOR)) {
                setCoordinate(xy, Tileset.WALL);
                int x = xy.getX();
                int y = xy.getY();
                if (getCoordinate(new Coordinate(x, y - 1)).description().equals("floor")) {
                    setCoordinate(xy, Tileset.topwall);
                }
                if (getCoordinate(new Coordinate(x, y + 1)).description().equals("floor")) {
                    setCoordinate(xy, Tileset.bottomwall);
                }
                if (getCoordinate(new Coordinate(x + 1, y)).description().equals("floor")) {
                    setCoordinate(xy, Tileset.leftwall);
                }
                if (getCoordinate(new Coordinate(x - 1, y)).description().equals("floor")) {
                    setCoordinate(xy, Tileset.rightwall);
                }
            }
        }
        for (int x = 1; x < mapwidth - 1; x++) {
            for (int y = 1; y < mapheight - 1; y++) {
                if (getCoordinate(new Coordinate(x, y)).description().equals("wall")) {
                    if (getCoordinate(new Coordinate(x, y - 1)).description().equals("floor")
                            && (getCoordinate(new Coordinate(x + 1, y)).description().equals(
                            "wall")
                            || getCoordinate(new Coordinate(x - 1, y)).description().equals(
                            "wall"))) {
                        setCoordinate(new Coordinate(x, y), Tileset.topwall);
                    }
                }
            }
        }
    }


    /**
     * A point on the map representing a single tile.
     */
    public static class Coordinate implements Serializable {
        private final int x;
        private final int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean equals(Coordinate coor2) {
            return this.getX() == coor2.getX() && this.getY() == coor2.getY();
        }
    }
}
