package kr.co.crevill.settlement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class SettlementVo {
	private String storeId;
	private String storeName;
	private String salePrice;
	private String name;
	private String cellPhone;
    private String searchDate;
    private String visitMemberCount;
    private String noshowCount;
    private String cancelCount;
    private String withdrawCount;
    private String repurchaseCount;
    private String totalCount;
    private String voucherCount;  
    private String shortVoucherCount; 
    private String nonMemberCount;
    private String ticketName;
    private String totalPrice;
	private String fbRevenue;
	private String voucherRefundPrice;
	private String shortTicketCase1Count;
	private String shortTicketCase1Price;
	private String shortTicketCase2Count;
	private String shortTicketCase2Price;
	private String shortTicketCase1Result;
	private String shortTicketCase2Result;
	private String memo;
	private String finalPrice;
	private String voucherTotalPrice;
	private String totalVisitMemberCount;
    private String regDate;
    private String staffName;
}
