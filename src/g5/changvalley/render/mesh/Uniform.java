package g5.changvalley.render.mesh;

import g5.changvalley.render.ShaderManager;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL33.*;

public class Uniform {
    // this is probably not optimal and i should use an array with enum ordinals, BUT
    // i am incredibly lazy, forgive me reid sama
    private static final Map<String, Integer> uniforms = new HashMap<>();

    public static void makeUniform(String name, Matrix4f matrix) {
        int uid = glGetUniformLocation(ShaderManager.getPid(), name);
        updateUniformFromUid(uid, matrix);
        uniforms.put(name, uid);
    }

    public static void updateUniform(String name, Matrix4f matrix) {
        updateUniformFromUid(uniforms.get(name), matrix);
    }

    // stack buffer should work just fine here cuz 16 bytes aint much??
    private static void updateUniformFromUid(int uid, Matrix4f matrix) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            matrix.get(fb);
            // update matrix in gpu
            glUniformMatrix4fv(uid, false, fb);
        }
    }
}
