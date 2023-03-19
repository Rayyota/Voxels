#ifdef GL_ES
precision mediump float;
#endif

uniform vec4 color;

varying vec3 varColor;

void main() {
    gl_FragColor = vec4(varColor * color.rgb, color.a);
}