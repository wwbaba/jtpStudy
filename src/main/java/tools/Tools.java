package tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
 hello
 ÖÐÎÄ
 * 
 */

public class Tools {
	static String sc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789" ;
	public static String getCodeRandom(){
		int x = (int)( Math.random()* 62);
		return sc.substring(x,x+1) + "" ;
	}
	public static int getCodeRandomData(int min,int max){
		int x = min+ (int)( Math.random()* (max-min+1));
		return x ;
	}
	
//	System.out.println("eoroosadfasdf") ;
	
	static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS") ;
	static DateFormat df3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
	static DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd") ;
//	static DateFormat df3= new SimpleDateFormat("yyyy-MM-dd") ;
	public static String datetimeToString(Date dx){
		if (dx == null) {
			return null;
		}
		String rst = df3.format(dx) ;
		return rst ;
	}
	public static Date stringToDatetime(String dx){
		if (dx == null) {
			return null;
		}
		try {
//			df3.setLenient(false);
			Date rst = df3.parse(dx) ;
			
			return rst ;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String datetimeMiToString(Date dx){
		if (dx == null) {
			return null;
		}
		String rst = df.format(dx) ;
		return rst ;
	}
	public static Date stringToDatetimeMi(String dx){
		if (dx == null) {
			return null;
		}
		try {
			df.setLenient(false);
			Date rst = df.parse(dx) ;
			
			return rst ;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String dateToString(Date dx){
		if (dx == null) {
			return null;
		}
		String rst = df2.format(dx) ;
		return rst ;
	}
	public static Date stringToDate(String dx){
		if (dx == null) {
			return null;
		}
		try {
			df2.setLenient(false);
			Date rst = df2.parse(dx) ;
			
			return rst ;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
}
