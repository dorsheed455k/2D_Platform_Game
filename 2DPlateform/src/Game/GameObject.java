package Game;

import java.awt.*;

public abstract class GameObject {

    protected double x, y;
    private double xVel = 0, yVel = 0;

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public abstract void tick();
    public abstract void render(Graphics g);

    public abstract void setX(double x);
    public abstract void setY(double y);
    public abstract double getX();
    public abstract double getY();


    public abstract void setVelX(double xVel);
    public abstract void setVelY(double yVel);
    public abstract double getVelX();
    public abstract double getVelY();

}
