package com.JsonBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13 0013.
 */
public class MyMessageBean {

    /**
     * lastPage : 1
     * rows : [{"createDate":"2016-10-10 13:49:59.0","detailId":1609220244031770,"id":1,"image":"http://192.168.1.149/images/13.png","orderCode":"0","title":"系统消息","type":0,"userId":null},{"createDate":"2016-10-10 13:49:59.0","detailId":1609220244031770,"id":2,"image":"http://192.168.1.149/images/12.png","orderCode":"0","title":"系统消息2","type":0,"userId":null},{"createDate":"2016-10-10 13:49:59.0","detailId":1609220244031770,"id":3,"image":"http://192.168.1.149/images/11.png","orderCode":"0","title":"系统消息3","type":0,"userId":null}]
     * total : 3
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
        private int lastPage;
        private int total;
        /**
         * createDate : 2016-10-10 13:49:59.0
         * detailId : 1609220244031770
         * id : 1
         * image : http://192.168.1.149/images/13.png
         * orderCode : 0
         * title : 系统消息
         * type : 0
         * userId : null
         */

        private List<RowsBean> rows;

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

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            private String createDate;
            private long detailId;
            private long id;
            private String image;
            private String orderCode;
            private String title;
            private int type;
            private Object userId;

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public long getDetailId() {
                return detailId;
            }

            public void setDetailId(long detailId) {
                this.detailId = detailId;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public Object getUserId() {
                return userId;
            }

            public void setUserId(Object userId) {
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
