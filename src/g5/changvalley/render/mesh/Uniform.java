package g5.changvalley.render.mesh;

import g5.changvalley.render.ShaderManager;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class Uniform {
    private static final Map<String, Integer> uniforms = new HashMap<>();

    public static void makeUniform(String name, Matrix4f matrix) {
        int uid = glGetUniformLocation(ShaderManager.getPid(), name);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            matrix.get(fb);
            glUniformMatrix4fv(uid, false, fb);
        }
        uniforms.put(name, uid);
    }
}
