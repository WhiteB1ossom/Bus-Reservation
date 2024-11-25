import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.GridLayout;

public class FinalTest extends JFrame {
	
    int index = 0;
    String[] Num = new String[45]; // ���� �迭
    JButton[] Btn = new JButton[Num.length]; // ��ư �迭
    JPanel BtnPanel1, BtnPanel2; //��ư �г�

    FinalTest() {
    	
    	BtnPanel1 = new JPanel();
    	BtnPanel2 = new JPanel();

    	BtnPanel1.setLayout(new GridLayout(10, 4, 5, 5));
        BtnPanel2.setLayout(new FlowLayout());
    	
        // 1~45���� ���ڸ� �迭�� ����
        for (int i = 0; i < Num.length; i++) {
            Num[i] = String.valueOf(i + 1); // ���ڸ� ���ڿ��� ��ȯ�Ͽ� �迭�� ����
        }

        // ���� for������ ��ư ���� �� �߰�
        for (int i = 0; i < Num.length; i++) {
        	
            Btn[i] = new JButton(Num[i]); // ��ư ���� �� Num �迭�� ���� �ؽ�Ʈ�� ���
            
            if (i < 40) {
                BtnPanel1.add(Btn[i]); // ù ��° �гο� �߰�
            } else {
                BtnPanel2.add(Btn[i]); // �� ��° �гο� �߰�
            }
        }

        // �г� �߰� �� ������ ����
        this.setLayout(new GridLayout(2, 1));
        this.add(BtnPanel1);
        this.add(BtnPanel2);
        
        this.setSize(400, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new FinalTest();
    }
}
