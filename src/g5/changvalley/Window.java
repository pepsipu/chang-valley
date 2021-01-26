package g5.changvalley;

// bad style but
// *dont* *care*
import static org.lwjgl.glfw.GLFW.*;

public class Window {
    static long window;
    static {
        glfwDefaultWindowHints();
        // im going to use glfw 3.2. i have no other justification besides its what im familiar with
        // sorry mr reid
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        window = glfwCreateWindow(500, 500, "Chang Valley", 0, 0);
        if (window == 0) {
            throw new RuntimeException("could not make window :(");
        }
    }
}
