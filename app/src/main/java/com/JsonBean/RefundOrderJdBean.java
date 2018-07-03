package com.JsonBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class RefundOrderJdBean {

    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;
    /**
     * check_days : 1
     * consumerId : 1609190445546590
     * describe_img : http://192.168.1.149/images/1.jpg
     * end_date : 2016-09-30 12:00:00
     * head_img : http://192.168.1.149/images/1.jpg
     * id : 1609290301033940
     * name : 这是月月的酒店
     * nick_name : 大圣
     * order_code : 1609290301033941
     * order_state : 3
     * price : 223
     * quantity : 1
     * real_price : 223
     * start_date : 2016-09-29 12:00:00
     */

    private List<DataBean> data;

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
        private long consumerId;
        private String describe_img;
        private String end_date;
        private String head_img;
        private long id;
        private String name;
        private String nick_name;
        private String order_code;
        private int order_state;
        private int price;
        private int quantity;
        private int real_price;
        private String start_date;

        public int getCheck_days() {
            return check_days;
        }

        public void setCheck_days(int check_days) {
            this.check_days = check_days;
        }

        public long getConsumerId() {
            return consumerId;
        }

        public void setConsumerId(long consumerId) {
            this.consumerId = consumerId;
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

        public int getOrder_state() {
            return order_state;
        }

        public void setOrder_state(int order_state) {
            this.order_state = order_state;
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

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }
    }
}
