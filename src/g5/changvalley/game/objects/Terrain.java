package g5.changvalley.game.objects;

import g5.changvalley.engine.GameObject;
import g5.changvalley.render.mesh.Mesh;

public class Terrain extends GameObject {
    public static final int TERRAIN_SIZE = 256;
    private static final float TERRAIN_VOLATILITY = 0.007f;

    private final float[][] terrainOscillations;
    public Terrain(float[][] terrainOscillations) {
        super(Terrain.generateMesh(terrainOscillations));
        this.terrainOscillations = terrainOscillations;
    }

    // temporary
    private static void randomInitializeTerrain(float[][] terrainOscillations) {
        for (int r = 0; r < TERRAIN_SIZE; r++) {
            for (int c = 0; c < TERRAIN_SIZE; c++) {
                terrainOscillations[r][c] = (float) (Math.random() * Math.PI * 2);
            }
        }
    }

    private static Mesh generateMesh(float[][] terrainOscillations) {
        // every point on the terrain needs an xyz component
        float[] vertices = new float[3 * TERRAIN_SIZE * TERRAIN_SIZE];
        // 3 indices define a triangle, 2 triangles define a square, TERRAIN_SIZE ^ 2 squares spans the terrain's area
        int[] indices = new int[6 * TERRAIN_SIZE * TERRAIN_SIZE];
        // vertices will be laid out in the arr like:
        // 0, 1, 2, ... TERRAIN_SIZE - 1
        // TERRAIN_SIZE, TERRAIN_SIZE + 1 .... TERRAIN_SIZE * 2 - 1

        // any given square starting at index k can be described neatly as (k, k + 1, k + TERRAIN_SIZE, k + TERRAIN_SIZE + 1)
        // the two triangles which compose the square can be written an (k, k + 1, k + TERRAIN_SIZE) and
        // (k + TERRAIN_SIZE + 1, k + 1, k + TERRAIN_SIZE)
        for (int r = 0; r < TERRAIN_SIZE; r++) {
            for (int c = 0; c < TERRAIN_SIZE; c++) {
                // 2d index to 1d with a stride of 3
                int vertexIdx = 3 * (r * TERRAIN_SIZE + c);
                vertices[vertexIdx] = r;
                // y component varies
                vertices[vertexIdx + 1] = (float) Math.sin(terrainOscillations[r][c]);
                vertices[vertexIdx + 2] = c;
            }
        }

        // now we need to generate indices
        // to do this i think uhhhh
        // iterate over every square that must be made, which is
        for (int i = 0; i < TERRAIN_SIZE * TERRAIN_SIZE; i++) {
            int indexIdx = i * 6;
            // first triangle, connect current index to adjacent and to top
            indices[indexIdx] = i;
            indices[indexIdx + 1] = i + 1;
            indices[indexIdx + 2] = i + TERRAIN_SIZE + 1;
            // same thing, the reference point now being the top point
            indices[indexIdx + 3] = i + TERRAIN_SIZE + 1;
            indices[indexIdx + 4] = i + TERRAIN_SIZE + 2;
            indices[indexIdx + 5] = i + 1;
        }
        return new Mesh(vertices, vertices, indices, vertices);
    }


    public static Terrain makeTerrain() {
        float[][] terrainOscillations = new float[TERRAIN_SIZE][TERRAIN_SIZE];
        // temp; fetch this from server
        Terrain.randomInitializeTerrain(terrainOscillations);
        return new Terrain(terrainOscillations);
    }

    public void update() {
        for (int r = 0; r < TERRAIN_SIZE; r++) {
            for (int c = 0; c < TERRAIN_SIZE; c++) {
                terrainOscillations[r][c] = (float) ((terrainOscillations[r][c] + TERRAIN_VOLATILITY) % (2 * Math.PI));
            }
        }
    }

    public void regenerate() {
        // memory leek if no dealloc
        getMesh().destruct();
        setMesh(Terrain.generateMesh(terrainOscillations));
        getMesh().setColor(0, (float) (.7 + Math.sin(terrainOscillations[0][0]) / 8), 0f, 1);
    }
}
