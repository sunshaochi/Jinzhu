package com.beyonditsm.financial.util;

import com.beyonditsm.financial.entity.PhoneInfo;

import java.util.Comparator;

/**
 * 
 * @author gxy
 *
 */
public class PinyinComparator implements Comparator<PhoneInfo> {

	public int compare(PhoneInfo o1, PhoneInfo o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
