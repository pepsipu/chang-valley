package g5.changvalley.render;

import g5.changvalley.Utils;
import g5.changvalley.render.shader.Shader;

import static org.lwjgl.opengl.GL33.*;

// holy this was actually such a pain in the ass idk anything about shaders
// mr reid please give me an A+ :sob:
public class ShaderManager {
    private static int pid;
    private static Shader vertexShader;
    private static Shader fragmentShader;

    public static void construct() {
        if ((pid = glCreateProgram()) == 0) {
            throw new RuntimeException("could not init shader program");
        }
        vertexShader = new Shader(Utils.loadResource("./render/shader/vertex.shader"), GL_VERTEX_SHADER, pid);
        fragmentShader = new Shader(Utils.loadResource("./render/shader/fragment.shader"), GL_FRAGMENT_SHADER, pid);

        link();
        glUseProgram(pid);
    }

    public static void link() {
        glLinkProgram(pid);
        if (glGetProgrami(pid, GL_LINK_STATUS) == 0) {
            throw new RuntimeException("linker error: " + glGetProgramInfoLog(pid, 1024));
        }

        // we can throw out our shaders after we've linked them i think lol
        vertexShader.detach();
        fragmentShader.detach();

        // run validation checks to catch stupid mistakes, throw out in production
        glValidateProgram(pid);
        if (glGetProgrami(pid, GL_VALIDATE_STATUS) == 0) {
            System.err.println("shader code validation: " + glGetProgramInfoLog(pid, 1024));
        }
    }

    public static int getPid() {
        return pid;
    }

    public static void destruct() {
        glUseProgram(0);
        glDeleteProgram(pid);
    }

}

