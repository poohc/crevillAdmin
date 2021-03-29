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
	
	/* 페이징 관련 */
	private int currentPageNo;
	private int recordsPerPage;
	private int pageSize;
	private String searchKeyword;
	private String searchType;
	
	//페이징 초기 설정
	public ReservationVo() {
		this.currentPageNo = 1;
		this.recordsPerPage = 10;
		this.pageSize = 10;
	}	
	
	public int getStartPage() {
		return (currentPageNo - 1) * recordsPerPage;
	}
}