package com.wei.apktools.config;

import java.awt.Color;
import java.awt.Font;
import java.util.Properties;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

public class ThemeProperty {

	private ColorUIResource primary1 = new ColorUIResource(Color.decode("#006282"));
	private ColorUIResource primary2 = new ColorUIResource(Color.decode("#006C8C"));
	private ColorUIResource primary3 = new ColorUIResource(Color.decode("#007696"));

	private ColorUIResource secondary1 = new ColorUIResource(Color.decode("#D5DDC6"));
	private ColorUIResource secondary2 = new ColorUIResource(Color.decode("#DFE7D0"));
	private ColorUIResource secondary3 = new ColorUIResource(Color.decode("#E9F1DA"));

	private ColorUIResource white = new ColorUIResource(Color.decode("#FFFFFF"));
	private ColorUIResource black = new ColorUIResource(Color.decode("#000000"));

	private FontUIResource font = new FontUIResource(Font.decode("Microsoft YaHei-PLAIN-12"));
	private FontUIResource boldFont = new FontUIResource(Font.decode("Microsoft YaHei-PLAIN-12").deriveFont(Font.BOLD));

	private int opacidadMenu = 164;
	private int opacidadFrame = 196;

	public ThemeProperty() {}
	
	public ThemeProperty(ThemeProperty themeProperty) {
		
		this.primary1 = themeProperty.getPrimary1();
		this.primary2 = themeProperty.getPrimary2();
		this.primary3 = themeProperty.getPrimary3();
		
		this.secondary1 = themeProperty.getSecondary1();
		this.secondary2 = themeProperty.getSecondary2();
		this.secondary3 = themeProperty.getSecondary3();
		
		this.black = themeProperty.getBlack();
		this.white = themeProperty.getWhite();
		
		this.font = themeProperty.getFont();
		this.boldFont = themeProperty.getBoldFont();
		
		this.opacidadMenu = themeProperty.getOpacidadMenu();
		this.opacidadFrame = themeProperty.getOpacidadFrame();
	}

	public ColorUIResource getPrimary1() {
		return primary1;
	}

	public void setPrimary1(ColorUIResource primary1) {
		this.primary1 = primary1;
	}

	public ColorUIResource getPrimary2() {
		return primary2;
	}

	public void setPrimary2(ColorUIResource primary2) {
		this.primary2 = primary2;
	}

	public ColorUIResource getPrimary3() {
		return primary3;
	}

	public void setPrimary3(ColorUIResource primary3) {
		this.primary3 = primary3;
	}

	public ColorUIResource getSecondary1() {
		return secondary1;
	}

	public void setSecondary1(ColorUIResource secondary1) {
		this.secondary1 = secondary1;
	}

	public ColorUIResource getSecondary2() {
		return secondary2;
	}

	public void setSecondary2(ColorUIResource secondary2) {
		this.secondary2 = secondary2;
	}

	public ColorUIResource getSecondary3() {
		return secondary3;
	}

	public void setSecondary3(ColorUIResource secondary3) {
		this.secondary3 = secondary3;
	}

	public ColorUIResource getBlack() {
		return black;
	}

	public void setBlack(ColorUIResource black) {
		this.black = black;
	}

	public ColorUIResource getWhite() {
		return white;
	}

	public void setWhite(ColorUIResource white) {
		this.white = white;
	}

	public FontUIResource getFont() {
		return font;
	}

	public void setFont(FontUIResource font) {
		this.font = font;
	}

	public FontUIResource getBoldFont() {
		return boldFont;
	}

	public void setBoldFont(FontUIResource boldFont) {
		this.boldFont = boldFont;
	}

	public int getOpacidadMenu() {
		return opacidadMenu;
	}

	public void setOpacidadMenu(int opacidadMenu) {
		this.opacidadMenu = opacidadMenu;
	}

	public int getOpacidadFrame() {
		return opacidadFrame;
	}

	public void setOpacidadFrame(int opacidadFrame) {
		this.opacidadFrame = opacidadFrame;
	}
	
	public void setFont(Font font) {
		this.font = new FontUIResource(font);
		this.boldFont = new FontUIResource(font.deriveFont(Font.BOLD));
	}
	
	public void setPrimary(Color selection) {
	    int r = selection.getRed();
	    int g = selection.getGreen();
	    int b = selection.getBlue();
		    
	    primary1 = new ColorUIResource(new Color((r>20 ? r-20 : 0), (g>20 ? g-20 : 0), (b>20 ? b-20 : 0)));
	    primary2 = new ColorUIResource(new Color((r>10 ? r-10 : 0), (g>10 ? g-10 : 0), (b>10 ? b-10 : 0)));
	    primary3 = new ColorUIResource(selection);
	}
		  
	public void setSecondary( Color background) {
	    int r = background.getRed();
	    int g = background.getGreen();
	    int b = background.getBlue();
	    
	    secondary1 = new ColorUIResource(new Color((r>20 ? r-20 : 0), (g>20 ? g-20 : 0), (b>20 ? b-20 : 0)));
	    secondary2 = new ColorUIResource(new Color((r>10 ? r-10 : 0), (g>10 ? g-10 : 0), (b>10 ? b-10 : 0)));
	    secondary3 = new ColorUIResource(background);
	}
	
	public void setProperties(Properties properties) {
		
		setPrimary1(new ColorUIResource(Color.decode(properties.getProperty("nimrodlf.p1"))));
		setPrimary2(new ColorUIResource(Color.decode(properties.getProperty("nimrodlf.p2"))));
		setPrimary3(new ColorUIResource(Color.decode(properties.getProperty("nimrodlf.p3"))));
		
		setSecondary1(new ColorUIResource(Color.decode(properties.getProperty("nimrodlf.s1"))));
		setSecondary2(new ColorUIResource(Color.decode(properties.getProperty("nimrodlf.s2"))));
		setSecondary3(new ColorUIResource(Color.decode(properties.getProperty("nimrodlf.s3"))));
		
		setWhite(new ColorUIResource(Color.decode(properties.getProperty("nimrodlf.w"))));
		setBlack(new ColorUIResource(Color.decode(properties.getProperty("nimrodlf.b"))));
		
		setOpacidadMenu(Integer.parseInt(properties.getProperty("nimrodlf.menuOpacity")));
		setOpacidadFrame(Integer.parseInt(properties.getProperty("nimrodlf.frameOpacity")));
		
		if (properties.getProperty("nimrodlf.font") != null) {
			setFont(Font.decode(properties.getProperty("nimrodlf.font")));
		}
	}
	
	public Properties getProperties() {
		
		Properties properties = new Properties();
		
		properties.put("nimrodlf.p1", encode(primary1));
		properties.put("nimrodlf.p2", encode(primary2));
		properties.put("nimrodlf.p3", encode(primary3));
		
		properties.put("nimrodlf.s1", encode(secondary1));
		properties.put("nimrodlf.s2", encode(secondary2));
		properties.put("nimrodlf.s3", encode(secondary3));
		
		properties.put("nimrodlf.w", encode(white));
		properties.put("nimrodlf.b", encode(black));
		
		properties.put("nimrodlf.menuOpacity", Integer.toString(opacidadMenu));
		properties.put("nimrodlf.frameOpacity", Integer.toString(opacidadFrame));
		
		properties.put("nimrodlf.font", encode(font));
		
		return properties;
	}
	
	protected String encode(Font font) {
		
		StringBuilder result = new StringBuilder(font.getName() + "-");
		
		if (font.isPlain()) {
			result.append("PLAIN-");
		} else if (font.isBold() && font.isItalic()) {
			result.append("BOLDITALIC-");
		} else if (font.isBold()) {
			result.append("BOLD-");
		} else if (font.isItalic()) {
			result.append("ITALIC-");
		}
		
		result.append(font.getSize());
		
		return result.toString();
	}
	
	protected String encode(Color color) {
		
		String r = Integer.toHexString(color.getRed()).toUpperCase();
		String g = Integer.toHexString(color.getGreen()).toUpperCase();
		String b = Integer.toHexString(color.getBlue()).toUpperCase();
		
		return "#" + (r.length() == 1 ? "0" + r : r)
				   + (g.length() == 1 ? "0" + g : g)
				   + (b.length() == 1 ? "0" + b : b);
	}
}
