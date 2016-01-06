package com.beyonditsm.financial.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.leaf.library.db.annotation.Column;
import com.leaf.library.db.annotation.Table;

/**
 * 消息
 * @author wangbin
 *
 */
@Table(name="message_table")
public class MessageBean implements Parcelable {
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1119446680541391191L;
	@Column
	private String title;
	@Column
    private String message;//消息栏描述
	@Column
    private String type;//
	@Column
    private String time;
	@Column
	private String msg_id;


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.title);
		dest.writeString(this.message);
		dest.writeString(this.type);
		dest.writeString(this.time);
		dest.writeString(this.msg_id);
	}

	public MessageBean() {
	}

	protected MessageBean(Parcel in) {
		this.title = in.readString();
		this.message = in.readString();
		this.type = in.readString();
		this.time = in.readString();
		this.msg_id = in.readString();
	}

	public static final Parcelable.Creator<MessageBean> CREATOR = new Parcelable.Creator<MessageBean>() {
		public MessageBean createFromParcel(Parcel source) {
			return new MessageBean(source);
		}

		public MessageBean[] newArray(int size) {
			return new MessageBean[size];
		}
	};
}
