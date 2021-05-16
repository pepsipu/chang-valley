package g5.server;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.io.Serializable;

public class PlayerState implements Serializable {
    private final Vector3f position = new Vector3f(0, 0, 0);
    private final Quaternionf orientation = new Quaternionf(1, 0, 0, 0);
    public float lastHeartbeat = 0;

    public void updateState(Vector3f position, Quaternionf orientation) {
        this.position.set(position);
        this.orientation.set(orientation);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Quaternionf getOrientation() {
        return orientation;
    }

    public boolean hasHeartbeat() {
        return true;
    }
}
