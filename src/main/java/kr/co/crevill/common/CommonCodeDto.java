package kr.co.crevill.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CommonCodeDto {
	private String codeType;
	private String codeKey;
	private String codeValue;
}