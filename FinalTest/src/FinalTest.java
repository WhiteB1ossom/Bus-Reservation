import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 버스 좌석 예약 시스템을 구현한 클래스입니다.
 * GUI를 통해 좌석을 예약하거나 취소할 수 있으며, 예약 데이터를 파일에 저장하고 불러올 수 있습니다.
 * 
 * @author 2021011877 방 경식
 * @version 1.0
 * @since 2024-12-16
 * 
 * @created 2024-12-16
 * 
 * @changelog
 * <ul>
 *   <li>2024-12-16: 시스템 초기 구현</li>
 *   <li>2024-12-21: 시스템 외관 구현</li>
 * </ul>
 */

public class FinalTest extends JFrame {

    /** GUI 창 객체 **/
    private static JFrame frame;
    
    JButton[] seats = new JButton[45]; // 45개의 좌석 버튼
    JPanel seatPanel;

    /** GUI **/
    FinalTest() {
        seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(11, 5, 10, 10)); // 11행 5열로 설정 (좌석+통로)
        
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

        // 프레임 설정
        this.add(seatPanel);
        this.setSize(600, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    // 버튼 생성 메서드
    private JButton createSeatButton(int number) {
        JButton button = new JButton(number + "번");
        // 버튼에 이벤트나 스타일을 추가할 수 있음
        return button;
    }

    public static void main(String[] args) {
        new FinalTest();
    }
}
