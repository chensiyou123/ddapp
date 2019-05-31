package com.csy.ddapp.uitls;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DataUtils {

    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yy/MM/dd HH:mm");

    /**
     *
     * @param d1 当前日期的24.00
     * @param d2 当前时间的时分秒
     * @return 小于零 开始日期早于结束日期
     */
    public static Integer compare(Date d1, Date d2) {

        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(d1);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, +1);  //设置为后一天
        Date dAfter = calendar.getTime();   //得到前一天的时间
        String str1 = FORMAT.format(dAfter);
        String str2 = FORMAT.format(d2);
        int result = str1.compareTo(str2);
        return  result;
    }

    /**
     * 时间格式转换(String-Date)
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date dateFormatLong(String str)  {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time = new Long(str);
        Date date= null;
        str = format.format(time);
        try {
            date= format.parse(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间格式转换(String-Date)
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date dateFormatStr(String str)  {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date= null;
        try {
            date= format.parse(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 获取当前系统时间
     * @param time
     * @return
     */
    public static String dateToString(String time){
        Date date = new Date(time);
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("yyyyMMdd");
        String ctime = formatter.format(date);
        return ctime;
    }
    /**
     * 获取当前系统时间
     * @param formatter
     * @return
     */
    public static String getDate(SimpleDateFormat formatter){
        Date date = new Date();
        if(formatter==null){
            formatter = new SimpleDateFormat ("yyyyMMdd");
        }
        String ctime = formatter.format(date);
        return ctime;
    }

    /**
     * 获取当前系统时间
     * @param df
     * @return
     */
    public static String getSystemTime(DateFormat df){
        Date date = new Date();
        String ctime = df.format(date);
        return ctime;
    }

    // 获取前月的第一天
    public static Date getMonthStartDate(){
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return cale.getTime();
    }


    /**
     * Timestamp转Date
     * @param timestamp 时间撮
     * @param df 转换的时间格式
     * @return
     */
    public static Date timestamp2Date(Timestamp timestamp,DateFormat df){
        Date time = null;
        try {
            time = df.parse(df.format(timestamp));
        }catch (Exception e){
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 获取当前系统时间
     * @param df
     * @return
     */
    public static Date getSysTemDate(DateFormat df){
        Date today = null;
        try {
            today=df.parse(df.format(new Date()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return today;
    }

    /**
     * 获得当前星期的第一天
     * @return
     */
    public static  LocalDate getweekDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate inputDate = LocalDate.parse(sdf.format(date));
        TemporalAdjuster FIRST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.minusDays(localDate.getDayOfWeek().getValue()- DayOfWeek.MONDAY.getValue()));
        // TemporalAdjuster LAST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.plusDays(DayOfWeek.SUNDAY.getValue() - localDate.getDayOfWeek().getValue()));
        return inputDate.with(FIRST_OF_WEEK);
    }

    public static Date getleamTerm(){
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Integer year = c.get(Calendar.YEAR);
        if(year < 3){
            c.add(Calendar.YEAR, -1);
            c.set(c.get(Calendar.YEAR),9, 1, 0, 0, 0);
        }
        if(year >= 3 && year < 9){
            c.set(c.get(Calendar.YEAR),3, 1, 0, 0, 0);
        }
        if(year >= 9){
            c.set(c.get(Calendar.YEAR),9, 1, 0, 0, 0);
        }
        return c.getTime();
    }


    //获取当天的开始时间
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    //获取当天的结束时间
    public static Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }


    /**
     * 获取当前时间前一天、后一天时间
     * @param date 时间
     * @param mount {1:后一天;-1：前一天}
     * @return 时间为 00:00:00
     */
    public static Date getDayAddBegin(Date date,int mount ){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE,mount);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 0, 0, 0);
        return c.getTime();
    }

    /**
     * 获取当前时间前一天、后一天时间
     * @param date 时间
     * @param mount {1:后一天;-1：前一天}
     * @return 时间为 23:59:59
     */
    public static Date getDayAddEnd(Date date,int mount ){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE,mount);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 23, 59, 59);
        return c.getTime();
    }


    /**
     * 获取当前时间java.sql.TimeStamp
     *
     * @return TimeStamp对象
     */
    public static Timestamp getTimestamp() {
        return dateToTimestamp(new Date());
    }

    /**
     * java.util.Date转换成java.sql.TimeStamp
     *
     * @param date 日期
     * @return TimeStamp对象
     */
    public static Timestamp dateToTimestamp(Date date) {
        Date newDate = new Date();
        if (date == null) {
            newDate = new Date();
        }
        return new Timestamp(newDate.getTime());
    }





}
