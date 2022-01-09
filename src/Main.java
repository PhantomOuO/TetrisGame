import java.awt.Color;
import java.util.Random;
import java.awt.*;
public class Main {
    MainFrame frame = new MainFrame();
    static Random rng = new Random();
    static boolean state; // 判斷是否結束
    static int rect; // 目前方塊
    static int x, y; // 方塊位置
    int time = 1000; // 執行緒暫停時間
    static int[] allRect = new int[] { 0x00cc, 0x8888, 0x000f, 0x888f, 0xf888, 0xf111, 0x111f, 0x0eee, 0xffff, 0x0008, // 所有方塊
            0x0888, 0x000e, 0x0088, 0x000c, 0x08c8, 0x00e4, 0x04c4, 0x004e, 0x08c4, 0x006c, 0x04c8, 0x00c6 };

    /*
     * EX:
     * 0000 = 0x00cc
     * 0000
     * 1100
     * 1100
     */
    public static void main(String[] args) throws Exception {
        Main main = new Main();// 建立物件
        state = true;
        main.gameBegin();
    }

    public void gameBegin() { // 遊戲開始
        Font font = new Font("微軟正黑體", Font.BOLD, 20);
        while (true) {
            if (!state) { 
                break;// 遊戲結束
            }
            gameRun(); // 開始遊戲
        }
        MainFrame.gameState.setFont(font);
        MainFrame.gameState.setForeground(Color.RED);
        MainFrame.gameState.setText("        已結束!       ");
    }

    public void randomRect() { // 產生隨機墜落方塊
        Random random = new Random();
        rect = allRect[random.nextInt(22)]; // 隨機選擇allRect一個方塊放到Rect
    }

    public void gameRun() { // 遊戲進行
        randomRect();
        x = 0;
        y = 5;
        for (int i = 0; i < MainFrame.gameX; i++) {
            try {
                Thread.sleep(time); // 執行緒暫停使方塊下墜有停頓感
                if (!canfall(x, y)) { // 若不能再下墜
                    preData(x, y); // 設定目前位置data為有方塊
                    for (int j = x; j < x + 4; j++) {
                        int sum = 0; // 總和各列有方塊數
                        for (int p = 1; p <= (MainFrame.gameY - 2); p++) {
                            if (MainFrame.data[j][p] == 1) {
                                sum++;
                            }
                        }
                        if (sum == (MainFrame.gameY - 2)) { // 判斷是否符合消除方塊條件
                            removeRow(j);// 刪除該行
                        }
                    }
                    for (int j = 1; j < (MainFrame.gameY - 2); j++) { // 遊戲失敗
                        System.out.print(MainFrame.data[3][j]);
                        if (MainFrame.data[3][j] == 1) {
                            state = false;
                            break;
                        }
                    }
                    break;
                } else { // 方塊下墜
                    x++;
                    fall(x, y);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean canfall(int i, int j) {// 判斷能否再下墜
        int temp = 0x8000;
        for (int k = 0; k < 4; k++) {
            for (int p = 0; p < 4; p++) {
                if ((temp & rect) != 0) {
                    if (MainFrame.data[i + 1][j] == 1) { // 若該位置下一行data為有方塊
                        return false; // 不可再下墜
                    }
                }
                j++;
                temp >>= 1; // 右移行
            }
            i++;
            j = j - 4; // 返回第一列
        }
        return true; // 可以下墜
    }

    public void preData(int i, int j) { // 將方塊停止位置data設為1
        int temp = 0x8000;
        for (int k = 0; k < 4; k++) {
            for (int p = 0; p < 4; p++) {
                if ((temp & rect) != 0) {
                    MainFrame.data[i][j] = 1;// 設該位置data為有方塊
                }
                j++;
                temp >>= 1; // 右移每行
            }
            i++;
            j = j - 4; // 返回第一列
        }
    }

    public void removeRow(int row) { // 刪除方塊已滿的行
        Font font = new Font("微軟正黑體", Font.BOLD, 20);
        int temp = 50;
        for (int k = row; k >= 1; k--) {
            for (int p = 1; p <= (MainFrame.gameY - 2); p++) {
                MainFrame.data[k][p] = MainFrame.data[k - 1][p]; // 將該行每格data設無方塊
            }
        }
        reFresh(row);

        if (time > temp) { // 下墜加速
            time -= temp;
        }
        MainFrame.score += temp; // 消除行 加分50
        MainFrame.gameScore.setFont(font);
        MainFrame.gameScore.setForeground(Color.RED);
        MainFrame.gameScore.setText("          " + "          "+ MainFrame.score+"分");
    }

    public void reFresh(int row) { // 消除行後刷新介面
        for (int k = row; k >= 1; k--) {
            for (int p = 1; p <= (MainFrame.gameY - 2); p++) {
                if (MainFrame.data[k][p] == 1) {
                    MainFrame.text[k][p].setBackground(randomColor());
                } else {
                    MainFrame.text[k][p].setBackground(Color.WHITE);
                }
            }
        }
    }
    
    public static Color randomColor() { // 方塊隨機色
        int r = rng.nextInt(255);
        int g = rng.nextInt(255);
        int b = rng.nextInt(255);
        return new Color(r, g, b);
    }

    public void fall(int i, int j) { // 使方塊下墜一列
        if (i > 0) {
            clear(i - 1, j); // 清除上一列方塊
        }
        draw(i, j);
    }

    public static void clear(int i, int j) { // 清除原下墜方塊位置
        int temp = 0x8000;
        for (int k = 0; k < 4; k++) {
            for (int p = 0; p < 4; p++) {
                if ((temp & rect) != 0) { // 若此處有方塊
                    MainFrame.text[i][j].setBackground(Color.WHITE); // 設原位置為白色
                }
                j++;
                temp >>= 1;
            }
            i++;
            j = j - 4;
        }
    }

    public static void draw(int i, int j) {//停下後方塊
        int temp = 0x8000;
        for (int k = 0; k < 4; k++) {
            for (int p = 0; p < 4; p++) {
                if ((temp & rect) != 0) { // 若此處有方塊
                    MainFrame.text[i][j].setBackground(randomColor()); // 設位置藍色
                }
                j++;
                temp >>= 1;
            }
            i++;
            j = j - 4;
        }
    }

}