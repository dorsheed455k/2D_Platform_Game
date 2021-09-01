package Game;

import Maps.Terrain;

import javax.swing.*;

public class PopUpFrame extends JFrame {
    private final Terrain terrain;
    private final Player player;

    public PopUpFrame(Terrain terrain, Player player) {
        this.setLocationRelativeTo(null);
        this.player = player;
        this.terrain = terrain;
    }

    public void initClosingDialog() {
        if(player.getX() >= player.maxDistX && (!player.isFalling() || !player.isJumping())) {
           int response =  JOptionPane.showConfirmDialog(null, "You have completed this level with " + terrain.getCoins() + " coins!\nAdvanced to the next level?");
           if(response == JOptionPane.YES_OPTION) {
               GamePanel.level = GamePanel.LEVEL.lv1;
               player.x = terrain.getPx();
               player.y = terrain.getPy();
           } else if (response == JOptionPane.NO_OPTION){
               System.exit(0);
           } else {
               GamePanel.State = GamePanel.STATE.MENU;
               GamePanel.level = GamePanel.LEVEL.lv1;
               player.x = terrain.getPx();
               player.y = terrain.getPy();
           }
        }
    }
}
