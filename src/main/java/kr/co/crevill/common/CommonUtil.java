package kr.co.crevill.common;

import java.sql.Blob;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class CommonUtil {

	private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	
	/**
	 * 파일처리 메서드
	 * @methodName : setBlobByMultiPartFile
	 * @author : Juyoung Park
	 * @date : 2021.02.16
	 * @param file
	 * @return
	 */
	public static FileDto setBlobByMultiPartFile(MultipartFile file) {
		FileDto fileDto = new FileDto();
		byte[] bytes;
		try {
			bytes = file.getBytes();
			String fileName = file.getOriginalFilename();
			fileDto.setData(bytes);
			fileDto.setFileName(fileName);
			fileDto.setFileSize(bytes.length);
		} catch (Exception e) {
			logger.error("setBlobByMultiPartFile 메서드 오류 : " + e);
		}
		return fileDto;
	}
	
	public static String getByteImage(Blob blob) throws Exception{
		int blobLength = (int) blob.length();  
		byte[] blobAsBytes = blob.getBytes(1, blobLength);
		return Base64.encodeBase64String(blobAsBytes);
	}
}