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
		System.out.println("请输入确定位置的一个数字，其他位输入*，例如，如果确定个位数为2，请输入**2");
		Scanner s=new Scanner(System.in);
		String inputnum=s.nextLine();
		inputnum =inputnum.replaceAll(" ", "");
		if (inputnum.length()!=3)
			return false;
		inputChar=new char[3];
		int starcount=0;
		for(int i=0;i<3;i++) {
	//只能输入数字或者*	
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
			Class.forName("lottery."+playName);//注意固定的包名，也可以设置配置文件，从配置文件里面获取
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
		System.out.print("中奖号为：");
		for(int num:winNum) {
			System.out.print(num+" ");
		}
		System.out.println();
	}
	public boolean getInput() {
		System.out.println("请输入0-999之间的整数");
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
		System.out.println("请输入投注方式");
		while(sin.hasNext()) {
			String playtype=sin.nextLine();
			if(Lottery3DManager.checkPlayList(playtype)) {
				Lottery3D temp=Lottery3D.playList.get(playtype);
				temp.randwinNum();
				temp.printWinNum();				
				while(true) {
					if(temp.getInput()) {
						System.out.println("您获得的奖金为"+temp.getwinLevel());
						break;
					}
					else{
						System.out.println("请输入正确的投注内容");
					}
				}
			}
			else {
				System.out.println("您输入的投注方式不存在，请重新输入");
			}
			System.out.println("请输入投注方式");
		}
	}
}
