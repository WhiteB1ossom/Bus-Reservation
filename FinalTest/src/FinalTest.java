import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 버스 좌석 GUI를 구현한 클래스입니다.
 * 
 * @author 2021011877 방 경식
 * @version 1.0.1
 * @since 2024-12-16
 * 
 * 	 <li>2024-12-16: 시스템 초기 구현</li>
 *   <li>2024-12-21: 시스템 외관 구현</li>
 *   <li>2024-12-22: 시스템 외관 업데이트</li>
 */
public class FinalTest {

    /** 총 좌석 수를 정의합니다. */
    private static final int TOTAL_SEATS = 45;
    
    /** GUI 창 객체입니다. */
    private static JFrame frame;

    /**
     * 프로그램의 진입점입니다. GUI를 초기화합니다.
     * 
     * @param args 실행 시 전달되는 명령줄 인수
     */
    public FinalTest() {
        // GUI 창 설정
        frame = new JFrame("버스 좌석 예약 시스템");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLayout(new BorderLayout());

        // 좌석 배치 패널 생성
        JPanel seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(9, 1, 10, 10)); // 9행 배치

        List<JButton> seatButtons = new ArrayList<>();
        int seatCounter = 1; // 좌석 번호 할당용 변수

        // 좌석 배치를 생성하고 통로를 표현합니다.
        for (int row = 0; row < 9; row++) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new GridLayout(1, 5, 10, 10)); // 각 행은 5열로 구성

            for (int col = 0; col < 5; col++) {
                if (col == 2) { // 중앙 통로
                    JLabel aisle = new JLabel(); // 통로를 표현하는 빈 공간
                    rowPanel.add(aisle);
                } else {
                    JButton seatButton = new JButton(String.valueOf(seatCounter));
                    seatButtons.add(seatButton);
                    rowPanel.add(seatButton);

                    // 초기 버튼 상태 설정
                    updateButtonStatus(seatButton, false);

                    seatCounter++;
                    // 좌석이 모두 할당되면 루프 종료
                    if (seatCounter > TOTAL_SEATS) break;
                }
            }
            seatPanel.add(rowPanel);
        }

        // 구성 요소를 프레임에 추가
        frame.add(new JLabel("버스 좌석 상태"), BorderLayout.NORTH);
        frame.add(seatPanel, BorderLayout.CENTER);

        // GUI 표시
        frame.setVisible(true);
    }

    /**
     * 버튼 상태를 좌석 예약 여부에 따라 업데이트합니다.
     * 
     * @param button 좌석 버튼 객체
     * @param reserved 좌석 예약 여부
     */
    private static void updateButtonStatus(JButton button, boolean reserved) {
        if (reserved) {
            button.setText("예약됨");
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
        } else {
            button.setText(button.getActionCommand()); // 좌석 번호로 복원
            button.setBackground(Color.GREEN);
            button.setForeground(Color.BLACK);
        }
    }

    /**
     * 메인 메서드로 GUI를 실행합니다.
     * 
     * @param args 실행 시 전달되는 명령줄 인수
     */
    public static void main(String[] args) {
        new FinalTest();
    }
}
