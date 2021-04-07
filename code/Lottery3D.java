package lottery;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
class SingleChoose extends Lottery3D{
	static {
		Lottery3D.playList.put("SingleChoose", new SingleChoose());
	}
	@Override
	public int getwinLevel() {
		// TODO Auto-generated method stub
		boolean same=true;
		for(int i=0;i<3;i++) {
			if(winNum[i]!=inputNum[i]){
				same=false;
				break;
				}
		}
		if(same)
			return 1040;
		else
			return 0;
		
	}
	
}
class OneDChoose extends Lottery3D{
	static {
		Lottery3D.playList.put("OneDChoose", new OneDChoose());
	}
	char[] inputChar;
	@Override
	public boolean getInput() {
		System.out.println("������ȷ��λ�õ�һ�����֣�����λ����*�����磬���ȷ����λ��Ϊ2��������**2");
		Scanner s=new Scanner(System.in);
		String inputnum=s.nextLine();
		inputnum =inputnum.replaceAll(" ", "");
		if (inputnum.length()!=3)
			return false;
		inputChar=new char[3];
		int starcount=0;
		for(int i=0;i<3;i++) {
	//ֻ���������ֻ���*	
			char temp=inputnum.charAt(i);
			if((temp>='0'&&temp<='9')||temp=='*') {
				inputChar[i]=temp;
				if(temp=='*')
					starcount++;
			}
			else
				return false;
		}
		if (starcount==2)
			return true;
		else
			return false;
		
	}
	@Override
	public int getwinLevel() {
		// TODO Auto-generated method stub
		
		for(int i=0;i<3;i++) {
			if(inputChar[i]!='*' && winNum[i]+'0'==inputChar[i])
				return 10;

		}
		return 0;
		
	}
	
}
class Lottery3DManager{
	public static boolean checkPlayList(String playName) {
		Lottery3D temp=Lottery3D.playList.get(playName);
		if (temp==null) {
			try {
			Class.forName("lottery."+playName);//ע��̶��İ�����Ҳ�������������ļ����������ļ������ȡ
			return true;
			}
			catch(Exception e)
			{
				return false;
			}
		}
		return true;
	}
}
public abstract class Lottery3D {

	int [] winNum;
	int [] inputNum;
	protected static Map<String,Lottery3D> playList=new HashMap();
	public void setWinNum(int [] winNum) {
		this.winNum=winNum;
	}
	public static void numsplit(int num,int [] t) {
		t[2]=num %10 ;
		t[1]=(num/10)%10;
		t[0]=(num/100);
	}
	public void randwinNum() {
		Random rd=new Random();
		winNum=new int[3];
		numsplit(rd.nextInt(1000),winNum);
	}
	public void printWinNum() {
		System.out.print("�н���Ϊ��");
		for(int num:winNum) {
			System.out.print(num+" ");
		}
		System.out.println();
	}
	public boolean getInput() {
		System.out.println("������0-999֮�������");
		Scanner s=new Scanner(System.in);
		int inputnum=s.nextInt();
		if (inputnum<1000&&inputnum>=0) {
			inputNum=new int[3];
			numsplit(inputnum,inputNum);
			return true;
		}
		else
			return false;	
		
	}
	public abstract int getwinLevel();
	public static void main(String[] arg) {
		Scanner sin = new Scanner(System.in);
		System.out.println("������Ͷע��ʽ");
		while(sin.hasNext()) {
			String playtype=sin.nextLine();
			if(Lottery3DManager.checkPlayList(playtype)) {
				Lottery3D temp=Lottery3D.playList.get(playtype);
				temp.randwinNum();
				temp.printWinNum();				
				while(true) {
					if(temp.getInput()) {
						System.out.println("����õĽ���Ϊ"+temp.getwinLevel());
						break;
					}
					else{
						System.out.println("��������ȷ��Ͷע����");
					}
				}
			}
			else {
				System.out.println("�������Ͷע��ʽ�����ڣ�����������");
			}
			System.out.println("������Ͷע��ʽ");
		}
	}
}
