import java.awt.Color;
import java.awt.Graphics;

public class BasicZombie extends Zombie {
    public BasicZombie(int x, int y) {
        this.x = x; this.y = y;
        this.width = 40; this.height = 70;
        this.hp = 100;
        this.speed = 1;
    }
    @Override
    public void update() {
        if (!isAttacking) {
        x -= speed; // Chỉ di chuyển nếu KHÔNG đang cắn cây
        }
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        g.drawString("HP:" + hp, x, y - 5);
    }
}