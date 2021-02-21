package kr.co.crevill.staff;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class InstructorVo {
	private String nsId;
	private String nationality;
	private String name;
	private String fullName;
	private String telNo;
	private String address;
	private String startDate;
	private String officeId;
	private String pictureIdx;
	private String criminalRecordsIdx;
	private String resumeIdx;
	private String regId;
	private String regDate;
	private String updId;
	private String updDate;
}