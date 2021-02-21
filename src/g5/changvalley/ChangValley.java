package g5.changvalley;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;

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
        glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
        if (!glfwInit()) {
            throw new IllegalStateException("glfw brokey");
        };
    }

    // game loop. i like to keep everything event based but its good to have a loop to just start/stop coroutines if we
    // need to lol
    private static void loop() {
        double accumulator = 0;
        // user & external input, update state, render scene cycle
        while (running && !glfwWindowShouldClose(Window.window)) {
            // collect input here

            // fixed timestep updates here
            accumulator += Timer.getDelta();
            while (accumulator >= INTERVAL) {
                // do interval updates here
                accumulator -= INTERVAL;
            }

            // render here

            Timer.sync();
            System.out.println(accumulator);
        }
    }

    private static void sync() {

    }

    public static void main(String[] args) {
        System.out.println("Chang Valley: Euro Kim, Evan Chang, and Sammy Hajhamid");
        System.out.println("LWJGL version: " + Version.getVersion());
        // construct, loop, destruct
        construct();
        loop();
    }
}
