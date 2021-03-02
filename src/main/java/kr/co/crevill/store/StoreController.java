package kr.co.crevill.store;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.crevill.common.CommonCodeDto;
import kr.co.crevill.common.CommonService;
import kr.co.crevill.play.PlayDto;
import kr.co.crevill.play.PlayService;

@Controller
@RequestMapping("store")
public class StoreController {
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private PlayService playService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("list.view")
	public ModelAndView list(HttpServletRequest request, StoreDto storeDto) {
		ModelAndView mav = new ModelAndView("store/list");
		mav.addObject("list", storeService.selectStoreList(storeDto));
		return mav;
	}
	
	@GetMapping("regist.view")
	public ModelAndView regist(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("store/regist");
		CommonCodeDto commonCodeDto = new CommonCodeDto();
		commonCodeDto.setCodeType("STORE_TYPE");
		mav.addObject("storeType", commonService.selectCommonCode(commonCodeDto));
		PlayDto playDto = new PlayDto();
		mav.addObject("playList", playService.selectPlayList(playDto));
		return mav;
	}
	
	@PostMapping("regist.proc")
	@ResponseBody
	public JSONObject registProc(HttpServletRequest request, @ModelAttribute StoreDto storeDto) {
		JSONObject result = new JSONObject();
		result = storeService.insertStore(storeDto);
		return result;
	}
}