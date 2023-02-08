package com.voxels.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class Voxels extends ApplicationAdapter {
    private List<Cube> cubes;
    private ShaderProgram shader;
    private Camera camera;
    private FirstPersonCameraController controller;
    private Vector3 lightDirection;
    private Matrix4 translationMatrix;

    @Override
    public void create() {
        super.create();
        translationMatrix = new Matrix4();

        MeshBuilder meshBuilder = new MeshBuilder();
        VertexAttributes vertexAttributes = new VertexAttributes(
                new VertexAttribute(Usage.Position, 3, "a_Position"),
                new VertexAttribute(Usage.Normal, 3, "a_Normal"));
        meshBuilder.begin(vertexAttributes, GL20.GL_TRIANGLES);
        meshBuilder.box(4f, 4f, 4f);
        Mesh mesh = meshBuilder.end();

        cubes = new ArrayList<>();
        Cube cube = new Cube(mesh);
        Cube cube1 = new Cube(mesh);
        cubes.add(cube);
        cubes.add(cube1);
        cube.setPosition(0f, 0f, 0f);
        cube1.setPosition(-5f, 0f, -5f);
//        for (int i = 0; i < 10; i++) {
//            Cube cube1 = new Cube(mesh);
//            cube1.setPosition(-MathUtils.random(30f), 0f, -MathUtils.random(30f));
//            cubes.add(cube1);
//        }

        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(10f, 10f, 10f);
        camera.lookAt(0f, 0f, 0f);
        camera.near = 1f;
        camera.far = 30f;
        camera.update();

        shader = new ShaderProgram(Gdx.files.internal("shaders/vertexShader.glsl").readString(),
                Gdx.files.internal("shaders/fragmentShader.glsl").readString());

        controller = new FirstPersonCameraController(camera);
        Gdx.input.setInputProcessor(controller);

        lightDirection = new Vector3(13f, 16f, 10f).nor();
    }

    @Override
    public void render() {
        super.render();

        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

        Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        Gdx.gl20.glClearColor(128f / 255f, 1f / 255f, 83f / 255f, 1f);

        camera.update(true);
        controller.update(Gdx.graphics.getDeltaTime());

        shader.begin();
        shader.setUniformMatrix("viewProjection", camera.combined);
        shader.setUniformf("lightDirection", lightDirection);

        for (Cube c : cubes) {
            translationMatrix.setToTranslation(c.getPosition());
            shader.setUniformMatrix("model", translationMatrix);
            c.getMesh().render(shader, GL20.GL_TRIANGLES);
        }

        shader.end();
    }

    @Override
    public void dispose() {

    }
}
