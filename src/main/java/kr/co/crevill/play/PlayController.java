package kr.co.crevill.play;

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

import kr.co.crevill.common.CommonService;

@Controller
@RequestMapping("play")
public class PlayController {
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private PlayService playService;
	
	@GetMapping("list.view")
	public ModelAndView list(HttpServletRequest request, @ModelAttribute PlayDto playDto) {
		ModelAndView mav = new ModelAndView("play/list");
		mav.addObject("count", playService.selectPlayCount(playDto));
		mav.addObject("list", playService.selectPlayList(playDto));
		return mav;
	}
	
	@GetMapping("master.view")
	public ModelAndView master(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("play/master");
		return mav;
	}
	
	@PostMapping("master.proc")
	@ResponseBody
	public JSONObject masterProc(HttpServletRequest request, @ModelAttribute PlayDto playDto) {
		JSONObject result = new JSONObject();
		result = playService.insertPlay(playDto);
		return result;
	}
}