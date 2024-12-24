import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 버스 좌석 예약 시스템
 * <p>
 * GUI를 통해 좌석을 예약하거나 취소할 수 있으며, 예약 데이터를 HashMap에 저장하고,
 * CSV 파일 형식으로 자동 불러오기 및 저장할 수 있습니다.
 * </p>
 * 
 * @author 2021011877 방 경식
 * @version 1.6.0
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
 *   <li>2024-12-24: 예약 자동 불러오기 기능 구현</li>
 *   <li>2024-12-24: 파일 저장 형식을 CSV로 변경</li>
 *   <li>2024-12-24: 불러오기 버튼 제거 및 자동 불러오기 구현</li>
 * </ul>
 * 
 * @see <a href="https://steffen-lee.tistory.com/27"> 전체적인 기능 참조</a>
 * 
 * @see <a href="https://dev-zephyr.tistory.com/8"> 예약 시스템 구축 참조링크1</a>
 * @see <a href="https://cross-milestone.tistory.com/100"> 예약 시스템 구축 참조링크2</a>
 * @see <a href="https://zombiecoder.tistory.com/10"> 예약 시스템 구축 참조링크3</a>
 * 
 * @see <a href="https://ming9mon.tistory.com/47"> GUI 업데이트 참조링크1</a>
 * @see <a href="https://m.blog.naver.com/ddalgikhj/222098797984"> GUI 업데이트 참조링크2</a>
 * 
 * @see <a href="https://velog.io/@kkj53051000/JAVASwing-JFileChooser-%ED%8C%8C%EC%9D%BC-%EC%97%B4%EA%B8%B0%EC%B0%BD-%EA%B5%AC%ED%98%84-%EB%B0%8F-%EA%B2%BD%EB%A1%9C-%EC%B6%9C%EB%A0%A5"> 파일 저장 및 불러오기 기능 참조링크1</a>
 * @see <a href="https://blog.naver.com/battledocho/220035925900">  파일 저장 및 불러오기 기능 참조링크2</a>
 * 
 */
public class FinalTest {

    /** 총 좌석 수 */
    private static final int TOTAL_SEATS = 45;

    /** 예약 데이터를 저장할 기본 파일 이름 (CSV 형식) */
    private static final String FILE_NAME = "reservations.csv";

    /** 배경 이미지 파일 경로 */
    private static final String BACKGROUND_IMAGE = "CJU.png";

    /** 메인 JFrame */
    private static JFrame frame;

    /** 좌석 버튼 리스트 */
    private static List<JButton> seatButtons = new ArrayList<>();

    /** 좌석 예약 정보를 저장하는 HashMap */
    private static Map<Integer, String> reservations = new HashMap<>();

    /**
     * 프로그램 초기화 및 GUI 설정
     */
    public FinalTest() {
        // 메인 프레임 설정
        frame = new JFrame("버스 좌석 예약 시스템");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 800);

        // 배경 패널 설정
        BackgroundPanel backgroundPanel = new BackgroundPanel(BACKGROUND_IMAGE);
        backgroundPanel.setLayout(new BorderLayout());

        // 상단 텍스트 레이블 추가
        JLabel headerLabel = new JLabel("버스 좌석 상태 (클릭하여 예약/취소)", JLabel.CENTER);
        headerLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        headerLabel.setForeground(Color.BLACK);

        // 좌석 패널 생성
        JPanel seatPanel = createSeatPanel();

        // 하단 버튼 패널 생성
        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false); // 배경 투명화
        JButton saveButton = new JButton("저장");
        JButton exitButton = new JButton("종료");

        // 저장 버튼 이벤트 처리
        saveButton.addActionListener(e -> saveReservationsToFile());

        // 종료 버튼 이벤트 처리
        exitButton.addActionListener(e -> System.exit(0));

        // 버튼 패널에 버튼 추가
        controlPanel.add(saveButton);
        controlPanel.add(exitButton);

        // 전체 패널 레이아웃에 구성 요소 추가
        backgroundPanel.add(headerLabel, BorderLayout.NORTH);
        backgroundPanel.add(seatPanel, BorderLayout.CENTER);
        backgroundPanel.add(controlPanel, BorderLayout.SOUTH);

        // 파일로부터 예약 데이터 불러오기
        loadReservationsFromFile(FILE_NAME);

        // 프레임에 패널 설정
        frame.setContentPane(backgroundPanel);
        frame.setVisible(true);
    }

    /**
     * 좌석 패널을 생성합니다.
     * 
     * @return 좌석 패널
     */
    private JPanel createSeatPanel() {
        JPanel seatPanel = new JPanel(new GridLayout(9, 1, 10, 10));
        seatPanel.setOpaque(false); // 배경 투명화

        int seatCounter = 1;
        for (int row = 0; row < 9; row++) {
            JPanel rowPanel = new JPanel(new GridLayout(1, 5, 10, 10));
            rowPanel.setOpaque(false);

            for (int col = 0; col < 5; col++) {
                if (col == 2) {
                    rowPanel.add(new JLabel()); // 통로
                } else {
                    JButton seatButton = new JButton(String.valueOf(seatCounter));
                    seatButtons.add(seatButton);
                    rowPanel.add(seatButton);

                    // 초기 버튼 상태 업데이트
                    updateButtonStatus(seatButton, false, seatCounter);

                    // 버튼 클릭 이벤트 처리
                    int seatNumber = seatCounter;
                    seatButton.addActionListener(e -> handleSeatClick(seatNumber, seatButton));

                    seatCounter++;
                    if (seatCounter > TOTAL_SEATS) break;
                }
            }
            seatPanel.add(rowPanel);
        }
        return seatPanel;
    }

    /**
     * 좌석 버튼 클릭 이벤트 처리
     * 
     * @param seatNumber 좌석 번호
     * @param seatButton 해당 좌석 버튼 객체
     */
    private void handleSeatClick(int seatNumber, JButton seatButton) {
        if (reservations.containsKey(seatNumber)) {
            // 예약 취소 확인
            String reservationInfo = reservations.get(seatNumber);
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "좌석 " + seatNumber + "번 예약(" + reservationInfo + ")을 취소하시겠습니까?",
                    "예약 취소", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // 예약 취소 처리
                reservations.remove(seatNumber);
                updateButtonStatus(seatButton, false, seatNumber);
                JOptionPane.showMessageDialog(frame, "예약이 취소되었습니다.");
            }
        } else {
            // 예약 정보 입력 UI 생성
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

                // 예약 정보 저장
                String reservationInfo = String.format("%s,%s,%s", studentId, name, phone);
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
     * @param reserved 예약 여부
     * @param seatNumber 좌석 번호
     */
    private void updateButtonStatus(JButton button, boolean reserved, int seatNumber) {
        if (reserved) {
            button.setText("예약됨");
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
        } else {
            button.setText(String.valueOf(seatNumber));
            button.setBackground(new Color(194, 220, 255));
            button.setForeground(Color.BLACK);
        }
    }

    /**
     * 예약 데이터를 CSV 파일로 저장합니다.
     */
    private void saveReservationsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<Integer, String> entry : reservations.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(frame, "예약 정보가 저장되었습니다.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "파일 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * CSV 파일에서 예약 데이터를 불러옵니다.
     * 
     * @param filePath 불러올 파일 경로
     */
    private void loadReservationsFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reservations.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int seatNumber = Integer.parseInt(parts[0]);
                    String reservationInfo = String.format("%s,%s,%s", parts[1], parts[2], parts[3]);

                    reservations.put(seatNumber, reservationInfo);
                    if (seatNumber >= 1 && seatNumber <= TOTAL_SEATS) {
                        JButton button = seatButtons.get(seatNumber - 1);
                        updateButtonStatus(button, true, seatNumber);
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "파일 읽기 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 배경 이미지를 표시하는 패널 클래스
     */
    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
            } catch (Exception e) {
                System.out.println("배경 이미지를 로드할 수 없습니다: " + imagePath);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    /**
     * 메인 메서드로 프로그램 실행
     */
    public static void main(String[] args) {
        FinalTest app = new FinalTest();
    }
}
