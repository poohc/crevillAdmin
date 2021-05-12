package kr.co.crevill.storeProgram;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class StoreProgramVo {
	private String programId;
	private String chainClass;
	private String programName;
	private String programContents;
	private String thumbnailId;
	private String pictureId;
	private String teachingPlanImgId;
	private String regId;
	private String regDate;
	private String updId;
	private String updDate;
}