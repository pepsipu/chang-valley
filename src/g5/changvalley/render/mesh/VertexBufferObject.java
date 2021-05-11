package g5.changvalley.render.mesh;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class VertexBufferObject {
    public static int attachAttributeVbo(float[] data, int index, int size) {
        int vbo = glGenBuffers();
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data).flip();

        VertexBufferObject.bind(GL_ARRAY_BUFFER, vbo);
        VertexBufferObject.setAttribute(buffer, index, size);
        VertexBufferObject.unbind();

        MemoryUtil.memFree(buffer);
        return vbo;
    }

    public static int attachIndexVbo(int[] data) {
        int vbo = glGenBuffers();
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data).flip();

        VertexBufferObject.bind(GL_ELEMENT_ARRAY_BUFFER, vbo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        VertexBufferObject.unbind();

        MemoryUtil.memFree(buffer);
        return vbo;
    }

    private static void bind(int type, int vbo) {
        glBindBuffer(type, vbo);
    }

    public static void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private static void setAttribute(FloatBuffer buffer, int index, int size) {
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
    }

    public static void delete(int vbo) {
        glDeleteBuffers(vbo);
    }
}
