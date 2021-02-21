package kr.co.crevill.play;

import java.sql.Blob;

import kr.co.crevill.common.CommonUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class PlayVo {
	private String playId;
	private String name;
	private String description;
	private String playTime;
	private String operationType;
	private Blob thumbnail;
	private Blob picture;
	private String thumbnailIdx;
	private String pictureIdx;
	private String regId;
	private String regDate;
	private String updId;
	private String updDate;
	private String thumbImg;
	private String pictureImg;
	
	/* 페이징 관련 */
	private int currentPageNo;
	private int recordsPerPage;
	private int pageSize;
	private String searchKeyword;
	private String searchType;
	
	public void setThumbImg() throws Exception {
		this.thumbImg = CommonUtil.getByteImage(getThumbnail());
	}
	
	public void setPictureImg() throws Exception {
		this.pictureImg = CommonUtil.getByteImage(getPicture());
	}
	
	//페이징 초기 설정
	public PlayVo() {
		this.currentPageNo = 1;
		this.recordsPerPage = 10;
		this.pageSize = 10;
	}	
	
	public int getStartPage() {
		return (currentPageNo - 1) * recordsPerPage;
	}
}