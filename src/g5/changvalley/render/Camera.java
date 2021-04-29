package g5.changvalley.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    private static final Vector3f position = new Vector3f(0, 0, 0);
    private static final Vector3f rotation = new Vector3f(0, 0, 0);
    private static final Matrix4f viewMatrix = new Matrix4f().identity();

    private static final Vector3f X_POLE = new Vector3f(1, 0, 0);
    private static final Vector3f Y_POLE = new Vector3f(0, 1, 0);


    public static Vector3f getPosition() {
        return position;
    }

    public static Vector3f getRotation() {
        return rotation;
    }

    public static Matrix4f getViewMatrix() {
        return viewMatrix
                .rotation(rotation.x, X_POLE)
                .rotate(rotation.y, Y_POLE)
                .translate(-position.x, -position.y, -position.z);
    }

    public static void addPosition(Vector3f offset) {
        position.add(offset);
    }
    public static void addRotation(Vector3f offset) {
        rotation.add(offset);
    }
}
