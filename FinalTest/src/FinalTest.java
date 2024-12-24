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
 * ���� �¼� ���� �ý����� ������ Ŭ�����Դϴ�.
 * GUI�� ���� �¼��� �����ϰų� ����� �� ������, ���� �����͸� HashMap�� �����մϴ�.
 * 
 * @author 2021011877 �� ���
 * @version 1.2
 * @since 2024-12-16
 * 
 * @created 2024-12-16
 * 
 * @changelog
 * <ul>
 *   <li>2024-12-16: �ý��� �ʱ� ����</li>
 *   <li>2024-12-21: �ý��� �ܰ� ����</li>
 *   <li>2024-12-22: �ý��� �ܰ� ������Ʈ</li>
 *   <li>2024-12-22: ���� �ý����� HashMap���� �����ϵ��� ��� Ȯ��</li>
 *   <li>2024-12-24: ���� ������ ���Ϸ� �����ϴ� ��� �߰�</li>
 *   <li>2024-12-24: ��ȭ��ȣ �Է� �ʵ� �߰� �� ���� �Է� UI ����</li>
 * </ul>
 * 
 * @see <a href="https://dev-zephyr.tistory.com/8">Dev Zephyr</a>
 * @see <a href="https://cross-milestone.tistory.com/100"> ���� �ý��� ���� ������ũ2</a>
 * @see <a href="https://zombiecoder.tistory.com/10"> ���� �ý��� ���� ������ũ3</a>
 */
public class FinalTest {

    /** �� �¼� ���� �����մϴ�. */
    private static final int TOTAL_SEATS = 45;

    /** ����� �¼� ��ȣ�� ������ ������ �����մϴ�. */
    private static Map<Integer, String> reservations = new HashMap<>();

    /** GUI â ��ü�Դϴ�. */
    private static JFrame frame;

    /** ���� ������ ������ ���� �̸� */
    private static final String FILE_NAME = "reservations.txt";

    /**
     * ���α׷��� �������Դϴ�. GUI�� �ʱ�ȭ�ϰ� ���� �ý����� �߰��մϴ�.
     * 
     * @param args ���� �� ���޵Ǵ� ����� �μ�
     */
    public FinalTest() {
    	
    	// ���Ͽ��� ���� ���� �ҷ�����
    	
        // GUI â ����
        frame = new JFrame("���� �¼� ���� �ý���");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 800);
        frame.setLayout(new BorderLayout());
        
        // �ϴ� ��ư �г� ����
        JPanel controlPanel = new JPanel();
        JButton saveButton = new JButton("����");
        JButton exitButton = new JButton("����");
        
        // ���� ��ư Ŭ�� �̺�Ʈ
        saveButton.addActionListener(e -> saveReservationsToFile());
        // ���� ��ư Ŭ�� �̺�Ʈ
        exitButton.addActionListener(e -> System.exit(0));
        
        // �¼� ��ġ �г� ����
        JPanel seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(9, 1, 10, 10)); // 9�� ��ġ

        List<JButton> seatButtons = new ArrayList<>();
        int seatCounter = 1; // �¼� ��ȣ �Ҵ�� ����

        // �¼� ��ġ�� �����ϰ� ��θ� ǥ���մϴ�.
        for (int row = 0; row < 9; row++) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new GridLayout(1, 5, 10, 10)); // �� ���� 5���� ����

            for (int col = 0; col < 5; col++) {
                if (col == 2) { // �߾� ���
                    JLabel aisle = new JLabel(); // ��θ� ǥ���ϴ� �� ����
                    rowPanel.add(aisle);
                } else {
                    JButton seatButton = new JButton(String.valueOf(seatCounter));
                    seatButtons.add(seatButton);
                    rowPanel.add(seatButton);

                    // �ʱ� ��ư ���� ����
                    updateButtonStatus(seatButton, false, seatCounter);

                    int seatNumber = seatCounter; // ���ٿ��� ������ seatNumber
                    seatButton.addActionListener(e -> handleSeatClick(seatNumber, seatButton));

                    seatCounter++;
                    // �¼��� ��� �Ҵ�Ǹ� ���� ����
                    if (seatCounter > TOTAL_SEATS) break;
                }
            }
            seatPanel.add(rowPanel);
        }
        
        controlPanel.add(saveButton);
        controlPanel.add(exitButton);
        
        // ���� ��Ҹ� �����ӿ� �߰�
        frame.add(new JLabel("���� �¼� ���� (Ŭ���Ͽ� ����/���)"), BorderLayout.NORTH);
        frame.add(seatPanel, BorderLayout.CENTER);

//    	loadReservationsFromFile(seatButtons);
        
        // GUI ǥ�� �� �ϴ� ��Ʈ�� �г� �߰�
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    /**
     * �¼� ��ư�� Ŭ������ �� ���� �Ǵ� ��� ������ ó���մϴ�.
     * 
     * @param seatNumber �¼� ��ȣ
     * @param seatButton �¼��� �ش��ϴ� ��ư ��ü
     */
    private static void handleSeatClick(int seatNumber, JButton seatButton) {
        if (reservations.containsKey(seatNumber)) {
            // ���� ���
            String reservationInfo = reservations.get(seatNumber);
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "�¼� " + seatNumber + "�� ����(" + reservationInfo + ")�� ����Ͻðڽ��ϱ�?",
                    "���� ���", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                reservations.remove(seatNumber);
                updateButtonStatus(seatButton, false, seatNumber); // �¼� ��ȣ ����
                JOptionPane.showMessageDialog(frame, "������ ��ҵǾ����ϴ�.");
            }
        } else {
            // �й�, �̸�, ��ȭ��ȣ�� �Է¹޴� �� ����
            JPanel inputPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel studentIdLabel = new JLabel("�й�:");
            JLabel nameLabel = new JLabel("�̸�:");
            JLabel phoneLabel = new JLabel("��ȭ��ȣ:");
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

            int result = JOptionPane.showConfirmDialog(frame, inputPanel, "���� ���� �Է�",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String studentId = studentIdField.getText().trim();
                String name = nameField.getText().trim();
                String phone = phoneField.getText().trim();

                if (studentId.isEmpty() || name.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "��� ������ �Է��ϼ���.");
                    return;
                }

                // �й�, �̸�, ��ȭ��ȣ�� �Բ� ����
                String reservationInfo = String.format("�й�: %s �̸�: %s ��ȭ��ȣ: %s", studentId, name, phone);
                reservations.put(seatNumber, reservationInfo);
                updateButtonStatus(seatButton, true, seatNumber);
                JOptionPane.showMessageDialog(frame, "�¼��� ����Ǿ����ϴ�.");
            }
        }
    }

    /**
     * ��ư ���¸� �¼� ���� ���ο� ���� ������Ʈ�մϴ�.
     * 
     * @param button �¼� ��ư ��ü
     * @param reserved �¼� ���� ����
     * @param seatNumber �¼� ��ȣ (�ؽ�Ʈ ���� �� �ʿ�)
     */
    private static void updateButtonStatus(JButton button, boolean reserved, int seatNumber) {
        if (reserved) {
            button.setText("�����");
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
        } else {
            // �¼� ��ȣ�� �ؽ�Ʈ ����
            button.setText(String.valueOf(seatNumber));
            button.setBackground(Color.GREEN);
            button.setForeground(Color.BLACK);
        }
    }

    /**
     * ���� ��ư Ŭ�� �� HashMap�� ����� ������ ���Ϸ� �����մϴ�.
     */
    private static void saveReservationsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            System.out.println("���� ��... ���� ������:");
            for (Map.Entry<Integer, String> entry : reservations.entrySet()) {
                // ���� ������ '�¼���ȣ: ����, �й�: -----, �̸�: ----, ��ȭ��ȣ: ---'�� ����
                String formattedData = String.format("�¼���ȣ: %d, %s", entry.getKey(), entry.getValue());
                writer.write(formattedData);
                writer.newLine();
                System.out.println(formattedData); // �ֿܼ� ���
            }
            JOptionPane.showMessageDialog(frame, "���� ������ ���Ͽ� ����Ǿ����ϴ�: " + FILE_NAME);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "���� ���� �� ������ �߻��߽��ϴ�: " + e.getMessage());
        }
    }
    
//    private static void loadReservationsFromFile(List<JButton> seatButtons) {
//        File file = new File(FILE_NAME);
//        if (!file.exists()) {
//            System.out.println("���� ������ �������� �ʽ��ϴ�.");
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
//                            parts[1], parts[2], parts[3]); // �й�, �̸�, ��ȭ��ȣ ����
//
//                        // HashMap�� ����
//                        reservations.put(seatNumber, reservationInfo);
//
//                        // ��ư ���� ������Ʈ
//                        for (JButton button : seatButtons) {
//                            if (button.getText().equals(String.valueOf(seatNumber))) {
//                                updateButtonStatus(button, true, seatNumber);
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    System.out.println("�߸��� ������ ������: " + line);
//                }
//            }
//            System.out.println("���� ���� �ε� �Ϸ�.");
//        } catch (IOException e) {
//            System.out.println("���� �б� ���� �߻�: " + e.getMessage());
//        }
//    }


    /**
     * ���� �޼���� GUI�� �����մϴ�.
     * 
     * @param args ���� �� ���޵Ǵ� ����� �μ�
     */
    public static void main(String[] args) {
        new FinalTest();
    }
}
