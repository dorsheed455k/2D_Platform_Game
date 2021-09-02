package Game;

import Img.SpriteSheet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Help {

    private final int WIDTH;
    private final int HEIGHT;
    private Rectangle backButton;
    private SpriteSheet spriteSheet;
    private BufferedImage rightKey = ImageIO.read(new File("C:\\Users\\admin\\IdeaProjects\\2DPlateform\\src\\Img\\keyboard_key_right.png"));
    private BufferedImage leftKey = ImageIO.read(new File("C:\\Users\\admin\\IdeaProjects\\2DPlateform\\src\\Img\\keyboard_key_left.png"));
    public Font pixel;

    public Help(int WIDTH, int HEIGHT) throws IOException {
        setFont();
        this.WIDTH = WIDTH + 150;
        this.HEIGHT = HEIGHT;
        spriteSheet = new SpriteSheet();
        backButton = new Rectangle(50, HEIGHT-200, 180, 100);

    }

    public void setFont() {
        try {
            pixel = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\admin\\IdeaProjects\\2DPlateform\\src\\Fonts\\VCR_OSD_MONO_1.001.ttf")).deriveFont(50f);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Rectangle getBackButton() {
        return backButton;
    }




    public void render(Graphics2D g2) {
        g2.setColor(Color.CYAN);
        g2.fillRect(0,0, WIDTH, HEIGHT);
        g2.setStroke(new BasicStroke(10));
        g2.setFont(pixel);
        g2.setColor(Color.WHITE);
        g2.draw(backButton);
        g2.drawString("BACK", backButton.x+30, backButton.y+65);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("GB18030 Bitmap", Font.BOLD, 60));
        g2.drawString("Press 'P' to play ", 400, 250);
        g2.drawString("Press 'A' to pause ", 395, 320);
        g2.drawString("Press ", 175, 410);
        g2.drawImage(rightKey, 390, 355, 90,90, null);
        g2.drawString(" key to move right ", 500, 410);
        g2.drawString(" Press ", 175, 520);
        g2.drawImage(leftKey, 390, 470, 90,90, null);
        g2.drawString(" key to move left ", 500, 520);
        g2.drawString(" Press ", 175, 640);
        g2.drawImage(spriteSheet.rotateImage(rightKey, -90), 390, 590, 90,90, null);
        g2.drawString(" key to move up ", 500, 640);

    }
}

