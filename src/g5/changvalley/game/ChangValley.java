package g5.changvalley.game;
import g5.changvalley.Registrar;
import g5.changvalley.engine.Engine;
import g5.changvalley.engine.GameObject;
import org.lwjgl.Version;

import java.util.ArrayList;

public class ChangValley implements Engine {
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private Player player;

    public static void main(String[] args) {
        System.out.println("Chang Valley: Euro Kim, Evan Chang, and Sammy Hajhamid");
        System.out.println("LWJGL version: " + Version.getVersion());
        Registrar.registerGame(new ChangValley());
    }

    public void takeInput() {
        player.updateDirectionalVector();
    }

    public void updateState() {
        player.updateState();
    }

    public ArrayList<GameObject> render() {
        return gameObjects;
    }

    public void construct() {
        player = new Player();
        player.forEach((g) -> {
            g.position.set(0, 0, 3f);
        });
        player.addTo(gameObjects);
//        Particle p = new Particle();
//        p.setPosition(0, -3f, -9f);
//        gameObjects.add(p);
    }

    public void finalUpdate() {
        player.finalTransforms();
    }
}
