package Calculator_test02;

import javax.swing.*; // 운영체제에 상관없이 자바에서 제공하는 기능을 받아옴
import java.awt.*; // 운영체제에 따른 화면구성 도와줌

public class Calculator2 extends JFrame { // JFrame 상속

    public Calculator2() {
        setLayout(null); // 기본 레이아웃 관리자 제거
        
        // 라벨
        JLabel userNameInfo = new JLabel("USER : ");
        userNameInfo.setFont(new Font("나눔고딕", Font.BOLD, 15)); // 폰트
        userNameInfo.setBounds(10, 15, 80, 30); //위치

       
        // 네임 입력
        JLabel userNameInput = new JLabel("강영수"); //데이터베이스 연결할 자리
        userNameInput.setHorizontalAlignment(userNameInput.LEFT); // 정렬
        userNameInput.setFont(new Font("나눔고딕", Font.BOLD, 15)); // 폰트
        userNameInput.setBounds(85, 15, 80, 30); //위치
	
        // 계산 창
        PadActionListener padActionListener = new PadActionListener();
        JTextField jTextField = new JTextField();// instance
        jTextField.setEditable(false); // 편집 불가능
        jTextField.setBackground(Color.WHITE); // 배경색
        jTextField.setHorizontalAlignment(JTextField.RIGHT); // 정렬
        jTextField.setFont(new Font("나눔고딕", Font.BOLD, 35)); // 폰트
        jTextField.setBounds(8, 55, 270, 50); // x:8 y:10 위치, 270*70 크기 의미
        padActionListener.setInputSpace(jTextField);

        // 버튼 담을 패널
        JPanel buttonPanel = new JPanel(); // 버튼 담음
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10)); // 가로 칸수, 세로 칸수, 좌우 간격, 상하 간격 => 격자 형태 배치
        buttonPanel.setBounds(8, 115, 270, 235); // 위치

        // 버튼 정보 입력
        String[] button_names = {"7", "8", "9", "÷", "4", "5", "6", "×", "1", "2", "3", "-", "C", "0", "=", "+"}; //계산기 버튼 글자 배열 (16)
        JButton[] buttons = new JButton[button_names.length]; //버튼 배열

        // 버튼 생성
        for (int i = 0; i < button_names.length; i++) {
            buttons[i] = new JButton(button_names[i]);
            buttons[i].setFont(new Font("나눔고딕", Font.BOLD, 20));

            // 배열 0~3 4~7 8~11 12~15
            if (button_names[i].equals("C")) {
                buttons[i].setBackground(Color.blue); // 배경색
                buttons[i].setForeground(Color.white); //글자색
            } else if ((i >= 0 && i <= 2) || (i >= 4 && i <= 6) || (i >= 8 && i <= 10) || i == 13) {
                buttons[i].setBackground(Color.white); // 배경색
            } else {
                buttons[i].setForeground(Color.white); //글자색
                buttons[i].setBackground(Color.gray);
            }
            buttons[i].setBorderPainted(false); //테두리 제거
            buttons[i].addActionListener(padActionListener); // 액션리스너 버튼에 추가
            buttonPanel.add(buttons[i]);
        }

        // 패널에 추가
        add(padActionListener.getInputSpace());
        add(buttonPanel);
        add(userNameInfo);
        add(userNameInput);

        // 창 제목, 창 보이기, 창 사이즈 등
        setTitle("계산기"); //제목
        setVisible(true); //프레임 보이기
        setSize(300, 400);
        setLocationRelativeTo(null); // 화면 가운데
        setResizable(false); // 사이즈 조절 불가
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫을 때 실행 중인 프로그램도 종료
    }
}