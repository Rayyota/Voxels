package com.voxels.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.Color;

public class Voxels extends ApplicationAdapter {
    private Cube[][][] matrixCubes;
    private ShaderProgram shader;
    private Camera camera;
    private FirstPersonCameraController controller;
    private Vector3 lightDirection;
    float cubeSize = 2f; // чтобы не было расстояний между кубами
    private Scene scene;

    @Override
    public void create() {
        super.create();

        scene = new Scene();
        matrixCubes = scene.getMatrixCubes();

        camera = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(3f, 3f, 3f);
        camera.lookAt(0, 2f, 0);
        camera.near = 0.1f;
        camera.far = 100f;
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

        // прозрачность
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Gdx.gl20.glClearColor(127f / 255f, 233f / 255f, 235f / 255f, 1f);

        camera.update(true);
        controller.update(Gdx.graphics.getDeltaTime());

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector3 position = new Vector3();
            Color color = new Color(20f / 255f, 166f / 255f, 39f / 255f, 1f);
            camera.unproject(position.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            int x = (int) (position.x / cubeSize);
            int y = (int) (position.y / cubeSize);
            int z = (int) (position.z / cubeSize);
            if (x >= 0 && x < matrixCubes.length && y >= 0 && y < matrixCubes[0].length && z >= 0 && z < matrixCubes[0][0].length) {
                Cube cube = matrixCubes[x][y][z];
                cube.setColor(color);
            }
        }

        shader.begin();

        shader.setUniformMatrix("viewProjection", camera.combined);
        shader.setUniformf("lightDirection", lightDirection);

        scene.render();

        shader.end();
    }

    @Override
    public void dispose() {

    }
}
