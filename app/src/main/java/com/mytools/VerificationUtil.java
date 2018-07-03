package com.mytools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 宋玉磊 on 2016/1/11 16:54.
 */
public class VerificationUtil {
    /**
     * 验证是否为手机号
     *
     * @param mobiles
     *            待验证字符串
     * @return <pre>
     * </pre>
     */
    public static boolean isPhoneNumber(String mobiles) {
        if (mobiles == null) {
            return false;
        }
//        Pattern p = Pattern
//                .compile("^((13[0-9])|(147)|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$"
//                );
//        Matcher m = p.matcher(mobiles);
        if(mobiles.length()!=11)
            return  false;
        if(!mobiles.substring(0,1).equals("1"))
            return false;
            else return  true;
//        return m.matches();
    }


    /**
     * 检查整数
     *
     * @param num
     * @param type
     *            "0+":非负整数 "+":正整数 "-0":非正整数 "-":负整数 "":整数
     * @return
     */
    public static boolean checkNumber(String num, String type) {
        String eL = "";
        if (type.equals("0+"))
            eL = "^//d+$";// 非负整数
        else if (type.equals("+"))
            eL = "^//d*[1-9]//d*$";// 正整数
        else if (type.equals("-0"))
            eL = "^((-//d+)|(0+))$";// 非正整数
        else if (type.equals("-"))
            eL = "^-//d*[1-9]//d*$";// 负整数
        else
            eL = "^-?//d+$";// 整数
        Pattern p = Pattern.compile(eL);
        Matcher m = p.matcher(num);
        boolean b = m.matches();
        return b;
    }
}
