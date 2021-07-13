package kr.co.crevill.settlement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class SettlementDto {
	private String storeId;
	private String searchDate;
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
	private String regId;
	private String regDate;
	private String updId;
	private String updDate;
}