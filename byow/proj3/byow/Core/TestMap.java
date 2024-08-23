package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.Test;

public class TestMap {

    @Test
    public void a1() {
        TERenderer ter = new TERenderer();
        ter.initialize(Engine.WIDTH, Engine.HEIGHT);

        Engine engine = new Engine();
        TETile[][] world = engine.interactWithInputString("N999SDDDWWWDDD");

        ter.renderFrame(world);
        StdDraw.pause(1000);
    }

    @Test
    public void a2() {
        TERenderer ter = new TERenderer();
        ter.initialize(Engine.WIDTH, Engine.HEIGHT);

        Engine engine = new Engine();
        TETile[][] world = engine.interactWithInputString("N999SDDD:Q");
        TETile[][] newworld = engine.interactWithInputString("LWWWDDD");
        ter.renderFrame(newworld);
        StdDraw.pause(1000);
    }

    @Test
    public void a3() {
        TERenderer ter = new TERenderer();
        ter.initialize(Engine.WIDTH, Engine.HEIGHT);

        Engine engine = new Engine();
        TETile[][] w1 = engine.interactWithInputString("N999SDDD:Q");
        TETile[][] w2 = engine.interactWithInputString("LWWW:Q");
        TETile[][] w3 = engine.interactWithInputString("LDDD:Q");
        ter.renderFrame(w3);
        StdDraw.pause(1000);
    }

    @Test
    public void a4() {
        TERenderer ter = new TERenderer();
        ter.initialize(Engine.WIDTH, Engine.HEIGHT);

        Engine engine = new Engine();
        TETile[][] w1 = engine.interactWithInputString("N999SDDD:Q");
        TETile[][] w2 = engine.interactWithInputString("L:Q");
        TETile[][] w3 = engine.interactWithInputString("L:Q");
        TETile[][] w4 = engine.interactWithInputString("LWWWDDD");

        ter.renderFrame(w4);
        StdDraw.pause(1000);

    }


}
