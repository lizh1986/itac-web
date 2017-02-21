package com.lenovo.itac.entity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

public class MenuXmlParser extends DefaultHandler2 {

	private static List<MenuEntity> menus;
	private List<MenuEntity> secondLevelMenus;

	private MenuEntity firstLevelMenu;
	private MenuEntity secondLevelMenu;
	
	private String tagName;
	private String menuFlag;
	
	private static MenuXmlParser instance = new MenuXmlParser();
	
	public static void parse() {
		SAXParser parser = null;
		InputStream stream = null;
		
		try {
			parser = SAXParserFactory.newInstance().newSAXParser();
			stream = MenuXmlParser.class.getClassLoader().getResourceAsStream("menu.xml");
			parser.parse(stream, instance);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		} finally {
			if (null != stream) {
				try {
					stream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static List<MenuEntity> getFirstMenus() {
		List<MenuEntity> firstMenus = new ArrayList<MenuEntity>();
		for (MenuEntity menu : getMenus()) {
			firstMenus.add(menu);
		}
		return firstMenus;
	}
	
	public static List<MenuEntity> getSecondMenusByParentId(String parentId) {
		List<MenuEntity> secondMenus = new ArrayList<MenuEntity>();
		if (null != parentId) {
			for (MenuEntity first : getMenus()) {
				if (parentId.equals(first.getId())) {
					for (MenuEntity second : first.getChildren()) {
						secondMenus.add(second);
					}
				}
			}
		}
		
		return secondMenus;
	}

	@Override
	public void startDocument() throws SAXException {
		menus = new ArrayList<MenuEntity>();
		secondLevelMenus = new ArrayList<MenuEntity>();
	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if ("first-level".equals(qName)) {
			firstLevelMenu = new MenuEntity();
			menuFlag = "first-level";
		}
		if ("second-level".equals(qName)) {
			secondLevelMenu = new MenuEntity();
			menuFlag = "second-level";
		}
		
		this.tagName = qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (secondLevelMenu != null && "second-level".equals(qName)) {
			this.secondLevelMenus.add(secondLevelMenu);
			secondLevelMenu = null;
			menuFlag = "first-level";
		}
		
		if (firstLevelMenu != null && "first-level".equals(qName)) {
			firstLevelMenu.setChildren(secondLevelMenus);
			MenuXmlParser.menus.add(firstLevelMenu);
			firstLevelMenu = null;
			secondLevelMenus = new ArrayList<MenuEntity>();
			menuFlag = null;
		}
		
		this.tagName = null;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (null != this.tagName) {
			String value = new String(ch, start, length);
			if ("first-level".equals(this.menuFlag)) {
				if ("id".equals(this.tagName)) {
					this.firstLevelMenu.setId(value);
				} else if ("text".equals(this.tagName)) {
					this.firstLevelMenu.setText(value);
				} else if ("iconCls".equals(this.tagName)) {
					this.firstLevelMenu.setIconCls(value);
				} 
			} else if ("second-level".equals(this.menuFlag)) {
				if ("id".equals(this.tagName)) {
					this.secondLevelMenu.setId(value);
				} else if ("text".equals(this.tagName)) {
					this.secondLevelMenu.setText(value);
				} else if ("url".equals(this.tagName)) {
					this.secondLevelMenu.setUrl(value);
				} else if ("iconCls".equals(this.tagName)) {
					this.secondLevelMenu.setIconCls(value);
				}
			}
		}
	}

	public static List<MenuEntity> getMenus() {
		if (null == menus) {
			parse();
		}
		return menus;
	}

	public List<MenuEntity> getSecondLevelMenus() {
		return secondLevelMenus;
	}

	public void setSecondLevelMenus(List<MenuEntity> secondLevelMenus) {
		this.secondLevelMenus = secondLevelMenus;
	}

	public MenuEntity getFirstLevelMenu() {
		return firstLevelMenu;
	}

	public void setFirstLevelMenu(MenuEntity firstLevelMenu) {
		this.firstLevelMenu = firstLevelMenu;
	}

	public MenuEntity getSecondLevelMenu() {
		return secondLevelMenu;
	}

	public void setSecondLevelMenu(MenuEntity secondLevelMenu) {
		this.secondLevelMenu = secondLevelMenu;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
}
