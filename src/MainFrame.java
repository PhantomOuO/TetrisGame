import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;

public class MainFrame extends JFrame implements KeyListener {
    static final int gameX = 26; // 行數
    static final int gameY = 12; // 行數
    static JTextArea[][] text;// 文字
    static int[][] data;
    JLabel gameState; // 遊戲狀態
    static JLabel gameScore; // 遊戲分數
    static int score = 0; // 預設分數0

    public MainFrame() { // 初始化
        text = new JTextArea[gameX][gameY];
        data = new int[gameX][gameY];
        gameState = new JLabel(" 遊戲狀態:開始! ");
        gameScore = new JLabel(" 遊戲分數:0 ");
        initGamePanel();
        initExplainPanel();
        init(); // 介面
    }

    private void init() { // 初始化介面
        this.setSize(600, 950); // 設定介面長寬
        this.setLocationRelativeTo(null); // 設定初始介面座標
        this.setTitle("俄羅斯方塊 Tertris"); // 設定標題(俄羅斯方塊Tertris)
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); // 設定關閉程式
        this.setVisible(true); // 設定介面是否可見
        this.setResizable(false); // 固定窗口大小
    }

    public void initGamePanel() { // 初始化遊戲邊界
        JPanel gameMain = new JPanel();
        gameMain.setLayout(new GridLayout(gameX, gameY, 1, 1));// 布局

        for (int i = 0; i < text.length; i++) {
            for (int j = 0; j < text[i].length; j++) {
                text[i][j] = new JTextArea(gameX, gameY); // 設定
                text[i][j].setBackground(Color.white); // 設定背景為白色
                text[i][j].addKeyListener(this);// 設定鍵盤監聽
                text[i][j].setEditable(false);// 設定不可編輯

                if (j == 0 || j == text[i].length - 1 || i == text.length - 1) {
                    text[i][j].setBackground(Color.GRAY); // 設定背景為黑色
                    data[i][j] = 1; // 設定邊界為有方塊
                }

                gameMain.add(text[i][j]);
            }
        }
        this.setLayout(new BorderLayout());
        this.add(gameMain, BorderLayout.CENTER);
    }

    public void initExplainPanel() { // 初始化遊玩規則介面
        JPanel explainR = new JPanel();
        JPanel explainL = new JPanel();
        explainR.setLayout(new GridLayout(20, 100));
        explainL.setLayout(new GridLayout(1, 1));

        explainR.add(new JLabel(" 空白鍵:改變方向 ")); // 說明文字
        explainR.add(new JLabel(" 左鍵:方塊左移 "));
        explainR.add(new JLabel(" 右鍵:方塊右移 "));
        explainR.add(new JLabel("下鍵:方塊下移 ")); // -
        explainL.add(new JLabel("    "));
        explainR.add(gameState); // 把遊戲狀態加入說明介面
        explainR.add(gameScore); // 把遊戲分數加入說明介面
        this.add(explainR, BorderLayout.EAST); // 把說明加入主介面
        this.add(explainL, BorderLayout.WEST);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_SPACE) { // 旋轉方塊
            int k; // 原方塊
            int p; // 旋轉後方快
            if (!Main.state) { // 遊戲是否結束
                return;
            }
            for (k = 0; k < Main.allRect.length; k++) {// 方塊陣列
                if (Main.rect == Main.allRect[k]) {
                    break;
                }
            }
            if (k == 0 || k == 7 || k == 8 || k == 9) { // 方形方塊不用旋轉
                return;
            }
            Main.clear(Main.x, Main.y); // 清除該方塊
            if (k == 1 || k == 2) { // 0x8888,0x000f
                p = Main.allRect[k == 1 ? 2 : 1]; // 旋轉
                if (canTure(p, Main.x, Main.y)) { // 判斷方塊是否可以旋轉
                    p = Main.rect;
                }
            }
            if (k >= 3 && k <= 6) { // 0x888f,0xf888,0xf111,0x111f
                p = Main.allRect[k + 1 > 6 ? 3 : k + 1];// 旋轉
                if (canTure(p, Main.x, Main.y)) { // 判斷方塊是否可以旋轉
                    p = Main.rect;
                }
            }
            if (k == 10 || k == 11) { // 0x0888,0x000e
                p = Main.allRect[k == 10 ? 11 : 10]; // 旋轉
                if (canTure(p, Main.x, Main.y)) { // 判斷方塊是否可以旋轉
                    p = Main.rect;
                }
            }
            if (k == 12 || k == 13) { // 0x0088,0x000c
                p = Main.allRect[k == 10 ? 11 : 10]; // 旋轉
                if (canTure(p, Main.x, Main.y)) { // 判斷方塊是否可以旋轉
                    p = Main.rect;
                }
            }
            if (k >= 14 && k <= 17) { // 0x08c8,0x00e4,0x04c4,0x004e
                p = Main.allRect[k + 1 > 17 ? 14 : k + 1];// 旋轉
                if (canTure(p, Main.x, Main.y)) { // 判斷方塊是否可以旋轉
                    p = Main.rect;
                }
            }
            if (k == 18 || k == 19) { // 0x08c4,0x006c
                p = Main.allRect[k == 18 ? 19 : 18]; // 旋轉
                if (canTure(p, Main.x, Main.y)) { // 判斷方塊是否可以旋轉
                    p = Main.rect;
                }
            }

            if (k == 20 || k == 21) { // 0x04c8,0x00c6
                p = Main.allRect[k == 20 ? 21 : 20]; // 旋轉
                if (canTure(p, Main.x, Main.y)) { // 判斷方塊是否可以旋轉
                    p = Main.rect;
                }
            }
            Main.draw(Main.x, Main.y);// 刷新旋轉後方塊
        }
    }

    public boolean canTure(int i, int j, int o) { // 判斷方塊是否可以旋轉
        int temp = 0x8000;
        for (int k = 0; k < 4; k++) {
            for (int p = 0; p < 4; p++) {
                if ((i & temp) != 0) {
                    if (data[j][o] == 1) { // 有方塊
                        return false; // 方塊不能旋轉
                    }
                }
                o++;
                temp >>= 1; // 右移
            }
            j++;
            o = o - 4;
        }
        return true; // 方塊可以旋轉
    }

    @Override
    public void keyPressed(KeyEvent e) { // 鍵盤監聽
        if (e.getKeyCode() == 37) { // 左鍵鍵盤代碼37
            int temp = 0x8000;
            if (!Main.state) {// 遊戲是否結束
                return;
            }
            if (Main.y <= 1) { // 判斷是否碰到左邊屆
                return;
            }
            for (int i = Main.x; i < Main.x + 4; i++) {
                for (int j = Main.y; j < Main.y + 4; j++) {
                    if ((temp & Main.rect) != 0) { // 判斷移動方向是否有方塊
                        if (data[i][j - 1] == 1) {
                            return;
                        }
                    }
                    temp >>= 1;
                }
            }
            Main.clear(Main.x, Main.y);
            Main.y--;
            Main.draw(Main.x, Main.y);
        }
        if (e.getKeyCode() == 39) {// 右鍵鍵盤代碼39
            int temp = 0x8000;
            int k = Main.x;
            int n = Main.y;
            int g = 1;
            if (!Main.state) {// 遊戲是否結束
                return;
            }
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if ((temp & Main.rect) != 0) {
                        if (n > g) {
                            g = n;
                        }
                    }
                    n++;
                    temp >>= 1;
                }
                k++;
                n = n - 4;
            }
            if (g >= (gameY - 2)) { // 判斷是否碰到右邊屆
                return;
            }
            temp = 0x8000;
            for (int i = Main.x; i < Main.x + 4; i++) {
                for (int j = Main.y; j < Main.y + 4; j++) {
                    if ((temp & Main.rect) != 0) {
                        if (data[i][j + 1] == 1) {
                            return;
                        }
                    }
                    temp >>= 1;
                }
            }
            Main.clear(Main.x, Main.y);
            Main.y++;
            Main.draw(Main.x, Main.y);
        }
        if (e.getKeyCode() == 40) { // 下鍵鍵盤代碼40
            if (!Main.state) {// 遊戲是否結束
                return;
            }
            if (!Main.canfall(Main.x, Main.y)) { // 判斷移動方向是否有方塊
                return; // 不能下墜
            }
            Main.clear(Main.x, Main.y);
            Main.x++; // 移動座標
            Main.draw(Main.x, Main.y);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
