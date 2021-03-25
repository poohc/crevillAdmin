package kr.co.crevill.entrance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class EntranceVo {
	private String reservationId;
	private String cellPhone;
	private String voucherNo;
	private String scheduleId;
	private String scheduleTime; 
    private String numberOfPeople;
    private String tutoringNumber; 
	private String status;
	private String reservationCnt;
	private String scheduleStart;
	private String memberQrCode;
	private String childrenName;
	private String playName;
	private String playTime;
	private String voucherAvailableYn;
	private String timeLeftMinute;
	private String useTime;
	private String entranceCount;
    private String regId;
	private String regDate;
	private String updId;
	private String updDate;
}