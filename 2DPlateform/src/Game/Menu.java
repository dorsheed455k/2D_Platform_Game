package Game;

import java.awt.*;
import java.io.File;

public class Menu {
    public Rectangle playButton;
    public Rectangle helpButton;
    public Rectangle quitButton;
    private int WIDTH, HEIGHT;
    public Font pixel;
    public Font pixelPlus;

    public Menu(int WIDTH, int HEIGHT, Font pixelPlus) {
        setFont();
        this.WIDTH = WIDTH + 150;
        this.HEIGHT = HEIGHT;
        this.pixelPlus = pixelPlus.deriveFont(60f);
        playButton = new Rectangle(this.WIDTH/2-90, 200, 180, 100);
        helpButton = new Rectangle(this.WIDTH/2-90, 400, 180, 100);
        quitButton = new Rectangle(this.WIDTH/2-90, 600, 180, 100);
    }

    public void setFont() {
        try {
            pixel = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\admin\\IdeaProjects\\2DPlateform\\src\\Fonts\\VCR_OSD_MONO_1.001.ttf")).deriveFont(50f);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Rectangle getPlayButton() {
        return playButton;
    }

    public Rectangle getQuitButton() {
        return quitButton;
    }

    public Rectangle getHelpButton() {
        return helpButton;
    }

    public void render(Graphics2D g) {
        g.setColor(Color.CYAN);
        g.fillRect(0,0, WIDTH, HEIGHT);

        g.setFont(pixelPlus);
        g.setColor(Color.WHITE);
        g.drawString("2D PLATFORM GAME", 150, 140);

        g.setStroke(new BasicStroke(10));

        g.setFont(pixel);
        g.drawString("Play", playButton.x+30, playButton.y+65);
        g.draw(playButton);
        g.draw(helpButton);
        g.drawString("Help", helpButton.x+30, helpButton.y+65);
        g.draw(quitButton);
        g.drawString("Quit", quitButton.x+30, quitButton.y+65);
    }
}
