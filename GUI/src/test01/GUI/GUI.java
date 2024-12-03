package test01.GUI;

import javax.swing.*;

public class GUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Input Example");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JLabel 생성
        JLabel label = new JLabel("Enter your name:");
        
        // JTextField 생성
        JTextField textField = new JTextField(20);

        // JPanel 생성 (컴포넌트들을 담기 위한 패널)
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(textField);

        // JButton 생성
        JButton button = new JButton("Submit");
        button.addActionListener(e -> {
            String name = textField.getText();
            JOptionPane.showMessageDialog(frame, "Hello, " + name + "!");
        });

        panel.add(button);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
