package com.lecz.clubdelosvencedores.Game;

/**
 * Created by Arte-2 on 30/09/2014.
 */
public class Block {
    private boolean isTarget;
    private int color;
    private int image;
    private boolean clicked;

    public Block() {}

    public Block(boolean isTarget, int color, int image) {
        this.isTarget = isTarget;
        this.color = color;
        this.image = image;
        this.clicked = false;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isClicked() {
        return this.clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public boolean isTarget() {
        return this.isTarget;
    }

    public void setTarget(boolean isTarget) {
        this.isTarget = isTarget;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
