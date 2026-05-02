import java.awt.Color;
import java.awt.Graphics;

public class WallNut extends Plant {

    public WallNut(int x, int y) {
        this.x = x; this.y = y;
        this.width = 50; this.height = 60;
        this.hp = 2000;
        this.cost = 50; // THÊM DÒNG NÀY// Máu trâu gấp gần 7 lần cây thường
    }

    @Override
    public void update(GameManager game) {
        // Hạt dẻ chỉ đứng im chịu đòn nên hàm update để trống
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(139, 69, 19)); // Màu nâu
        g.fillOval(x, y, width, height); // Vẽ hình Oval cho giống hạt dẻ
        g.setColor(Color.WHITE);
        g.drawString("WALL", x + 8, y + 35);
    }
}