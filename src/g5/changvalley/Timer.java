package g5.changvalley;

import org.lwjgl.glfw.GLFW;

// suuuper basic timer, but to keep fixed time step loops we need to get the time delta
public class Timer {
    private static double lastTime = 0;

    protected static double getTime() {
        // glfw has an ok timer?? i think?? seems more accurate than javas lol
        return GLFW.glfwGetTime();
    }

    protected static double getDelta() {
        double time = getTime();
        double delta = time - lastTime;
        lastTime = time;
        return delta;
    }

    // wait until frame should complete
    protected static void sync() {
        double wait =  lastTime + ChangValley.INTERVAL;
        while (getTime() < wait) {
            // spin waiting is a lot better than time stepping 1ms or busy waiting
            Thread.onSpinWait();
        }
    }

}
