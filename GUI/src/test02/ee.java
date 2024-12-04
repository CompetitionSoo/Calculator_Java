package test02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

class ee extends Frame implements WindowListener, ActionListener, KeyListener {

    private Label dispL, inputL;
    private JButton[] button;  //버튼은 총 18개이다
    StringBuffer sb = new StringBuffer();
    DecimalFormat df = new DecimalFormat("#,##0");  //소숫점 이하 15자리까지 표현
    private String disp;   //중간 결과값
    private int op;   //연산자가 들어가는 int
    public double result;  //맨 처음 값
    public double number;
    boolean opClick = false;

    public ee() {
        super("강영수의 계산기");

        Panel whole = new Panel();  //전체
        Panel p1 = new Panel();
        Panel p2 = new Panel();
        Panel p3 = new Panel();
        Panel p4 = new Panel();
        Panel p5 = new Panel();
        Panel p6 = new Panel();
        Panel p7 = new Panel();

        //.....버튼
        String[] buttonName = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", ".", "0", "=", "+", "Back", "C"};
        button = new JButton[18];
        for (int i = 0; i < 18; i++) {
            button[i] = new JButton(buttonName[i]);
        }

        //....라벨
        dispL = new Label("0", Label.RIGHT);
        dispL.setBackground(new Color(139, 158, 226));
        inputL = new Label("0", Label.RIGHT);
        inputL.setBackground(new Color(139, 158, 226));

        //....전체 패널(whole) 7행 1열 각 행사이 5픽셀 간격을 둔다
        whole.setLayout(new GridLayout(7, 1, 5, 5));

        //계산기의 결과값을 표시하는 레이아웃이다.
        p1.setLayout(new GridLayout(1, 1, 5, 5));
        p1.add(dispL);

        //사용자가 입력하는 값을 표시하는 레이아웃입니다.
        p2.setLayout(new GridLayout(1, 1, 5, 5));
        p2.add(inputL);

        // Back(뒤로가기 버튼)과 C(초기화 버튼)를 배치합니다 1행 2열 ,  // 배열에서 17번째와 18번째 버튼입니다  
        p3.setLayout(new GridLayout(1, 2));
        p3.add(button[16]);
        p3.add(button[17]);

        // 배열에서 숫자 버튼 "7", "8", "9"와 연산자 "/" 버튼을 배치
        p4.setLayout(new GridLayout(1, 4));
        for (int i = 0; i < 4; i++) {
            p4.add(button[i]);
        }

        // 숫자 버튼 "4", "5", "6"과 연산자 "*" 버튼을 배치
        p5.setLayout(new GridLayout(1, 4));
        for (int i = 4; i < 8; i++) {
            p5.add(button[i]);
        }

        // 숫자 버튼 "1", "2", "3"과 연산자 "-" 버튼을 배치
        p6.setLayout(new GridLayout(1, 4));
        for (int i = 8; i < 12; i++) {
            p6.add(button[i]);
        }

        // 연산자 ".", 숫자"0", "=", "+" 버튼을 배치
        p7.setLayout(new GridLayout(1, 4));
        for (int i = 12; i < 16; i++) {
            p7.add(button[i]);
        }

        //...전체 패널에 여러 개의 패널 담기
        whole.add(p1);
        whole.add(p2);
        whole.add(p3);
        whole.add(p4);
        whole.add(p5);
        whole.add(p6);
        whole.add(p7);
        add("Center", whole);

        //...윈도우 창 설정
        setBounds(900, 180, 350, 500);
        setBackground(new Color(105, 132, 224));
        setVisible(true);

        //...이벤트 설정
        for (int i = 0; i < 18; i++) {
            button[i].addActionListener(this);
        }
        this.addWindowListener(this);
        this.addKeyListener(this); // 키 입력을 받을 수 있도록 추가
        setFocusable(true); // 키 입력을 받기 위해 설정
        requestFocusInWindow(); // 포커스를 요청하여 키 입력을 받을 수 있게 함
    }

    //...actionPerformed 이벤트
    public void actionPerformed(ActionEvent e) {
        //전에 입력했던 연산자가 =이었을 때(계산이 한번 종료된 후, 그 값에 다시 계산을 하기 위한 초기작업)
        if (op == 61) {
            sb.delete(0, sb.length());
            sb.append(result);
            disp = "";
            op = 0;
            dispL.setText(disp);
        }

        //숫자버튼을 눌렀을 시
        String actionCommand = e.getActionCommand();
        if ("0123456789".contains(actionCommand)) {
            sb.append(actionCommand);
        } else if (actionCommand.equals("C")) {
            sb.delete(0, sb.length());
            dispL.setText("0");
            disp = "";
            result = 0;
            op = 0;
        } else if (actionCommand.equals("Back")) {
            if (sb.length() > 0)
                sb = sb.delete(sb.length() - 1, sb.length());
            else
                sb.delete(0, sb.length());
        } else if (actionCommand.equals(".")) {
            if (sb.indexOf(".") == -1) {
                sb.append(".");
            }
        } else {
            //연산자 버튼을 클릭하는 모든 경우(+,-,*,/)
            opClick = true;
            if (disp != null) disp += sb.toString();
            else disp = sb.toString();

            if (op == 0) {
                result = Double.parseDouble(sb.toString());
                op = actionCommand.hashCode();
                disp += actionCommand;
                dispL.setText(disp);
            } else {
                disp += actionCommand;
                if (op == 43) {  //"+"  
                    result += Double.parseDouble(sb.toString());
                    dispL.setText(disp);
                } else if (op == 45) {   //"-"
                    result -= Double.parseDouble(sb.toString());
                    dispL.setText(disp);
                } else if (op == 42) {  //"*"
                    result *= Double.parseDouble(sb.toString());
                    dispL.setText(disp);
                } else if (op == 47) {   //"/"
                    result /= Double.parseDouble(sb.toString());
                    dispL.setText(disp);
                }
                op = actionCommand.hashCode();
            }
            sb.delete(0, sb.length());
        }

        // 화면 업데이트
        String temp = sb.toString();
        if (!temp.isEmpty()) {
            if (temp.indexOf(".") != -1) {
                String intPart = temp.substring(0, temp.indexOf("."));
                String decPart = temp.substring(temp.indexOf(".") + 1);
                temp = df.format(Integer.parseInt(intPart)) + "." + decPart;
            } else {
                temp = df.format(Integer.parseInt(temp));
            }
            inputL.setText(temp);
        }
    }

    // WindowListener 메소드 구현
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    public void windowActivated(WindowEvent e) {}

    public void windowDeactivated(WindowEvent e) {}

    public void windowDeiconified(WindowEvent e) {}

    public void windowIconified(WindowEvent e) {}

    public void windowOpened(WindowEvent e) {}

    public void windowClosed(WindowEvent e) {}

    // 키보드 입력 처리 메소드 구현 (KeyListener)
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        char keyChar = e.getKeyChar();
        String keyString = String.valueOf(keyChar);

        // 숫자 또는 연산자 키를 처리합니다.
        if ("0123456789".contains(keyString)) {
            sb.append(keyString);
        } else if (keyChar == '.') {
            if (sb.indexOf(".") == -1) {
                sb.append(".");
            }
        } else if (keyString.equals("=")) {
            actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "="));
        } else if ("+-*/".contains(keyString)) {
            actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, keyString));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        new ee();
    }
}
