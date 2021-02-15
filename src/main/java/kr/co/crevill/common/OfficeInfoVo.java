package kr.co.crevill.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class OfficeInfoVo {
	private String officeId;
	private String officeName;
	private String officeDescription;
	private String telNo;
	private String sortRank;
}