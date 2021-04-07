package exception;
class AccountException extends Exception{
	private BankAccount ba;
	AccountException(BankAccount ba,String msg){
		super(msg);
		this.ba=ba;
	}
	public BankAccount getBa() {
		return ba;
	}
	public void setBa(BankAccount ba) {
		this.ba = ba;
	}
	
}
public class BankAccount {

	private int balance;
	private String accountName;
	private String account;
	private boolean available;

	public BankAccount(int balance, String accountName, String account, boolean available) {
		super();
		this.balance = balance;
		this.accountName = accountName;
		this.account = account;
		this.available = available;
	}
	int deposit(int value) throws AccountException {
		if(!available)
			throw( new AccountException(this,"unavilable"));
		balance+=value;
		return balance;
	}
	int withdraw (int value) throws AccountException  {
		if(!available)
			throw( new AccountException(this,"unavilable"));
		
		if(balance<value) {
			throw( new AccountException(this,"insuff balance"));
		}
		
		return balance-=value;
	}
	int getBalance() {
		return this.balance;
	}
	public static void main(String[] arg) {
		BankAccount ba=new BankAccount(0,"marry","001",false);
		try {
			ba.deposit(10);
			ba.withdraw(20);
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getBa().accountName+":"+e.getMessage());
			
		}
		
	}
}
