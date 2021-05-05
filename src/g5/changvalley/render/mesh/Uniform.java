package g5.changvalley.render.mesh;

import g5.changvalley.render.ShaderManager;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;


import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL33.*;

// variables with arbitrary types that are referencable in the GPU. ~~each type is wrapped in a uniformable class,
// please see render.mesh.uniforms for examples~~ (this is not true as of 4/29/2021, logic was moved to here for
// performance reasons).
public class Uniform {
    // this is probably not optimal and i should use an array with enum ordinals, BUT
    // i am incredibly lazy, forgive me reid sama
    private static final Map<String, Integer> uniforms = new HashMap<>();

    public static void makeUniform(String name, Object value) {
        int uid = glGetUniformLocation(ShaderManager.getPid(), name);
        // ~~this is kind of a pain. We need to design wrapper classes with updateUniform in order to get type specific
        // logic without incurring the cost of duplicating all the code with type changes (generics don't work, see
        // https://stackoverflow.com/questions/23722124/make-a-java-class-generic-but-only-for-two-or-three-types)
        // i've defined a "Uniformable" interface which all uniform wrapper classes will implement, but this still
        // incurs performance penalties due to the layers of indirection associated with singleton serving.
        // but whatcha gonna do ¯\_(ツ)_/¯~~ (this is not true as of 4/29/2021, logic was moved to here for
        // performance reasons)

        // UPDATE: instead of writing wrapper classes, ive caved and decided to use runtime checks ONLY for
        // makeUniform (it's only called once). it sucks but at least we won't get performance drawbacks for
        // updateUniform which gets called several times per render.
        uniforms.put(name, uid);
        if (value instanceof Matrix4f) {
            updateUniform(name, (Matrix4f) value);
        } else if (value instanceof Integer) {
            updateUniform(name, (Integer) value);
        } else if (value instanceof Vector4f) {
            updateUniform(name, (Vector4f) value);
        } else if (value instanceof Boolean) {
            updateUniform(name, (Boolean) value);
        } else if (value instanceof Float) {
            updateUniform(name, (Float) value);
        } else {
            throw new RuntimeException("weird uniform type what");
        }
    }

    public static void updateUniform(String name, int value) {
        glUniform1i(uniforms.get(name), value);
    }

    public static void updateUniform(String name, Matrix4f matrix) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            matrix.get(fb);
            // update matrix in gpu
            glUniformMatrix4fv(uniforms.get(name), false, fb);
        }
    }

    public static void updateUniform(String name, Vector4f vec) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(4);
            vec.get(fb);
            // update matrix in gpu
            glUniform4fv(uniforms.get(name), fb);
        }
    }

    public static void updateUniform(String name, boolean state) {
        glUniform1i(uniforms.get(name), state ? 1 : 0);
    }

    public static void updateUniform(String name, float value) {
        glUniform1f(uniforms.get(name), value);
    }
}
