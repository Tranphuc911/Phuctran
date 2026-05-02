import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class GameManager extends JPanel implements ActionListener, MouseListener {
    private Timer gameLoop;
    
    private List<Plant> plants = new ArrayList<>();
    private List<Zombie> zombies = new ArrayList<>();
    private List<Projectile> projectiles = new ArrayList<>();
    private List<Sun> suns = new ArrayList<>();
    
    private int sunScore = 150; 
    private String selectedPlant = "Sunflower"; 
    
    private int zombieSpawnTimer = 0;
    private Random random = new Random();

    private boolean isGameOver = false;
    private boolean isVictory = false;
    private int zombiesSpawned = 0; 
    private int zombiesKilled = 0;  
    private final int TOTAL_ZOMBIES_IN_LEVEL = 10; 

    public GameManager() {
        this.setPreferredSize(new Dimension(810, 580)); 
        this.setBackground(new Color(34, 139, 34));
        this.addMouseListener(this); 

        gameLoop = new Timer(16, this); 
        gameLoop.start();
    }

    public void addProjectile(Projectile p) { projectiles.add(p); }
    public void addSun(Sun s) { suns.add(s); }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isGameOver || isVictory) return;

        zombieSpawnTimer++;
        if (zombieSpawnTimer >= 240 && zombiesSpawned < TOTAL_ZOMBIES_IN_LEVEL) {
            int randomRow = random.nextInt(5); 
            int gridY = randomRow * 100 + 80 + 15; 
            zombies.add(new BasicZombie(810, gridY));
            zombiesSpawned++;
            zombieSpawnTimer = 0;
        }

        for (int i = 0; i < plants.size(); i++) plants.get(i).update(this);
        for (int i = 0; i < zombies.size(); i++) zombies.get(i).update();
        for (int i = 0; i < projectiles.size(); i++) projectiles.get(i).update();
        for (int i = 0; i < suns.size(); i++) suns.get(i).update();

        checkCollisions();

        for (Zombie z : zombies) {
            if (z.x < 0) { 
                isGameOver = true; 
            }
        }

        projectiles.removeIf(p -> p.x > 810 || p.isDead());
        plants.removeIf(Plant::isDead);
        suns.removeIf(Sun::isDead);
        
        zombies.removeIf(z -> {
            if (z.isDead()) {
                zombiesKilled++;
                if (zombiesKilled >= TOTAL_ZOMBIES_IN_LEVEL) {
                    isVictory = true; 
                }
                return true;
            }
            return false;
        });

        repaint();
    }

    private void checkCollisions() {
        for (Projectile p : projectiles) {
            for (Zombie z : zombies) {
                if (!p.isDead() && !z.isDead() && p.getBounds().intersects(z.getBounds())) {
                    z.takeDamage(p.damage);
                    p.setDead(true); 
                }
            }
        }

        for (Zombie z : zombies) {
            boolean isEating = false; 
            for (Plant p : plants) {
                if (!p.isDead() && z.getBounds().intersects(p.getBounds())) {
                    p.takeDamage(1); 
                    isEating = true; 
                    break; 
                }
            }
            z.setAttacking(isEating); 
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isGameOver || isVictory) return; 

        int mx = e.getX();
        int my = e.getY();

        // 1. CLICK THANH MENU
        if (my < 80) {
            if (mx >= 150 && mx <= 230) selectedPlant = "Sunflower";
            if (mx >= 250 && mx <= 330) selectedPlant = "Peashooter";
            if (mx >= 350 && mx <= 430) selectedPlant = "WallNut"; 
            if (mx >= 450 && mx <= 530) selectedPlant = "Shovel"; 
            return; 
        }

        // 2. NHẶT MẶT TRỜI
        for (Sun s : suns) {
            if (s.getBounds().contains(mx, my)) {
                sunScore += s.getValue();
                s.setDead(true);
                return; 
            }
        }

        // 3. TÍNH TOÁN TỌA ĐỘ LƯỚI
        int col = mx / 90; 
        int row = (my - 80) / 100; 
        int gridX = col * 90 + 20; 
        int gridY = row * 100 + 80 + 20;

        // 4. KIỂM TRA Ô ĐẤT CÓ CÂY KHÔNG
        Plant targetPlant = null;
        for (Plant p : plants) {
            if (p.x == gridX && p.y == gridY) {
                targetPlant = p;
                break;
            }
        }

        // 5. THỰC HIỆN HÀNH ĐỘNG (ĐÀO HOẶC TRỒNG)
        if (selectedPlant.equals("Shovel")) {
            if (targetPlant != null) {
                targetPlant.setDead(true); // Đào cây lên
                
                // --- LOGIC HOÀN TIỀN Ở ĐÂY ---
                sunScore += targetPlant.getCost(); 
                
                selectedPlant = "Sunflower"; // Reset con trỏ chuột
            }
        } else {
            // Logic trồng cây (chỉ trồng khi ô đất trống)
            if (targetPlant == null) {
                if (selectedPlant.equals("Sunflower") && sunScore >= 50) {
                    plants.add(new Sunflower(gridX, gridY));
                    sunScore -= 50;
                } else if (selectedPlant.equals("Peashooter") && sunScore >= 100) {
                    plants.add(new Peashooter(gridX, gridY));
                    sunScore -= 100;
                } else if (selectedPlant.equals("WallNut") && sunScore >= 50) { 
                    plants.add(new WallNut(gridX, gridY));
                    sunScore -= 50;
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // --- Vẽ UI Menu ---
        g.setColor(new Color(139, 69, 19)); 
        g.fillRect(0, 0, 810, 80);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Mặt trời: " + sunScore, 10, 45);

        // Các nút Menu
        g.setColor(selectedPlant.equals("Sunflower") ? Color.YELLOW : Color.GRAY);
        g.fillRect(150, 10, 80, 60);
        g.setColor(Color.BLACK); g.drawString("Sun(50)", 155, 45);

        g.setColor(selectedPlant.equals("Peashooter") ? Color.GREEN : Color.GRAY);
        g.fillRect(250, 10, 80, 60);
        g.setColor(Color.BLACK); g.drawString("Pea(100)", 255, 45);

        g.setColor(selectedPlant.equals("WallNut") ? new Color(205, 133, 63) : Color.GRAY);
        g.fillRect(350, 10, 80, 60);
        g.setColor(Color.BLACK); g.drawString("Wall(50)", 355, 45);

        g.setColor(selectedPlant.equals("Shovel") ? Color.RED : Color.DARK_GRAY);
        g.fillRect(450, 10, 80, 60);
        g.setColor(Color.WHITE); g.drawString("SHOVEL", 455, 45);

        g.setColor(Color.WHITE);
        g.drawString("Wave: " + zombiesKilled + "/" + TOTAL_ZOMBIES_IN_LEVEL, 650, 45);

        // --- Vẽ Lưới và Thực thể ---
        g.setColor(new Color(0, 0, 0, 40));
        for (int i = 0; i <= 9; i++) g.drawLine(i * 90, 80, i * 90, 580);
        for (int i = 0; i <= 5; i++) g.drawLine(0, i * 100 + 80, 810, i * 100 + 80);

        for (Plant p : plants) p.draw(g);
        for (Zombie z : zombies) z.draw(g);
        for (Projectile p : projectiles) p.draw(g);
        for (Sun s : suns) s.draw(g);

        // --- Màn hình Thắng/Thua ---
        if (isGameOver) {
            g.setColor(new Color(0, 0, 0, 150)); 
            g.fillRect(0, 0, 810, 580);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 60));
            g.drawString("THE ZOMBIES ATE YOUR BRAINS!", 40, 300);
        } else if (isVictory) {
            g.setColor(new Color(255, 255, 255, 150)); 
            g.fillRect(0, 0, 810, 580);
            g.setColor(new Color(34, 139, 34));
            g.setFont(new Font("Arial", Font.BOLD, 60));
            g.drawString("LEVEL CLEARED! YOU SURVIVED!", 50, 300);
        }
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}