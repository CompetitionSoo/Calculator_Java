package Calculator_test01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
class Calculator extends Frame implements WindowListener, ActionListener
{	
	private Label dispL, inputL;
	private JButton[] button;  //버튼은 총 18개이다
	
	//sb는 입력된 값이 저장되는 곳입니다.
	//disp는 화면에 표시될 계산식입니다.
	
	StringBuffer sb= new StringBuffer();
	DecimalFormat df= new DecimalFormat("#,##0");  //소숫점 이하 15자리까지 표현
	private String disp;   //중간 결과값
	private int op;   //연산자가 들어가는 int
	public double result;  //맨 처음 값
	public double number;
	boolean opClick=false;

	public Calculator(){
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
		String[] buttonName = {"7","8","9","/","4","5","6","*","1","2","3","-",".","0","=","+","Back","C"};
		button = new JButton[18];
		for(int i=0;i<18;i++){
			 button[i]=new JButton(buttonName[i]);
		}
		
		//....라벨
		dispL= new Label("0",Label.RIGHT);
		dispL.setBackground(new Color(139,158,226));
		inputL= new Label("0", Label.RIGHT);
		inputL.setBackground(new Color(139,158,226));
		
		//....전체 패널(whole) 7행 1열 각 행사이 5픽셀 간격을 둔다
		whole.setLayout(new GridLayout(7,1,5,5));
		
		//계산기의 결과값을 표시하는 레이아웃이다.
		p1.setLayout(new GridLayout(1,1,5,5));
		p1.add(dispL);  
		
		//사용자가 입력하는 값을 표시하는 레이아웃입니다.
		p2.setLayout(new GridLayout(1,1,5,5));
		p2.add(inputL); 
		
		// Back(뒤로가기 버튼)과 C(초기화 버튼)를 배치합니다 1행 2열 ,  // 배열에서 17번째와 18번째 버튼입니다  
		p3.setLayout(new GridLayout(1,2));
		p3.add(button[16]);   
		p3.add(button[17]);
		
		// 배열에서 숫자 버튼 "7", "8", "9"와 연산자 "/" 버튼을 배치
		p4.setLayout(new GridLayout(1,4));
		for(int i=0;i<4;i++) { p4.add(button[i]); }
		
		// 숫자 버튼 "4", "5", "6"과 연산자 "*" 버튼을 배치
		p5.setLayout(new GridLayout(1,4));
		for(int i=4;i<8;i++) { p5.add(button[i]); }
		
		
		// 숫자 버튼 "1", "2", "3"과 연산자 "-" 버튼을 배치
		p6.setLayout(new GridLayout(1,4));
		for(int i=8;i<12;i++) { p6.add(button[i]); }
		
		// 연산자 ".", 숫자"0", "=", "+" 버튼을 배치
		p7.setLayout(new GridLayout(1,4));
		for(int i=12;i<16;i++) { p7.add(button[i]);	}

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
		setBounds(900,180,350,500);
		setBackground(new Color(105,132,224));
		setVisible(true);
		//...이벤트 설정
		for(int i=0;i<18;i++){ button[i].addActionListener(this); }
		this.addWindowListener(this);
	}

		//...actionPerFormed 이벤트
		public void actionPerformed(ActionEvent e){
			//전에 입력했던 연산자가 =이었을 때(계산이 한번 종료된 후, 그 값에 다시 계산을 학[ 만들기 위한 초기작업)
			if(op==61){  
				sb.delete(0,sb.length());
				sb.append(result);
				disp="";
				op=0;
				dispL.setText(disp);
			}
			//숫자버튼을 눌렀을 시 
			if(e.getActionCommand()=="1"){ sb.append(1); } 
			else if(e.getActionCommand()=="2"){ sb.append(2); }
			else if(e.getActionCommand()=="3"){ sb.append(3); }
			else if(e.getActionCommand()=="4"){ sb.append(4); }
			else if(e.getActionCommand()=="5"){ sb.append(5); }
			else if(e.getActionCommand()=="6"){ sb.append(6); }
			else if(e.getActionCommand()=="7"){ sb.append(7); }
			else if(e.getActionCommand()=="8"){ sb.append(8); }
			else if(e.getActionCommand()=="9"){ sb.append(9); }
			else if(e.getActionCommand()=="0"){ sb.append(0); }
			else if(e.getActionCommand()=="C"){ 
				sb.delete(0,sb.length()); 
				dispL.setText("0"); 
				disp=""; 
				result=0;
				op=0;
				}
			else if(e.getActionCommand()=="Back"){ 
				if(sb.length()>0) sb=sb.delete(sb.length()-1,sb.length());
				else sb.delete(0,sb.length());
			}
			else if(e.getActionCommand()=="."){ 
				if(sb.indexOf(".")==-1){
					if(inputL.getText().equals("0")){
						sb.append("0.");
					} else{
						sb.append(".");
					}
				} 
			} else { 
				//연산자 버튼을 클릭하는 모든 경우(+,-,*,/) 
				opClick=true;
				//disp안의 값이 null인지 아닌지 확인
				if(disp!=null) disp+=sb.toString();
				else disp=sb.toString();
				//op안에 연산자의 값이 있는지 확인(초기값 확인용);
				if(op==0){
					result=Double.parseDouble(sb.toString());
					//연산자를 10진수로 변환
					op=e.getActionCommand().hashCode();
					disp+=e.getActionCommand();
					dispL.setText(disp);
				} else {
					disp+=e.getActionCommand();
					if(op==43){  //"+"
						result+=Double.parseDouble(sb.toString());
						dispL.setText(disp);
						} 
					else if(op==45){   //"-"
						result-=Double.parseDouble(sb.toString());
						dispL.setText(disp);
						} 
					else if(op==42){  //"*"
						result*=Double.parseDouble(sb.toString());
						dispL.setText(disp);
						} 
					else if(op==47){   //"/"
						result/=Double.parseDouble(sb.toString());
						dispL.setText(disp);
					} 
					op=e.getActionCommand().hashCode();
				}
				sb.delete(0,sb.length());
			}
			//...inputL에 표시하기 위한 설정
			String temp;
			String temp2;
			if(sb.length()>0){
				//Stringbuffer에 소수점이 있는 경우
				if(sb.indexOf(".")!=-1){
					if(sb.indexOf(".")==sb.length()-1){
						//StringBuffer에 입력된 맨 끝 값이 소수점일 경우(에러 방지용)
						temp=sb.substring(0,sb.indexOf("."));
						temp=df.format(Integer.parseInt(temp));
						temp+=".";
						inputL.setText(temp);
					} else {
						//소수점을 기준으로 정수부분은temp에, 소수부분은 temp2에 저장 후 합침
						temp=sb.substring(0,sb.indexOf("."));
						temp2=sb.substring(sb.indexOf(".")+1, sb.length());
						temp=df.format(Integer.parseInt(temp));
						temp+=".";
						temp+=temp2;
						inputL.setText(temp);
					}
				} else {
					temp=sb.toString();
					temp=df.format(Integer.parseInt((temp)));
					inputL.setText(temp);
				}
			} else inputL.setText("0");
			//...연산자가 클릭되었을 때, inputL에 계산한 result값이 보이게 설정/ "="버튼을 클릭하였을 시 최종 결과값 표시
			if(opClick==true||op==61){
					inputL.setText(Double.toString(result));
					opClick=false;
			}
		}

		//...windowListener의 추상메소드
		public void windowActivated(WindowEvent e){}
		public void windowClosed(WindowEvent e){} //창을 닫은 뒤 사후처리
		public void windowClosing(WindowEvent e){ System.exit(0); }  //x를 누르는 시정
		public void windowDeactivated(WindowEvent e){}
		public void windowDeiconified(WindowEvent e){}
		public void windowIconified(WindowEvent e){}
		public void windowOpened(WindowEvent e){}

}