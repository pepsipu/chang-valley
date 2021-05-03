package g5.changvalley;

import g5.changvalley.engine.Engine;
import g5.changvalley.engine.GameObject;
import g5.changvalley.engine.Timer;
import g5.changvalley.render.Renderer;
import g5.changvalley.render.ShaderManager;
import g5.changvalley.render.mesh.Mesh;
import g5.changvalley.render.mesh.Texture;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;

// main game class, run constructors, run the game loop, run destructors cuz memory leaks L
// evan chang for president 2023
public class ChangValley {
    private static boolean running = true;
    // 60 updates a second is good
    public static final double INTERVAL = 1d / 60d;
    public static GameObject s;

    private static void construct() {
        // this should definitely be handled better, but for now glfw can just yell at us in stderr
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new IllegalStateException("glfw brokey");
        }
        // we can make these static blocks, but i prefer a more explicit and pragmatic construction/destruction
        // so we can control when we want to setup the window VS just in time construction
        Window.construct();
        ShaderManager.construct();
        Renderer.construct();
    }

    private static void destruct() {
        // kill window
        Window.destruct();
        ShaderManager.destruct();
        glfwTerminate();

        // freeing the err callback should *never* cause a npe, but dont want errs coming from lwjgl codebase
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();

        System.out.println("thx for playing :D");
    }

    public static void stop() {
        running = false;
    }

    // game loop, following the fixed timestep game loop paradigm
    private static void loop() {
        double accumulator = 0;
        float x = 0;
        float y = 0;
        float z = 0;
        // user & external input, update state, render scene cycle
        while (running && !Window.shouldClose()) {
            // collect input here
            Engine.takeInput();

            // fixed timestep updates here
            accumulator += Timer.getDelta();
            while (accumulator >= INTERVAL) {
                // do interval updates here
                x += .02f;
                y += .03f;
                z += .01f;
                float rotation = s.getRotation().z + 0.02f;
                if (rotation > Math.PI * 2) {
                    rotation = 0;
                }
                s.setRotation(rotation, rotation, rotation);
                s.getMesh().setColor((float) (Math.sin(x) + 2) / 3, (float) (Math.sin(y) + 2) / 3, (float) (Math.sin(z) + 2) / 3, .5f);
                Engine.updateState();
                accumulator -= INTERVAL;
            }
            // render here
            Engine.render();
            Renderer.render(s);
            // swap display buffers to show the scene
            Window.bufferSwap();
            Window.pollEvents();
            Timer.sync();
        }
    }

    public static void main(String[] args) {
        System.out.println("Chang Valley: Euro Kim, Evan Chang, and Sammy Hajhamid");
        System.out.println("LWJGL version: " + Version.getVersion());
        // construct, loop, destruct
        construct();

        Texture t = new Texture("./cube/cube.png");
        Mesh m = Mesh.fromFile("teapot/teapot.obj");
        s = new GameObject(m);
        s.setPosition(0, 0, -8f);

        loop();
        destruct();
    }
}
