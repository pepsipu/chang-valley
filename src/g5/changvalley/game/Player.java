package g5.changvalley.game;

import g5.changvalley.Window;
import g5.changvalley.engine.GameObject;
import g5.changvalley.render.Camera;
import g5.changvalley.render.Renderer;
import g5.changvalley.render.mesh.Mesh;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;


public class Player extends GameObjectContainer {
    private final GameObject upperWing;
    private final GameObject lowerWing;
    private final GameObject playerBody;

    private final Vector3f directionalVector = new Vector3f(0, 0, 0);
    private double oscillation = 0;

    public Player() {
        // this is stupid but java doesnt allow assignment before super construction
        super(new GameObject(Mesh.fromFile("player/upper_wing.obj")),
                new GameObject(Mesh.fromFile("player/lower_wing.obj")),
                new GameObject(Mesh.fromFile("player/ball.obj")));
        GameObject[] gameObjects = getGameObjects();
        upperWing = gameObjects[0];
        lowerWing = gameObjects[1];
        playerBody = gameObjects[2];
    }

    public void render() {
        Renderer.render(playerBody);
        Renderer.render(upperWing);
        Renderer.render(lowerWing);
    }

    public void updateDirectionalVector() {
        int x = Window.pressValue(GLFW_KEY_A) - Window.pressValue(GLFW_KEY_D);
        int z = Window.pressValue(GLFW_KEY_W) - Window.pressValue(GLFW_KEY_S);
//        Camera.orientation.rotateX((float) x / 64);
        directionalVector.set(x, 0, z).div(16);
    }

    public void updateState() {
        oscillation = (oscillation + .04) % (2 * Math.PI);
        // flap wings
        // refer to integral comment below
//        upperWing.orientation.angle = (float) oscillation;
//        upperWing.orientation.rotateX((float) Math.sin(oscillation) / 64);
//        upperWing.setRotationAngle((float) (Math.sin(oscillation) * Math.PI / 2), 1, 0, 0);
//        lowerWing.orientation.rotateX((float) Math.cos(oscillation) / 64);

        // bounce model up and down
        forEach(g -> {
            Vector3f position = g.position;
            // since the integral of sin from 0 to 2pi is 0, adding sin(oscillation) in increments of dx
            // shouldn't actually affect the players overall position.
            position.add(0, (float) Math.sin(oscillation) / 128, 0);
            // also add directional input offset
            position.add(directionalVector);
            g.orientation.rotateY(.02f);
        });
        // change color of body
        playerBody.getMesh().setColor(
                // in order to generate colors which stay consistently bright (never fade to black)
                // we need to make sure the sine waves never add up in a way so every color value is low.
                // this means we're trying to minimize the range of sin(x + a) + sin(x + b) + sin(x + c)
                // to do this, we can ~~use cool math~~ plot in desmos
                // we can add a "brightness constant" which our sine wave, having a low range, will rarely deviate from
                (float) (Math.sin(oscillation) / 3 + .5),
                (float) (Math.sin(oscillation + 2) / 3 + .5),
                (float) (Math.sin(oscillation + 4) / 3 + .5),
                1);
    }

    public void finalTransforms() {
        // tilt user based on directional vector
    }
}
