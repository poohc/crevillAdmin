package kr.co.crevill.common;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class MenuDto {
	private String menuName;
	private String homeLink;
	private String upperMenu;
	private String currentMenu;
	
	public MenuDto() {
		this.homeLink = "/admin/index.view";
	}
	
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getHomeLink() {
		return homeLink;
	}
	public void setHomeLink(String homeLink) {
		this.homeLink = homeLink;
	}
	public String getUpperMenu() {
		return upperMenu;
	}
	public void setUpperMenu(String upperMenu) {
		this.upperMenu = upperMenu;
	}
	public String getCurrentMenu() {
		return currentMenu;
	}
	public void setCurrentMenu(String currentMenu) {
		this.currentMenu = currentMenu;
	}
	
	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> tempMap = mapper.convertValue(this, Map.class);
		return tempMap.toString();
	}
	
}