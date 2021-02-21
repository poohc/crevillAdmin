package kr.co.crevill.common;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("common")
public class CommonController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CommonService commonService;
	
	@GetMapping("getImage")
	public void  getByteImage(@RequestParam String imageIdx, HttpServletResponse response) throws Exception{
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.setHeader("Cache-Control", "max-age=2628000");
        FileDto fileDto = new FileDto();
        fileDto.setImageIdx(imageIdx);
        FileVo fileVo = commonService.selectImageInfo(fileDto);
        byte[] blob = fileVo.getData();
        try (OutputStream out = response.getOutputStream()) {
            out.write(blob);
        }
		
	}
}
