package g5.changvalley.engine;

import g5.changvalley.render.mesh.Mesh;

public class Particle extends GameObject {
    private static final float[] vertices = new float[]{
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
    };
    private static final int[] indices = new int[]{0, 1, 2};
    private static final Mesh mesh = new Mesh(vertices, vertices, indices, vertices);

    public Particle() {
        super(mesh);
        mesh.setDithering(false);
        scale.set(.2f);
    }
}
