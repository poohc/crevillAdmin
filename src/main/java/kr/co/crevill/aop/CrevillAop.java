package kr.co.crevill.aop;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kr.co.crevill.common.MenuDto;

@Aspect
@Component
public class CrevillAop {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MenuDto menuDto;
	
	@Around("execution(* kr.co.crevill..*Controller.*(..))")
    public Object Around(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String requestUri = request.getRequestURI();
		String contextPath = requestUri.split("/")[1];
		String servletPath = requestUri.split("/")[2];
		
        try {
        	logger.info("==================== Logging 시작 ====================");
        	Map<String, String[]> requestMap = request.getParameterMap();
        	for(Entry<String, String[]> temp  : requestMap.entrySet()) {
        		logger.info("Request KEY : " + temp.getKey() + ", Value : " + ((String[])temp.getValue())[0]);
        	}
        	
        	logger.info("requestUri 확인 : " + requestUri);
        	logger.info("contextPath 확인 : " + contextPath);
        	logger.info("servletPath 확인 : " + servletPath);
        	
        	
        	String menu = "";
        	if(contextPath.indexOf("member") > -1) {
        		menuDto.setUpperMenu("고객관리");
        		if(servletPath.indexOf("list.view") > -1) {
        			menu = "고객리스트";
        		}
        		if(servletPath.indexOf("join.view") > -1) {
        			menu = "신규고객등록";
        		}
        	}
        	
        	if(contextPath.indexOf("staff") > -1) {
        		menuDto.setUpperMenu("직원관리");
        		if(servletPath.indexOf("list.view") > -1) {
        			menu = "직원리스트";
        		}
        		if(servletPath.indexOf("join.view") > -1) {
        			menu = "직원등록";
        		}
        	}
        	
        	menuDto.setMenuName(menu);
    		menuDto.setCurrentMenu(menu);
    		logger.info("menuDto : " + menuDto.toString());
        	request.setAttribute("menu", menuDto);
        	logger.info("==================== Logging 종료 ====================");
        	Object result = joinPoint.proceed();
            return result;
        }catch (Exception e) {
        	logger.error("Logging 오류 : " + e);
        	return null;
        }
    }	
}