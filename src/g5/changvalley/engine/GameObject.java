package g5.changvalley.engine;

import g5.changvalley.render.mesh.Mesh;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class GameObject {
    private Mesh mesh;
    public final Vector3f position = new Vector3f(0, 0, 0);
    public final Quaternionf orientation = new Quaternionf(1, 0, 0, 0);
    public final Vector3f scale = new Vector3f(1, 1, 1);

    public GameObject(Mesh mesh) {
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void render() {
        mesh.render();
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Vector4f getColor() {
        return mesh.getColor();
    }

    public void to(GameObject other) {
        position.set(other.position);
    }
}
