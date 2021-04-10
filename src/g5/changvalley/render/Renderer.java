package g5.changvalley.render;

import g5.changvalley.Window;
import g5.changvalley.render.mesh.Mesh;
import g5.changvalley.render.mesh.Uniform;
import org.joml.Matrix4f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class Renderer {
    public static void construct() {
        // make projection matrix and add it to a uniform
        Uniform.makeUniform("projectionMatrix", new Matrix4f().perspective(
                (float) Math.toRadians(60),
                (float) Window.getWidth() / Window.getHeight(),
                0.01f, 1000f));
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
