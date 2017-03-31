package com.lenovo.itac.export;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.lenovo.itac.model.SnInfoModel;

public class SnsExporter extends Exporter {

	public SnsExporter(List<SnInfoModel> records) {
		super(records);
	}

	@Override
	protected void setContent(HSSFSheet sheet) {
		for (int i = 0; i < records.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			SnInfoModel model = (SnInfoModel)records.get(i);
			row.createCell(0).setCellValue(model.getMo());
			row.getCell(0).setCellStyle(style);
			row.createCell(1).setCellValue(model.getSn());
			row.getCell(1).setCellStyle(style);
			row.createCell(2).setCellValue(model.getWorkStep());
			row.getCell(2).setCellStyle(style);
			row.createCell(3).setCellValue(model.getLastWsTime());
			row.getCell(3).setCellStyle(style);
		}
	}

	@Override
	protected void setHeader(HSSFSheet sheet) {
		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("MO");
		header.getCell(0).setCellStyle(headerStyle);
		header.createCell(1).setCellValue("SN");
		header.getCell(1).setCellStyle(headerStyle);
		header.createCell(2).setCellValue("Work Step");
		header.getCell(2).setCellStyle(headerStyle);
		header.createCell(3).setCellValue("Last Work Step Time");
		header.getCell(3).setCellStyle(headerStyle);
	}

	@Override
	protected String getSheetName() {
		return "Pending SN";
	}

	@Override
	protected void setColumnWidths() {
		if (columnWidths == null) {
			columnWidths = new int[4];
			columnWidths[0] = 20 * 256;
			columnWidths[1] = 25 * 256;
			columnWidths[2] = 20 * 256;
			columnWidths[3] = 25 * 256;
		}
	}

	@Override
	public String getExportFileName() {
		return "Pending SN.xls";
	}

}
