package com.lenovo.itac.export;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.lenovo.itac.entity.KitOn3FEntity;

public class KitOn3FExporter extends Exporter {

	public KitOn3FExporter(List<?> records) {
		super(records);
	}

	@Override
	protected void setContent(HSSFSheet sheet) {
		// TODO Auto-generated method stub
		for (int i = 0; i < records.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			KitOn3FEntity model = (KitOn3FEntity)records.get(i);
			row.createCell(0).setCellValue(model.getSerialNumber());
			row.getCell(0).setCellStyle(style);
			row.createCell(1).setCellValue(model.getContainerNumber());
			row.getCell(1).setCellStyle(style);
			row.createCell(2).setCellValue(model.getPartNumber());
			row.getCell(2).setCellStyle(style);
		}
	}

	@Override
	protected void setHeader(HSSFSheet sheet) {
		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("Serial Number");
		header.getCell(0).setCellStyle(headerStyle);
		header.createCell(1).setCellValue("Container Number");
		header.getCell(1).setCellStyle(headerStyle);
		header.createCell(2).setCellValue("Part Number");
		header.getCell(2).setCellStyle(headerStyle);
	}

	@Override
	protected String getSheetName() {
		return "Kit Info On 3F";
	}

	@Override
	protected void setColumnWidths() {
		if (columnWidths == null) {
			columnWidths = new int[3];
			columnWidths[0] = 25 * 256;
			columnWidths[1] = 35 * 256;
			columnWidths[2] = 20 * 256;
		}
	}

	@Override
	public String getExportFileName() {
		return "Kit Info On 3F.xls";
	}

}
