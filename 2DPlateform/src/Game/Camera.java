package Game;

import Maps.Terrain;

public class Camera {

    private float cx, cy;
    private int WIDTH, HEIGHT;
    public Camera(float cx, float cy, int WIDTH, int HEIGHT) {
        this.cx = cx;
        this.cy = cy;
        this.WIDTH = WIDTH+150;
        this.HEIGHT = HEIGHT;
    }

    public float getX() {
        return cx;
    }

    public float getY() {
        return cy;
    }

    public void tick(Player p, Terrain terrain) {
        cx = (float) -(p.getX() + p.getPlayerWidth()/2) + (float) WIDTH/2;
        cy = (float) -(p.getY() + p.getPlayerHeight()/2) + (float) HEIGHT/2;
      //  System.out.println(terrain.getMaxY());

         if(cx >= 5) cx = 5;
         if(cy <= -terrain.getMaxY()+1100) cy = -terrain.getMaxY()+1100;
         if(cx <= -terrain.getMaxX()+1240) cx = -terrain.getMaxX()+1240;

        // System.out.println("cx: " + cx + " cy: " + cy);
    }
}
