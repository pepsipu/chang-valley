package g5.changvalley.game;
import g5.changvalley.Registrar;
import g5.changvalley.engine.Engine;
import g5.changvalley.engine.GameObject;
import g5.changvalley.engine.Particle;
import g5.changvalley.render.Camera;
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

    }

    public void updateState() {
        player.updateState();
    }

    public ArrayList<GameObject> render() {
        return gameObjects;
    }

    public void construct() {
        player = new Player();
        player.on((g) -> g.setPosition(0, 0, -9f));
        player.addTo(gameObjects);
        Particle particle = new Particle();
        particle.setPosition(0, -3, -9f);
        gameObjects.add(particle);
    }
}
