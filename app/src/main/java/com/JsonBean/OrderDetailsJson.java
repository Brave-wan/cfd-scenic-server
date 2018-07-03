package com.JsonBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class OrderDetailsJson implements Serializable {


    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;
    /**
     * check_days : 1
     * create_time : 2016-09-27 09:01:55
     * describe_img : http://192.168.1.149/images/1.jpg
     * end_date : 2016-09-28 12:00:00
     * goods_id : 17
     * goods_name : 月月的酒店
     * head_img : http://192.168.1.149/images/1.jpg
     * id : 1609270901550930
     * is_balance : 0
     * is_comment : 0
     * name : 这是月月的酒店
     * nick_name : 大圣
     * order_code : 1609270901550940
     * order_describe : 月月的酒店
     * order_state : 2
     * pay_state : 1
     * pay_time : 2016-09-27 09:02:05
     * pay_way : 1
     * personName : ["阿基拉","嗯哼哼"]
     * price : 223
     * quantity : 2
     * real_price : 446
     * refund_time : null
     * start_date : 2016-09-27 12:00:00
     * telphone : 13464578964
     * user_id : 1609190445546590
     */

    private List<DataBean> data=new ArrayList<>();

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class HeaderBean {
        private String msg;
        private int status;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class DataBean {
        private int check_days;
        private String create_time;
        private String describe_img;
        private String end_date;
        private long goods_id;
        private String goods_name;
        private String head_img;
        private long id;
        private int is_balance;
        private int is_comment;
        private String name;
        private String nick_name;
        private String order_code;
        private String order_describe;
        private int order_state;
        private int pay_state;
        private String pay_time;
        private int pay_way;
        private int price;
        private int quantity;
        private int real_price;
        private Object refund_time;
        private String start_date;
        private String telphone;
        private long user_id;
        private List<String> personName;

        public int getCheck_days() {
            return check_days;
        }

        public void setCheck_days(int check_days) {
            this.check_days = check_days;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getDescribe_img() {
            return describe_img;
        }

        public void setDescribe_img(String describe_img) {
            this.describe_img = describe_img;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public long getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(long goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getIs_balance() {
            return is_balance;
        }

        public void setIs_balance(int is_balance) {
            this.is_balance = is_balance;
        }

        public int getIs_comment() {
            return is_comment;
        }

        public void setIs_comment(int is_comment) {
            this.is_comment = is_comment;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getOrder_describe() {
            return order_describe;
        }

        public void setOrder_describe(String order_describe) {
            this.order_describe = order_describe;
        }

        public int getOrder_state() {
            return order_state;
        }

        public void setOrder_state(int order_state) {
            this.order_state = order_state;
        }

        public int getPay_state() {
            return pay_state;
        }

        public void setPay_state(int pay_state) {
            this.pay_state = pay_state;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public int getPay_way() {
            return pay_way;
        }

        public void setPay_way(int pay_way) {
            this.pay_way = pay_way;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getReal_price() {
            return real_price;
        }

        public void setReal_price(int real_price) {
            this.real_price = real_price;
        }

        public Object getRefund_time() {
            return refund_time;
        }

        public void setRefund_time(Object refund_time) {
            this.refund_time = refund_time;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public List<String> getPersonName() {
            return personName;
        }

        public void setPersonName(List<String> personName) {
            this.personName = personName;
        }
    }
}
