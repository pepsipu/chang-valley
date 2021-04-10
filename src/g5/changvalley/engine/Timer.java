package g5.changvalley.engine;

import g5.changvalley.ChangValley;
import org.lwjgl.glfw.GLFW;

// suuuper basic timer, but to keep fixed time step loops we need to get the time delta
public class Timer {
    private static double lastTime = 0;

    protected static double getTime() {
        // glfw has an ok timer?? i think?? seems more accurate than javas lol
        return GLFW.glfwGetTime();
    }

    public static double getDelta() {
        double time = getTime();
        double delta = time - lastTime;
        lastTime = time;
        return delta;
    }

    // wait until frame should complete
    public static void sync() {
        double wait =  lastTime + ChangValley.INTERVAL;
        while (getTime() < wait) {
            // spin waiting is a lot better than time stepping 1ms or busy waiting
            Thread.onSpinWait();
        }
    }

}
