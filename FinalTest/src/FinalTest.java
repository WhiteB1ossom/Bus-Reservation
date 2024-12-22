import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * ���� �¼� GUI�� ������ Ŭ�����Դϴ�.
 * 
 * @author 2021011877 �� ���
 * @version 1.0.1
 * @since 2024-12-16
 * 
 * 	 <li>2024-12-16: �ý��� �ʱ� ����</li>
 *   <li>2024-12-21: �ý��� �ܰ� ����</li>
 *   <li>2024-12-22: �ý��� �ܰ� ������Ʈ</li>
 */
public class FinalTest {

    /** �� �¼� ���� �����մϴ�. */
    private static final int TOTAL_SEATS = 45;
    
    /** GUI â ��ü�Դϴ�. */
    private static JFrame frame;

    /**
     * ���α׷��� �������Դϴ�. GUI�� �ʱ�ȭ�մϴ�.
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
                    updateButtonStatus(seatButton, false);

                    seatCounter++;
                    // �¼��� ��� �Ҵ�Ǹ� ���� ����
                    if (seatCounter > TOTAL_SEATS) break;
                }
            }
            seatPanel.add(rowPanel);
        }

        // ���� ��Ҹ� �����ӿ� �߰�
        frame.add(new JLabel("���� �¼� ����"), BorderLayout.NORTH);
        frame.add(seatPanel, BorderLayout.CENTER);

        // GUI ǥ��
        frame.setVisible(true);
    }

    /**
     * ��ư ���¸� �¼� ���� ���ο� ���� ������Ʈ�մϴ�.
     * 
     * @param button �¼� ��ư ��ü
     * @param reserved �¼� ���� ����
     */
    private static void updateButtonStatus(JButton button, boolean reserved) {
        if (reserved) {
            button.setText("�����");
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
        } else {
            button.setText(button.getActionCommand()); // �¼� ��ȣ�� ����
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
