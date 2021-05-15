package kr.co.crevill.branches;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class NoticeDto {
	private String noticeId;
	private String title;
	private String noticeType;
	private String noticeSendType;
	private String contents;
	private MultipartFile banner;
	private String bannerId;
	private String regId;
	private String regDate;
	private String updId;
	private String updDate;
	private String startDate;
	private String endDate;
}
