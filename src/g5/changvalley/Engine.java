package g5.changvalley;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL32.*;

public class Engine {
    private static int direction = 0;
    public static float color = 0;

    public static void takeInput() {
        if (Window.isPressed(GLFW_KEY_UP)) {
            direction = 1;
        } else if (Window.isPressed(GLFW_KEY_DOWN)) {
            direction = -1;
        } else {
            direction = 0;
        }
    }

    public static void updateState() {
        color += direction * 0.01f;
        if (color > 1) {
            color = 1.0f;
        } else if (color < 0) {
            color = 0.0f;
        }
    }

    public static void render() {
        Window.setClearColor(color, color, color);
        Renderer.clear();
    }
}
