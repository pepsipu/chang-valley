package g5.changvalley.render;

import g5.changvalley.Window;
import g5.changvalley.engine.GameObject;
import g5.changvalley.render.mesh.Mesh;
import g5.changvalley.render.mesh.Uniform;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class Renderer {
    private static final float FOV = (float) Math.toRadians(60);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000f;
    private static final Matrix4f modelViewMatrix = new Matrix4f();
    private static final Matrix4f projectionMatrix = new Matrix4f();


    public static void construct() {
        // make projection matrix and add it to a uniform
        Uniform.makeUniform("projectionMatrix", Renderer.updateProjectionMatrix());
        // doesn't rlly matter what the world matrix starts off as so for now lets make it not change the object
        Uniform.makeUniform("modelViewMatrix", modelViewMatrix.identity());
        Uniform.makeUniform("textureSampler", 0);
    }

    public static void render(GameObject gameObject) {
        Uniform.updateUniform("modelViewMatrix", updateModelViewMatrix(gameObject.getPosition(), gameObject.getRotation(), gameObject.getScale()));
        gameObject.getMesh().render();
    }

    public static Matrix4f updateModelViewMatrix(Vector3f translation, Vector3f rotation, Vector3f scale) {
        return new Matrix4f(Camera.getViewMatrix())
                .mul(modelViewMatrix
                        .translation(translation)
                        .rotateX(rotation.x)
                        .rotateY(rotation.y)
                        .rotateZ(rotation.z)
                        .scale(scale));
    }

    public static Matrix4f updateProjectionMatrix() {
        return projectionMatrix.setPerspective(Renderer.FOV, Window.getWidth() / Window.getHeight(), Renderer.Z_NEAR, Renderer.Z_FAR);
    }

    public static void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}
