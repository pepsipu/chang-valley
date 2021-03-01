package g5.changvalley;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class Renderer {
    private static final float[] vertices = new float[]{
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    };

    private static final FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);

    public static void construct() {
        int vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        verticesBuffer.put(vertices).flip();

        int vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);
    }

    public static void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void drawTriangle() {
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }

    public static void rotateTriangle() {
        vertices[0] = (float) (vertices[0] * Math.cos(Math.PI / 60) - vertices[1] * Math.sin(Math.PI / 60));
        vertices[1] = (float) (vertices[0] * Math.sin(Math.PI / 60) + vertices[1] * Math.cos(Math.PI / 60));

        vertices[3] = (float) (vertices[3] * Math.cos(Math.PI / 60) - vertices[4] * Math.sin(Math.PI / 60));
        vertices[4] = (float) (vertices[3] * Math.sin(Math.PI / 60) + vertices[4] * Math.cos(Math.PI / 60));

        vertices[6] = (float) (vertices[6] * Math.cos(Math.PI / 60) - vertices[7] * Math.sin(Math.PI / 60));
        vertices[7] = (float) (vertices[6] * Math.sin(Math.PI / 60) + vertices[7] * Math.cos(Math.PI / 60));

        verticesBuffer.clear().put(vertices, 0, 9).flip();
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
    }
}
