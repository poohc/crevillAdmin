package kr.co.crevill.storeProgram;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("storeProgram")
public class StoreProgramController {
	
	@Autowired
	private StoreProgramService storeProgramService;
	
	@GetMapping("list.view")
	public ModelAndView list(HttpServletRequest request, @ModelAttribute StoreProgramDto storeProgramDto) {
		ModelAndView mav = new ModelAndView("storeProgram/list");
		mav.addObject("list", storeProgramService.selectPromotionList(storeProgramDto));
		return mav;
	}
	
	@GetMapping("regist.view")
	public ModelAndView master(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("storeProgram/regist");
		return mav;
	}
	
	@PostMapping("regist.proc")
	@ResponseBody
	public JSONObject registProc(HttpServletRequest request, @ModelAttribute StoreProgramDto storeProgramDto) {
		JSONObject result = new JSONObject();
		result = storeProgramService.insertStoreProgram(storeProgramDto, request);
		return result;
	}
	
	@GetMapping("update.view")
	public ModelAndView master(HttpServletRequest request, StoreProgramDto promotionDto) {
		ModelAndView mav = new ModelAndView("storeProgram/update");
		return mav;
	}
	
	@PostMapping("update.proc")
	@ResponseBody
	public JSONObject updateProc(HttpServletRequest request, @ModelAttribute StoreProgramDto storeProgramDto) {
		JSONObject result = new JSONObject();
		result = storeProgramService.updateStoreProgram(storeProgramDto, request);
		return result;
	}
	
	@PostMapping("delete.proc")
	@ResponseBody
	public JSONObject deleteProc(HttpServletRequest request, @ModelAttribute StoreProgramDto storeProgramDto) {
		JSONObject result = new JSONObject();
		result = storeProgramService.deleteStoreProgram(storeProgramDto);
		return result;
	}
}