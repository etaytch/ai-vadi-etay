package Tools;

public class StringUtil {
	public static final int PAD = 18;
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
