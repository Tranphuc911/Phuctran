import java.awt.Color;
import java.awt.Graphics;

public class Sunflower extends Plant {
    private int sunTimer = 0;

    public Sunflower(int x, int y) {
        this.x = x; this.y = y;
        this.width = 50; this.height = 60;
        this.hp = 200;
        this.cost = 50; // THÊM DÒNG NÀY
    }
    @Override
    public void update(GameManager game) {
        sunTimer++;
        if (sunTimer >= 400) { // Tạo mặt trời mỗi ~6-7 giây
            game.addSun(new Sun(this.x + 10, this.y + 10));
            sunTimer = 0;
        }
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(255, 215, 0)); // Vàng
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawString("SUN", x + 10, y + 35);
    }
}