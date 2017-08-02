package com.colisa.maputo.entities;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
@SuppressWarnings("WeakerAccess")
public abstract class AbstractEntity {
    public Vector2 position;
    public Vector2 dimension;
    Vector2 origin;
    Vector2 scale;
    float rotation;
    Animation animation;
    Vector2 velocity;
    Vector2 terminalVelocity;
    Vector2 acceleration;
    public Rectangle bounds;
    Vector2 friction;

    AbstractEntity() {
        position = new Vector2();
        dimension = new Vector2(1, 1);
        origin = new Vector2();
        scale = new Vector2(1, 1);
        rotation = 0;

        animation = null;
        velocity = new Vector2();
        terminalVelocity = new Vector2(1, 1);
        acceleration = new Vector2();
        friction = new Vector2();
        bounds = new Rectangle();
    }

    public abstract void render(SpriteBatch batch);

    public void update(float deltaTime) {
        updateX(deltaTime);
        updateY(deltaTime);

        position.x += velocity.x * deltaTime;
        position.y += velocity.y * deltaTime;
    }

    private void updateX(float delta) {
        if (velocity.x != 0){
            // apply friction
            if (velocity.x > 0)
                velocity.x = Math.max(velocity.x - friction.x * delta , 0);
            else
                velocity.x = Math.min(velocity.x + friction.x * delta, 0);
        }
        // apply acceleration
        velocity.x += acceleration.x * delta;
        velocity.x = MathUtils.clamp(velocity.x, -terminalVelocity.x, terminalVelocity.x);
    }

    private void updateY(float delta) {
        if (velocity.y != 0) {
            // apply friction
            if (velocity.y > 0)
                velocity.y = Math.max(velocity.y - friction.y * delta, 0);
            else
                velocity.y = Math.min(velocity.y + friction.y * delta, 0);
        }
        // apply acceleration
        velocity.y += acceleration.y * delta;
        velocity.y = MathUtils.clamp(velocity.y, -terminalVelocity.y, terminalVelocity.y);
    }

}
