package g5.changvalley;

import g5.changvalley.engine.Engine;
import g5.changvalley.engine.GameObject;
import g5.changvalley.engine.Timer;
import g5.changvalley.render.Renderer;
import g5.changvalley.render.ShaderManager;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;

// main game class, run constructors, run the game loop, run destructors cuz memory leaks L
// evan chang for president 2023
public class Registrar {
    public static boolean running = true;
    private static Engine engine;
    // 60 updates a second is good
    public static final double INTERVAL = 1d / 60d;

    private static void construct() {
        // this should definitely be handled better, but for now glfw can just yell at us in stderr
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new IllegalStateException("glfw brokey");
        }
        // we can make these static blocks, but i prefer a more explicit and pragmatic construction/destruction
        // so we can control when we want to setup the window VS just in time construction
        // important for glfw construction
        Window.construct();
        ShaderManager.construct();
        Renderer.construct();
        engine.construct();
    }

    private static void destruct() {
        running = false;

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
            // collect input here
            engine.takeInput();

            // fixed timestep updates here
            accumulator += Timer.getDelta();
            while (accumulator >= INTERVAL) {
                // do interval updates here
                engine.updateState();
                accumulator -= INTERVAL;
            }
            // render here
            Renderer.clear();
            for (GameObject gameObject: engine.render()) {
                Renderer.render(gameObject);
            };
            // swap display buffers to show the scene
            Window.bufferSwap();
            Window.pollEvents();
            Timer.sync();
        }
    }

    public static void registerGame(Engine engine) {
        Registrar.engine = engine;
        // construct, loop, destruct
        construct();
        loop();
        destruct();
    }
}
