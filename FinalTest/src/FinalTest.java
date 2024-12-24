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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * ���� �¼� ���� �ý���
 * <p>
 * GUI�� ���� �¼��� �����ϰų� ����� �� ������, ���� �����͸� HashMap�� �����մϴ�.
 * </p>
 * 
 * @author 2021011877 �� ���
 * @version 1.3
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
 *   <li>2024-12-24: ���� �ҷ����� ��� ����</li>
 *   <li>2024-12-24: ��ü ��� �̹��� �߰� �� UI ����</li>
 * </ul>
 */
public class FinalTest {

    /** �� �¼� �� */
    private static final int TOTAL_SEATS = 45;

    /** ���� �����͸� ������ �⺻ ���� �̸� */
    private static final String FILE_NAME = "reservations.txt";

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
        // JFrame ����
        frame = new JFrame("���� �¼� ���� �ý���");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 800);

        // ��� �г� ����
        BackgroundPanel backgroundPanel = new BackgroundPanel(BACKGROUND_IMAGE);
        backgroundPanel.setLayout(new BorderLayout());

        // ��� �ؽ�Ʈ ���̺�
        JLabel headerLabel = new JLabel("���� �¼� ���� (Ŭ���Ͽ� ����/���)", JLabel.CENTER);
        headerLabel.setFont(new Font("���� ���", Font.BOLD, 16));
        headerLabel.setForeground(Color.BLACK);

        // �¼� ��ġ �г� ����
        JPanel seatPanel = createSeatPanel();

        // �ϴ� ��ư �г�
        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false); // ��� ����ȭ
        JButton saveButton = new JButton("����");
        JButton loadButton = new JButton("�ҷ�����");
        JButton exitButton = new JButton("����");

        // ��ư �̺�Ʈ ����
        saveButton.addActionListener(e -> saveReservationsToFile());
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                loadReservationsFromFile(selectedFile.getPath());
            }
        });
        exitButton.addActionListener(e -> System.exit(0));

        // ��ư �߰�
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);
        controlPanel.add(exitButton);

        // ���� ��� �߰�
        backgroundPanel.add(headerLabel, BorderLayout.NORTH);
        backgroundPanel.add(seatPanel, BorderLayout.CENTER);
        backgroundPanel.add(controlPanel, BorderLayout.SOUTH);

        // JFrame�� ��� �г� ����
        frame.setContentPane(backgroundPanel);
        frame.setVisible(true);
    }

    /**
     * �¼� ��ġ �г��� �����մϴ�.
     * 
     * @return �¼� ��ġ �г�
     */
    private JPanel createSeatPanel() {
        JPanel seatPanel = new JPanel(new GridLayout(9, 1, 10, 10));
        seatPanel.setOpaque(false); // ��� ����ȭ

        int seatCounter = 1;
        for (int row = 0; row < 9; row++) {
            JPanel rowPanel = new JPanel(new GridLayout(1, 5, 10, 10));
            rowPanel.setOpaque(false); // ��� ����ȭ

            for (int col = 0; col < 5; col++) {
                if (col == 2) {
                    rowPanel.add(new JLabel()); // �߾� ���
                } else {
                    JButton seatButton = new JButton(String.valueOf(seatCounter));
                    seatButtons.add(seatButton);
                    rowPanel.add(seatButton);

                    updateButtonStatus(seatButton, false, seatCounter);

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
     * �¼� ��ư�� Ŭ������ �� ���� �Ǵ� ��� ������ ó���մϴ�.
     * 
     * @param seatNumber �¼� ��ȣ
     * @param seatButton �¼��� �ش��ϴ� ��ư ��ü
     */
    private void handleSeatClick(int seatNumber, JButton seatButton) {
        if (reservations.containsKey(seatNumber)) {
            String reservationInfo = reservations.get(seatNumber);
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "�¼� " + seatNumber + "�� ����(" + reservationInfo + ")�� ����Ͻðڽ��ϱ�?",
                    "���� ���", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                reservations.remove(seatNumber);
                updateButtonStatus(seatButton, false, seatNumber);
                JOptionPane.showMessageDialog(frame, "������ ��ҵǾ����ϴ�.");
            }
        } else {
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
     * ���� �����͸� ���Ϸ� �����մϴ�.
     */
    private void saveReservationsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<Integer, String> entry : reservations.entrySet()) {
                String formattedData = String.format("�¼���ȣ: %d, %s", entry.getKey(), entry.getValue());
                writer.write(formattedData);
                writer.newLine();
            }
            JOptionPane.showMessageDialog(frame, "���� ������ ����Ǿ����ϴ�.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "���� ���� �� ������ �߻��߽��ϴ�.");
        }
    }

    /**
     * ���Ͽ��� ���� �����͸� �ҷ��ɴϴ�.
     * 
     * @param filePath �ҷ��� ���� ���
     */
    private void loadReservationsFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(frame, "������ �������� �ʽ��ϴ�: " + filePath);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reservations.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 4) {
                    int seatNumber = Integer.parseInt(parts[0].split(": ")[1]);
                    String reservationInfo = String.format("%s, %s, %s", parts[1], parts[2], parts[3]);

                    reservations.put(seatNumber, reservationInfo);
                    for (JButton button : seatButtons) {
                        if (button.getText().equals(String.valueOf(seatNumber))) {
                            updateButtonStatus(button, true, seatNumber);
                        }
                    }
                }
            }
            JOptionPane.showMessageDialog(frame, "���Ͽ��� ���� ������ �ҷ��Խ��ϴ�.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "���� �б� �� ������ �߻��߽��ϴ�.");
        }
    }

    /**
     * ��� �̹����� �׸��� �г� Ŭ����
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
     * ���� �޼���� GUI�� �����մϴ�.
     */
    public static void main(String[] args) {
        new FinalTest();
    }
}
