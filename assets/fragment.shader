#version 330

in vec2 outTextureIndex;
out vec4 fragColor;

uniform sampler2D textureSampler;
uniform vec4 color;

// fragment shader for ~colors~
void main()
{
    // this is intentional! this causes undefined behaviour due to an unintialized fragment shade but thats ok!
    // it gives a retro vibe i haven't been able to reproduce otherwise.
    if (outTextureIndex.x != -1) {
        fragColor = texture(textureSampler, outTextureIndex) * color;
    }
}
