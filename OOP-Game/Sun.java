import java.awt.Color;
import java.awt.Graphics;

public class Sun extends Entity {
    private int value = 25;
    private long spawnTime;

    public Sun(int x, int y) {
        this.x = x; this.y = y;
        this.width = 30; this.height = 30;
        this.spawnTime = System.currentTimeMillis();
    }
    public int getValue() { return value; }
    public void update() {
        if (System.currentTimeMillis() - spawnTime > 7000) this.isDead = true; // Biến mất sau 7s
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, width, height);
        g.setColor(Color.ORANGE);
        g.drawOval(x, y, width, height);
    }
}