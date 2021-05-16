package g5.changvalley.game.objects;

import g5.changvalley.engine.GameObject;
import g5.changvalley.game.GameObjectContainer;
import g5.changvalley.render.Renderer;
import g5.changvalley.render.mesh.Mesh;
import org.joml.Vector3f;

public class Rabbit extends GameObjectContainer {
    private final GameObject body;
    private final GameObject legs;
    private final GameObject eyes;

    public Rabbit() {
        // this is stupid but java doesnt allow assignment before super construction
        super(new GameObject(Mesh.fromFile("rabbit/rabbit.obj")),
                new GameObject(Mesh.fromFile("rabbit/legs.obj")),
                new GameObject(Mesh.fromFile("rabbit/eye.obj")));
        GameObject[] gameObjects = getGameObjects();
        body = gameObjects[0];
        legs = gameObjects[1];
        eyes = gameObjects[2];
    }

    public void render() {
        Renderer.render(body);
        Renderer.render(legs);
        Renderer.render(eyes);
    }
}
