package kr.co.crevill.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MemberDto {
	private String name;
	private String cellPhone;
	private String email;
	private String address;
	private String parentName;
	private String parentCellPhone;
	private String childName;
	private String birthday;
	private String sex;
	private String learningGrade;
	private String qrCode;
	private String regDate;
	private String regId;
	private String updDate;
	private String updId;
	private String status;	
	/* 페이징 관련 */
	private int currentPageNo;
	private int recordsPerPage;
	private int pageSize;
	private String searchKeyword;
	private String searchType;
	
	//페이징 초기 설정
	public MemberDto() {
		this.currentPageNo = 1;
		this.recordsPerPage = 10;
		this.pageSize = 10;
	}	
	
	public int getStartPage() {
		return (currentPageNo - 1) * recordsPerPage;
	}
}