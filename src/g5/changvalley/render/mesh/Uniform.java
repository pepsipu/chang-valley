package g5.changvalley.render.mesh;

import g5.changvalley.render.ShaderManager;
import g5.changvalley.render.mesh.uniforms.Uniformable;


import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL33.*;

// variables with arbitrary types that are referencable in the GPU. each type is wrapped in a uniformable class,
// please see render.mesh.uniforms for examples.
public class Uniform {
    // this is probably not optimal and i should use an array with enum ordinals, BUT
    // i am incredibly lazy, forgive me reid sama
    private static final Map<String, Integer> uniforms = new HashMap<>();

    public static void makeUniform(String name, Uniformable value) {
        int uid = glGetUniformLocation(ShaderManager.getPid(), name);
        // this is kind of a pain. We need to design wrapper classes with this method in order to get type specific
        // logic without incurring the cost of duplicating all the code with type changes (generics don't work, see
        // https://stackoverflow.com/questions/23722124/make-a-java-class-generic-but-only-for-two-or-three-types)
        // i've defined a "Uniformable" interface which all uniform wrapper classes will implement, but this still
        // incurs performance penalties due to the layers of indirection associated with singleton serving.
        // but whatcha gonna do ¯\_(ツ)_/¯
        value.updateUniformFromUid(uid);
        uniforms.put(name, uid);
    }

    public static void updateUniform(String name, Uniformable value) {
        value.updateUniformFromUid(uniforms.get(name));
    }
}
