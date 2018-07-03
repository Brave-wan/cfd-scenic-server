package com.JsonBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12 0012.
 */
public class OrderCountSpBean {

    /**
     * all : {"count":7,"realPrice":1480}
     * orderList : [[{"deliver_fee":30,"describe_img":"http://192.168.1.149/images/06.jpg","goods_name":"曹妃甸腰果","goods_price":200,"goods_real_price":180,"head_img":"","id":1610121138343330,"is_deliver_fee":0,"is_pickup":1,"name":"曹妃甸腰果","nick_name":"刘","order_code":"1610121138343320","order_describe":"腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述","order_state":3,"pay_state":1,"price":180,"quantity":1,"real_price":230},{"deliver_fee":20,"describe_img":"http://192.168.1.149/images/06.jpg","goods_name":"曹妃甸罗汉果","goods_price":160,"goods_real_price":130,"head_img":"","id":1610121138343810,"is_deliver_fee":0,"is_pickup":0,"name":"曹妃甸罗汉果","nick_name":"刘","order_code":"1610121138343320","order_describe":"商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述","order_state":3,"pay_state":1,"price":130,"quantity":1,"real_price":180},{"deliver_fee":50,"describe_img":"http://192.168.1.149/images/06.jpg","goods_name":"曹妃甸腰果","goods_price":200,"goods_real_price":180,"head_img":"","id":1610121138344310,"is_deliver_fee":1,"is_pickup":1,"name":"总和","nick_name":"刘","order_code":"1610121138343320","order_describe":"总和","order_state":3,"pay_state":1,"price":0,"quantity":2,"real_price":310}],[{"deliver_fee":30,"describe_img":"http://192.168.1.149/images/06.jpg","goods_name":"曹妃甸腰果","goods_price":200,"goods_real_price":180,"head_img":"","id":1610121137349991,"is_deliver_fee":0,"is_pickup":1,"name":"刘","nick_name":"刘","order_code":"1610121137349990","order_describe":"腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述","order_state":3,"pay_state":1,"price":200,"quantity":1,"real_price":180},{"deliver_fee":30,"describe_img":"http://192.168.1.149/images/06.jpg","goods_name":"曹妃甸腰果","goods_price":200,"goods_real_price":180,"head_img":"","id":1610121137350920,"is_deliver_fee":1,"is_pickup":1,"name":"总和","nick_name":"刘","order_code":"1610121137349990","order_describe":"总和","order_state":3,"pay_state":1,"price":0,"quantity":1,"real_price":200}],[{"deliver_fee":30,"describe_img":"http://192.168.1.149/images/06.jpg","goods_name":"曹妃甸腰果","goods_price":200,"goods_real_price":180,"head_img":"","id":1610121138035481,"is_deliver_fee":0,"is_pickup":0,"name":"刘","nick_name":"刘","order_code":"1610121138035480","order_describe":"腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述","order_state":3,"pay_state":1,"price":200,"quantity":1,"real_price":180},{"deliver_fee":30,"describe_img":"http://192.168.1.149/images/06.jpg","goods_name":"曹妃甸腰果","goods_price":200,"goods_real_price":180,"head_img":"","id":1610121138038300,"is_deliver_fee":1,"is_pickup":0,"name":"总和","nick_name":"刘","order_code":"1610121138035480","order_describe":"总和","order_state":3,"pay_state":1,"price":0,"quantity":1,"real_price":200}]]
     * today : {"count":7,"realPrice":1480}
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
         * realPrice : 1480
         */

        private AllBean all;
        /**
         * count : 7
         * realPrice : 1480
         */

        private TodayBean today;
        /**
         * deliver_fee : 30
         * describe_img : http://192.168.1.149/images/06.jpg
         * goods_name : 曹妃甸腰果
         * goods_price : 200
         * goods_real_price : 180
         * head_img :
         * id : 1610121138343330
         * is_deliver_fee : 0
         * is_pickup : 1
         * name : 曹妃甸腰果
         * nick_name : 刘
         * order_code : 1610121138343320
         * order_describe : 腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述
         * order_state : 3
         * pay_state : 1
         * price : 180
         * quantity : 1
         * real_price : 230
         * userId;1231
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
            private int deliver_fee;
            private String describe_img;
            private String goods_name;
            private int goods_price;
            private int goods_real_price;
            private String head_img;
            private long id;
            private int is_deliver_fee;
            private int is_pickup;
            private String name;
            private String nick_name;
            private String order_code;
            private String order_describe;
            private int order_state;
            private int pay_state;
            private int price;
            private int quantity;
            private int real_price;
            private long userId;

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public int getDeliver_fee() {
                return deliver_fee;
            }

            public void setDeliver_fee(int deliver_fee) {
                this.deliver_fee = deliver_fee;
            }

            public String getDescribe_img() {
                return describe_img;
            }

            public void setDescribe_img(String describe_img) {
                this.describe_img = describe_img;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public int getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(int goods_price) {
                this.goods_price = goods_price;
            }

            public int getGoods_real_price() {
                return goods_real_price;
            }

            public void setGoods_real_price(int goods_real_price) {
                this.goods_real_price = goods_real_price;
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

            public int getIs_deliver_fee() {
                return is_deliver_fee;
            }

            public void setIs_deliver_fee(int is_deliver_fee) {
                this.is_deliver_fee = is_deliver_fee;
            }

            public int getIs_pickup() {
                return is_pickup;
            }

            public void setIs_pickup(int is_pickup) {
                this.is_pickup = is_pickup;
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
