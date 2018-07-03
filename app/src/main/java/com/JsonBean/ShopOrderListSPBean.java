package com.JsonBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class ShopOrderListSPBean {


    /**
     * orderCount : 3
     * orderList : {"lastPage":2,"rows":[[{"deliver_fee":30,"describe_img":"http://192.168.1.149/images/06.jpg","goods_name":"曹妃甸腰果","goods_price":200,"goods_real_price":180,"head_img":"","id":1610140923362311,"is_pickup":1,"name":"刘","nick_name":"刘","order_code":"1610140923362310","order_describe":"腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述","order_state":2,"pay_state":1,"price":200,"quantity":1,"real_price":180}],[{"deliver_fee":20,"describe_img":"http://192.168.1.149/images/06.jpg","goods_name":"曹妃甸罗汉果","goods_price":160,"goods_real_price":130,"head_img":"","id":1610140922370851,"is_pickup":1,"name":"刘","nick_name":"刘","order_code":"1610140922370850","order_describe":"商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述","order_state":2,"pay_state":1,"price":160,"quantity":1,"real_price":130}],[{"deliver_fee":30,"describe_img":"http://192.168.1.149/images/06.jpg","goods_name":"曹妃甸腰果","goods_price":200,"goods_real_price":180,"head_img":"","id":1610140923240321,"is_pickup":1,"name":"刘","nick_name":"刘","order_code":"1610140923240320","order_describe":"腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述","order_state":2,"pay_state":1,"price":200,"quantity":1,"real_price":180}]],"total":5}
     */

    private DataBean data;
    /**
     * msg : success
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
        private int orderCount;
        /**
         * lastPage : 2
         * rows : [[{"deliver_fee":30,"describe_img":"http://192.168.1.149/images/06.jpg","goods_name":"曹妃甸腰果","goods_price":200,"goods_real_price":180,"head_img":"","id":1610140923362311,"is_pickup":1,"name":"刘","nick_name":"刘","order_code":"1610140923362310","order_describe":"腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述","order_state":2,"pay_state":1,"price":200,"quantity":1,"real_price":180}],[{"deliver_fee":20,"describe_img":"http://192.168.1.149/images/06.jpg","goods_name":"曹妃甸罗汉果","goods_price":160,"goods_real_price":130,"head_img":"","id":1610140922370851,"is_pickup":1,"name":"刘","nick_name":"刘","order_code":"1610140922370850","order_describe":"商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述","order_state":2,"pay_state":1,"price":160,"quantity":1,"real_price":130}],[{"deliver_fee":30,"describe_img":"http://192.168.1.149/images/06.jpg","goods_name":"曹妃甸腰果","goods_price":200,"goods_real_price":180,"head_img":"","id":1610140923240321,"is_pickup":1,"name":"刘","nick_name":"刘","order_code":"1610140923240320","order_describe":"腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述","order_state":2,"pay_state":1,"price":200,"quantity":1,"real_price":180}]]
         * total : 5
         */

        private OrderListBean orderList;

        public int getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(int orderCount) {
            this.orderCount = orderCount;
        }

        public OrderListBean getOrderList() {
            return orderList;
        }

        public void setOrderList(OrderListBean orderList) {
            this.orderList = orderList;
        }

        public static class OrderListBean {
            private int lastPage;
            private int total;
            /**
             * deliver_fee : 30
             * describe_img : http://192.168.1.149/images/06.jpg
             * goods_name : 曹妃甸腰果
             * goods_price : 200
             * goods_real_price : 180
             * head_img :
             * id : 1610140923362311
             * is_pickup : 1
             * name : 刘
             * nick_name : 刘
             * order_code : 1610140923362310
             * order_describe : 腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述
             * order_state : 2
             * pay_state : 1
             * price : 200
             * quantity : 1
             * real_price : 180
             */

            private List<List<RowsBean>> rows;

            public int getLastPage() {
                return lastPage;
            }

            public void setLastPage(int lastPage) {
                this.lastPage = lastPage;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public List<List<RowsBean>> getRows() {
                return rows;
            }

            public void setRows(List<List<RowsBean>> rows) {
                this.rows = rows;
            }

            public static class RowsBean {
                private int deliver_fee;
                private String describe_img;
                private String goods_name;
                private int goods_price;
                private int goods_real_price;
                private String head_img;
                private long id;
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
