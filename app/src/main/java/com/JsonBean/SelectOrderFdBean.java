package com.JsonBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/11 0011.
 */
public class SelectOrderFdBean {

    private DataBean data;
    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public static class DataBean {
        /**
         * describe_img : null
         * eat_date : 2016-10-11 11:20:00
         * goods_name : null
         * goods_type : 1
         * id : 1610111120598791
         * name : 番茄炒鸡蛋
         * nick_name : 今天
         * order_code : 1610111120598790
         * order_describe : 番茄炒鸡蛋
         * order_state : 2
         * pay_state : 1
         * quantity : 1
         * real_price : 666
         * userHeadImg : http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1608160918510940/1476174566076image.png
         * userId : 1608160918510940
         */

        private List<List<OrderListBean>> orderList;

        public List<List<OrderListBean>> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<List<OrderListBean>> orderList) {
            this.orderList = orderList;
        }

        public static class OrderListBean {
            private String describe_img;
            private String eat_date;
            private String goods_name;
            private int goods_type;
            private long id;
            private String name;
            private String nick_name;
            private String order_code;
            private String order_describe;
            private int order_state;
            private int pay_state;
            private int quantity;
            private int real_price;
            private String userHeadImg;
            private long userId;

            public String getDescribe_img() {
                return describe_img;
            }

            public void setDescribe_img(String describe_img) {
                this.describe_img = describe_img;
            }

            public String getEat_date() {
                return eat_date;
            }

            public void setEat_date(String eat_date) {
                this.eat_date = eat_date;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public int getGoods_type() {
                return goods_type;
            }

            public void setGoods_type(int goods_type) {
                this.goods_type = goods_type;
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

            public String getUserHeadImg() {
                return userHeadImg;
            }

            public void setUserHeadImg(String userHeadImg) {
                this.userHeadImg = userHeadImg;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }
        }
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
}
