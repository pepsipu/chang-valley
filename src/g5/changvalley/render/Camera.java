package g5.changvalley.render;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Camera {
    private static final Vector3f position = new Vector3f(0, 0, 0);
    public static final Quaternionf orientation = new Quaternionf(1, 0, 0, 0);
    private static final Matrix4f viewMatrix = new Matrix4f().identity();


    public static Matrix4f getViewMatrix() {
        System.out.println(orientation);
        return viewMatrix
                .rotation(orientation)
                .translate(-position.x, -position.y, -position.z);
    }

    public static void addPosition(Vector3f offset) {
        position.add(offset);
    }
}
