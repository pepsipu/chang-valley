package g5.changvalley.render.mesh.uniforms;

import static org.lwjgl.opengl.GL33.*;

public class IntUniform implements Uniformable {
    private int value = 0;
    private static final IntUniform singleton = new IntUniform();

    public static IntUniform from(int value) {
        IntUniform.singleton.set(value);
        return IntUniform.singleton;
    }

    public void set(int value) {
        this.value = value;
    }

    public void updateUniformFromUid(int uid) {
        glUniform1i(uid, value);
    }
}
