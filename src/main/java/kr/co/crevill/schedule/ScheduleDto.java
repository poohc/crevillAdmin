package kr.co.crevill.schedule;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ScheduleDto {
	private String scheduleId;
	private String operationType;
	private String playId;
	private String numberOfPeople;
	private String tutoringNumber;
	private String scheduleType;
	private String scheduleStart;
	private String scheduleEnd;
	private String regId;
	private String regDate;
	private String updId;
	private String updDate;
	private String storeId;
	private String subTopic;
	
	/* 페이징 관련 */
	private int currentPageNo;
	private int recordsPerPage;
	private int pageSize;
	private String searchKeyword;
	private String searchType;
	
	//페이징 초기 설정
	public ScheduleDto() {
		this.currentPageNo = 1;
		this.recordsPerPage = 10;
		this.pageSize = 10;
	}	
	
	public int getStartPage() {
		return (currentPageNo - 1) * recordsPerPage;
	}
}