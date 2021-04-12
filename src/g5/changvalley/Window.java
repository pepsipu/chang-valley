package g5.changvalley;

// bad style but
// *dont* *care*

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class Window {
    // no reason the window should be modified after creation
    private static long window;
    private static float width;
    private static float height;

    public static void construct() {
        glfwDefaultWindowHints();
        // im going to use glfw 3.3. no justification besides its the most familiar, forgive me mr reid sensei
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
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

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            Window.width = width;
            Window.height = height;
        });
        try (MemoryStack stack = MemoryStack.stackPush()) {
            // bruh why doesnt lwjgl not have bindings or smth so i dont need to actually deal with ptrs myself??
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            Window.width = pWidth.get();
            Window.height = pHeight.get();
        }
        glEnable(GL_DEPTH_TEST);
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

    // buffer swapping for v-sync, tbh tests show this doesnt change much since our application is pretty light
    // but might prevent screen tearing further on when we add more features? idk idt we're even scored on
    // performance lol
    public static void bufferSwap() {
        glfwSwapBuffers(window);
    }

    public static void pollEvents() {
        glfwPollEvents();
    }

    public static void destruct() {
        // clear our callbacks and kill the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
    }

    public static float getWidth() {
        return width;
    }

    public static float getHeight() {
        return height;
    }
}
