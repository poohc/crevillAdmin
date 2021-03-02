package kr.co.crevill.login;

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
@RequestMapping("login")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@GetMapping("login.view")
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}
	
	@PostMapping("login.proc")
	@ResponseBody
	public JSONObject loginProc(HttpServletRequest request, @ModelAttribute LoginDto loginDto) {
		JSONObject result = new JSONObject();
		result = loginService.loginProcess(loginDto, request);
		return result;
	}
}