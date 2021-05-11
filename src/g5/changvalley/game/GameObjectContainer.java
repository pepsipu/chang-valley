package g5.changvalley.game;

import g5.changvalley.engine.GameObject;

import java.util.ArrayList;
import java.util.Arrays;

interface RunnableGameObject {
    void run(GameObject gameObject);
}

public class GameObjectContainer {
    private final GameObject[] gameObjects;
    GameObjectContainer(GameObject ...gameObjects) {
        this.gameObjects = gameObjects;
    }

    public GameObject[] getGameObjects() {
        return gameObjects;
    }

    public void addTo(ArrayList<GameObject> list) {
        list.addAll(Arrays.asList(getGameObjects()));
    }

    public void on(RunnableGameObject runnable) {
        for (GameObject gameObject: gameObjects) {
            runnable.run(gameObject);
        }
    }
}
