package com.JsonBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class MyBalancejson implements Serializable{


    /**
     * balanceMap : {"balance":13780,"id":1610110633583220,"user_id":1610110633582710}
     * tradeLogList : [{"balance":4412,"createTime":"2016-11-01 19:11:19","id":1611010710192170,"integration":0,"name":"饭店类订单","nick_name":"哈哈","price":180,"trade_integration":0,"type":1},{"balance":4592,"createTime":"2016-11-01 19:11:49","id":1611010701498260,"integration":0,"name":"饭店类订单","nick_name":"哈哈","price":180,"trade_integration":0,"type":1},{"balance":4772,"createTime":"2016-11-01 18:11:01","id":1611010640011820,"integration":0,"name":"饭店类订单","nick_name":"哈哈","price":180,"trade_integration":0,"type":1},{"balance":4952,"createTime":"2016-11-01 18:11:07","id":1611010639073730,"integration":0,"name":"饭店类订单","nick_name":"哈哈","price":180,"trade_integration":0,"type":1},{"balance":5132,"createTime":"2016-11-01 18:11:25","id":1611010632259770,"integration":0,"name":"饭店类订单","nick_name":"哈哈","price":180,"trade_integration":0,"type":1},{"balance":5312,"createTime":"2016-11-01 18:11:29","id":1611010631298400,"integration":0,"name":"饭店类订单","nick_name":"哈哈","price":180,"trade_integration":0,"type":1},{"balance":9.9911998E7,"createTime":"2016-11-01 16:11:37","id":1611010427371720,"integration":0,"name":"饭店类订单","nick_name":"刘","price":360,"trade_integration":0,"type":1},{"balance":9.9913358E7,"createTime":"2016-11-01 16:11:48","id":1611010415481410,"integration":0,"name":"饭店类订单","nick_name":"刘","price":180,"trade_integration":0,"type":1},{"balance":9.9913538E7,"createTime":"2016-11-01 16:11:55","id":1611010414552680,"integration":0,"name":"饭店类订单","nick_name":"刘","price":180,"trade_integration":0,"type":1},{"balance":9.9913718E7,"createTime":"2016-11-01 16:11:09","id":1611010414096960,"integration":0,"name":"饭店类订单","nick_name":"刘","price":360,"trade_integration":0,"type":1},{"balance":9.9914078E7,"createTime":"2016-11-01 15:11:38","id":1611010330381940,"integration":0,"name":"饭店类订单","nick_name":"刘","price":360,"trade_integration":0,"type":1},{"balance":9.9914438E7,"createTime":"2016-11-01 15:11:26","id":1611010328266970,"integration":0,"name":"饭店类订单","nick_name":"刘","price":180,"trade_integration":0,"type":1},{"balance":9.9914618E7,"createTime":"2016-11-01 15:11:09","id":1611010302091560,"integration":0,"name":"饭店类订单","nick_name":"刘","price":360,"trade_integration":0,"type":1},{"balance":7777,"createTime":"2016-11-01 14:11:11","id":1611011203042915,"integration":0,"name":"饭店类订单","nick_name":"我们","price":180,"trade_integration":0,"type":1},{"balance":9.9915798E7,"createTime":"2016-10-31 17:10:26","id":1610310505264650,"integration":0,"name":"饭店类订单","nick_name":"刘","price":180,"trade_integration":0,"type":1}]
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
         * balance : 13780
         * id : 1610110633583220
         * user_id : 1610110633582710
         */

        private BalanceMapBean balanceMap;
        /**
         * balance : 4412
         * createTime : 2016-11-01 19:11:19
         * id : 1611010710192170
         * integration : 0
         * name : 饭店类订单
         * nick_name : 哈哈
         * price : 180
         * trade_integration : 0
         * type : 1
         */

        private List<TradeLogListBean> tradeLogList;

        public BalanceMapBean getBalanceMap() {
            return balanceMap;
        }

        public void setBalanceMap(BalanceMapBean balanceMap) {
            this.balanceMap = balanceMap;
        }

        public List<TradeLogListBean> getTradeLogList() {
            return tradeLogList;
        }

        public void setTradeLogList(List<TradeLogListBean> tradeLogList) {
            this.tradeLogList = tradeLogList;
        }

        public static class BalanceMapBean {
            private int balance;
            private long id;
            private long user_id;

            public int getBalance() {
                return balance;
            }

            public void setBalance(int balance) {
                this.balance = balance;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }
        }

        public static class TradeLogListBean {
            private int balance;
            private String createTime;
            private long id;
            private int integration;
            private String name;
            private String nick_name;
            private int price;
            private int trade_integration;
            private int type;

            public int getBalance() {
                return balance;
            }

            public void setBalance(int balance) {
                this.balance = balance;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public int getIntegration() {
                return integration;
            }

            public void setIntegration(int integration) {
                this.integration = integration;
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

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getTrade_integration() {
                return trade_integration;
            }

            public void setTrade_integration(int trade_integration) {
                this.trade_integration = trade_integration;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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
