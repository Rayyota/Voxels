package com.voxels.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Vector3;

public class Cube {
    private final Mesh mesh;
    private final Vector3 position;
    private Color color;

    public Cube(Mesh mesh) {
        this.mesh = mesh;
        position = new Vector3(0f, 0f, 0f);
        color = new Color(1f, 1f, 1f, 0.5f);
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public void setColorPosition(Vector3 position, Color color) {
        if (this.position.equals(position)) {
            this.color = color;
        }
    }
}

