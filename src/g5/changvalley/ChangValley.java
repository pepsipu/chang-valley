package g5.changvalley;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.util.Objects;

import static org.lwjgl.opengl.GL32.*;

import static org.lwjgl.glfw.GLFW.*;

// main game class, run constructors, run the game loop, run destructors cuz memory leaks L
// evan chang for president 2024
public class ChangValley {
    // need 2 make this volatile bc spin waiting is non sequential
    private static boolean running = true;
    // 60 updates a second is good
    protected static final double INTERVAL = 1d / 60d;

    private static void construct() {
        // this should definitely be handled better, but for now glfw can just yell at us in stderr
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new IllegalStateException("glfw brokey");
        }
        ;
    }

    private static void destruct() {
        // kill window
        Window.destruct();
        glfwTerminate();

        // freeing the err callback should *never* cause a npe, but dont want errs coming from lwjgl codebase
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();

        System.out.println("thx for playing :D");
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
            // swap display buffers to show the scene
            Window.bufferSwap();

            glfwWaitEvents();
            Timer.sync();
        }
    }

    public static void main(String[] args) {
        System.out.println("Chang Valley: Euro Kim, Evan Chang, and Sammy Hajhamid");
        System.out.println("LWJGL version: " + Version.getVersion());
        // construct, loop, destruct
        construct();
        loop();
        destruct();
    }
}
