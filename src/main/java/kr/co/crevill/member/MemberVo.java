package kr.co.crevill.member;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MemberVo {
	private String name;
	private String cellPhone;
	private String email;
	private String address;
	private String roadAddress;
	private String detailAddress;
	private String parentName;
	private String parentBirthday;
	private String parentSex;
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
	private String voucherCount;
	private String storeName;
	private String storeId;
	private String voucherStoreName;
	private String memberType;
	private String ticketName;
	private String memberParentCount;
	private String memberChildrenCount;
	private String nvMemberCount;
	private String voucherMemberCount;
	private List<String> checkedlearningGradeList;
}