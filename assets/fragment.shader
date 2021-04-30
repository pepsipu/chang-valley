#version 330

in vec2 outTextureIndex;
out vec4 fragColor;

uniform sampler2D textureSampler;
uniform vec4 color;

// fragment shader for ~colors~
void main()
{
    fragColor = texture(textureSampler, outTextureIndex) * color;
}
