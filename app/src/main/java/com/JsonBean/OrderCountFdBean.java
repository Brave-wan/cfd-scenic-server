package com.JsonBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/11 0011.
 */
public class OrderCountFdBean {


    /**
     * all : {"count":7,"realPrice":4972}
     * orderList : [[{"describe_img":"http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1476147079706sc4-tu2@3x.png","eat_date":"2016-10-11 11:16:00","goods_name":"番茄","goods_type":0,"id":1610111117401431,"name":"番茄","nick_name":"刘","order_code":"1610111117401430","order_describe":"番茄啊番茄","order_state":4,"pay_state":1,"quantity":1,"real_price":180,"userHeadImg":"","userId":1610090721239070},{"describe_img":"http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1476147115261sc7-tu1@3x.png","eat_date":"2016-10-11 11:16:00","goods_name":"鸡蛋","goods_type":0,"id":1610111117402140,"name":"鸡蛋","nick_name":"刘","order_code":"1610111117401430","order_describe":"鸡蛋啊鸡蛋","order_state":4,"pay_state":1,"quantity":1,"real_price":130,"userHeadImg":"","userId":1610090721239070}],[{"describe_img":"http://192.168.1.149/images/2.jpg","eat_date":"2016-10-11 09:30:00","goods_name":"番茄炒鸡蛋","goods_type":1,"id":1610110932570371,"name":"番茄炒鸡蛋","nick_name":"刘","order_code":"1610110932570370","order_describe":"番茄炒鸡蛋","order_state":4,"pay_state":1,"quantity":3,"real_price":1998,"userHeadImg":"","userId":1610090721239070}],[{"describe_img":"http://192.168.1.149/images/2.jpg","eat_date":"2016-10-11 11:16:00","goods_name":"番茄炒鸡蛋","goods_type":1,"id":1610111116553761,"name":"番茄炒鸡蛋","nick_name":"刘","order_code":"1610111116553760","order_describe":"番茄炒鸡蛋","order_state":4,"pay_state":1,"quantity":1,"real_price":666,"userHeadImg":"","userId":1610090721239070}],[{"describe_img":"http://192.168.1.149/images/2.jpg","eat_date":"2016-10-11 11:16:00","goods_name":"番茄炒鸡蛋","goods_type":1,"id":1610111117207211,"name":"番茄炒鸡蛋","nick_name":"刘","order_code":"1610111117207210","order_describe":"番茄炒鸡蛋","order_state":2,"pay_state":1,"quantity":1,"real_price":666,"userHeadImg":"","userId":1610090721239070}],[{"describe_img":"http://192.168.1.149/images/2.jpg","eat_date":"2016-10-11 11:16:00","goods_name":"番茄炒鸡蛋","goods_type":1,"id":1610111116557041,"name":"番茄炒鸡蛋","nick_name":"刘","order_code":"1610111116557040","order_describe":"番茄炒鸡蛋","order_state":2,"pay_state":1,"quantity":1,"real_price":666,"userHeadImg":"","userId":1610090721239070}],[{"describe_img":"http://192.168.1.149/images/2.jpg","eat_date":"2016-10-11 11:20:00","goods_name":"番茄炒鸡蛋","goods_type":1,"id":1610111120598791,"name":"番茄炒鸡蛋","nick_name":"今天","order_code":"1610111120598790","order_describe":"番茄炒鸡蛋","order_state":2,"pay_state":1,"quantity":1,"real_price":666,"userHeadImg":"http://192.168.1.149/images/01.jpg","userId":1608160918510940}]]
     * today : {"count":7,"realPrice":4972}
     */

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
         * count : 7
         * realPrice : 4972
         */

        private AllBean all;
        /**
         * count : 7
         * realPrice : 4972
         */

        private TodayBean today;
        /**
         * describe_img : http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1476147079706sc4-tu2@3x.png
         * eat_date : 2016-10-11 11:16:00
         * goods_name : 番茄
         * goods_type : 0
         * id : 1610111117401431
         * name : 番茄
         * nick_name : 刘
         * order_code : 1610111117401430
         * order_describe : 番茄啊番茄
         * order_state : 4
         * pay_state : 1
         * quantity : 1
         * real_price : 180
         * userHeadImg :
         * userId : 1610090721239070
         */

        private List<List<OrderListBean>> orderList;

        public AllBean getAll() {
            return all;
        }

        public void setAll(AllBean all) {
            this.all = all;
        }

        public TodayBean getToday() {
            return today;
        }

        public void setToday(TodayBean today) {
            this.today = today;
        }

        public List<List<OrderListBean>> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<List<OrderListBean>> orderList) {
            this.orderList = orderList;
        }

        public static class AllBean {
            private int count;
            private int realPrice;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getRealPrice() {
                return realPrice;
            }

            public void setRealPrice(int realPrice) {
                this.realPrice = realPrice;
            }
        }

        public static class TodayBean {
            private int count;
            private int realPrice;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getRealPrice() {
                return realPrice;
            }

            public void setRealPrice(int realPrice) {
                this.realPrice = realPrice;
            }
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
