package Game;

import java.awt.*;
import java.io.File;

public class Levels {
    public Rectangle lv1;
    public Rectangle lv2;
    public Rectangle lv3;
    private Rectangle backButton;

    private final int WIDTH;
    private final int HEIGHT;
    public Font pixel;

    public Levels(int WIDTH, int HEIGHT) {
        setFont();
        this.WIDTH = WIDTH + 150;
        this.HEIGHT = HEIGHT;
        lv1 = new Rectangle(this.WIDTH/2-120, 200, 240, 100);
        lv2 = new Rectangle(this.WIDTH/2-120, 400, 240, 100);
        lv3 = new Rectangle(this.WIDTH/2-120, 600, 240, 100);
        backButton = new Rectangle(50, HEIGHT-200, 180, 100);
    }

    public void setFont() {
        try {
            pixel = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/VCR_OSD_MONO_1.001.ttf")).deriveFont(50f);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Rectangle getBackButton() {
        return backButton;
    }

    public Rectangle getLv1() {
        return lv1;
    }

    public Rectangle getLv2() {
        return lv2;
    }

    public Rectangle getLv3() {
        return lv3;
    }

    public void render(Graphics2D g2) {
        g2.setColor(Color.CYAN);
        g2.fillRect(0,0, WIDTH, HEIGHT);
        g2.setStroke(new BasicStroke(10));

        g2.setColor(Color.WHITE);
        g2.setFont(pixel);
        g2.draw(lv1);
        g2.drawString("LEVEL 1", lv1.x+20, lv1.y+65);
        g2.draw(lv2);
        g2.drawString("LEVEL 2", lv2.x+20, lv2.y+65);
        g2.draw(lv3);
        g2.drawString("LEVEL 3", lv3.x+20, lv3.y+65);
        g2.draw(backButton);
        g2.drawString("BACK", backButton.x+30, backButton.y+65);
    }
}
