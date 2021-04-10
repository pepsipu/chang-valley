package g5.changvalley.engine;

import g5.changvalley.render.mesh.Mesh;
import org.joml.Vector3f;

public class GameObject {
    private final Mesh mesh;
    private final Vector3f position;
    private final Vector3f rotation;
    private float scale;

    public GameObject(Mesh mesh) {
        this.mesh = mesh;
        position = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);
        scale = 1;
    }
}
