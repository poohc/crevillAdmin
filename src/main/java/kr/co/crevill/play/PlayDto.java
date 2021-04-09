package kr.co.crevill.play;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class PlayDto {
	private String playId;
	private String name;
	private String description;
	private String playTime;
	private String voucherTime;
	private String operationType;
	private MultipartFile thumbnail;
	private MultipartFile picture;
	private String thumbnailIdx;
	private String pictureIdx;
	private String regId;
	private String regDate;
	private String updId;
	private String updDate;	
}