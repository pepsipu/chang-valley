package g5.changvalley;

import org.lwjgl.glfw.GLFW;

// suuuper basic timer, but to keep fixed timestep loops we need to get the time delta
public class Timer {
    private static double lastTime = 0;

    public static double getTime() {
        // glfw has an ok timer?? i think?? seems more accurate than javas lol
        return GLFW.glfwGetTime();
    }

    public static double getDelta() {
        double time = getTime();
        double delta = time - lastTime;
        lastTime = time;
        return delta;
    }
}
