package Tools;

public class StringUtil {
	public static final int PAD = 15;
	public static final boolean DEBUG = false;
	public static final String STAR = "*";
	
	public static void println(String str){
		if(DEBUG){
			System.out.println(str);			
		}
	}
	
	public static String Pad(String str){
		int tmp = PAD-str.length();
		int half = tmp/2;
		int rest = tmp-half;
		String ans = "";
		for(int i=0;i<half;i++){
			ans+=" ";
		}
		ans+=str;
		for(int i=0;i<rest;i++){
			ans+=" ";
		}
		return ans;
	}
}
