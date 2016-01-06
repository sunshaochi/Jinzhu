package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 产品表相关参数
 * Created by Yang on 2015/11/24 0024.
 */
public class Product implements Parcelable{

    private DataEntity data;


    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity implements Parcelable{
        private int total;
        /**
         * monthlyRate : 1
         * timeMaxVal : 1
         * publishSts : 已发布
         * productName : 1
         * modifyPerson : 张三
         * maxVal : 1
         * payType : 1
         * modifyTime : 1447935097000
         * minVal : 1
         * applyMaterial : 1
         * assuranceType : 1
         * totalInterest : 1
         * costDescribe : 1
         * productId : 2c909da3511edb9101511fa83877000d
         * mortgageType : 1
         * isValid : 1
         * totalMonth : 1
         * createPerson : 李四
         * succeedApplyCount : 0
         * imageLogoPath : 1
         * applyCondition : 1
         * organizationType : 1
         * productAmount : 1
         * timeMinVal : 1
         * createTime : 1447935101000
         * reputationLevel : 0
         * detailDescribe : 1
         * monthlyPay : 1
         * loanPeriod : 1
         */

        private List<RowsEntity> rows;

        public void setTotal(int total) {
            this.total = total;
        }

        public void setRows(List<RowsEntity> rows) {
            this.rows = rows;
        }

        public int getTotal() {
            return total;
        }

        public List<RowsEntity> getRows() {
            return rows;
        }

        public static class RowsEntity implements Parcelable{
            private int monthlyRate;//月利率 1.3代表1.3%
            private int timeMaxVal;//期限范围 最大值 单位为月 1代表1个月
            private String publishSts;//发布状态 待审批 审批通过 审批未通过
            private String productName;//产品名称
            private String modifyPerson;//修改人
            private int maxVal;//额度范围最大值
            private String payType;//还款方式
            private long modifyTime;//修改时间
            private int minVal;//额度范围最小值
            private String applyMaterial;//申请资料
            private String assuranceType;//担保类型
            private int totalInterest;//总利息 单位 元
            private String costDescribe;//费用说明
            private String productId;//产品id
            private String mortgageType;//抵押类型
            private int isValid;//是否有效，默认值1；1为有效，0为无效
            private int totalMonth;//期限 单位为月 12 代表12个月
            private String createPerson;//创建人
            private int succeedApplyCount;//成功申请人数 默认0
            private String imageLogoPath;//产品logo图片路径
            private String applyCondition;//申请条件
            private String organizationType;//机构类型
            private int productAmount;//金额 单位是万 5.2代表5.2万
            private int timeMinVal;//期限范围 最小值 单位为月 1代表1个月
            private long createTime;//创建时间
            private int reputationLevel;//好评星级 1.5代表 1星半
            private String detailDescribe;//详细说明
            private int monthlyPay;//月供 单位为元
            private int loanPeriod;//放款周期 单位 工作日

            public void setMonthlyRate(int monthlyRate) {
                this.monthlyRate = monthlyRate;
            }

            public void setTimeMaxVal(int timeMaxVal) {
                this.timeMaxVal = timeMaxVal;
            }

            public void setPublishSts(String publishSts) {
                this.publishSts = publishSts;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public void setModifyPerson(String modifyPerson) {
                this.modifyPerson = modifyPerson;
            }

            public void setMaxVal(int maxVal) {
                this.maxVal = maxVal;
            }

            public void setPayType(String payType) {
                this.payType = payType;
            }

            public void setModifyTime(long modifyTime) {
                this.modifyTime = modifyTime;
            }

            public void setMinVal(int minVal) {
                this.minVal = minVal;
            }

            public void setApplyMaterial(String applyMaterial) {
                this.applyMaterial = applyMaterial;
            }

            public void setAssuranceType(String assuranceType) {
                this.assuranceType = assuranceType;
            }

            public void setTotalInterest(int totalInterest) {
                this.totalInterest = totalInterest;
            }

            public void setCostDescribe(String costDescribe) {
                this.costDescribe = costDescribe;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public void setMortgageType(String mortgageType) {
                this.mortgageType = mortgageType;
            }

            public void setIsValid(int isValid) {
                this.isValid = isValid;
            }

            public void setTotalMonth(int totalMonth) {
                this.totalMonth = totalMonth;
            }

            public void setCreatePerson(String createPerson) {
                this.createPerson = createPerson;
            }

            public void setSucceedApplyCount(int succeedApplyCount) {
                this.succeedApplyCount = succeedApplyCount;
            }

            public void setImageLogoPath(String imageLogoPath) {
                this.imageLogoPath = imageLogoPath;
            }

            public void setApplyCondition(String applyCondition) {
                this.applyCondition = applyCondition;
            }

            public void setOrganizationType(String organizationType) {
                this.organizationType = organizationType;
            }

            public void setProductAmount(int productAmount) {
                this.productAmount = productAmount;
            }

            public void setTimeMinVal(int timeMinVal) {
                this.timeMinVal = timeMinVal;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public void setReputationLevel(int reputationLevel) {
                this.reputationLevel = reputationLevel;
            }

            public void setDetailDescribe(String detailDescribe) {
                this.detailDescribe = detailDescribe;
            }

            public void setMonthlyPay(int monthlyPay) {
                this.monthlyPay = monthlyPay;
            }

            public void setLoanPeriod(int loanPeriod) {
                this.loanPeriod = loanPeriod;
            }

            public int getMonthlyRate() {
                return monthlyRate;
            }

            public int getTimeMaxVal() {
                return timeMaxVal;
            }

            public String getPublishSts() {
                return publishSts;
            }

            public String getProductName() {
                return productName;
            }

            public String getModifyPerson() {
                return modifyPerson;
            }

            public int getMaxVal() {
                return maxVal;
            }

            public String getPayType() {
                return payType;
            }

            public long getModifyTime() {
                return modifyTime;
            }

            public int getMinVal() {
                return minVal;
            }

            public String getApplyMaterial() {
                return applyMaterial;
            }

            public String getAssuranceType() {
                return assuranceType;
            }

            public int getTotalInterest() {
                return totalInterest;
            }

            public String getCostDescribe() {
                return costDescribe;
            }

            public String getProductId() {
                return productId;
            }

            public String getMortgageType() {
                return mortgageType;
            }

            public int getIsValid() {
                return isValid;
            }

            public int getTotalMonth() {
                return totalMonth;
            }

            public String getCreatePerson() {
                return createPerson;
            }

            public int getSucceedApplyCount() {
                return succeedApplyCount;
            }

            public String getImageLogoPath() {
                return imageLogoPath;
            }

            public String getApplyCondition() {
                return applyCondition;
            }

            public String getOrganizationType() {
                return organizationType;
            }

            public int getProductAmount() {
                return productAmount;
            }

            public int getTimeMinVal() {
                return timeMinVal;
            }

            public long getCreateTime() {
                return createTime;
            }

            public int getReputationLevel() {
                return reputationLevel;
            }

            public String getDetailDescribe() {
                return detailDescribe;
            }

            public int getMonthlyPay() {
                return monthlyPay;
            }

            public int getLoanPeriod() {
                return loanPeriod;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.monthlyRate);
                dest.writeInt(this.timeMaxVal);
                dest.writeString(this.publishSts);
                dest.writeString(this.productName);
                dest.writeString(this.modifyPerson);
                dest.writeInt(this.maxVal);
                dest.writeString(this.payType);
                dest.writeLong(this.modifyTime);
                dest.writeInt(this.minVal);
                dest.writeString(this.applyMaterial);
                dest.writeString(this.assuranceType);
                dest.writeInt(this.totalInterest);
                dest.writeString(this.costDescribe);
                dest.writeString(this.productId);
                dest.writeString(this.mortgageType);
                dest.writeInt(this.isValid);
                dest.writeInt(this.totalMonth);
                dest.writeString(this.createPerson);
                dest.writeInt(this.succeedApplyCount);
                dest.writeString(this.imageLogoPath);
                dest.writeString(this.applyCondition);
                dest.writeString(this.organizationType);
                dest.writeInt(this.productAmount);
                dest.writeInt(this.timeMinVal);
                dest.writeLong(this.createTime);
                dest.writeInt(this.reputationLevel);
                dest.writeString(this.detailDescribe);
                dest.writeInt(this.monthlyPay);
                dest.writeInt(this.loanPeriod);
            }

            public RowsEntity() {
            }

            protected RowsEntity(Parcel in) {
                this.monthlyRate = in.readInt();
                this.timeMaxVal = in.readInt();
                this.publishSts = in.readString();
                this.productName = in.readString();
                this.modifyPerson = in.readString();
                this.maxVal = in.readInt();
                this.payType = in.readString();
                this.modifyTime = in.readLong();
                this.minVal = in.readInt();
                this.applyMaterial = in.readString();
                this.assuranceType = in.readString();
                this.totalInterest = in.readInt();
                this.costDescribe = in.readString();
                this.productId = in.readString();
                this.mortgageType = in.readString();
                this.isValid = in.readInt();
                this.totalMonth = in.readInt();
                this.createPerson = in.readString();
                this.succeedApplyCount = in.readInt();
                this.imageLogoPath = in.readString();
                this.applyCondition = in.readString();
                this.organizationType = in.readString();
                this.productAmount = in.readInt();
                this.timeMinVal = in.readInt();
                this.createTime = in.readLong();
                this.reputationLevel = in.readInt();
                this.detailDescribe = in.readString();
                this.monthlyPay = in.readInt();
                this.loanPeriod = in.readInt();
            }

            public static final Creator<RowsEntity> CREATOR = new Creator<RowsEntity>() {
                public RowsEntity createFromParcel(Parcel source) {
                    return new RowsEntity(source);
                }

                public RowsEntity[] newArray(int size) {
                    return new RowsEntity[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.total);
            dest.writeTypedList(rows);
        }

        public DataEntity() {
        }

        protected DataEntity(Parcel in) {
            this.total = in.readInt();
            this.rows = in.createTypedArrayList(RowsEntity.CREATOR);
        }

        public static final Creator<DataEntity> CREATOR = new Creator<DataEntity>() {
            public DataEntity createFromParcel(Parcel source) {
                return new DataEntity(source);
            }

            public DataEntity[] newArray(int size) {
                return new DataEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.data = in.readParcelable(DataEntity.class.getClassLoader());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
