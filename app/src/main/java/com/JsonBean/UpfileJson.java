package com.JsonBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class UpfileJson {


    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;

    private List<String> data;

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
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
}
