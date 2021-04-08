package g5.changvalley.render.mesh;

import g5.changvalley.render.Renderer;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL33.*;

// constructing a mesh should load the vertices into GPU memory. would be nice to have complete wrappers around opengl
// operations like binding/loading but not necessary.

// AYO ok so i have brain damage and apparently there's this thing called DrawElements in OpenGL??? its like
// instead of repeating vertices u give each vertex an index and specify the index instead of vertex. tbh sounds cool
// but im like stupid so imma stick with my bad inefficient way of repeating vertices until further notice
// sorry reid sama
public class Mesh {
    // vertex array object id
    private final int vao = glGenVertexArrays();
    private final static int POSITION_INDEX = 0;
    private final static int COLOR_INDEX = 1;
    private final int vertexCount;

    public Mesh(float[] vertices, float[] colors) {
        vertexCount = vertices.length / 3;

        bindVertex();

        // attach vertex vbo to this vao
        VertexBufferObject.attachVbo(vertices, POSITION_INDEX);
        // attach color vbo to this vao
        VertexBufferObject.attachVbo(colors, COLOR_INDEX);

        Mesh.unbindVertex();
    }

    public void bindVertex() {
        glBindVertexArray(vao);
    }

    public static void unbindVertex() {
        glBindVertexArray(0);
    }

    public void draw() {
        glDrawArrays(GL_TRIANGLES, 0, vertexCount);
    }

    public static void enableAttributes() {
        glEnableVertexAttribArray(Mesh.POSITION_INDEX);
        glEnableVertexAttribArray(Mesh.COLOR_INDEX);
    }

    public static void disableAttributes() {
        glDisableVertexAttribArray(Mesh.POSITION_INDEX);
        glDisableVertexAttribArray(Mesh.COLOR_INDEX);
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
