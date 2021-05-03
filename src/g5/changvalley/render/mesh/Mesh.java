package g5.changvalley.render.mesh;

import com.mokiat.data.front.parser.*;
import g5.changvalley.Utils;
import org.joml.Vector4f;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;

// constructing a mesh should load the vertices into GPU memory. would be nice to have complete wrappers around opengl
// operations like binding/loading but not necessary.

// AYO ok so i have brain damage and apparently there's this thing called DrawElements in OpenGL??? its like
// instead of repeating vertices u give each vertex an index and specify the index instead of vertex. tbh sounds cool
// but im like stupid so imma stick with my bad inefficient way of repeating vertices until further notice
// sorry reid sama
// UPDATE i tried to learn how to do this for fun and tbh kinda works hopefully??
public class Mesh {
    private final static int POSITION_INDEX = 0;
    private final static int TEXTURE_INDEX = 1;
    private final static int NORMAL_INDEX = 2;
    private final static Texture DEFAULT_TEXTURE = new Texture("white.png");

    // vertex array object id
    private final int vao = glGenVertexArrays();
    private int vertexCount = 0;
    // will act like a unit vector and wont change the texture
    private final Vector4f color = new Vector4f(1, 1, 1, 1);
    private Texture texture = DEFAULT_TEXTURE;

    public Mesh(float[] vertices, float[] normals, int[] indexes) {
        vertexCount = indexes.length;
        bindVertex();

        // attach vertex vbo to this vao
        VertexBufferObject.attachAttributeVbo(vertices, POSITION_INDEX, 3);
        VertexBufferObject.attachAttributeVbo(vertices, TEXTURE_INDEX, 2);
        // attach texture index vbo to this vao
        VertexBufferObject.attachAttributeVbo(normals, NORMAL_INDEX, 3);
        VertexBufferObject.attachIndexVbo(indexes);

        Mesh.unbindVertex();
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
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
        bindTexture();
        Mesh.enableAttributes();

        draw();

        Mesh.disableAttributes();
        Mesh.unbindVertex();
    }

    public static void enableAttributes() {
        glEnableVertexAttribArray(Mesh.POSITION_INDEX);
        glEnableVertexAttribArray(Mesh.TEXTURE_INDEX);
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

    public static Mesh fromFile(String filename) {
        IOBJParser parser = new OBJParser();
        OBJModel model;
        try {
            model = parser.parse(new ByteArrayInputStream(Utils.loadResource(filename).getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("failed to get resource \"" + filename + "\"");
        }

        List<OBJVertex> verticesList = model.getVertices();
        float[] vertices = new float[verticesList.size() * 3];
        for (int i = 0; i < verticesList.size(); i++) {
            OBJVertex vertex = verticesList.get(i);
            vertices[i * 3] = vertex.x;
            vertices[i * 3 + 1] = vertex.y;
            vertices[i * 3 + 2] = vertex.z;
        }

        List<OBJNormal> normalsList = model.getNormals();
        float[] normals = new float[normalsList.size() * 3];
        for (int i = 0; i < normalsList.size(); i++) {
            OBJNormal normal = normalsList.get(i);
            normals[i * 3] = normal.x;
            normals[i * 3 + 1] = normal.y;
            normals[i * 3 + 2] = normal.z;
        }

        List<Integer> indicesList = new ArrayList<Integer>();
        OBJMesh readMesh = model.getObjects().get(0).getMeshes().get(0);
        for (OBJFace face: readMesh.getFaces()) {
            for (OBJDataReference reference: face.getReferences()) {
                indicesList.add(reference.vertexIndex);
            }
        }
        // lol ty stack overflow
        int[] indices = indicesList.stream().mapToInt(i -> i).toArray();

//        List<OBJTexCoord> textList = model.getTexCoords();
//        float[] textureCoords = new float[textList.size() * 2];
//        for (int i = 0; i < textList.size(); i++) {
//            OBJTexCoord textureCoordinate = textList.get(i);
//            textureCoords[i * 2] = textureCoordinate.u;
//            textureCoords[i * 2 + 1] = textureCoordinate.v;
//        }

        return new Mesh(vertices, normals, indices);
    }
}
