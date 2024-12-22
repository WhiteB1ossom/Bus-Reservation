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
 * ���� �¼� ���� �ý����� ������ Ŭ�����Դϴ�.
 * GUI�� ���� �¼��� �����ϰų� ����� �� ������, ���� �����͸� HashMap�� �����մϴ�.
 * 
 * @author 2021011877 �� ���
 * @version 1.1
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
 * </ul>
 * 
 * @see <a href="https://dev-zephyr.tistory.com/8">Dev Zephyr"> ���� �ý��� ���� ������ũ1</a>
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

    /**
     * ���α׷��� �������Դϴ�. GUI�� �ʱ�ȭ�ϰ� ���� �ý����� �߰��մϴ�.
     * 
     * @param args ���� �� ���޵Ǵ� ����� �μ�
     */
    public FinalTest() {
        // GUI â ����
        frame = new JFrame("���� �¼� ���� �ý���");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLayout(new BorderLayout());

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

        // ���� ��Ҹ� �����ӿ� �߰�
        frame.add(new JLabel("���� �¼� ���� (Ŭ���Ͽ� ����/���)"), BorderLayout.NORTH);
        frame.add(seatPanel, BorderLayout.CENTER);

        // GUI ǥ��
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
            // �й��� �̸��� �Է¹޴� �� ����
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(2, 2, 5, 5)); // ���� 5�ȼ��� ����

            JLabel studentIdLabel = new JLabel("�й�:");
            JLabel nameLabel = new JLabel("�̸�:");
            JTextField studentIdField = new JTextField();
            JTextField nameField = new JTextField();

            inputPanel.add(studentIdLabel);
            inputPanel.add(studentIdField);
            inputPanel.add(nameLabel);
            inputPanel.add(nameField);

            int result = JOptionPane.showConfirmDialog(frame, inputPanel, "���� ���� �Է�",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String studentId = studentIdField.getText().trim();
                String name = nameField.getText().trim();

                if (studentId.isEmpty() || name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "�й��� �̸��� ��� �Է��ϼ���.");
                    return;
                }

                // �й��� �̸��� �Բ� ����
                String reservationInfo = "�й�: " + studentId + " �̸�: " + name;
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
     * ���� �޼���� GUI�� �����մϴ�.
     * 
     * @param args ���� �� ���޵Ǵ� ����� �μ�
     */
    public static void main(String[] args) {
        new FinalTest();
    }
}
