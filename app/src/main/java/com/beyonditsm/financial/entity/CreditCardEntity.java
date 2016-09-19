package com.beyonditsm.financial.entity;

import java.util.List;

/**
 * Created by xuleyuan on 2016/9/8
 */
public class CreditCardEntity {

    /**
     * id : 89w289uwriodsi9328
     * creditcardName : 光大银行信用卡
     * mobileCreditcardDesc : ["额度高","发卡快","安全性高"]
     * mobileUrl : http://www.xxxx.com
     * mobileCreditcardImg : img/2824449919861760.jpg
     */

    private List<CreditCardsBean> creditCards;

    public List<CreditCardsBean> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCardsBean> creditCards) {
        this.creditCards = creditCards;
    }

    public static class CreditCardsBean {
        private String id;
        private String creditcardName;
        private String mobileUrl;
        private String mobileCreditcardImg;
        private String mobileCreditcardDesc;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreditcardName() {
            return creditcardName;
        }

        public void setCreditcardName(String creditcardName) {
            this.creditcardName = creditcardName;
        }

        public String getMobileUrl() {
            return mobileUrl;
        }

        public void setMobileUrl(String mobileUrl) {
            this.mobileUrl = mobileUrl;
        }

        public String getMobileCreditcardImg() {
            return mobileCreditcardImg;
        }

        public void setMobileCreditcardImg(String mobileCreditcardImg) {
            this.mobileCreditcardImg = mobileCreditcardImg;
        }

        public String getMobileCreditcardDesc() {
            return mobileCreditcardDesc;
        }

        public void setMobileCreditcardDesc(String mobileCreditcardDesc) {
            this.mobileCreditcardDesc = mobileCreditcardDesc;
        }
    }
}
