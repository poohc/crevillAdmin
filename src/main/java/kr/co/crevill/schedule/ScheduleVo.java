package kr.co.crevill.schedule;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ScheduleVo {
	private String scheduleId;
	private String operationType;
	private String playId;
	private String playName;
	private String numberOfPeople;
	private String tutoringNumber;
	private String scheduleStart;
	private String scheduleEnd;
	private String scheduleDate;
	private String scheduleTime;
	private String regId;
	private String regDate;
	private String updId;
	private String updDate;
	private String subTopic;
	
	/* 페이징 관련 */
	private int currentPageNo;
	private int recordsPerPage;
	private int pageSize;
	private String searchKeyword;
	private String searchType;
	
	//페이징 초기 설정
	public ScheduleVo() {
		this.currentPageNo = 1;
		this.recordsPerPage = 10;
		this.pageSize = 10;
	}	
	
	public int getStartPage() {
		return (currentPageNo - 1) * recordsPerPage;
	}
}