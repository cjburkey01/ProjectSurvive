#version 330

layout (location = 0) in vec3 position;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

out vec3 p;

void main() {
	p = position;
	gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 1.0);
}