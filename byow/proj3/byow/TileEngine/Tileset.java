package byow.TileEngine;

import java.awt.*;
import java.io.Serializable;


/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 * <p>
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 * <p>
 * Ex:
 * world[x][y] = Tileset.FLOOR;
 * <p>
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */
public class Tileset implements Serializable {

    public static final TETile OFFLIGHTBULB = new TETile('●', Color.white,
            Color.black, "unlit logs",
            "byow/Core/pixels/fire/logs.png");


    public static final TETile lit1 = new TETile('●', Color.white,
            Color.black, "lighting up",
            "byow/Core/pixels/fire/lit1LIT.png");
    public static final TETile lit2 = new TETile('●', Color.white,
            Color.black, "lighting up",
            "byow/Core/pixels/fire/lit2LIT.png");
    public static final TETile lit3 = new TETile('●', Color.white,
            Color.black, "lighting up",
            "byow/Core/pixels/fire/lit3LIT.png");
    public static final TETile lit4 = new TETile('●', Color.white,
            Color.black, "lighting up",
            "byow/Core/pixels/fire/lit4LIT.png");


    public static final TETile fire1 = new TETile('●', Color.white,
            Color.black, "fire",
            "byow/Core/pixels/fire/atempt1LIT.png");
    public static final TETile fire2 = new TETile('●', Color.white,
            Color.black, "fire",
            "byow/Core/pixels/fire/atempt2LIT.png");
    public static final TETile fire3 = new TETile('●', Color.white,
            Color.black, "fire",
            "byow/Core/pixels/fire/atempt3LIT.png");
    public static final TETile fire4 = new TETile('●', Color.white,
            Color.black, "fire",
            "byow/Core/pixels/fire/atempt4LIT.png");


    public static final TETile LIGHTBULB = new TETile('●', Color.white,
            new Color(255, 228, 153), "lit fire",
            "byow/Core/pixels/fire/atempt1LIT.png");

    public static final TETile tllight = new TETile('·', Color.white,
            new Color(230, 132, 32), "lit floor",
            "byow/Core/pixels/wall/floor1litTL.png");
    public static final TETile llight = new TETile('·', Color.white,
            new Color(230, 132, 32), "lit floor",
            "byow/Core/pixels/wall/floor1litL.png");
    public static final TETile rlight = new TETile('·', Color.white,
            new Color(230, 132, 32), "lit floor",
            "byow/Core/pixels/wall/floor1litR.png");
    public static final TETile trlight = new TETile('·', Color.white,
            new Color(230, 132, 32), "lit floor",
            "byow/Core/pixels/wall/floor1litTR.png");
    public static final TETile bllight = new TETile('·', Color.white,
            new Color(230, 132, 32), "lit floor",
            "byow/Core/pixels/wall/floor1litBL.png");
    public static final TETile brlight = new TETile('·', Color.white,
            new Color(230, 132, 32), "lit floor",
            "byow/Core/pixels/wall/floor1litBR.png");
    public static final TETile blight = new TETile('·', Color.white,
            new Color(230, 132, 32), "lit floor",
            "byow/Core/pixels/wall/floor1litB.png");
    public static final TETile tlight = new TETile('·', Color.white,
            new Color(230, 132, 32), "lit floor",
            "byow/Core/pixels/wall/floor1litT.png");
    public static final TETile AVATAR = new TETile('@',
            new Color(230, 132, 32), Color.BLACK, "you",
            "byow/Core/pixels/movingDown/char1Down.png");


    public static final TETile monster1 = new TETile('@',
            Color.RED, Color.BLACK, "monster",
            "byow/Core/pixels/monster1.png");
    public static final TETile monster2 = new TETile('@',
            Color.RED, Color.BLACK, "monster",
            "byow/Core/pixels/monster2.png");
    public static final TETile monster3 = new TETile('@',
            Color.RED, Color.BLACK, "monster",
            "byow/Core/pixels/monster3.png");


    /* DEFAULT GRAY AVATAR */
    // avatar-left
    public static final TETile left1 = new TETile('@',
            new Color(180, 180, 180), Color.black, "you",
            "byow/Core/pixels/movingleft/char1Left.png");
    public static final TETile left2 = new TETile('@',
            new Color(180, 180, 180), Color.black, "you",
            "byow/Core/pixels/movingleft/char2Left.png");
    public static final TETile left3 = new TETile('@',
            new Color(180, 180, 180), Color.black, "you",
            "byow/Core/pixels/movingleft/char3Left.png");
    //avatar-right
    public static final TETile right1 = new TETile('@',
            new Color(180, 180, 180), Color.black, "you",
            "byow/Core/pixels/movingRight/char1Right.png");
    public static final TETile right2 = new TETile('@',
            new Color(180, 180, 180), Color.black, "you",
            "byow/Core/pixels/movingRight/char2Right.png");
    public static final TETile right3 = new TETile('@',
            new Color(180, 180, 180), Color.black, "you",
            "byow/Core/pixels/movingRight/char3Right.png");
    //avatar-up
    public static final TETile up1 = new TETile('@',
            new Color(180, 180, 180), Color.black, "you",
            "byow/Core/pixels/movingUp/char1Up.png");
    public static final TETile up2 = new TETile('@',
            new Color(180, 180, 180), Color.black, "you",
            "byow/Core/pixels/movingUp/char2Up.png");
    public static final TETile up3 = new TETile('@',
            new Color(180, 180, 180), Color.black, "you",
            "byow/Core/pixels/movingUp/char3Up.png");
    //avatar-down
    public static final TETile down1 = new TETile('@',
            new Color(180, 180, 180), Color.black, "you",
            "byow/Core/pixels/movingDown/char1Down.png");
    public static final TETile down2 = new TETile('@',
            new Color(180, 180, 180), Color.black, "you",
            "byow/Core/pixels/movingDown/char2Down.png");
    public static final TETile down3 = new TETile('@',
            new Color(180, 180, 180), Color.black, "you",
            "byow/Core/pixels/movingDown/char3Down.png");


    public static final TETile topwall = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall", "byow/Core/pixels/wall/brick.png");
    public static final TETile bottomwall = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall", "byow/Core/pixels/wall/brickbottom.png");
    public static final TETile leftwall = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall", "byow/Core/pixels/wall/brickleft.png");
    public static final TETile rightwall = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall", "byow/Core/pixels/wall/brickright.png");
    public static final TETile brwall = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall", "byow/Core/pixels/wall/brickbr.png");
    public static final TETile blwall = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall", "byow/Core/pixels/wall/brickbl.png");


    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
            "floor", "byow/Core/pixels/wall/floor2.png");
    public static final TETile floor1 = new TETile('·', new Color(128, 192, 128), Color.black,
            "floor", "byow/Core/pixels/wall/floor1.png");
    public static final TETile floor3 = new TETile('·', new Color(128, 192, 128), Color.black,
            "floor", "byow/Core/pixels/wall/floor3.png");
    public static final TETile floor4 = new TETile('·', new Color(128, 192, 128), Color.black,
            "floor", "byow/Core/pixels/wall/floor4.png");


    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
}


