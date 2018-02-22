#version 330

in vec3 p;

out vec4 fragColor;

void main() {
	fragColor = vec4(p, 1.0);
}