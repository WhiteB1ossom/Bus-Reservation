import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FinalTest extends JFrame {

    /** GUI â ��ü **/
    private static JFrame frame;

    JButton[] seats = new JButton[45]; // 45���� �¼� ��ư
    JPanel seatPanel;

    /** GUI **/
    FinalTest() {
        // �¼� �г� �ʱ�ȭ
        seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(11, 5, 10, 10)); // 11�� 5�� (�¼�+���)
        seatPanel.setPreferredSize(new Dimension(500, 600)); // �г� ũ�� ����

        // ������ �ʱ�ȭ
        frame = new JFrame("���� �¼� ���� �ý���");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLayout(new BorderLayout());

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

        // �г� �߰�
        frame.add(seatPanel, BorderLayout.CENTER);

        // �г� ũ��� ��ġ ����
        frame.pack(); // ������Ʈ ũ�⿡ �°� ������ ũ�⸦ ����
        frame.setVisible(true);
    }

    // ��ư ���� �޼���
    private JButton createSeatButton(int number) {
        JButton button = new JButton(number + "��");
        button.setPreferredSize(new Dimension(80, 30)); // ��ư ũ�� ����
        return button;
    }

    public static void main(String[] args) {
        new FinalTest();
    }
}
