package g5.changvalley.game.objects;

import g5.changvalley.Window;
import g5.changvalley.engine.GameObject;
import g5.changvalley.engine.Particle;
import g5.changvalley.game.GameObjectContainer;
import g5.changvalley.render.Camera;
import g5.changvalley.render.Renderer;
import g5.changvalley.render.mesh.Mesh;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import static org.lwjgl.glfw.GLFW.*;


public class Player extends GameObjectContainer {
    private final GameObject upperWing;
    private final GameObject lowerWing;
    private final GameObject playerBody;

    private final Vector3f directionalVector = new Vector3f(0, 0, 0);
    private double oscillation = 0;

    private float lastTiltX;
    private float lastTiltZ;

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
        int x = Window.pressValue(GLFW_KEY_D) - Window.pressValue(GLFW_KEY_A);
        int z = Window.pressValue(GLFW_KEY_W) - Window.pressValue(GLFW_KEY_S);
        // if the vector changed
        if (x != directionalVector.x * 16 || z != directionalVector.z * 16) {
            forEach(g -> {
                // undo last tilt
                g.orientation.rotateZ(-lastTiltX);
                g.orientation.rotateX(-lastTiltZ);

                // apply new tilt
                g.orientation.rotateZ((float) x / 8);
                g.orientation.rotateX((float) z / 8);
            });

            lastTiltX = (float) x / 8;
            lastTiltZ = (float) z / 8;
        }
//        Camera.orientation.rotateX((float) x / 64);
        directionalVector.set(x, 0, z).div(16);
    }

    public void updateState() {
        oscillation = (oscillation + .04) % (2 * Math.PI);
        // flap wings
        // refer to integral comment below
//        upperWing.orientation.angle = (float) oscillation;
//        Vector3fc parallelVec = new Vector3f(upperWing.orientation.y, upperWing.orientation.z, upperWing.orientation.w);
//        System.out.println(upperWing.orientation);
        upperWing.orientation.rotateX((float) Math.sin(oscillation) / 128);
//        upperWing.setRotationAngle((float) (Math.sin(oscillation) * Math.PI / 2), 1, 0, 0);
        lowerWing.orientation.rotateX((float) Math.cos(oscillation) / 128);

        // bounce model up and down
        forEach(g -> {
            Vector3f position = g.position;
            // since the integral of sin from 0 to 2pi is 0, adding sin(oscillation) in increments of dx
            // shouldn't actually affect the players overall position.
            position.add(0, (float) Math.sin(oscillation) / 128, 0);
            // also add directional input offset
            position.add(directionalVector);
//            g.orientation.rotateY(.02f);
        });
        Camera.position.add(directionalVector);
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

    public Vector3f getPosition() {
        return playerBody.position;
    }

    public Quaternionf getOrientation() {
        return playerBody.orientation;
    }

    public void setPosition(Vector3f position) {
        forEach(g -> g.position.set(position));
    }

    public void setOrientation(Quaternionf orientation) {
        forEach(g -> g.orientation.set(orientation));
    }
}
