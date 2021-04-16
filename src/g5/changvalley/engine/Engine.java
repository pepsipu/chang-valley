package g5.changvalley.engine;

import g5.changvalley.Window;
import g5.changvalley.render.Camera;
import g5.changvalley.render.Renderer;
import g5.changvalley.render.mesh.Mesh;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Engine {
    private static Vector3f cameraOffset = new Vector3f();
    public static void takeInput() {
        cameraOffset.zero();
        if (Window.isPressed(GLFW_KEY_W)) {
            cameraOffset.z = -0.1f;
        } else if (Window.isPressed(GLFW_KEY_S)) {
            cameraOffset.z = 0.1f;
        }
        if (Window.isPressed(GLFW_KEY_A)) {
            cameraOffset.x = -0.1f;
        } else if (Window.isPressed(GLFW_KEY_D)) {
            cameraOffset.x = 0.1f;
        }
        if (Window.isPressed(GLFW_KEY_Z)) {
            cameraOffset.y = -0.1f;
        } else if (Window.isPressed(GLFW_KEY_X)) {
            cameraOffset.y = 0.1f;
        }
    }

    public static void updateState() {
        Camera.offset(cameraOffset);
    }

    public static void render() {
        Renderer.clear();
    }
}
