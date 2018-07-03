package com.JsonBean;

/**
 * Created by 钱月 on 2016/9/26 0026.
 *
 * 请求成功
 */
public class LoginBean {


    /**
     * shopMessge : {"accountBank":"456","accountName":"111","accountType":2,"address":null,"age":1,"auditFail":null,"backCardImg":"223","backgroudImg":null,"bankCard":"223","businessScope":"11","cashPassWord":null,"consumption":null,"content":null,"createTime":null,"detailId":null,"endDate":null,"faceCardImg":"http://192.168.1.149/cfdScenic/scripts/upload/1609260338323420/1474947910397d282bb8c262d47cd90922f4a8471a806image1.jpg","headImg":null,"holdCardImg":"http://192.168.1.149/cfdScenic/scripts/upload/1609260338323420/14749479103964ba1a511d12841d7af9c0340c5e122e6image0.jpg","id":1609271145106150,"idCard":"130682199112027097","isAudit":0,"isBlss":null,"isFood":null,"isLicense":1,"isMedia":null,"isWifi":null,"isYushi":null,"latitude":null,"licenseImg":"","longitude":null,"name":"11","otherImg1":"http://192.168.1.149/cfdScenic/scripts/upload/1609260338323420/14749479103988c6f4bdb4a4e47c6a090106d5dcbd613image3.jpg","otherImg2":"http://192.168.1.149/cfdScenic/scripts/upload/1609260338323420/14749479103988a11587ee5d0441baf75ce3f269b9336image4.jpg","phone":null,"realName":"1","sex":1,"shopId":2,"shopImg":null,"shopUserId":1609260338323420,"startDate":null,"state":0}
     * state : 0
     * token : 8407e14b-f8d9-43a2-8fd5-66448e243248
     * userId : 1609260338323420
     */

    private DataBean data;
    /**
     * msg : 登录成功
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
         * accountBank : 456
         * accountName : 111
         * accountType : 2
         * address : null
         * age : 1
         * auditFail : null
         * backCardImg : 223
         * backgroudImg : null
         * bankCard : 223
         * businessScope : 11
         * cashPassWord : null
         * consumption : null
         * content : null
         * createTime : null
         * detailId : null
         * endDate : null
         * faceCardImg : http://192.168.1.149/cfdScenic/scripts/upload/1609260338323420/1474947910397d282bb8c262d47cd90922f4a8471a806image1.jpg
         * headImg : null
         * holdCardImg : http://192.168.1.149/cfdScenic/scripts/upload/1609260338323420/14749479103964ba1a511d12841d7af9c0340c5e122e6image0.jpg
         * id : 1609271145106150
         * idCard : 130682199112027097
         * isAudit : 0
         * isBlss : null
         * isFood : null
         * isLicense : 1
         * isMedia : null
         * isWifi : null
         * isYushi : null
         * latitude : null
         * licenseImg :
         * longitude : null
         * name : 11
         * otherImg1 : http://192.168.1.149/cfdScenic/scripts/upload/1609260338323420/14749479103988c6f4bdb4a4e47c6a090106d5dcbd613image3.jpg
         * otherImg2 : http://192.168.1.149/cfdScenic/scripts/upload/1609260338323420/14749479103988a11587ee5d0441baf75ce3f269b9336image4.jpg
         * phone : null
         * realName : 1
         * sex : 1
         * shopId : 2
         * shopImg : null
         * shopUserId : 1609260338323420
         * startDate : null
         * state : 0
         */

        private AuthenticationJson shopMessge=new AuthenticationJson();
        private int state;
        private String token;
        private long userId;

        public AuthenticationJson getShopMessge() {
            return shopMessge;
        }

        public void setShopMessge(AuthenticationJson shopMessge) {
            this.shopMessge = shopMessge;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public static class ShopMessgeBean {
            private String accountBank;
            private String accountName;
            private int accountType;
            private Object address;
            private int age;
            private Object auditFail;
            private String backCardImg;
            private Object backgroudImg;
            private String bankCard;
            private String businessScope;
            private Object cashPassWord;
            private Object consumption;
            private Object content;
            private Object createTime;
            private Object detailId;
            private Object endDate;
            private String faceCardImg;
            private Object headImg;
            private String holdCardImg;
            private long id;
            private String idCard;
            private int isAudit;
            private Object isBlss;
            private Object isFood;
            private int isLicense;
            private Object isMedia;
            private Object isWifi;
            private Object isYushi;
            private Object latitude;
            private String licenseImg;
            private Object longitude;
            private String name;
            private String otherImg1;
            private String otherImg2;
            private Object phone;
            private String realName;
            private int sex;
            private int shopId;
            private Object shopImg;
            private long shopUserId;
            private Object startDate;
            private int state;

            public String getAccountBank() {
                return accountBank;
            }

            public void setAccountBank(String accountBank) {
                this.accountBank = accountBank;
            }

            public String getAccountName() {
                return accountName;
            }

            public void setAccountName(String accountName) {
                this.accountName = accountName;
            }

            public int getAccountType() {
                return accountType;
            }

            public void setAccountType(int accountType) {
                this.accountType = accountType;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public Object getAuditFail() {
                return auditFail;
            }

            public void setAuditFail(Object auditFail) {
                this.auditFail = auditFail;
            }

            public String getBackCardImg() {
                return backCardImg;
            }

            public void setBackCardImg(String backCardImg) {
                this.backCardImg = backCardImg;
            }

            public Object getBackgroudImg() {
                return backgroudImg;
            }

            public void setBackgroudImg(Object backgroudImg) {
                this.backgroudImg = backgroudImg;
            }

            public String getBankCard() {
                return bankCard;
            }

            public void setBankCard(String bankCard) {
                this.bankCard = bankCard;
            }

            public String getBusinessScope() {
                return businessScope;
            }

            public void setBusinessScope(String businessScope) {
                this.businessScope = businessScope;
            }

            public Object getCashPassWord() {
                return cashPassWord;
            }

            public void setCashPassWord(Object cashPassWord) {
                this.cashPassWord = cashPassWord;
            }

            public Object getConsumption() {
                return consumption;
            }

            public void setConsumption(Object consumption) {
                this.consumption = consumption;
            }

            public Object getContent() {
                return content;
            }

            public void setContent(Object content) {
                this.content = content;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public Object getDetailId() {
                return detailId;
            }

            public void setDetailId(Object detailId) {
                this.detailId = detailId;
            }

            public Object getEndDate() {
                return endDate;
            }

            public void setEndDate(Object endDate) {
                this.endDate = endDate;
            }

            public String getFaceCardImg() {
                return faceCardImg;
            }

            public void setFaceCardImg(String faceCardImg) {
                this.faceCardImg = faceCardImg;
            }

            public Object getHeadImg() {
                return headImg;
            }

            public void setHeadImg(Object headImg) {
                this.headImg = headImg;
            }

            public String getHoldCardImg() {
                return holdCardImg;
            }

            public void setHoldCardImg(String holdCardImg) {
                this.holdCardImg = holdCardImg;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getIdCard() {
                return idCard;
            }

            public void setIdCard(String idCard) {
                this.idCard = idCard;
            }

            public int getIsAudit() {
                return isAudit;
            }

            public void setIsAudit(int isAudit) {
                this.isAudit = isAudit;
            }

            public Object getIsBlss() {
                return isBlss;
            }

            public void setIsBlss(Object isBlss) {
                this.isBlss = isBlss;
            }

            public Object getIsFood() {
                return isFood;
            }

            public void setIsFood(Object isFood) {
                this.isFood = isFood;
            }

            public int getIsLicense() {
                return isLicense;
            }

            public void setIsLicense(int isLicense) {
                this.isLicense = isLicense;
            }

            public Object getIsMedia() {
                return isMedia;
            }

            public void setIsMedia(Object isMedia) {
                this.isMedia = isMedia;
            }

            public Object getIsWifi() {
                return isWifi;
            }

            public void setIsWifi(Object isWifi) {
                this.isWifi = isWifi;
            }

            public Object getIsYushi() {
                return isYushi;
            }

            public void setIsYushi(Object isYushi) {
                this.isYushi = isYushi;
            }

            public Object getLatitude() {
                return latitude;
            }

            public void setLatitude(Object latitude) {
                this.latitude = latitude;
            }

            public String getLicenseImg() {
                return licenseImg;
            }

            public void setLicenseImg(String licenseImg) {
                this.licenseImg = licenseImg;
            }

            public Object getLongitude() {
                return longitude;
            }

            public void setLongitude(Object longitude) {
                this.longitude = longitude;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOtherImg1() {
                return otherImg1;
            }

            public void setOtherImg1(String otherImg1) {
                this.otherImg1 = otherImg1;
            }

            public String getOtherImg2() {
                return otherImg2;
            }

            public void setOtherImg2(String otherImg2) {
                this.otherImg2 = otherImg2;
            }

            public Object getPhone() {
                return phone;
            }

            public void setPhone(Object phone) {
                this.phone = phone;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public int getShopId() {
                return shopId;
            }

            public void setShopId(int shopId) {
                this.shopId = shopId;
            }

            public Object getShopImg() {
                return shopImg;
            }

            public void setShopImg(Object shopImg) {
                this.shopImg = shopImg;
            }

            public long getShopUserId() {
                return shopUserId;
            }

            public void setShopUserId(long shopUserId) {
                this.shopUserId = shopUserId;
            }

            public Object getStartDate() {
                return startDate;
            }

            public void setStartDate(Object startDate) {
                this.startDate = startDate;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
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
