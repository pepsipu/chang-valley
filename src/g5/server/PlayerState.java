package g5.server;

import g5.Constants;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.io.Serializable;

public class PlayerState implements Serializable {
    private final Vector3f position = new Vector3f(0, 0, 0);
    private final Quaternionf orientation = new Quaternionf(1, 0, 0, 0);
    public long lastHeartbeat = System.currentTimeMillis();

    public void updateState(Vector3f position, Quaternionf orientation) {
        this.position.set(position);
        this.orientation.set(orientation);
        lastHeartbeat = System.currentTimeMillis();
    }

    public Vector3f getPosition() {
        return position;
    }

    public Quaternionf getOrientation() {
        return orientation;
    }

    public boolean hasHeartbeat() {
        return System.currentTimeMillis() - lastHeartbeat < Constants.HEARTBEAT_TIME ;
    }
}
