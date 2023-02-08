attribute vec3 a_Position;
attribute vec3 a_Normal;

uniform mat4 viewProjection;
uniform mat4 model;

uniform vec3 lightDirection;

varying vec3 varColor;

void main() {
    float dotValue = max(dot(a_Normal, lightDirection), 0.2);
    float dotValue1 = max(dot(a_Normal, lightDirection * (-1)), 0.2);

    varColor = vec3(dotValue + dotValue1 * 0.5);
    gl_Position = viewProjection * model * vec4(a_Position, 1.0);
}