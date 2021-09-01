package Maps;

import Game.CheckCollision;
import Game.Player;
import Img.SpriteSheet;
import Sound.Audio;
import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Terrain {
    ArrayList<Tiles> tiles = new ArrayList<>();
    ArrayList<Lava> lava = new ArrayList<>();
    ArrayList<Coins> coins = new ArrayList<>();
    private double width, height;
    private BufferedImage items;
    private BufferedImage[] elements;
    private BufferedImage grass,wood, stone;
    private SpriteSheet sprite;
    private int value;
    public ArrayList<BufferedImage> inventory = new ArrayList<>();
    private int mapX, mapY, mapS = 100;
    private int[] map;
    private double px, py;
    private int ex, ey;
    public int maxX, maxY;
    private int scale;


    public Terrain(double WIDTH, double HEIGHT) throws IOException {
        this.width = WIDTH;
        this.height = HEIGHT;
        sprite = new SpriteSheet();
        items = sprite.trimImage(ImageIO.read(new File("C:\\Users\\admin\\IdeaProjects\\2DPlateform\\src\\Img\\sprites.png")));
        elements = new BufferedImage[]{
                items.getSubimage(0,0,20,20),
                items.getSubimage(20,0,20,20),
                sprite.trimImage(items.getSubimage(40,0,12,20))
        };
        grass = ImageIO.read(new File("C:\\Users\\admin\\IdeaProjects\\2DPlateform\\src\\Img\\grass.png"));
        wood = ImageIO.read(new File("C:\\Users\\admin\\IdeaProjects\\2DPlateform\\src\\Img\\wood1.jpg"));
        stone = ImageIO.read(new File("C:\\Users\\admin\\IdeaProjects\\2DPlateform\\src\\Img\\stone.png"));

        value = 0;
        scale = 1;
    }

    public ArrayList<Tiles> getTiles() {
        return tiles;
    }

    public ArrayList<Lava> getLava() {
        return lava;
    }

    public ArrayList<Coins> getCoin() {
        return coins;
    }

    public void setMap2D(String s) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(s));
            mapX = Integer.parseInt(br.readLine());
            mapY = Integer.parseInt(br.readLine());
            map = new int[mapX*mapY];
            System.out.println("Map Y: " + mapY);
            maxX = scale * mapX * 100;
            maxY = scale * mapY * 100;
            String delimiter = "";
            int pos = 0;
            for (int i = 0; i < mapY; i++) {
                String line = br.readLine();
                String[] token = line.split(delimiter);
                for (int j = 0; j < mapX; j++) {
                    map[pos] = Integer.parseInt(token[j]);
                    if(map[pos] == 6) {
                        px = j * 100 * scale; py = i * 100 * scale;
                        System.out.println(py);
                    }
                    if(map[pos] == 7) {
                        ex = j * 100 * scale; ey = i * 100 * scale;
                       // System.out.println(ex);
                    }
                    pos++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawMap2D(Graphics2D g) {
        int x, y, xo, yo;
        int tileWidth = mapS, tileHeight = mapS;
        for (y = 0; y < mapY; y++) {
            for (x = 0; x < mapX; x++) {
                xo = x*mapS; yo = y*mapS;
                if(map[y*mapX+x] == 1) {
                    g.drawImage(grass, xo, yo, tileWidth*scale, tileHeight*scale, null);
                } else if(map[y*mapX+x] == 2){
                    g.drawImage(wood,xo, yo, tileWidth*scale, tileHeight*scale, null);
                } else if(map[y*mapX+x] == 8){
                    g.drawImage(stone,xo, yo, tileWidth*scale, tileHeight*scale, null);
                } else if(map[y*mapX+x] == 3) {
                    g.drawImage(elements[1],xo, yo, tileWidth*scale, tileHeight*scale, null);
                } else if(map[y*mapX+x] == 4) {
                    g.drawImage(elements[0],xo, yo, tileWidth*scale, tileHeight*scale, null);
                } else if(map[y*mapX+x] == 5) {
                    inventory.add(elements[2]);
                } else if(map[y*mapX+x] == 6) {
                    continue;
                } if(map[y*mapX+x] == 0){
                    continue;
                }
            }
        }
    }

    public double getPx() {
        return px;
    }

    public double getPy() {
        return py;
    }

    public int getEx() {
        return ex;
    }

    public int getEy() {
        return ex;
    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public int getMapS() {
        return mapS;
    }


    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void init() {
        for (int i = 0; i < mapY; i++) {
            for (int j = 0; j < mapX; j++) {
                if(map[i * mapX + j] == 1 || map[i * mapX + j] == 4 || map[i * mapX + j] == 2) {
                    tiles.add(new Tiles(j, i, scale, mapS));
                }
                if(map[i * mapX + j] == 3 ) {
                    lava.add(new Lava(j, i, scale, mapS));
                }
                if(map[i * mapX + j] == 5) {
                    coins.add(new Coins(j, i, scale, mapS, elements[2]));
                }
            }
        }
    }

    public void tick() {
        //TODO
    }

    public int getCoins() {
        return value;
    }

    public void drawGrid(Graphics g) {
        g.setColor(Color.GRAY);
        for (int i = 0; i < mapY; i++) {
            g.drawLine(0,100*i, (int) width,100*i);
        }

        for (int i = 0; i < mapX; i++) {
            g.drawLine(100*i,0,100*i, (int) height);
        }
    }

    public void drawLevelState(Graphics g, int i) {
        g.drawString("Level ", 800, 140);
        g.drawString(String.valueOf(i+1), 1050, 140);
    }


    public void render(Graphics2D g, Player p, Audio audio) {
        drawMap2D(g);
        // drawGrid(g);
        for(Coins coin : coins) {
            boolean collided = false;
            coin.render(g);
            if(CheckCollision.collisionWithCoin(p, coin)) {
                collided = true;
            }
            if(collided && coin.coin != null) {
                audio.coinSound();
                coin.coin = null;
                value++;
            }
            if(p.getX() == getPx()) {
                coin.coin = elements[2];
                value = 0;
            }
        }
    }

    public static class Tiles {
        private int tx;
        private int ty;
        private int tileWidth;
        private int tileHeight;

        Tiles(int tx, int ty, int scale, int baseSize) {
            tileWidth = baseSize;
            tileHeight = baseSize;
            this.tx = tx * tileWidth * scale;
            this.ty = ty * tileHeight * scale;
        }

        public Rectangle2D.Double getBounds() {
            return new Rectangle2D.Double(tx, ty, tileWidth, tileHeight);
        }
    }

    public static class Lava {
        private int lx;
        private int ly;
        private int lavaWidth;
        private int lavaHeight;

        Lava(int lx, int ly, int scale, int baseSize) {
            lavaWidth = scale * baseSize;
            lavaHeight = scale * baseSize;
            this.lx = lx * lavaWidth;
            this.ly = ly * lavaHeight + (int)(Math.random()+1)*5;

        }
        public Rectangle2D.Double getBounds() {
            return new Rectangle2D.Double(lx, ly, lavaWidth, lavaHeight);
        }
    }

    public static class Coins {
        private double cx;
        private double cy;
        private int coinWidth;
        private int coinHeight;
        BufferedImage coin;

        Coins(double cx, double cy, int scale, int baseSize, BufferedImage coin) {
            coinWidth = baseSize;
            coinHeight = baseSize;
            this.cx = cx * coinWidth * scale;
            this.cy = cy * coinHeight * scale;
            this.coin = coin;
        }

        double wobbleSpeed = 2, wobbleDist = 6;
        double wobble() {
            double wobble = LocalDateTime.now().getSecond() * wobbleSpeed;
            double wobblePos = Math.sin(wobble) * wobbleDist;
            return wobblePos;
        }

        void render(Graphics2D g) {
            g.drawImage(coin,(int)cx+15, (int)(cy+wobble()), 60,60, null);
            g.setColor(Color.BLACK);
        }

        public Rectangle2D.Double getBounds() {
            return new Rectangle2D.Double(cx, cy+wobble(), coinWidth, coinHeight);
        }
    }
}