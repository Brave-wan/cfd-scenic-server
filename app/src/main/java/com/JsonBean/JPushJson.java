package com.JsonBean;

/**
 * Created by Administrator on 2016/10/24 0024.
 */
public class JPushJson {


    /**
     * content : 您有新的订单，请注意查收！
     * siId : 1610110635258390
     * type : 1
     * orderCode : 1610240858554230
     * goodsType : 1
     */

    private String content;
    private String siId;
    private String type;
    private String orderCode;
    private String goodsType;
    private String detailId;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSiId() {
        return siId;
    }

    public void setSiId(String siId) {
        this.siId = siId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }
}
