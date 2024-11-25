import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.GridLayout;

public class FinalTest extends JFrame {
	
    int index = 0;
    String[] Num = new String[45]; // 숫자 배열
    JButton[] Btn = new JButton[Num.length]; // 버튼 배열
    JPanel BtnPanel1, BtnPanel2; //버튼 패널

    FinalTest() {
    	
    	BtnPanel1 = new JPanel();
    	BtnPanel2 = new JPanel();

    	BtnPanel1.setLayout(new GridLayout(10, 4, 5, 5));
        BtnPanel2.setLayout(new FlowLayout());
    	
        // 1~45까지 숫자를 배열에 저장
        for (int i = 0; i < Num.length; i++) {
            Num[i] = String.valueOf(i + 1); // 숫자를 문자열로 변환하여 배열에 저장
        }

        // 향상된 for문으로 버튼 생성 및 추가
        for (int i = 0; i < Num.length; i++) {
        	
            Btn[i] = new JButton(Num[i]); // 버튼 생성 시 Num 배열의 값을 텍스트로 사용
            
            if (i < 40) {
                BtnPanel1.add(Btn[i]); // 첫 번째 패널에 추가
            } else {
                BtnPanel2.add(Btn[i]); // 두 번째 패널에 추가
            }
        }

        // 패널 추가 및 프레임 설정
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
