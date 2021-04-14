package g5.changvalley.render.mesh;

import java.nio.*;

import static org.lwjgl.opengl.GL33.*;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class Texture {
    private final int tid = glGenTextures();

    public Texture(String filename) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer widthPtr = stack.mallocInt(1);
            IntBuffer heightPtr = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            ByteBuffer png = STBImage.stbi_load(filename, widthPtr, heightPtr, channels, 4);
            if (png == null) {
                throw new Exception("cant find " + filename);
            }

            glBindTexture(GL_TEXTURE_2D, tid);
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, widthPtr.get(), heightPtr.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, png);
            glGenerateMipmap(GL_TEXTURE_2D);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            STBImage.stbi_image_free(png);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTid() {
        return tid;
    }
}
