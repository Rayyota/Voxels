package com.voxels.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

public class Scene {
    private final Cube[][][] matrixCubes;
    float cubeSize = 2f; // чтобы не было расстояний между кубами

    public Scene() {
        MeshBuilder meshBuilder = new MeshBuilder();
        VertexAttributes vertexAttributes = new VertexAttributes(
                new VertexAttribute(VertexAttributes.Usage.Position, 3, "a_Position"),
                new VertexAttribute(VertexAttributes.Usage.Normal, 3, "a_Normal"));
        meshBuilder.begin(vertexAttributes, GL20.GL_TRIANGLES);
        meshBuilder.box(2f, 2f, 2f);
        Mesh mesh = meshBuilder.end();

        matrixCubes = new Cube[2][2][2];
        for (int i = 0; i < matrixCubes.length; i++) {
            for (int j = 0; j < matrixCubes[i].length; j++) {
                for (int k = 0; k < matrixCubes[i][j].length; k++) {
                    Cube cube = new Cube(mesh);
                    matrixCubes[i][j][k] = cube;
                    cube.setPosition(i * cubeSize, j * cubeSize, k * cubeSize);
                }
            }
        }
    }

    public Cube[][][] getMatrixCubes() {
        return matrixCubes;
    }

    public void render() {
        Matrix4 translationMatrix = new Matrix4();
        ShaderProgram shader = new ShaderProgram(Gdx.files.internal("shaders/vertexShader.glsl").readString(),
                Gdx.files.internal("shaders/fragmentShader.glsl").readString());
        for (Cube[][] matrixCube : matrixCubes) {
            for (Cube[] value : matrixCube) {
                for (Cube c : value) {
                    translationMatrix.setToTranslation(c.getPosition());
                    shader.setUniformMatrix("model", translationMatrix);
                    Color color = c.getColor();
                    shader.setUniform4fv("color", new float[]
                            {color.r, color.g, color.b, color.a}, 0, 4);
                    c.getMesh().render(shader, GL20.GL_TRIANGLES);
                }
            }
        }
    }
}
