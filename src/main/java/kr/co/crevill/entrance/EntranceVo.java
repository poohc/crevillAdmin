package kr.co.crevill.entrance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class EntranceVo {
	private String reservationId;
	private String cellPhone;
	private String voucherNo;
	private String price;
	private String ticketName;
	private String scheduleId;
	private String scheduleTime;
    private String numberOfPeople;
    private String tutoringNumber; 
	private String status;
	private String reservationCnt;
	private String scheduleStart;
	private String memberQrCode;
	private String name;
	private String childName;
	private String childBirthday;
	private String childSex;
	private String playName;
	private String playTime;
	private String voucherTime;
	private String voucherAvailableYn;
	private String timeLeftMinute;
	private String useTime;
	private String entranceCount;
	private String reservationCount;
	private String tutoringReservationCount;
	private String remainCount;
	private String storeId;
	private String storeName;
	private String storeNameShort;
	private String staffName;
    private String regId;
	private String regDate;
	private String updId;
	private String updDate; 
	private String addText;
	private String subTopic;
	private String semId;
	private String entranceTime;
	private String scheduleType;
}