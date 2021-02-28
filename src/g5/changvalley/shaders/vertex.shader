#version 330

layout (location=0) in vec3 position;

// vertex shader to project 3d space onto 2d space
void main()
{
    gl_Position = vec4(position, 1.0);
}