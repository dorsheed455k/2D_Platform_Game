package Game;

import Maps.Terrain;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PopUpFrame extends JFrame {
    private Rectangle yesButton;
    private Rectangle noButton;
    private Rectangle cancelButton;
    public Player player;
    public Terrain terrain;
    private Font pixel;
    private Font pixelPlus;

    public PopUpFrame() {
        yesButton = new Rectangle(400, 500, 100,50);
        noButton = new Rectangle(550, 500, 100,50);
        cancelButton = new Rectangle(700, 500, 120,50);
        setFont();
    }

    public Rectangle getYesButton() {
        return yesButton;
    }

    public Rectangle getNoButton() {
        return noButton;
    }

    public Rectangle getCancelButton() {
        return cancelButton;
    }

    public void setFont() {
        try {
            pixel = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/VCR_OSD_MONO_1.001.ttf")).deriveFont(30f);
            pixelPlus = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/04B_30__.TTF")).deriveFont(40f);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public void render(Graphics2D g2) {
        g2.setStroke(new BasicStroke(10));
        g2.setColor(Color.RED);
        g2.setFont(pixelPlus);
        g2.drawString("You have completed all the rounds!", 80, 400);
        g2.drawString("Play again?", 420, 470);

        g2.setFont(pixel);
        g2.draw(yesButton);
        g2.drawString("Play", yesButton.x+15, yesButton.y+35);
        g2.draw(noButton);
        g2.drawString("Exit", noButton.x+15, noButton.y+35);
        g2.draw(cancelButton);
        g2.drawString("Cancel", cancelButton.x+15, cancelButton.y+35);
    }
}
