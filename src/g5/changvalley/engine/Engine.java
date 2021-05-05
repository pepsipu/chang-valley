package g5.changvalley.engine;

import java.util.ArrayList;

public interface Engine {
    void takeInput();

    void updateState();

    ArrayList<GameObject> render();

    void construct();
}
