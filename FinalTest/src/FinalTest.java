import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FinalTest extends JFrame {

    /** GUI 창 객체 **/
    private static JFrame frame;

    JButton[] seats = new JButton[45]; // 45개의 좌석 버튼
    JPanel seatPanel;

    /** GUI **/
    FinalTest() {
        // 좌석 패널 초기화
        seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(11, 5, 10, 10)); // 11행 5열 (좌석+통로)
        seatPanel.setPreferredSize(new Dimension(500, 600)); // 패널 크기 지정

        // 프레임 초기화
        frame = new JFrame("버스 좌석 예약 시스템");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLayout(new BorderLayout());

        int seatNumber = 1;

        // 좌석 배치
        for (int row = 0; row < 10; row++) {
            seatPanel.add(createSeatButton(seatNumber++)); // 왼쪽 좌석 1
            seatPanel.add(createSeatButton(seatNumber++)); // 왼쪽 좌석 2
            seatPanel.add(new JPanel()); // 통로 공간
            seatPanel.add(createSeatButton(seatNumber++)); // 오른쪽 좌석 1
            seatPanel.add(createSeatButton(seatNumber++)); // 오른쪽 좌석 2
        }

        // 마지막 줄 좌석 5개 추가
        for (int i = 0; i < 5; i++) {
            seatPanel.add(createSeatButton(seatNumber++));
        }

        // 패널 추가
        frame.add(seatPanel, BorderLayout.CENTER);

        // 패널 크기와 배치 조정
        frame.pack(); // 컴포넌트 크기에 맞게 프레임 크기를 조정
        frame.setVisible(true);
    }

    // 버튼 생성 메서드
    private JButton createSeatButton(int number) {
        JButton button = new JButton(number + "번");
        button.setPreferredSize(new Dimension(80, 30)); // 버튼 크기 설정
        return button;
    }

    public static void main(String[] args) {
        new FinalTest();
    }
}
