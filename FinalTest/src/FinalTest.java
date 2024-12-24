import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
 * @version 1.2
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
 *   <li>2024-12-24: 예약 정보를 파일로 저장하는 기능 추가</li>
 *   <li>2024-12-24: 전화번호 입력 필드 추가 및 정보 입력 UI 개선</li>
 * </ul>
 * 
 * @see <a href="https://dev-zephyr.tistory.com/8">Dev Zephyr</a>
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

    /** 예약 정보를 저장할 파일 이름 */
    private static final String FILE_NAME = "reservations.txt";

    /**
     * 프로그램의 진입점입니다. GUI를 초기화하고 예약 시스템을 추가합니다.
     * 
     * @param args 실행 시 전달되는 명령줄 인수
     */
    public FinalTest() {
    	
    	// 파일에서 예약 정보 불러오기
    	
        // GUI 창 설정
        frame = new JFrame("버스 좌석 예약 시스템");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 800);
        frame.setLayout(new BorderLayout());
        
        // 하단 버튼 패널 생성
        JPanel controlPanel = new JPanel();
        JButton saveButton = new JButton("저장");
        JButton exitButton = new JButton("종료");
        
        // 저장 버튼 클릭 이벤트
        saveButton.addActionListener(e -> saveReservationsToFile());
        // 종료 버튼 클릭 이벤트
        exitButton.addActionListener(e -> System.exit(0));
        
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
        
        controlPanel.add(saveButton);
        controlPanel.add(exitButton);
        
        // 구성 요소를 프레임에 추가
        frame.add(new JLabel("버스 좌석 상태 (클릭하여 예약/취소)"), BorderLayout.NORTH);
        frame.add(seatPanel, BorderLayout.CENTER);

//    	loadReservationsFromFile(seatButtons);
        
        // GUI 표시 및 하단 컨트롤 패널 추가
        frame.add(controlPanel, BorderLayout.SOUTH);
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
            // 학번, 이름, 전화번호를 입력받는 폼 생성
            JPanel inputPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel studentIdLabel = new JLabel("학번:");
            JLabel nameLabel = new JLabel("이름:");
            JLabel phoneLabel = new JLabel("전화번호:");
            JTextField studentIdField = new JTextField(10);
            JTextField nameField = new JTextField(10);
            JTextField phoneField = new JTextField(10);

            gbc.gridx = 0; gbc.gridy = 0;
            inputPanel.add(studentIdLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(studentIdField, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            inputPanel.add(nameLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(nameField, gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            inputPanel.add(phoneLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(phoneField, gbc);

            int result = JOptionPane.showConfirmDialog(frame, inputPanel, "예약 정보 입력",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String studentId = studentIdField.getText().trim();
                String name = nameField.getText().trim();
                String phone = phoneField.getText().trim();

                if (studentId.isEmpty() || name.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "모든 정보를 입력하세요.");
                    return;
                }

                // 학번, 이름, 전화번호를 함께 저장
                String reservationInfo = String.format("학번: %s 이름: %s 전화번호: %s", studentId, name, phone);
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
     * 저장 버튼 클릭 시 HashMap에 저장된 정보를 파일로 저장합니다.
     */
    private static void saveReservationsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            System.out.println("저장 중... 예약 데이터:");
            for (Map.Entry<Integer, String> entry : reservations.entrySet()) {
                // 저장 형식을 '좌석번호: 숫자, 학번: -----, 이름: ----, 전화번호: ---'로 변경
                String formattedData = String.format("좌석번호: %d, %s", entry.getKey(), entry.getValue());
                writer.write(formattedData);
                writer.newLine();
                System.out.println(formattedData); // 콘솔에 출력
            }
            JOptionPane.showMessageDialog(frame, "예약 정보가 파일에 저장되었습니다: " + FILE_NAME);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "파일 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
//    private static void loadReservationsFromFile(List<JButton> seatButtons) {
//        File file = new File(FILE_NAME);
//        if (!file.exists()) {
//            System.out.println("예약 파일이 존재하지 않습니다.");
//            return;
//        }
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                try {
//                    String[] parts = line.split(", ");
//                    if (parts.length >= 4) {
//                        int seatNumber = Integer.parseInt(parts[0].split(": ")[1]);
//                        String reservationInfo = String.format("%s, %s, %s",
//                            parts[1], parts[2], parts[3]); // 학번, 이름, 전화번호 조합
//
//                        // HashMap에 저장
//                        reservations.put(seatNumber, reservationInfo);
//
//                        // 버튼 상태 업데이트
//                        for (JButton button : seatButtons) {
//                            if (button.getText().equals(String.valueOf(seatNumber))) {
//                                updateButtonStatus(button, true, seatNumber);
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    System.out.println("잘못된 형식의 데이터: " + line);
//                }
//            }
//            System.out.println("예약 정보 로드 완료.");
//        } catch (IOException e) {
//            System.out.println("파일 읽기 오류 발생: " + e.getMessage());
//        }
//    }


    /**
     * 메인 메서드로 GUI를 실행합니다.
     * 
     * @param args 실행 시 전달되는 명령줄 인수
     */
    public static void main(String[] args) {
        new FinalTest();
    }
}
