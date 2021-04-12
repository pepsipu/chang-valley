#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec3 color;

uniform mat4 projectionMatrix;
uniform mat4 worldMatrix;

out vec3 exColour;
// vertex shader to project 3d space onto 2d space
void main()
{
    gl_Position = projectionMatrix * worldMatrix * vec4(position, 1.0);
    exColour = color;
}