package kr.co.crevill.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class FileDto {
	private String imageIdx;
	private String fileIdx;
	private byte[] data;
	private String description;
	private String fileName;
	private long fileSize;
	private String imageName;
	private long imageSize;
}