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
	private String classAvaCnt;
	private String tutoringAvaCnt;
	private String classCnt;
	private String tutoringCnt;
	private String storeNameShort;
}