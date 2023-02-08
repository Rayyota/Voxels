package com.voxels.game;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Vector3;

public class Cube {
    private final Mesh mesh;
    private final Vector3 position;

    public Cube(Mesh mesh) {
        this.mesh = mesh;
        position = new Vector3(0f, 0f, 0f);
    }

    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
