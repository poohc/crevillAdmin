package kr.co.crevill.reservation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ReservationVo {
	private String reservationId;
	private String cellPhone;
	private String customerName;
	private String reservationSignUpDate;
	private String reservationDate;
	private String reservationTime;
	private String voucherType;
	private String statusName;
	private String voucherNo;
	private String scheduleId;
	private String scheduleTime;
	private String progress;
	private String numberOfPeople;
	private String tutoringNumber;
	private String reservationCnt;
	private String reservationPer;
	private String scheduleStart;
	private String status;
	private String regId;
	private String regDate;
	private String updId;
	private String updDate;
	private String reservationYn;
	private String voucherYn;
	private String playTime;
	private String voucherTime;
	private String playName;
	private String childName;
	private String tutoringYn;
	private String tutoringCnt;
	private String tutoringPer;
	private String voucherUseId;
	private String memberRegDate;
	private String address;
	private String childBirthday;
	private String storeName;
	private String storeNameShort;
	private String totalSchedulePeopleNumber;
	private String reservationCount;
}