package Calculator_test01;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PadActionListener implements ActionListener {
    private JTextField inputSpace;
    private String num = ""; // 계산식의 숫자를 담음
    private String prevOperation = ""; // 작동 기억하도록 변수 하나 생성
    private ArrayList<String> equation = new ArrayList<>(); // 숫자와 연산 기호를 구분해서 담음

//    getActionCommand() 이벤트가 발생한 객체의 텍스트가 리턴
//    getSource() 이벤트가 발생한 객체의 해시코드가 리턴
//    getModifiers() 이벤트가 발생했을 때 같이 누른 조합키(Alt, Ctrl, Shift) 값이 리턴

    public JTextField getInputSpace() {
        return inputSpace;
    }

    public void setInputSpace(JTextField inputSpace) {
        this.inputSpace = inputSpace;
    }

    @Override
    public void actionPerformed(ActionEvent e) { // 이벤트 처리
        String operation = e.getActionCommand(); //어떤 버튼 눌렸는지 받아옴

        if (operation.equals("C")) { //만약 C버튼이라면 계산식 내용을 지워줌
            inputSpace.setText("");
        } else if (operation.equals("=")) { //만약 =이라면 입력된 식을 계산해 계산값이 나오도록 함
            String result = Double.toString(calculate(inputSpace.getText())); //계산 기능
            inputSpace.setText("" + result);
            num = "";
        } else if (operation.equals("+") || operation.equals("-") || operation.equals("÷") || operation.equals("×")) {
            if (inputSpace.getText().equals("") && operation.equals("-")) { //음수
                inputSpace.setText(inputSpace.getText() + e.getActionCommand());
            } else if (!inputSpace.getText().equals("") && !prevOperation.equals("+") && !prevOperation.equals("-") && !prevOperation.equals("÷") && !prevOperation.equals("×")) {
                inputSpace.setText(inputSpace.getText() + e.getActionCommand());
            }
        } else { //나머지 버튼 눌렀을 경우 계산식에 추가되도록 함
            inputSpace.setText(inputSpace.getText() + e.getActionCommand());
        }
        prevOperation = e.getActionCommand(); //마지막으로 누른 버튼 기억
    }

    // 숫자 & 연산자
    private void fullTextParsing(String inputText) {
        equation.clear();

        for (int i = 0; i < inputText.length(); i++) { //계산식의 글자를 하나하나 거쳐감
            char ch = inputText.charAt(i);

            if (ch == '-' || ch == '+' || ch == '÷' || ch == '×') {
                equation.add(num);
                num = "";
                equation.add(ch + ""); //num 초기화하고, 연산 기호를 ArrayList에 추가
            } else {
                num = num + ch; //나머지 숫자의 경우 num문자에 더해줌
            }
        }
        equation.add(num);
        equation.remove("");
    }

    //    계산기능
    public double calculate(String inputText) {
        fullTextParsing(inputText);

        double prev = 0;
        double current = 0;
        String mode = ""; // 연산 기호에 대한 처리

        for (int i = 0; i < equation.size(); i++) {
            String s = equation.get(i);

            if (s.equals("+")) {
                mode = "add";
            } else if (s.equals("÷")) {
                mode = "div";
            } else if (s.equals("×")) {
                mode = "mul";
            } else { //숫자 double로 형 변환
                if ((mode.equals("mul") || mode.equals("div")) && (!s.equals("+") && !s.equals("-") && !s.equals("÷") && !s.equals("×"))) {
                    Double one = Double.parseDouble(equation.get(i - 2));
                    Double two = Double.parseDouble(equation.get(i));
                    Double result = 0.0;

                    if (mode.equals("mul")) {
                        result = one * two;
                    } else if (mode.equals("div")) {
                        result = one / two;
                    }
                    equation.add(i + 1, Double.toString(result));

                    for (int j = 0; j < 3; j++) {
                        equation.remove(i - 2);
                    }
                    i -= 2; //결과값이 생긴 인덱스로 이동
                }
            }
        } //곱셈 나눗셈을 먼저 계산한다

        for (String s : equation) {
            if (s.equals("+")) {
                mode = "add";
            } else if (s.equals("-")) {
                mode = "sub";
            } else {
                current = Double.parseDouble(s);
                if (mode.equals("add")) {
                    prev += current;
                } else if (mode.equals("sub")) {
                    prev -= current;
                } else {
                    prev = current;
                }
            }
            prev = Math.round(prev * 100000) / 100000.0; //소수점 자리 제한
        }
        return prev;
    }
}