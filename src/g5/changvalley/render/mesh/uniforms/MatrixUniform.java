package g5.changvalley.render.mesh.uniforms;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL33.glUniformMatrix4fv;

// !!! CRINGE ALERT !!!
// i dont know wtf im doing please help
// !!! CRINGE ALERT !!!
public class MatrixUniform implements Uniformable {
    private Matrix4f matrix = null;
    private static final MatrixUniform singleton = new MatrixUniform();

    public static MatrixUniform from(Matrix4f matrix) {
        MatrixUniform.singleton.set(matrix);
        return MatrixUniform.singleton;
    }

    public void set(Matrix4f matrix) {
        this.matrix = matrix;
    }

    // stack buffer should work just fine here cuz 16 bytes aint much??
    public void updateUniformFromUid(int uid) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            this.matrix.get(fb);
            // update matrix in gpu
            glUniformMatrix4fv(uid, false, fb);
        }
    }
}
