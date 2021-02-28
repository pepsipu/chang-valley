package g5.changvalley;

// bad style but
// *dont* *care*

import org.lwjgl.opengl.GL;

import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL32.*;

public class Window {
    // no reason the window should be modified after creation
    private static final long window;
    private static long width;
    private static long height;

    static {
        glfwDefaultWindowHints();
        // im going to use glfw 3.2. no justification besides its the most familiar, forgive me mr reid sensei
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        window = glfwCreateWindow(500, 500, "Chang Valley", 0, 0);
        if (window == 0) {
            throw new RuntimeException("could not make window :(");
        }

        // for window context
        glfwMakeContextCurrent(window);
        // v-sync will fix a lot of ripping
        glfwSwapInterval(1);
        glfwShowWindow(window);
        GL.createCapabilities();

        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            Window.width = width;
            Window.height = height;
        });
    }

    public static boolean isPressed(int keyCode) {
        return glfwGetKey(window, keyCode) == GLFW_PRESS;
    }

    public static boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public static void setClearColor(float red, float green, float blue) {
        glClearColor(red, green, blue, 0);
    }

    // buffer swapping for v-sync
    public static void bufferSwap() {
        glfwSwapBuffers(window);
    }

    public static void destruct() {
        // clear our callbacks and kill the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
    }
}
