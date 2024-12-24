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
 * ���� �¼� ���� �ý���
 * <p>
 * GUI�� ���� �¼��� �����ϰų� ����� �� ������, ���� �����͸� HashMap�� �����ϰ�,
 * CSV ���� �������� �ڵ� �ҷ����� �� ������ �� �ֽ��ϴ�.
 * </p>
 * 
 * @author 2021011877 �� ���
 * @version 1.6.0
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
 *   <li>2024-12-24: ���� �ڵ� �ҷ����� ��� ����</li>
 *   <li>2024-12-24: ���� ���� ������ CSV�� ����</li>
 *   <li>2024-12-24: �ҷ����� ��ư ���� �� �ڵ� �ҷ����� ����</li>
 * </ul>
 * 
 * @see <a href="https://steffen-lee.tistory.com/27"> ��ü���� ��� ����</a>
 * 
 * @see <a href="https://dev-zephyr.tistory.com/8"> ���� �ý��� ���� ������ũ1</a>
 * @see <a href="https://cross-milestone.tistory.com/100"> ���� �ý��� ���� ������ũ2</a>
 * @see <a href="https://zombiecoder.tistory.com/10"> ���� �ý��� ���� ������ũ3</a>
 * 
 * @see <a href="https://ming9mon.tistory.com/47"> GUI ������Ʈ ������ũ1</a>
 * @see <a href="https://m.blog.naver.com/ddalgikhj/222098797984"> GUI ������Ʈ ������ũ2</a>
 * 
 * @see <a href="https://velog.io/@kkj53051000/JAVASwing-JFileChooser-%ED%8C%8C%EC%9D%BC-%EC%97%B4%EA%B8%B0%EC%B0%BD-%EA%B5%AC%ED%98%84-%EB%B0%8F-%EA%B2%BD%EB%A1%9C-%EC%B6%9C%EB%A0%A5"> ���� ���� �� �ҷ����� ��� ������ũ1</a>
 * @see <a href="https://blog.naver.com/battledocho/220035925900">  ���� ���� �� �ҷ����� ��� ������ũ2</a>
 * 
 */
public class FinalTest {

    /** �� �¼� �� */
    private static final int TOTAL_SEATS = 45;

    /** ���� �����͸� ������ �⺻ ���� �̸� (CSV ����) */
    private static final String FILE_NAME = "reservations.csv";

    /** ��� �̹��� ���� ��� */
    private static final String BACKGROUND_IMAGE = "CJU.png";

    /** ���� JFrame */
    private static JFrame frame;

    /** �¼� ��ư ����Ʈ */
    private static List<JButton> seatButtons = new ArrayList<>();

    /** �¼� ���� ������ �����ϴ� HashMap */
    private static Map<Integer, String> reservations = new HashMap<>();

    /**
     * ���α׷� �ʱ�ȭ �� GUI ����
     */
    public FinalTest() {
        // ���� ������ ����
        frame = new JFrame("���� �¼� ���� �ý���");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 800);

        // ��� �г� ����
        BackgroundPanel backgroundPanel = new BackgroundPanel(BACKGROUND_IMAGE);
        backgroundPanel.setLayout(new BorderLayout());

        // ��� �ؽ�Ʈ ���̺� �߰�
        JLabel headerLabel = new JLabel("���� �¼� ���� (Ŭ���Ͽ� ����/���)", JLabel.CENTER);
        headerLabel.setFont(new Font("���� ���", Font.BOLD, 16));
        headerLabel.setForeground(Color.BLACK);

        // �¼� �г� ����
        JPanel seatPanel = createSeatPanel();

        // �ϴ� ��ư �г� ����
        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false); // ��� ����ȭ
        JButton saveButton = new JButton("����");
        JButton exitButton = new JButton("����");

        // ���� ��ư �̺�Ʈ ó��
        saveButton.addActionListener(e -> saveReservationsToFile());

        // ���� ��ư �̺�Ʈ ó��
        exitButton.addActionListener(e -> System.exit(0));

        // ��ư �гο� ��ư �߰�
        controlPanel.add(saveButton);
        controlPanel.add(exitButton);

        // ��ü �г� ���̾ƿ��� ���� ��� �߰�
        backgroundPanel.add(headerLabel, BorderLayout.NORTH);
        backgroundPanel.add(seatPanel, BorderLayout.CENTER);
        backgroundPanel.add(controlPanel, BorderLayout.SOUTH);

        // ���Ϸκ��� ���� ������ �ҷ�����
        loadReservationsFromFile(FILE_NAME);

        // �����ӿ� �г� ����
        frame.setContentPane(backgroundPanel);
        frame.setVisible(true);
    }

    /**
     * �¼� �г��� �����մϴ�.
     * 
     * @return �¼� �г�
     */
    private JPanel createSeatPanel() {
        JPanel seatPanel = new JPanel(new GridLayout(9, 1, 10, 10));
        seatPanel.setOpaque(false); // ��� ����ȭ

        int seatCounter = 1;
        for (int row = 0; row < 9; row++) {
            JPanel rowPanel = new JPanel(new GridLayout(1, 5, 10, 10));
            rowPanel.setOpaque(false);

            for (int col = 0; col < 5; col++) {
                if (col == 2) {
                    rowPanel.add(new JLabel()); // ���
                } else {
                    JButton seatButton = new JButton(String.valueOf(seatCounter));
                    seatButtons.add(seatButton);
                    rowPanel.add(seatButton);

                    // �ʱ� ��ư ���� ������Ʈ
                    updateButtonStatus(seatButton, false, seatCounter);

                    // ��ư Ŭ�� �̺�Ʈ ó��
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
     * �¼� ��ư Ŭ�� �̺�Ʈ ó��
     * 
     * @param seatNumber �¼� ��ȣ
     * @param seatButton �ش� �¼� ��ư ��ü
     */
    private void handleSeatClick(int seatNumber, JButton seatButton) {
        if (reservations.containsKey(seatNumber)) {
            // ���� ��� Ȯ��
            String reservationInfo = reservations.get(seatNumber);
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "�¼� " + seatNumber + "�� ����(" + reservationInfo + ")�� ����Ͻðڽ��ϱ�?",
                    "���� ���", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // ���� ��� ó��
                reservations.remove(seatNumber);
                updateButtonStatus(seatButton, false, seatNumber);
                JOptionPane.showMessageDialog(frame, "������ ��ҵǾ����ϴ�.");
            }
        } else {
            // ���� ���� �Է� UI ����
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

                // ���� ���� ����
                String reservationInfo = String.format("%s,%s,%s", studentId, name, phone);
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
     * @param reserved ���� ����
     * @param seatNumber �¼� ��ȣ
     */
    private void updateButtonStatus(JButton button, boolean reserved, int seatNumber) {
        if (reserved) {
            button.setText("�����");
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
        } else {
            button.setText(String.valueOf(seatNumber));
            button.setBackground(new Color(194, 220, 255));
            button.setForeground(Color.BLACK);
        }
    }

    /**
     * ���� �����͸� CSV ���Ϸ� �����մϴ�.
     */
    private void saveReservationsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<Integer, String> entry : reservations.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(frame, "���� ������ ����Ǿ����ϴ�.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "���� ���� �� ������ �߻��߽��ϴ�: " + e.getMessage());
        }
    }

    /**
     * CSV ���Ͽ��� ���� �����͸� �ҷ��ɴϴ�.
     * 
     * @param filePath �ҷ��� ���� ���
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
            JOptionPane.showMessageDialog(frame, "���� �б� �� ������ �߻��߽��ϴ�: " + e.getMessage());
        }
    }

    /**
     * ��� �̹����� ǥ���ϴ� �г� Ŭ����
     */
    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
            } catch (Exception e) {
                System.out.println("��� �̹����� �ε��� �� �����ϴ�: " + imagePath);
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
     * ���� �޼���� ���α׷� ����
     */
    public static void main(String[] args) {
        FinalTest app = new FinalTest();
    }
}
