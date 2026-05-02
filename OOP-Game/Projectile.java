import java.awt.Color;
import java.awt.Graphics;

public class Projectile extends Entity {
    public int damage = 20;
    private int speed = 6;

    public Projectile(int x, int y) {
        this.x = x; this.y = y;
        this.width = 15; this.height = 15;
    }
    public void update() { x += speed; }
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(x, y, width, height);
    }
}