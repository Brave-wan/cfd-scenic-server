package com.mytools;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

@SuppressLint("SimpleDateFormat")
public class DataUtil {
	
	/**
	 * 获取当前系统时间
	 * **/
	public static String gettime(String format){
		
		SimpleDateFormat formatter = new SimpleDateFormat (format);

		Date curDate = new Date(System.currentTimeMillis());//获取当前时间

		String str = formatter.format(curDate);
		return str;
	}
	

	public static String getDate(String longStr){
//		longStr=longStr.replace("T", " ");
//		longStr=	date2TimeStamp(longStr, "MM-dd HH:mm:ss");
		return twoDateDistance(Long.parseLong(longStr) * 1000, System.currentTimeMillis());
	}
	
	public static Date stringToDate(String dateString) {  
        ParsePosition position = new ParsePosition(0);  
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
        Date dateValue = simpleDateFormat.parse(dateString, position);  
        return dateValue;  
    } 
	
    public static  String twoDateDistance(long startDate,long endDate){  
          
        long timeLong = endDate - startDate;  
        if (timeLong<60*60*24*1000){  
        	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));  
            return sdf.format(startDate);  
        }  
        else if (timeLong<60*60*24*1000*7){  
            timeLong = timeLong/1000/ 60 / 60 / 24;  
            return timeLong + "天前";  
        }  
        else if (timeLong<60*60*24*1000*7*4){
            timeLong = timeLong/1000/ 60 / 60 / 24/7;  
            return timeLong + "周前";  
        }  
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");  
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));  
            return sdf.format(startDate);  
        }  
	}  
    
    /**
     * 时间戳转日期
     * **/
    public static  String getDateriqi(String startDate){  
        
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
  			String date = sdf.format(new Date(Long.parseLong(startDate)*1000));
  			return date;
    	} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
      
	}  
    
    /**
	 * long类型时间格式化
	 */
	public static String convertToTime(long time) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(time);
		return df.format(date);
	}
	 /**
		 * long类型时间格式化
		 */
		public static String convertToTime1(long time) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(time * 1000);
			return df.format(date);
		}
    /**
     * 时间戳转日期
     * **/
    public static  String getDateriqi2(String startDate,String format){  
        
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat(format);
  			String date = sdf.format(new Date(Long.parseLong(startDate)*1000));
  			return date;
    	} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
      
	}
    
    /**
     * 时间戳转日期
     * **/
    public static  String getDateriqi1(String  startDate,String dateformat){  
        
    	String date="";
	try {

SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		
		 date = sdf.format(new Date(Long.parseLong(startDate)*1000));
	} catch (Exception e) {
		// TODO: handle exception
	}
		
		return date;
        
	}


	/**
	 * 时间戳转日期
	 * **/
	public static  String getDateriqi3(String  startDate,String dateformat){

		String date="";
		try {

			SimpleDateFormat sdf = new SimpleDateFormat(dateformat);

			date = sdf.format(new Date(Long.parseLong(startDate)));
		} catch (Exception e) {
			// TODO: handle exception
		}

		return date;

	}

	/**
     * 日期格式字符串转换成时间戳 
     * @param date 字符串日期 
     * @param format 如：yyyy-MM-dd HH:mm:ss 
     * @return 
     */  
    public static long date2TimeStamp(String date_str,String format){  
    	if(date_str == null || "".equals(date_str))
    		return 0;
        try {  
            SimpleDateFormat sdf = new SimpleDateFormat(format);  
            return sdf.parse(date_str).getTime()/1000;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return 0;  
    }  
    
    /**
     * 日期比较
     * @param date1
     * @param date2
     * @param format :"yy-mm-dd hh:mm"
     * @return
     */
    public static boolean compareDate(String date1, String date2, String format){
    	long date1_l = date2TimeStamp(date1, format);
    	long date2_l = date2TimeStamp(date2, format);
    	if(date1_l >= date2_l)
    		return true;
    	else
    		return false;
    }
    /**获取当前时间
     * @param type
     * @return
     */
    public static String getCuTime(int type)
    {
    	String date = "";
    	SimpleDateFormat sDateFormat;
    	switch(type)
    	{
    	case 0:
    		sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");       
        	date = sDateFormat.format(new Date());
    		break;
    	case 1:
    		sDateFormat = new SimpleDateFormat("MM-dd hh:mm");
        	date = sDateFormat.format(new Date());
    		break;
    	case 2:
    		sDateFormat = new SimpleDateFormat("hh:mm");
        	date = sDateFormat.format(new Date());
    		break;
    	case 3:
    		sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        	date = sDateFormat.format(new Date());
    		break;
    	case 4:
    		sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:ss");
        	date = sDateFormat.format(new Date());
    		break;

    	case 5:
    		sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	date = sDateFormat.format(new Date());
    		break;
    	case 6:
    		sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	date = sDateFormat.format(new Date());
    		break;
    		
    	}
    	
    	return date;
    }
    
    
    
    /**
     * 获取距离今天相差tmp天的日期
     * 今天之前tmp为负数，之后为正数
     * **/
  public static   String getsomedaytodaydate(int tmp){
		Date date=new Date();//取时间
   	 Calendar calendar = new GregorianCalendar();
   	 calendar.setTime(date);
   	 calendar.add(calendar.DATE,tmp);//把日期往后增加一天.整数往后推,负数往前移动
   	 date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
   	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
   	 String dateString = formatter.format(date);
		return dateString;
    	
    }
  
  /**
   * 获取相距某一天n天的日期
   * **/
   public static String getsamedaydate(String dates,int i){
	 
	    	
	    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	    	Calendar calendar1 = Calendar.getInstance();
	    	  Date dd;
			try {
				dd = format.parse(dates);
				
		    	  calendar1.setTime(dd);
		    	  calendar1.add(calendar1.DATE,i);//把日期往后增加一天.整数往后推,负数往前移动
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	  
	    	  
	    	  
	    	Date date=new Date();//取时间
//	    	 Calendar calendar = new GregorianCalendar();
//	    	 calendar.setTime(date);
//	    	 calendar.add(calendar.DATE,i);//把日期往后增加一天.整数往后推,负数往前移动
	    	 date=calendar1.getTime(); //这个时间就是日期往后推一天的结果 
	    	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    	 String dateString = formatter.format(date);
			return dateString;
	    	 
//	    	 System.out.println(dateString);  
		
   }
    
}	