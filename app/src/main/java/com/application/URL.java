package com.application;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class URL {

    public  static  final String IP="http://111.62.21.42:8080/cfdScenic/";
    //public  static  final String IP="http://139.129.167.224/cfdScenic/";
    //public  static  final String IP="http://139.129.167.224:88/cfdScenic/";
    //public  static  final String IP="http://139.129.167.238:88/cfdScenic/";


    //注册
    public  static  final  String Regist=IP+"shopUser/register";
    //获取验证码
    public  static  final String getcode=IP+"consumerUser/checkCode";
    //登录
    public  static  final  String Login=IP+"shopUser/login";
    //找回密码
    public  static  final  String ForgetPassword=IP+"shopUser/findPsw";
    //上传文件
    public  static  final  String UploadFile=IP+"interFace/myTickets/upload";
    //注册实名认证,提交注册信息
    public  static final String   auditMessage=IP+"shopUser/auditMessage";
    //获取商户余额
    public  static String myBalance=IP+"shopUser/myBalance";
    //提现
    public  static String pwWithdraw=IP+"shopUser/pwWithdraw";
    //商品获取列表
    public  static String shopOrderList=IP+"interFace/orderDetail/shopOrderList";
    //商品版--订单中心—确认发货和确认完成
    public  static String shopOrderChange=IP+"interFace/orderDetail/shopOrderChange";
    //商品版--订单中心—商品自提
    public  static String siBackMoney=IP+"interFace/orderDetail/siBackMoney";
    //商品版--订单中心—商品送货
    public  static String saveExpress=IP+"interFace/orderDetail/saveExpress";
    //商品版--商户退款订单列表
    public  static String shopRefundORder=IP+"interFace/orderDetail/shopRefundORder";
    //商品版--商户退款订单详情
    public  static String shopFindOrderDetail=IP+"interFace/orderDetail/shopFindOrderDetail";
    //商品版--商户退款商品驳回申请，审核通过
    public  static String updateGoodsOrder=IP+"interFace/orderDetail/updateGoodsOrder";
    //商品版--确认退款
    public  static String shopRefundFinsh=IP+"interFace/orderDetail/shopRefundFinsh";
    //获取酒店列表
    public  static String hotelOrderorderList=IP+"hotelOrder/orderList";
    //获取酒店订单详情
    public  static  final  String informationFindOrderDetail=IP+"interFace/orderDetail/informationFindOrderDetail";
    //取消酒店订单
   public  static  final  String  hotelOrdercancelOrder=IP+"hotelOrder/cancelOrder";
    //验证销核酒店订单
    public  static  final  String hotelOrderorderVerification=IP+"hotelOrder/orderVerification";
    //获取饭店订单列表
    public  static  final  String restaurantOrderorderList=IP+"restaurantOrder/orderList";
    //获取酒店退款订单列表
    public  static  final  String hotelRefundOrder=IP+"hotelOrder/hotelRefundOrder";
    //获取饭店退款订单列表
    public  static  final  String restaurantRefundOrder=IP+"restaurantOrder/restaurantRefundOrder";
    //取消饭店订单
    public  static  final  String restaurantOrdercancelOrder=IP+"restaurantOrder/cancelOrder";
    //验证核饭店订单
    public  static  final  String restaurantOrderorderVerification=IP+"restaurantOrder/orderVerification";
    //获取酒店订单详情
    public  static  final  String restaurantOrderorderDetail=IP+"restaurantOrder/orderDetail";
    //修改密码
    public  static  final String updateShopPsw=IP+"shopUser/updateShopPsw";
    //订单详情
    public  static  final String orderDetail=IP+"restaurantOrder/orderDetail";
    //数据统计
    public  static  final String orderCount=IP+"interFace/orderDetail/orderCount";
    //订单管理--搜索
    public  static  final String selectOrder=IP+"interFace/orderDetail/selectOrder";
    //店铺中心--消息中心
    public  static  final String myMessage=IP+"shopUser/myMessage";
    //店铺中心--消息中心--详情
    public  static  final String myMessageDetail=IP+"shopUser/myMessageDetail";
    //店铺中心--店铺信息
    public  static  final String storeMessage=IP+"shopUser/storeMessage";

}
