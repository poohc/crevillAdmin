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
	private String operationType;
	private MultipartFile thumbnail;
	private MultipartFile picture;
	private String thumbnailIdx;
	private String pictureIdx;
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
	public PlayDto() {
		this.currentPageNo = 1;
		this.recordsPerPage = 10;
		this.pageSize = 10;
	}	
	
	public int getStartPage() {
		return (currentPageNo - 1) * recordsPerPage;
	}
}