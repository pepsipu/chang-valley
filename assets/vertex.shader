#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 textureIndex;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

out vec2 outTextureIndex;
// vertex shader to project 3d space onto 2d space
void main()
{
    gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 1.0);
    outTextureIndex = textureIndex;
}