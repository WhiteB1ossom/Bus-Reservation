import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 버스 좌석 예약 시스템을 구현한 클래스입니다.
 * GUI를 통해 좌석을 예약하거나 취소할 수 있으며, 예약 데이터를 HashMap에 저장합니다.
 * 
 * @author 2021011877 방 경식
 * @version 1.1
 * @since 2024-12-16
 * 
 * @created 2024-12-16
 * 
 * @changelog
 * <ul>
 *   <li>2024-12-16: 시스템 초기 구현</li>
 *   <li>2024-12-21: 시스템 외관 구현</li>
 *   <li>2024-12-22: 시스템 외관 업데이트</li>
 *   <li>2024-12-22: 예약 시스템을 HashMap으로 저장하도록 기능 확장</li>
 * </ul>
 * 
 * @see <a href="https://dev-zephyr.tistory.com/8">Dev Zephyr"> 예약 시스템 구축 참조링크1</a>
 * @see <a href="https://cross-milestone.tistory.com/100"> 예약 시스템 구축 참조링크2</a>
 * @see <a href="https://zombiecoder.tistory.com/10"> 예약 시스템 구축 참조링크3</a>
 */
public class FinalTest {

    /** 총 좌석 수를 정의합니다. */
    private static final int TOTAL_SEATS = 45;

    /** 예약된 좌석 번호와 예약자 정보를 매핑합니다. */
    private static Map<Integer, String> reservations = new HashMap<>();

    /** GUI 창 객체입니다. */
    private static JFrame frame;

    /**
     * 프로그램의 진입점입니다. GUI를 초기화하고 예약 시스템을 추가합니다.
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
                    updateButtonStatus(seatButton, false, seatCounter);

                    int seatNumber = seatCounter; // 람다에서 참조할 seatNumber
                    seatButton.addActionListener(e -> handleSeatClick(seatNumber, seatButton));

                    seatCounter++;
                    // 좌석이 모두 할당되면 루프 종료
                    if (seatCounter > TOTAL_SEATS) break;
                }
            }
            seatPanel.add(rowPanel);
        }

        // 구성 요소를 프레임에 추가
        frame.add(new JLabel("버스 좌석 상태 (클릭하여 예약/취소)"), BorderLayout.NORTH);
        frame.add(seatPanel, BorderLayout.CENTER);

        // GUI 표시
        frame.setVisible(true);
    }

    /**
     * 좌석 버튼을 클릭했을 때 예약 또는 취소 동작을 처리합니다.
     * 
     * @param seatNumber 좌석 번호
     * @param seatButton 좌석에 해당하는 버튼 객체
     */
    private static void handleSeatClick(int seatNumber, JButton seatButton) {
        if (reservations.containsKey(seatNumber)) {
            // 예약 취소
            String reservationInfo = reservations.get(seatNumber);
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "좌석 " + seatNumber + "번 예약(" + reservationInfo + ")을 취소하시겠습니까?",
                    "예약 취소", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                reservations.remove(seatNumber);
                updateButtonStatus(seatButton, false, seatNumber); // 좌석 번호 전달
                JOptionPane.showMessageDialog(frame, "예약이 취소되었습니다.");
            }
        } else {
            // 학번과 이름을 입력받는 폼 생성
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(2, 2, 5, 5)); // 간격 5픽셀로 설정

            JLabel studentIdLabel = new JLabel("학번:");
            JLabel nameLabel = new JLabel("이름:");
            JTextField studentIdField = new JTextField();
            JTextField nameField = new JTextField();

            inputPanel.add(studentIdLabel);
            inputPanel.add(studentIdField);
            inputPanel.add(nameLabel);
            inputPanel.add(nameField);

            int result = JOptionPane.showConfirmDialog(frame, inputPanel, "예약 정보 입력",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String studentId = studentIdField.getText().trim();
                String name = nameField.getText().trim();

                if (studentId.isEmpty() || name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "학번과 이름을 모두 입력하세요.");
                    return;
                }

                // 학번과 이름을 함께 저장
                String reservationInfo = "학번: " + studentId + " 이름: " + name;
                reservations.put(seatNumber, reservationInfo);
                updateButtonStatus(seatButton, true, seatNumber);
                JOptionPane.showMessageDialog(frame, "좌석이 예약되었습니다.");
            }
        }
    }

    /**
     * 버튼 상태를 좌석 예약 여부에 따라 업데이트합니다.
     * 
     * @param button 좌석 버튼 객체
     * @param reserved 좌석 예약 여부
     * @param seatNumber 좌석 번호 (텍스트 복원 시 필요)
     */
    private static void updateButtonStatus(JButton button, boolean reserved, int seatNumber) {
        if (reserved) {
            button.setText("예약됨");
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
        } else {
            // 좌석 번호로 텍스트 복원
            button.setText(String.valueOf(seatNumber));
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
