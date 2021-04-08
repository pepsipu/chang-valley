package g5.changvalley.render.shader;

import static org.lwjgl.opengl.GL33.*;

public class Shader {
    private final int id;
    private final int pid;

    public Shader(String code, int type, int pid) {
        this.pid = pid;
        if ((id = glCreateShader(type)) == 0) {
            throw new RuntimeException("couldnt create shader (" + type + ")");
        }
        glShaderSource(id, code);
        glCompileShader(id);
        if (glGetShaderi(id, GL_COMPILE_STATUS) == 0) {
            throw new RuntimeException("couldnt compile shader code: " + glGetShaderInfoLog(id, 1024));
        }
        glAttachShader(pid, id);
    }

    public void detach() {
        glDetachShader(pid, id);
    }
}
