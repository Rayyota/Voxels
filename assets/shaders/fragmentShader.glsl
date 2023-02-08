#version 300 es

#ifdef GL_ES
precision mediump float;
#endif

in vec3 varColor;

out vec4 outColor;

void main() {
    outColor = vec4(varColor, 1.0);
}