package com.tcc.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class VerificationUtil {
	static class Options{
		private String range = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		private int length = 4;
		private int width = 80;
		private int height = 30;
		private Color backColor;
		private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 25);
		private Color fontColor;
		public Options(){
			Random r = new Random();
			backColor = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			font = new Font(Font.SANS_SERIF, Font.BOLD, 25);
			fontColor = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
		}
		public String getRange() {
			return range;
		}
		public Options setRange(String range) {
			this.range = range;
			return this;
		}
		public int getLength() {
			return length;
		}
		public Options setLength(int length) {
			this.length = length;
			return this;
		}
		public int getWidth() {
			return width;
		}
		public Options setWidth(int width) {
			this.width = width;
			return this;
		}
		public int getHeight() {
			return height;
		}
		public Options setHeight(int height) {
			this.height = height;
			return this;
		}
		public Color getBackColor() {
			return backColor;
		}
		public Options setBackColor(Color backColor) {
			this.backColor = backColor;
			return this;
		}
		public Font getFont() {
			return font;
		}
		public Options setFont(Font font) {
			this.font = font;
			return this;
		}
		public Color getFontColor() {
			return fontColor;
		}
		public Options setFontColor(Color fontColor) {
			this.fontColor = fontColor;
			return this;
		}
		
	}
	public static class Result{
		private String code;
		private BufferedImage image;
		public Result(String code, BufferedImage image){
			this.code = code;
			this.image = image;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public BufferedImage getImage() {
			return image;
		}
		public void setImage(BufferedImage image) {
			this.image = image;
		}
		
	}
	/**
	 * 已默认参数生成验证码图片
	 * @return
	 */
	public static Result create() {
		return create(new Options());
	}
	/**
	 * 已自定义参数生成验证码图片
	 * @param opt
	 * @return
	 */
	public static Result create(Options opt) {
		BufferedImage image = new BufferedImage(opt.width, opt.height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(opt.backColor);
		g.fillRect(0, 0, opt.width, opt.height);
		String code = StrUtils.random(opt.range, opt.length);
		g.setFont(opt.font);
		g.setColor(opt.fontColor);
		g.drawString(code, 5, 25);
		Result result = new Result(code, image);
		return result;
	}
}
