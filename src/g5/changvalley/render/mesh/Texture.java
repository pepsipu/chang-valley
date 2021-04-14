package g5.changvalley.render.mesh;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.IOException;
import java.nio.ByteBuffer;
import static org.lwjgl.opengl.GL33.*;


public class Texture {
    private final int tid = glGenTextures();

    public Texture(String filename) {
        try {
            PNGDecoder decoder = new PNGDecoder(Texture.class.getResourceAsStream(filename));
            // bytebuffer alloc was being suuuper slow for some reason?? i switched it to allocateDirect, see
            // https://stackoverflow.com/questions/3651737/why-the-odd-performance-curve-differential-between-bytebuffer-allocate-and-byt
            ByteBuffer data = ByteBuffer.allocateDirect(decoder.getWidth() * decoder.getHeight() * 4);
            decoder.decode(data, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            data.flip();

            glBindTexture(GL_TEXTURE_2D, tid);
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
            glGenerateMipmap(GL_TEXTURE_2D);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        } catch (IOException ignored) {}
    }

    public int getTid() {
        return tid;
    }
}
