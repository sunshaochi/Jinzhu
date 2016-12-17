package com.beyonditsm.financial.entity;

/**
 * EventBus回调事件
 * Created by wangbin on 15/11/28.
 */
public class UserEvent {

    public ProfileInfoBean ue;
    public CreditManager cm;
    public ServantEntity se;
    public int position;
    public String type;
    public UserEvent(ProfileInfoBean ueI,int posi){
        ue=ueI;
        position=posi;
    }
    public UserEvent(CreditManager cmI,int posI){
        cm = cmI;
       position = posI;
    }
    public UserEvent(ServantEntity seI,int posI){
        se = seI;
        position = posI;
    }
}
