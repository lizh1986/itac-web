package com.lenovo.itac.export;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Excel导出的基类
 * @author lizh18
 *
 */
public abstract class Exporter {

	/** 待写入的记录 */
	protected List<?> records;
	
	/** 表头样式 */
	protected HSSFCellStyle headerStyle;
	
	/** 内容样式 */
	protected HSSFCellStyle style;
	
	/** 列的宽度 */
	protected int[] columnWidths;
	
	public Exporter(List<?> records) {
		this.records = records;
	}
	
	public HSSFWorkbook export() {
		HSSFWorkbook wb = new HSSFWorkbook();
		
		HSSFSheet sheet = wb.createSheet(getSheetName());
		
		setColumnWidths(sheet);
		
		initStyle(wb);
		
		setHeader(sheet);
		
		setContent(sheet);
		
		return wb;
	}
	
	/** 设置表的内容信息 */
	protected abstract void setContent(HSSFSheet sheet);
	
	/** 设置表头信息 */
	protected abstract void setHeader(HSSFSheet sheet);
	
	private void initStyle(HSSFWorkbook wb) {
		if (wb != null) {
			if (headerStyle == null) {
				headerStyle = wb.createCellStyle();
				headerStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
				headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
				headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				
				HSSFFont headerFont = wb.createFont();
				headerFont.setBold(true);
				headerStyle.setFont(headerFont);
			}
			
			if (style ==  null) {
				style = wb.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			}
		}
		
	}
	
	private void setColumnWidths(HSSFSheet sheet) {
		if (sheet != null) {
			setColumnWidths();
			for (int i = 0; i < columnWidths.length; i++) {
				sheet.setColumnWidth(i, columnWidths[i]);
			}
		}
	}
	
	/** 获取sheet name，不同的导出器，sheet name不同 */
	protected abstract String getSheetName();
	
	/** 设置列的宽度，不同的导出器，列的数量和宽度各不同 */
	protected abstract void setColumnWidths();
	
	public abstract String getExportFileName();
}
