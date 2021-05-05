package g5.changvalley.engine;

import g5.changvalley.render.mesh.Mesh;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class GameObject {
    private Mesh mesh;
    private final Vector3f position = new Vector3f(0, 0, 0);
    private final Vector3f rotation = new Vector3f(0, 0, 0);
    private final Vector3f scale = new Vector3f(1, 1, 1);

    public GameObject(Mesh mesh) {
        this.mesh = mesh;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public void setPosition(Vector3f newPos) {
        position.set(newPos);
    }

    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }

    public void setScale(float x, float y, float z) {
        scale.x = x;
        scale.y = y;
        scale.z = z;
    }

    public void setScale(float s) {
        scale.x = s;
        scale.y = s;
        scale.z = s;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Vector4f getColor() {
        return mesh.getColor();
    }
}
