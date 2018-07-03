package com.JsonBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12 0012.
 */
public class OrderDetailFdTcBean {

    /**
     * detail : {"create_time":"2016-10-12 08:30:32","eat_date":"2016-10-12 08:29:00","goodsImg":"http://192.168.1.149/images/3.jpg","goods_id":1857674839284756,"goods_ids":"1610080419079470,1610090455325050","goods_type":1,"head_img":"","id":1857674839284756,"is_balance":0,"is_comment":0,"name":"红酒鹅肝","nick_name":"刘","order_code":"1610120830323190","order_describe":"红酒鹅肝","order_state":2,"pay_state":1,"pay_time":"2016-10-12 08:30:38","pay_way":1,"price":360,"quantity":1,"real_price":360,"refund_time":null,"shop_information_id":1610110635258390,"telphone":"15027832626","user_id":1610090721239070}
     * shopGoodsList : [{"goods_name":"红酒","new_price":180,"quantity":1},{"goods_name":"鹅肝","new_price":180,"quantity":1}]
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
         * create_time : 2016-10-12 08:30:32
         * eat_date : 2016-10-12 08:29:00
         * goodsImg : http://192.168.1.149/images/3.jpg
         * goods_id : 1857674839284756
         * goods_ids : 1610080419079470,1610090455325050
         * goods_type : 1
         * head_img :
         * id : 1857674839284756
         * is_balance : 0
         * is_comment : 0
         * name : 红酒鹅肝
         * nick_name : 刘
         * order_code : 1610120830323190
         * order_describe : 红酒鹅肝
         * order_state : 2
         * pay_state : 1
         * pay_time : 2016-10-12 08:30:38
         * pay_way : 1
         * price : 360
         * quantity : 1
         * real_price : 360
         * refund_time : null
         * shop_information_id : 1610110635258390
         * telphone : 15027832626
         * user_id : 1610090721239070
         */

        private DetailBean detail;
        /**
         * goods_name : 红酒
         * new_price : 180
         * quantity : 1
         */

        private List<ShopGoodsListBean> shopGoodsList;

        public DetailBean getDetail() {
            return detail;
        }

        public void setDetail(DetailBean detail) {
            this.detail = detail;
        }

        public List<ShopGoodsListBean> getShopGoodsList() {
            return shopGoodsList;
        }

        public void setShopGoodsList(List<ShopGoodsListBean> shopGoodsList) {
            this.shopGoodsList = shopGoodsList;
        }

        public static class DetailBean {
            private String create_time;
            private String eat_date;
            private String goodsImg;
            private long goods_id;
            private String goods_ids;
            private int goods_type;
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
            private long shop_information_id;
            private String telphone;
            private long user_id;

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getEat_date() {
                return eat_date;
            }

            public void setEat_date(String eat_date) {
                this.eat_date = eat_date;
            }

            public String getGoodsImg() {
                return goodsImg;
            }

            public void setGoodsImg(String goodsImg) {
                this.goodsImg = goodsImg;
            }

            public long getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(long goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_ids() {
                return goods_ids;
            }

            public void setGoods_ids(String goods_ids) {
                this.goods_ids = goods_ids;
            }

            public int getGoods_type() {
                return goods_type;
            }

            public void setGoods_type(int goods_type) {
                this.goods_type = goods_type;
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

            public long getShop_information_id() {
                return shop_information_id;
            }

            public void setShop_information_id(long shop_information_id) {
                this.shop_information_id = shop_information_id;
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
        }

        public static class ShopGoodsListBean {
            private String goods_name;
            private int new_price;
            private int quantity;

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public int getNew_price() {
                return new_price;
            }

            public void setNew_price(int new_price) {
                this.new_price = new_price;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
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
