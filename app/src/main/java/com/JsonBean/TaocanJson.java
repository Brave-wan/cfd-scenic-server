package com.JsonBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class TaocanJson implements Serializable {

    /**
     * lastPage : 1
     * rows : [[{"describe_img":"http://192.168.1.149/images/1.jpg","eat_date":"2016-09-29 21:28:00","goods_type":1,"head_img":null,"id":1609291028507431,"name":"阿里巴巴","nick_name":null,"order_code":"1609291028507430","order_describe":"阿里巴巴","order_state":2,"pay_state":1,"real_price":40}]]
     * total : 1
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
        private int lastPage;
        private int total;
        /**
         * describe_img : http://192.168.1.149/images/1.jpg
         * eat_date : 2016-09-29 21:28:00
         * goods_type : 1
         * head_img : null
         * id : 1609291028507431
         * name : 阿里巴巴
         * nick_name : null
         * order_code : 1609291028507430
         * order_describe : 阿里巴巴
         * order_state : 2
         * pay_state : 1
         * real_price : 40
         */

        private List<List<RowsBean>> rows=new ArrayList<>();

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
            private String describe_img;
            private String eat_date;
            private int goods_type;
            private String head_img;
            private long id;
            private String name;
            private String nick_name;
            private String order_code;
            private String order_describe;
            private int order_state;
            private int pay_state;
            private int real_price;

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            private int quantity;
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
