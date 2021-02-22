package kr.co.crevill.voucher;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class VoucherVo {
	private String voucherNo;
	private String grade;
	private String ticketName;
	private String price;
	private String salePrice;
	private String useTime;
	private String endDate;
	private String saleCode;
	private String regId;
	private String regDate;
	private String updId;
	private String updDate;
	private String attribute;
	private String imageIdx;
	
	/* 페이징 관련 */
	private int currentPageNo;
	private int recordsPerPage;
	private int pageSize;
	private String searchKeyword;
	private String searchType;
	
	//페이징 초기 설정
	public VoucherVo() {
		this.currentPageNo = 1;
		this.recordsPerPage = 10;
		this.pageSize = 10;
	}	
	
	public int getStartPage() {
		return (currentPageNo - 1) * recordsPerPage;
	}
}
