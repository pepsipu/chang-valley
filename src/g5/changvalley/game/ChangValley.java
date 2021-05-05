package g5.changvalley.game;
import g5.changvalley.Registrar;
import g5.changvalley.engine.Engine;
import g5.changvalley.engine.GameObject;
import g5.changvalley.render.Camera;
import g5.changvalley.render.mesh.Mesh;
import g5.changvalley.render.mesh.Texture;
import g5.changvalley.render.mesh.Uniform;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.Version;

import java.util.ArrayList;

public class ChangValley implements Engine {
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private final Vector3f rotationOffset = new Vector3f(1, 1, 0);
    private final static float ROTATION_RATE = 0.01f;

    public static void main(String[] args) {
        System.out.println("Chang Valley: Euro Kim, Evan Chang, and Sammy Hajhamid");
        System.out.println("LWJGL version: " + Version.getVersion());
        Registrar.registerGame(new ChangValley());
    }

    public void takeInput() {

    }

    public void updateState() {

    }

    public ArrayList<GameObject> render() {
        // jitter every gameobject
        rotationOffset.rotateX(ROTATION_RATE).rotateY(ROTATION_RATE);
        for (GameObject gameObject: gameObjects) {
            gameObject.getPosition().add(rotationOffset);
        }
        Camera.addPosition(rotationOffset);
        return gameObjects;
    }

    public void construct() {
        GameObject o = new GameObject(Mesh.fromFile("teapot/teapot.obj"));
        o.setPosition(0, 0, -9f);
        gameObjects.add(o);
    }
}
