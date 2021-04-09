package g5.changvalley.render.mesh;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL33.*;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class VertexBufferObject {
    private final int vbo = glGenBuffers();

    public static void attachVbo(float[] data, int index) {
        new VertexBufferObject(data, index);
    }

    VertexBufferObject(float[] data, int index) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(data.length);
            buffer.put(data).flip();

            bind();
            setAttribute(buffer, index);
            VertexBufferObject.unbind();
        }
    }

    private void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
    }

    private static void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private void setAttribute(FloatBuffer buffer, int index) {
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(index, 3, GL_FLOAT, false, 0, 0);
    }
}
