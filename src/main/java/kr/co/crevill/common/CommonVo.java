package kr.co.crevill.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CommonVo {
	private String voucherTimeLeftHour;
	private String reservationCount;
	private String memberCount;
	private String todayJoinMemberCount;
	private String todayVoucherSalePrice;
	private String yesterdayVoucherSalePrice;
	private String lastweekVoucherSalePrice;
	private String lastTwWeekVoucherSalePrice;
	private String todayArrow;
	private String weekArrow;
}