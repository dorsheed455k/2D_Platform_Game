package Game;

import KeyInput.KeyHandler;
import KeyInput.KeyType;
import MouseInput.MouseType;
import Sound.Audio;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    private int WIDTH = 1100, HEIGHT = 1100;
    private Thread thread;
    private boolean running = false;
    private JFrame frame;
    private  MouseType mouse;
    private String TITLE;
    private KeyHandler key;
    private Audio audio;
    private PopUpFrame pop;
    private Font pixelPlus;
    private ArrayList<Handler> handlers;
    private boolean pauseGame = false;
    private String[] maps = {
            "C:\\Users\\admin\\IdeaProjects\\2DPlateform\\src\\Maps\\map1.txt",
            "C:\\Users\\admin\\IdeaProjects\\2DPlateform\\src\\Maps\\map3.txt",
            "C:\\Users\\admin\\IdeaProjects\\2DPlateform\\src\\Maps\\map2.txt",
    };

    private Menu menu;
    private Help help;
    private Levels levels;
    int index = 0;

    public static enum STATE {
       MENU,
       GAME,
       LEVEL,
       HELP
    }

    public static enum LEVEL {
        lv1,
        lv2,
        lv3
    }

    public static STATE State = STATE.MENU;
    public static LEVEL level = LEVEL.lv1;


    public GamePanel(JFrame frame) {
        super();
        retriveFont();
        setFocusable(true);
        requestFocus();
        TITLE = "2D Platform Game";
        try {
            frame.setIconImage(ImageIO.read(new File("C:\\Users\\admin\\IdeaProjects\\2DPlateform\\src\\Img\\Icon.PNG")));
        } catch(IOException e){
            e.printStackTrace();
        }
        this.frame = frame;
        this.frame.setSize(WIDTH+145, HEIGHT);
        this.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                updateMap();
                pausedGame();
                if (!pauseGame) {
                    update();
                    tick();
                    delta--;
                    render();
                    frames++;
                }
            }

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                this.frame.setTitle(TITLE + " | " + frames + " FPS");
                System.out.println(frames + " FPS");
                frames = 0;
            }
        }
        stop();
    }

    void start() {
        running = true;
        try {
            this.thread = new Thread(this, "Run");
            this.thread.start();
        } catch (NullPointerException e) {
            e.fillInStackTrace();
        }
    }

    void stop() {
        running = false;
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.getStackTrace();
        }
    }

     void init() throws IOException {
        this.addKeyListener(key = new KeyHandler());
        menu = new Menu(WIDTH, HEIGHT, pixelPlus);
        levels = new Levels(WIDTH, HEIGHT);
        pop = new PopUpFrame();
        help = new Help(WIDTH, HEIGHT);
        this.addMouseListener(mouse = new MouseType(menu, levels, help, pop));

        handlers = new ArrayList<>();
         for (int i = 0; i < 3; i++) {
             handlers.add(new Handler(WIDTH, HEIGHT, pop));
             handlers.get(i).setIndex(i);
             handlers.get(i).setUrl(maps[i]);
             handlers.get(i).init();
         }
         audio = new Audio();
         audio.playMusic();
     }


    void retriveFont() {
        try {
            pixelPlus = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\admin\\IdeaProjects\\2DPlateform\\src\\Fonts\\04B_30__.TTF")).deriveFont(55f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\admin\\IdeaProjects\\2DPlateform\\src\\Fonts\\04B_30__.TTF")));
        } catch (IOException | FontFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateMap() {
        switch (level) {
            case lv1:
                index = 0;
                break;
            case lv2:
                index = 1;
                break;
            case lv3:
                index = 2;
                break;
        }
    }

    void update()  {
        if(State == STATE.GAME) {
           handlers.get(index).update(key);
        }
    }

    public void pausedGame() {
        if (key.getKey() == KeyType.P) {
            pauseGame = false;
        } else if(key.getKey() == KeyType.A) {
            pauseGame = true;
        }
    }

    private void tick() {
        if(State == STATE.GAME) {
            handlers.get(index).tick();
        }
    }

     void render()  {
         // Setting up BufferStrategy
         BufferStrategy bs = frame.getBufferStrategy();
         if (bs == null) {
            frame.createBufferStrategy(3);
            return;
         }

         // Drawing Backdrop
         Graphics g = bs.getDrawGraphics();
         Graphics2D g2 = (Graphics2D) g;
         g2.setColor(Color.BLACK);
         g2.fillRect(0, 0,  WIDTH+150, HEIGHT);

         if(State == STATE.GAME) {
           // Drawing Stuff
            handlers.get(index).render(g2, g);
         } else if (State == STATE.MENU) {
             menu.render(g2);
         } else if (State == STATE.LEVEL) {
            levels.render(g2);
         } else {
             help.render(g2);
         }
         // Disposing of graphics
         g.dispose();
         bs.show();
    }
}
