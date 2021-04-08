package g5.changvalley.render;

import g5.changvalley.render.mesh.Mesh;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class Renderer {
    public static void construct() {
    }

    public static void render(Mesh mesh) {
        mesh.bindVertex();
        // considering just keeping the attributes enabled? idk anything about opengl but i cant find out if
        // this is illegal or not
        Mesh.enableAttributes();
        mesh.draw();

        // for now we can just enable/disable
        Mesh.disableAttributes();
        Mesh.unbindVertex();
    }

    public static void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}
