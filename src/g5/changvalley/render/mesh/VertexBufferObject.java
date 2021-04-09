package g5.changvalley.render.mesh;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;

import org.lwjgl.system.MemoryStack;

public class VertexBufferObject {
    public static void attachAttributeVbo(float[] data, int index) {
        int vbo = glGenBuffers();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(data.length);
            buffer.put(data).flip();

            VertexBufferObject.bind(GL_ARRAY_BUFFER, vbo);
            VertexBufferObject.setAttribute(buffer, index);
        }
        VertexBufferObject.unbind();
    }

    public static void attachIndexVbo(int[] data) {
        int vbo = glGenBuffers();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer buffer = stack.mallocInt(data.length);
            buffer.put(data).flip();

            VertexBufferObject.bind(GL_ELEMENT_ARRAY_BUFFER, vbo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        }
        VertexBufferObject.unbind();
    }

    private static void bind(int type, int vbo) {
        glBindBuffer(type, vbo);
    }

    private static void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private static void setAttribute(FloatBuffer buffer, int index) {
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(index, 3, GL_FLOAT, false, 0, 0);
    }
}
