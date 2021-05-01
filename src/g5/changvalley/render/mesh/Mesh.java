package g5.changvalley.render.mesh;

import org.joml.Vector4f;

import static org.lwjgl.opengl.GL33.*;

// constructing a mesh should load the vertices into GPU memory. would be nice to have complete wrappers around opengl
// operations like binding/loading but not necessary.

// AYO ok so i have brain damage and apparently there's this thing called DrawElements in OpenGL??? its like
// instead of repeating vertices u give each vertex an index and specify the index instead of vertex. tbh sounds cool
// but im like stupid so imma stick with my bad inefficient way of repeating vertices until further notice
// sorry reid sama
// UPDATE i tried to learn how to do this for fun and tbh kinda works hopefully??
public class Mesh {
    public final static int POSITION_INDEX = 0;
    public final static int TEXTURE_INDEX = 1;
    public final static int NORMAL_INDEX = 2;

    // vertex array object id
    private final int vao = glGenVertexArrays();
    private final int vertexCount;
    // will act like a unit vector and wont change the texture
    private final Vector4f color = new Vector4f(1, 1, 1, 1);
    private Texture texture = null;

    public Mesh(float[] vertices, float[] normals, int[] indexes) {
        vertexCount = indexes.length;
        bindVertex();

        // attach vertex vbo to this vao
        VertexBufferObject.attachAttributeVbo(vertices, POSITION_INDEX, 3);
        // attach texture index vbo to this vao
        VertexBufferObject.attachAttributeVbo(normals, NORMAL_INDEX, 3);
        VertexBufferObject.attachIndexVbo(indexes);

        Mesh.unbindVertex();
    }

    public void setTexture(Texture texture, float[] textureCoordinates) {
        this.texture = texture;
        bindVertex();
        VertexBufferObject.attachAttributeVbo(textureCoordinates, TEXTURE_INDEX, 2);
        Mesh.unbindVertex();
    }

    public void setColor(float r, float g, float b, float a) {
        color.set(r, g, b, a);
    }

    public void bindVertex() {
        glBindVertexArray(vao);
    }

    public static void unbindVertex() {
        glBindVertexArray(0);
    }

    public void bindTexture() {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture.getTid());
    }

    public void draw() {
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
    }

    public void render() {
        bindVertex();
        if (texture != null) bindTexture();
        enableAttributes();

        draw();

        Mesh.disableAttributes();
        Mesh.unbindVertex();
    }

    public void enableAttributes() {
        glEnableVertexAttribArray(Mesh.POSITION_INDEX);
        if (texture != null) glEnableVertexAttribArray(Mesh.TEXTURE_INDEX);
        glEnableVertexAttribArray(Mesh.NORMAL_INDEX);
    }

    public static void disableAttributes() {
        glDisableVertexAttribArray(Mesh.POSITION_INDEX);
        glDisableVertexAttribArray(Mesh.TEXTURE_INDEX);
        glDisableVertexAttribArray(Mesh.NORMAL_INDEX);
    }

    public Vector4f getColor() {
        return color;
    }

//    public static Mesh fromFile(String filename) {
//        IOBJParser parser = new OBJParser();
//        OBJModel model;
//        try {
//            model = parser.parse(new ByteArrayInputStream(Utils.loadResource(filename).getBytes()));
//        } catch (Exception e) {
//            throw new RuntimeException("failed to get resource");
//        }
//    }
}
