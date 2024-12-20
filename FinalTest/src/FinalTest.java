import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FinalTest extends JFrame {

    JButton[] seats = new JButton[45]; // 45���� �¼� ��ư
    JPanel seatPanel;

    FinalTest() {
        seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(11, 5, 10, 10)); // 11�� 5���� ���� (�¼�+���)

        int seatNumber = 1;

        // �¼� ��ġ
        for (int row = 0; row < 10; row++) {
            seatPanel.add(createSeatButton(seatNumber++)); // ���� �¼� 1
            seatPanel.add(createSeatButton(seatNumber++)); // ���� �¼� 2
            seatPanel.add(new JPanel()); // ��� ����
            seatPanel.add(createSeatButton(seatNumber++)); // ������ �¼� 1
            seatPanel.add(createSeatButton(seatNumber++)); // ������ �¼� 2
        }

        // ������ �� �¼� 5�� �߰�
        for (int i = 0; i < 5; i++) {
            seatPanel.add(createSeatButton(seatNumber++));
        }

        // ������ ����
        this.add(seatPanel);
        this.setSize(600, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    // ��ư ���� �޼���
    private JButton createSeatButton(int number) {
        JButton button = new JButton(number + "��");
        // ��ư�� �̺�Ʈ�� ��Ÿ���� �߰��� �� ����
        return button;
    }

    public static void main(String[] args) {
        new FinalTest();
    }
}
