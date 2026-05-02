import java.awt.Color;
import java.awt.Graphics;

public class Peashooter extends Plant {
    private int fireCooldown = 0;

    public Peashooter(int x, int y) {
        this.x = x; this.y = y;
        this.width = 50; this.height = 60;
        this.hp = 300;
        this.cost = 100;
    }
    @Override
    public void update(GameManager game) {
        fireCooldown++;
        if (fireCooldown >= 90) { // Bắn mỗi 1.5 giây
            // Tạo đạn ở giữa thân cây đậu
            game.addProjectile(new Projectile(this.x + 50, this.y + 20));
            fireCooldown = 0;
        }
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(50, 205, 50)); // Xanh lá
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawString("PEA", x + 12, y + 35);
    }
}