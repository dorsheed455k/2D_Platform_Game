package MouseInput;

import Game.*;
import Maps.Terrain;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseType implements MouseListener, MouseMotionListener {
    private Menu menu;
    private Levels levels;
    private Help help;
    private PopUpFrame pop;

    public MouseType(Menu menu,
                     Levels levels,
                     Help help,
                     PopUpFrame pop) {
        this.menu = menu;
        this.levels = levels;
        this.help = help;
        this.pop = pop;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //TODO
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (GamePanel.State == GamePanel.STATE.MENU) {
            if (mx >= menu.getPlayButton().getX() && mx <= menu.getPlayButton().getX() + menu.getPlayButton().getWidth()) {
                if (my >= menu.getPlayButton().getY() && my <= menu.getPlayButton().getY() + menu.getPlayButton().getHeight()) {
                    // Pressed Play Button
                 //   GamePanel.State = GamePanel.STATE.GAME;
                    GamePanel.State = GamePanel.STATE.LEVEL;
                }

                if (my >= menu.getHelpButton().getY() && my <= menu.getHelpButton().getY() + menu.getHelpButton().getHeight()) {
                    // Pressed Play Button
                    //   GamePanel.State = GamePanel.STATE.GAME;
                    GamePanel.State = GamePanel.STATE.HELP;
                }
                if (my >= menu.getQuitButton().getY() && my <= menu.getQuitButton().getY() + menu.getQuitButton().getHeight()) {
                    System.exit(0);
                }
            }
        } else if (GamePanel.State == GamePanel.STATE.LEVEL) {
            if (mx >= levels.getLv1().getX() && mx <= levels.getLv1().getX() + levels.getLv1().getWidth()) {
                if (my >= levels.getLv1().getY() && my <= levels.getLv1().getY() + levels.getLv1().getHeight()) {
                    GamePanel.level = GamePanel.LEVEL.lv1;
                }
                if (my >= levels.getLv2().getY() && my <= levels.getLv2().getY() + levels.getLv2().getHeight()) {
                    GamePanel.level = GamePanel.LEVEL.lv2;
                }
                if (my >= levels.getLv3().getY() && my <= levels.getLv3().getY() + levels.getLv3().getHeight()) {
                    GamePanel.level = GamePanel.LEVEL.lv3;
                }

                GamePanel.State = GamePanel.STATE.GAME;
            }
            if (mx >= levels.getBackButton().getX() && mx <= levels.getBackButton().getX() + levels.getBackButton().getWidth()) {
                if (my >= levels.getBackButton().getY() && my <= levels.getBackButton().getY() + levels.getBackButton().getHeight()) {
                    GamePanel.State = GamePanel.STATE.MENU;
                }
            }
        } else if (GamePanel.State == GamePanel.STATE.HELP) {
            if (mx >= help.getBackButton().getX() && mx <= help.getBackButton().getX() + help.getBackButton().getWidth()) {
                if (my >= help.getBackButton().getY() && my <= help.getBackButton().getY() + help.getBackButton().getHeight()) {
                    GamePanel.State = GamePanel.STATE.MENU;
                }
            }
        } else if(GamePanel.State == GamePanel.STATE.GAME) {
            if (mx >= pop.getYesButton().getX() && mx <= pop.getYesButton().getX() + pop.getYesButton().getWidth()) {
                pop.player.setX(pop.terrain.getPx());
                pop.player.setY(pop.terrain.getPy());
                GamePanel.level = GamePanel.LEVEL.lv1;
            }
            if (mx >= pop.getNoButton().getX() && mx <= pop.getNoButton().getX() + pop.getNoButton().getWidth()) {
                System.exit(0);
            }

            if (mx >= pop.getCancelButton().getX() && mx <= pop.getCancelButton().getX() + pop.getCancelButton().getWidth()) {
                GamePanel.State = GamePanel.STATE.MENU;
                pop.player.setX(pop.terrain.getPx());
                pop.player.setY(pop.terrain.getPy());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //TODO
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //TODO
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //TODO
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //TODO
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println(x + " " + y);
    }
}
