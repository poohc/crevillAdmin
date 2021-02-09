package kr.co.crevill.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CommonCodeVo {
	private String codeType;
	private String codeKey;
	private String codeValue;
	private String regId;
	private String regDate;
	private String updId;
	private String updDate;
}