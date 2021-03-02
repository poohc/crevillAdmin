package kr.co.crevill.store;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class StoreVo {
	private String storeId;
	private String playId;
	private String playName;
	private String storeName;
	private String storeNameReg;
	private String storeType;
	private String storePlay;
	private String storeStatus;
	private String telNo;
	private String sortRank;
	private String registrationNumber;
	private String ceo;
	private String address;
	private String openDay;
	private String closeDay;
	private String registrationCertificateIdx;
	private String playgroundCertificateIdx;
	private String insuranceCertificateIdx;
	private String etcFile1Idx;
	private String etcFile2Idx;
	private String etcFile3Idx;
	private String etcFile4Idx;
	private String thumbnailImageIdx;
	private String image1Idx;
	private String image2Idx;
	private String image3Idx;
	private String image4Idx;
	private String image5Idx;
	private String image6Idx;
	private String regId;
	private String regDate;
	private String updId;
	private String updDate;
	
	/* 페이징 관련 */
	private int currentPageNo;
	private int recordsPerPage;
	private int pageSize;
	private String searchKeyword;
	private String searchType;
	
	//페이징 초기 설정
	public StoreVo() {
		this.currentPageNo = 1;
		this.recordsPerPage = 10;
		this.pageSize = 10;
	}	
	
	public int getStartPage() {
		return (currentPageNo - 1) * recordsPerPage;
	}
}