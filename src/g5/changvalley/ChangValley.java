package g5.changvalley;

import g5.changvalley.engine.Engine;
import g5.changvalley.engine.Timer;
import g5.changvalley.render.Renderer;
import g5.changvalley.render.ShaderManager;
import g5.changvalley.render.mesh.Mesh;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;

// main game class, run constructors, run the game loop, run destructors cuz memory leaks L
// evan chang for president 2024
public class ChangValley {
    // need 2 make this volatile bc spin waiting is non sequential
    private static boolean running = true;
    // 60 updates a second is good
    public static final double INTERVAL = 1d / 60d;
    public static Mesh s;

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
        // user & external input, update state, render scene cycle
        while (running && !Window.shouldClose()) {
            Renderer.clear();
            // collect input here
            Engine.takeInput();

            // fixed timestep updates here
            accumulator += Timer.getDelta();
            while (accumulator >= INTERVAL) {
                // do interval updates here
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
        float[] positions = new float[]{
                -0.5f,  0.5f, -1.05f,
                -0.5f, -0.5f, -1.05f,
                0.5f, -0.5f, -1.05f,
                0.5f,  0.5f, -1.05f,
        };
        float[] colours = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
        };
        int[] indexes = new int[]{
                0, 1, 3, 3, 1, 2,
        };
        s = new Mesh(positions, colours, indexes);
        loop();
        destruct();
    }
}
