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
        // user & external input, update state, render scene cycle
        while (running && !Window.shouldClose()) {
            // collect input here
            Engine.takeInput();

            // fixed timestep updates here
            accumulator += Timer.getDelta();
            while (accumulator >= INTERVAL) {
                // do interval updates here
                float rotation = s.getRotation().z + 0.02f;
                if (rotation > Math.PI * 2) {
                    rotation = 0;
                }
                s.setRotation(rotation, rotation, rotation);
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
                // V0
                -0.5f, 0.5f, 0.5f,
                // V1
                -0.5f, -0.5f, 0.5f,
                // V2
                0.5f, -0.5f, 0.5f,
                // V3
                0.5f, 0.5f, 0.5f,
                // V4
                -0.5f, 0.5f, -0.5f,
                // V5
                0.5f, 0.5f, -0.5f,
                // V6
                -0.5f, -0.5f, -0.5f,
                // V7
                0.5f, -0.5f, -0.5f,
                // For text coords in top face
                // V8: V4 repeated
                -0.5f, 0.5f, -0.5f,
                // V9: V5 repeated
                0.5f, 0.5f, -0.5f,
                // V10: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V11: V3 repeated
                0.5f, 0.5f, 0.5f,
                // For text coords in right face
                // V12: V3 repeated
                0.5f, 0.5f, 0.5f,
                // V13: V2 repeated
                0.5f, -0.5f, 0.5f,
                // For text coords in left face
                // V14: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V15: V1 repeated
                -0.5f, -0.5f, 0.5f,
                // For text coords in bottom face
                // V16: V6 repeated
                -0.5f, -0.5f, -0.5f,
                // V17: V7 repeated
                0.5f, -0.5f, -0.5f,
                // V18: V1 repeated
                -0.5f, -0.5f, 0.5f,
                // V19: V2 repeated
                0.5f, -0.5f, 0.5f
        };
        float[] textureIndexes = new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                0.0f, 0.0f,
                0.5f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                // For text coords in top face
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,
                // For text coords in right face
                0.0f, 0.0f,
                0.0f, 0.5f,
                // For text coords in left face
                0.5f, 0.0f,
                0.5f, 0.5f,
                // For text coords in bottom face
                0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
        };
        int[] indexes = new int[]{
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                8, 10, 11, 9, 8, 11,
                // Right face
                12, 13, 7, 5, 12, 7,
                // Left face
                14, 15, 6, 4, 14, 6,
                // Bottom face
                16, 18, 19, 17, 16, 19,
                // Back face
                4, 6, 7, 5, 4, 7
        };
        Texture t = new Texture("./grassblock.png");
        s = new GameObject(new Mesh(positions, textureIndexes, indexes, t));
        s.setPosition(0, 0, -2f);
        loop();
        destruct();
    }
}
