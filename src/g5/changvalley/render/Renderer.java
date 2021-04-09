package g5.changvalley.render;

import g5.changvalley.render.mesh.Mesh;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class Renderer {
    public static void construct() {
    }

    public static void render(Mesh mesh) {
        mesh.bindVertex();
        Mesh.enableAttributes();

        mesh.draw();

        Mesh.disableAttributes();
        Mesh.unbindVertex();
    }

    public static void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}
