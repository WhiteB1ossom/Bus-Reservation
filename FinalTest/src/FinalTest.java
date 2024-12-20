import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * ���� �¼� ���� �ý����� ������ Ŭ�����Դϴ�.
 * GUI�� ���� �¼��� �����ϰų� ����� �� ������, ���� �����͸� ���Ͽ� �����ϰ� �ҷ��� �� �ֽ��ϴ�.
 * 
 * @author 2021011877 �� ���
 * @version 1.0
 * @since 2024-12-16
 * 
 * @created 2024-12-16
 * 
 * @changelog
 * <ul>
 *   <li>2024-12-16: �ý��� �ʱ� ����</li>
 *   <li>2024-12-21: �ý��� �ܰ� ����</li>
 * </ul>
 */

public class FinalTest extends JFrame {

    /** GUI â ��ü **/
    private static JFrame frame;
    
    JButton[] seats = new JButton[45]; // 45���� �¼� ��ư
    JPanel seatPanel;

    /** GUI **/
    FinalTest() {
        seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(11, 5, 10, 10)); // 11�� 5���� ���� (�¼�+���)
        
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
