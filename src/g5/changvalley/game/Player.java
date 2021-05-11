package g5.changvalley.game;

import g5.changvalley.engine.GameObject;
import g5.changvalley.render.Camera;
import g5.changvalley.render.Renderer;
import g5.changvalley.render.mesh.Mesh;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;

public class Player extends GameObjectContainer {
    private final GameObject upperWing;
    private final GameObject lowerWing;
    private final GameObject playerBody;

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



    public void updateState() {
        oscillation = (oscillation + .04) % (2 * Math.PI);
        // flap wings
        upperWing.setRotation((float) Math.sin(oscillation) / 4, 0, 0);
        lowerWing.setRotation((float) Math.cos(oscillation) / 4, 0, 0);
        // bounce model up and down
        on((g) -> {
            Vector3f position = g.getPosition();
            // since the integral of sin from 0 to 2pi is 0, adding sin(oscillation) in increments of dx
            // shouldn't actually affect the players overall position.
            position.add(0, (float) Math.sin(oscillation) / 256, 0);
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
//        on(gameObject -> gameObject.getRotation().add(0, 0, 0));
//        on(gameObject -> gameObject.getPosition().add(0, 0, 1));
//        playerBody.getPosition().add(0.01f, 0, 0);
//        Camera.lookAt(playerBody);
//        on(gameObject -> gameObject.setRotation(0, (float) Math.sin(oscillation), 0));
    }
}
