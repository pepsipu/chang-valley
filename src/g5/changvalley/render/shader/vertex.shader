#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec3 color;

uniform mat4 projectionMatrix;

out vec3 exColour;
// vertex shader to project 3d space onto 2d space
void main()
{
    gl_Position = projectionMatrix * vec4(position, 1.0);
    exColour = color;
}