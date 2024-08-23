package byow.Core;

import byow.Core.TileMap.Coordinate;
import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static edu.princeton.cs.algs4.StdDraw.*;


public class Engine {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private TileMap tilemap;
    private Random rand;
    private long seed;
    private boolean gameActive = true;


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        gameActive = true;
        showMenu();
    }

    public void checkPressedMouse(double mousex, double mousey) {
        /* if n*/
        if (mousey >= HEIGHT / 3 + 2) {
            checkKey('n');
        } else if (mousey >= HEIGHT / 3 - 2) {
            checkKey('l');
        } else {
            checkKey('q');
        }
    }

    public void checkKey(char key) {
        if (key == 'n') {
            gameActive = true;
            seed = seedMenu();
            this.rand = new Random(seed);
            instructionMenu();
            TileMap t = generateMap();
            startGame(t);
        } else if (key == 'l') {
            instructionMenu();
            seed = Long.parseLong(loadSeed());
            this.rand = new Random(seed);
            TileMap t = generateMap();
            TileMap world = loadTileMap();
            if (world == null) {
                System.exit(0);
            }
            gameActive = true;
            startGame(world);
        } else if (key == 'q') {
            gameActive = false;
            System.exit(0);
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        input = input.toLowerCase();
        String s = "";

        StringInputDevice inputdevice = new StringInputDevice(input);
        String rest = "";

        if ((input.charAt(0)) == ('n')) {
            s = input.substring(1, input.indexOf('s'));
            seed = Long.valueOf(s);
            this.rand = new Random(seed);
            tilemap = generateMap();
            rest = input.substring(input.indexOf('s') + 1);
        }
        if ((input.charAt(0)) == 'l') {
            seed = Long.parseLong(loadSeed());
            this.rand = new Random(seed);
            TileMap t = generateMap();
            TileMap world = loadTileMap();
            if (world == null) {
                System.exit(0);
            }
            tilemap = world;
            gameActive = true;
            rest = input.substring(input.indexOf('l') + 1);

        }

        tilemap = readMoves(tilemap, rest);


        TETile[][] finalWorldFrame = tilemap.getTileMap();
        return finalWorldFrame;

    }

    public TileMap readMoves(TileMap world, String moves) {
        char[] letters = moves.toCharArray();
        for (char key : letters) {
            if (gameActive) {
                if (key == 'w' || key == 'a' || key == 's' || key == 'd') {
                    world.movePlayer(key);
                }
                if (key == 'o') {
                    world.toggleLight(key);
                }
                if (key == ':') {
                    if (moves.charAt(moves.indexOf(key) + 1) == 'q') {
                        gameActive = false;
                        saveSeed(Long.toString(seed));
                        saveTileMap(world.getAvatar(), world.getTileMap(),
                                world.getTotalRooms(), world.getTotalLights(), world.getMonster());
                    }
                }
            }
        }
        return world;
    }


    public void instructionMenu() {
        Font font = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(font);
        StdDraw.clear(Color.BLACK);

        StdDraw.text(WIDTH / 2, HEIGHT - 5, "Useful Keys:");

        Font newfont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(newfont);

        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 4, "Whatever move you make, "
                + "the monster gets closer to you!");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 2, "N --> Start or load a new game!");
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "WASD --> Arrow Keys or click on a tile to move!");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 2, "O --> Light a fire!");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 4,
                "Get a description of the tile your mouse is hovering over!");


        Font newfont2 = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(newfont2);

        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 6, "Type 'r' when you're ready to play.");
        StdDraw.show();

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char currentKey = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (currentKey == 'r') {
                    break;
                }
            }
        }
    }

    public void showMenu() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(font);

        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.enableDoubleBuffering();

        StdDraw.text(WIDTH / 2, HEIGHT - 5, "CS61B: THE GAME");

        Font newfont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(newfont);

        StdDraw.text(WIDTH / 2, HEIGHT / 3 + 2, "New Game");
        StdDraw.text(WIDTH / 2, HEIGHT / 3 - 2, "Load Game");
        StdDraw.text(WIDTH / 2, HEIGHT / 3 - 6, "Quit");

        StdDraw.setPenColor(new Color(84, 14, 14));
        StdDraw.filledRectangle(WIDTH - 33, HEIGHT / 3 + 2, 1, 1);
        StdDraw.setPenColor(new Color(19, 84, 14));
        StdDraw.filledRectangle(WIDTH - 32, HEIGHT / 3 - 2, 1, 1);
        StdDraw.setPenColor(new Color(14, 47, 84));
        StdDraw.filledRectangle(WIDTH - 35, HEIGHT / 3 - 6, 1, 1);

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH - 33, HEIGHT / 3 + 1.9, "N");
        StdDraw.text(WIDTH - 32, HEIGHT / 3 - 2.1, "L");
        StdDraw.text(WIDTH - 35, HEIGHT / 3 - 6.1, "Q");

        StdDraw.show();

        while (gameActive) {
            double mousex = mouseX();
            double mousey = mouseY();
            if (isMousePressed()) {
                checkPressedMouse(mousex, mousey);
            }
            if (StdDraw.hasNextKeyTyped()) {
                char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                checkKey(key);
            }
        }
    }

    public long seedMenu() {
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.clear(Color.BLACK);

        StdDraw.text(WIDTH / 2, HEIGHT / 3, "Enter a random seed: ");
        StdDraw.text(WIDTH / 2, HEIGHT / 3 - 4, "Type 's' when you're ready!");
        StdDraw.show();

        String s = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char currentKey = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (currentKey == 's') {
                    break;
                } else {
                    if (Character.isDigit(currentKey)) {
                        s += currentKey;
                    }
                    StdDraw.clear(Color.BLACK);
                    StdDraw.text(WIDTH / 2, HEIGHT / 3, "Enter a random seed: ");
                    StdDraw.text(WIDTH / 2, HEIGHT / 3 - 2, s);
                    StdDraw.text(WIDTH / 2, HEIGHT / 3 - 4, "Type 's' when you're ready!");

                    StdDraw.show();
                }
            }
        }

        return Long.parseLong(s);
    }

    public void drawHUD(String left, String right) {
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(fontBig);
        StdDraw.textLeft(1.0, HEIGHT + 2.5, left);
        StdDraw.textRight(WIDTH - 1, HEIGHT + 2.5, right);
        StdDraw.show();
        StdDraw.pause(100);

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(1, HEIGHT + 2.5, WIDTH / 2, 1);
        StdDraw.filledRectangle(WIDTH - 1, HEIGHT + 2.5, WIDTH / 2, 1);

    }

    public void avatarAnimation(TERenderer ter, TileMap w, char dir, boolean after) {
        ArrayList<TETile> animation = new ArrayList<>();
        if (dir == ('w')) {
            animation.add(Tileset.up1);
            animation.add(Tileset.up2);
            animation.add(Tileset.up3);
        } else if (dir == ('s')) {
            animation.add(Tileset.down1);
            animation.add(Tileset.down2);
            animation.add(Tileset.down3);
        } else if (dir == ('d')) {
            animation.add(Tileset.right1);
            animation.add(Tileset.right2);
            animation.add(Tileset.right3);
        } else if (dir == ('a')) {
            animation.add(Tileset.left1);
            animation.add(Tileset.left2);
            animation.add(Tileset.left3);
        }

        if (after) {
            Collections.reverse(animation);
        }

        Coordinate current = w.getAvatar().getLocation();
        w.setCoordinate(current, animation.get(0));
        ter.renderFrame(w.getTileMap());
        StdDraw.pause(35);
        w.setCoordinate(current, animation.get(1));
        ter.renderFrame(w.getTileMap());
        StdDraw.pause(35);
        w.setCoordinate(current, animation.get(2));
        ter.renderFrame(w.getTileMap());
        StdDraw.pause(35);
    }

    public void lightAnimation(TERenderer ter, TileMap w, Coordinate light) {
        w.setCoordinate(light, Tileset.fire1);
        ter.renderFrame(w.getTileMap());
        StdDraw.pause(100);
        w.setCoordinate(light, Tileset.fire2);
        ter.renderFrame(w.getTileMap());
        StdDraw.pause(100);
        w.setCoordinate(light, Tileset.fire3);
        ter.renderFrame(w.getTileMap());
        StdDraw.pause(100);
        w.setCoordinate(light, Tileset.fire4);
        ter.renderFrame(w.getTileMap());
        StdDraw.pause(100);
        w.setCoordinate(light, Tileset.fire3);
        ter.renderFrame(w.getTileMap());
        StdDraw.pause(100);
        w.setCoordinate(light, Tileset.fire2);
        ter.renderFrame(w.getTileMap());
        StdDraw.pause(100);
        w.setCoordinate(light, Tileset.fire1);
    }

    public void onLightAnimation(TERenderer ter, TileMap w, Coordinate light, boolean before) {
        ArrayList<TETile> lit = new ArrayList<>();
        lit.add(Tileset.OFFLIGHTBULB);
        lit.add(Tileset.lit1);
        lit.add(Tileset.lit2);
        lit.add(Tileset.lit3);
        lit.add(Tileset.lit4);

        if (!before) {
            Collections.reverse(lit);
        }

        w.setCoordinate(light, lit.get(0));
        ter.renderFrame(w.getTileMap());
        StdDraw.pause(100);
        w.setCoordinate(light, lit.get(1));
        ter.renderFrame(w.getTileMap());
        StdDraw.pause(100);
        w.setCoordinate(light, lit.get(2));
        ter.renderFrame(w.getTileMap());
        StdDraw.pause(100);
        w.setCoordinate(light, lit.get(3));
        ter.renderFrame(w.getTileMap());
        StdDraw.pause(100);
        w.setCoordinate(light, lit.get(4));
    }

    public Rooms findRoom(TileMap w) {
        Rooms r = w.getTotalRooms().get(0);
        for (Rooms each : w.getTotalRooms()) {
            for (Coordinate xy : each.getFloor()) {
                if (w.getAvatar().getLocation().equals(xy)) {
                    r = each;
                }
            }
        }
        return r;
    }

    public void moveMonsterTo(TileMap w, TERenderer ter) {
        Coordinate current = w.getAvatar().getLocation();
        Coordinate mons = w.getMonster().getLocation();
        if (mons.getX() > current.getX()) {
            monsterAnimation(ter, w,  false);
            w.moveMonster('a');
            monsterAnimation(ter, w,  true);
        }
        if (mons.getX() < current.getX()) {
            monsterAnimation(ter, w,  false);
            w.moveMonster('d');
            monsterAnimation(ter, w,  true);
        }
        if (mons.getY() < current.getY()) {
            monsterAnimation(ter, w,  false);
            w.moveMonster('w');
            monsterAnimation(ter, w,  true);
        }
        if (mons.getY() > current.getY()) {
            monsterAnimation(ter, w,  false);
            w.moveMonster('s');
            monsterAnimation(ter, w,  true);
        }
        ter.renderFrame(w.getTileMap());
    }


    public char checkMouse(TileMap w, Coordinate mouseCoor, TERenderer ter, char lastmove) {
        if (w.validCoordinate(mouseCoor)) {
            Coordinate current = w.getAvatar().getLocation();
            if (mouseCoor.getX() < current.getX()) {
                avatarAnimation(ter, w, 'a', false);
                w.movePlayer('a');
                avatarAnimation(ter, w, 'a', true);
                lastmove = 'a';
            }
            current = w.getAvatar().getLocation();
            if (mouseCoor.getX() > current.getX()) {
                avatarAnimation(ter, w, 'd', false);
                w.movePlayer('d');
                avatarAnimation(ter, w, 'd', true);
                lastmove = 'd';
            }
            current = w.getAvatar().getLocation();
            if (mouseCoor.getY() > current.getY()) {
                avatarAnimation(ter, w, 'w', false);
                w.movePlayer('w');
                avatarAnimation(ter, w, 'w', true);
                lastmove = 'w';
            }
            current = w.getAvatar().getLocation();
            if (mouseCoor.getY() < current.getY()) {
                avatarAnimation(ter, w, 's', false);
                w.movePlayer('s');
                avatarAnimation(ter, w, 's', true);
                lastmove = 's';
            }
            ter.renderFrame(w.getTileMap());
        }
        return lastmove;
    }

    public void monsterAnimation(TERenderer ter, TileMap w, boolean after) {
        ArrayList<TETile> animation = new ArrayList<>();
        animation.add(Tileset.monster1);
        animation.add(Tileset.monster2);
        animation.add(Tileset.monster3);
        if (after) {
            Collections.reverse(animation);
        }
        Coordinate current = w.getMonster().getLocation();
        w.setCoordinate(current, animation.get(0));
        ter.renderFrame(w.getTileMap());
        StdDraw.pause(35);
        w.setCoordinate(current, animation.get(1));
        ter.renderFrame(w.getTileMap());
        StdDraw.pause(35);
        w.setCoordinate(current, animation.get(2));
        ter.renderFrame(w.getTileMap());
        StdDraw.pause(35);
    }

    public void startGame(TileMap world) {
        TileMap w = world;
        TERenderer ter = new TERenderer();
        ter.initialize(Engine.WIDTH + 5, Engine.HEIGHT + 5);
        ter.renderFrame(w.getTileMap());
        drawHUD("you are the blob!", "");
        boolean cantWalk = false;
        boolean cantLight = false;
        char lastmove = 's';
        boolean wasOff = false;
        String desc = "";
        boolean won = false;
        while (gameActive) {
            Rooms r = findRoom(w);
            Coordinate mouseCoor = new Coordinate((int) mouseX(), (int) mouseY());
            if (isMousePressed()) {
                lastmove = checkMouse(w, mouseCoor, ter, lastmove);
                moveMonsterTo(w, ter);
            }
            if (StdDraw.hasNextKeyTyped()) {
                char current = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (current == 'q') {
                    if (lastmove == ';') {
                        gameActive = false;
                        saveTileMap(w.getAvatar(), w.getTileMap(),
                                w.getTotalRooms(), w.getTotalLights(), world.getMonster());
                        System.exit(0);
                    }
                } else if (current == 'w' || current == 'a' || current == 's' || current == 'd') {
                    Coordinate curr = w.getAvatar().getLocation();
                    avatarAnimation(ter, w, current, false);
                    w.movePlayer(current);
                    cantWalk = curr.equals(w.getAvatar().getLocation());
                    avatarAnimation(ter, w, current, true);
                    moveMonsterTo(w, ter);
                    ter.renderFrame(w.getTileMap());
                } else if (current == 'o') {
                    if (!r.getLightOn()) {
                        wasOff = true;
                    }
                    if (!w.toggleLight(current)) {
                        cantLight = true;
                    } else {
                        onLightAnimation(ter, w, r.getCenter(), wasOff);
                        if (wasOff) {
                            lightAnimation(ter, w, r.getCenter());
                        }
                        wasOff = false;
                    }
                    ter.renderFrame(w.getTileMap());
                } else if (current == 'n') {
                    gameActive = false;
                    interactWithKeyboard();
                }
                if (cantWalk) {
                    drawHUD("", "You can't walk there!");
                }
                if (cantLight) {
                    drawHUD("", "You have to be next to the light!");
                }
                cantLight = false;
                lastmove = current;
            }
            if (w.validCoordinate(new Coordinate((int) mouseX(), (int) mouseY()))) {
                TETile tile = w.getCoordinate(new Coordinate((int) mouseX(), (int) mouseY()));
                if (tile != null && !tile.description().equals(desc)) {
                    desc = tile.description();
                    drawHUD(desc, "");
                }
            }
            won = checkLights(w);
            ifOver(w, won);
        }
        gameOver(won);
    }

    public void ifOver(TileMap w, boolean won) {
        if (w.getMonster().getLocation().equals(w.getAvatar().getLocation())) {
            gameActive = false;
            gameOver(won);
        }
        if (won) {
            gameActive = false;
            gameOver(won);
        }
    }

    public boolean checkLights(TileMap w) {
        boolean on = true;
        for (int i = 1; i < w.getTotalRooms().size() - 1; i++) {
            Rooms each = w.getTotalRooms().get(i);
            if (!each.getLightOn()) {
                on = false;
            }
        }
        return on;
    }

    public void gameOver(boolean won) {
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        if (won) {
            StdDraw.text(WIDTH / 2, HEIGHT / 2 + 5, "You won!");
        } else {
            StdDraw.text(WIDTH / 2, HEIGHT / 2 + 5, "You lost.");
        }
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "To quit, type 'q'");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 2, "To play again, type 'n'");

        StdDraw.show();

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char currentKey = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (currentKey == 'q') {
                    System.exit(0);
                }
                if (currentKey == 'n') {
                    interactWithKeyboard();
                }
            }
        }
    }


    public void saveTileMap(Player avatar, TETile[][] map,
                            ArrayList<Rooms> allrooms, ArrayList<Lights> alllights,
                            Player monster) {
        File f = new File("./tilemap.txt");

        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream worldf = new FileOutputStream(f);
            ObjectOutputStream worldo = new ObjectOutputStream(worldf);
            TileMap tosave = new TileMap(map, avatar, allrooms, alllights, monster);

            worldo.writeObject(tosave);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TileMap loadTileMap() {
        File f = new File("./tilemap.txt");
        try {
            FileInputStream worldf = new FileInputStream(f);
            ObjectInputStream worldo = new ObjectInputStream(worldf);
            tilemap = (TileMap) worldo.readObject();
            return tilemap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveSeed(String s) {
        try {
            FileWriter fileWriter = new FileWriter("./seed.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(s);
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loadSeed() {
        String read;
        try {
            FileReader fileReader = new FileReader("./seed.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer sb = new StringBuffer();
            while ((read = bufferedReader.readLine()) != null) {
                sb.append(read);
            }
            bufferedReader.close();
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Long.toString(rand.nextLong());
    }


    public TileMap generateMap() {
        TileMap newworld = new TileMap(WIDTH, HEIGHT);

        /* make rooms*/
        int num = rand.nextInt(6, 10);
        while (num != 0) {
            boolean validroom = true;
            int X = rand.nextInt(5, WIDTH - 6);
            int Y = rand.nextInt(5, HEIGHT - 5);
            Coordinate roomcenter = new Coordinate(X, Y);


            int roomHeight = rand.nextInt(2, 10);
            int roomWidth = rand.nextInt(2, 12);
            Rooms addroom = new Rooms(roomcenter, roomHeight, roomWidth);

            /*check if any overlapping*/
            if (!newworld.getTotalRooms().isEmpty()) {
                for (Rooms each : newworld.getTotalRooms()) {
                    if (each.getCenter() == roomcenter) {
                        validroom = false;
                    }
                    for (Coordinate floor : each.getFloor()) {
                        for (Coordinate addfloor : addroom.getFloor()) {
                            if (addfloor.equals(floor)) {
                                validroom = false;
                            }
                        }
                    }
                    for (Coordinate wall : each.getWalls()) {
                        for (Coordinate addwall : addroom.getWalls()) {
                            if (addwall.equals(wall)) {
                                validroom = false;
                            }
                        }
                    }
                }
            }

            if (validroom) {
                newworld.setRoom(addroom);
                num--;
            }
        }
        ArrayList<Rooms> listrooms = newworld.getTotalRooms();

        for (int i = 0; i < listrooms.size() - 1; i++) {
            Rooms room1 = listrooms.get(i);
            Rooms room2 = listrooms.get(i + 1);
            newworld.connectRooms(room1, room2);

        }
        newworld.addPlayer();

        for (int i = 1; i < listrooms.size() - 1; i++) {
            newworld.addLightbulb(listrooms.get(i));
        }


        return newworld;
    }
}
