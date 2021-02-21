package kr.co.crevill.common;

import java.sql.Blob;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("common")
public class CommonController {

	@GetMapping("getByteImage")
	public String getByteImage(Blob blob) throws Exception{
		int blobLength = (int) blob.length();  
		byte[] blobAsBytes = blob.getBytes(1, blobLength);
		return Base64.encodeBase64String(blobAsBytes);
	}
}
