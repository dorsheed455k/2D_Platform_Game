package Game;

import KeyInput.KeyHandler;
import Maps.Terrain;
import Sound.Audio;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Handler {
    private Terrain terrain;
    private Camera cam;
    private Player p;
    private Audio audio;
    private int WIDTH, HEIGHT;
    private PopUpFrame pop;
    private Font pixelPlus;
    private int index;
    private float alpha;
    private String url;

    public Handler(int WIDTH, int HEIGHT, PopUpFrame pop) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        audio = new Audio();
        this.pop = pop;
        retriveFont();
    }

    void retriveFont() {
        try {
            pixelPlus = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/04B_30__.TTF")).deriveFont(55f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/04B_30__.TTF")));
        } catch (IOException | FontFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    public void init() throws IOException {
        alpha = 0f;
        try {
            terrain = new Terrain(WIDTH, HEIGHT);

        } catch (Exception e) {
            e.printStackTrace();
        }
        terrain.setMap2D(url);
        terrain.init();
        p = new Player(terrain.getPx(), terrain.getPy(), terrain);
        cam = new Camera((float) p.getX(), (float) p.getY(), WIDTH, HEIGHT);
        pop.setPlayer(p);
        pop.setTerrain(terrain);
    }

    public void setIndex(int i) {
        index = i;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void update(KeyHandler key) {
        p.move(terrain.getTiles(), terrain.getLava(), key, audio);
     //   pop.initClosingDialog();
    }

    public void tick() {
        p.tick();
        cam.tick(p, terrain);
        terrain.tick();
        if (p.getX() >= terrain.getPx()) {
            fadeIn();
        }

        if (p.getX() >= terrain.getMaxX()-(p.getPlayerWidth()*3)) {
            fadeOut();
        } else {
            fadeIn();
        }

    }

    public void fadeOut() {
        alpha += -0.02f;
        if (alpha <= 0) {
            alpha = 0;
        }
    }

    public void fadeIn() {
        alpha += 0.02f;
        if (alpha >= 1f) {
            alpha = 1f;
        }
    }

    public void render(Graphics2D g2,
                       Graphics g) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        // Drawing Stuff
        g2.translate(cam.getX(), cam.getY());
        g2.setColor(Color.CYAN);
        g2.fillRect((int) -cam.getX(), (int) -cam.getY(), (int) (terrain.getMaxX() - cam.getX()), (int) (terrain.getMaxY() + cam.getY()));
        p.render(g);
        terrain.render(g2, p, audio);
        g2.translate(-cam.getX(), -cam.getY());
        if(p.getX() >= p.maxDistX && (!p.isFalling() || !p.isJumping()) && index == 2) {
            pop.render(g2);
        }

        // Drawing Points
        g.setColor(Color.BLACK);
        g.setFont(pixelPlus);
        g.drawString("Coins: ", 20, 140);
        g.drawString(String.valueOf(terrain.getCoins()), 270, 140);
        terrain.drawLevelState(g, index);
    }
}
