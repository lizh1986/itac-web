package com.lenovo.itac.export;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.lenovo.itac.model.GGYRModel;

public class GGYRExporter extends Exporter {

	public GGYRExporter(List<GGYRModel> models) {
		super(models);
	}
	
	@Override
	protected String getSheetName() {
		return "GGYR";
	}

	@Override
	protected void setColumnWidths() {
		if (columnWidths == null) {
			columnWidths = new int[4];
			columnWidths[0] = 20 * 256;
			columnWidths[1] = 15 * 256;
			columnWidths[2] = 25 * 256;
			columnWidths[3] = 25 * 256;
		}
	}

	@Override
	protected void setHeader(HSSFSheet sheet) {
		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("MO");
		header.getCell(0).setCellStyle(headerStyle);
		header.createCell(1).setCellValue("GGYR");
		header.getCell(1).setCellStyle(headerStyle);
		header.createCell(2).setCellValue("PSSD");
		header.getCell(2).setCellStyle(headerStyle);
		header.createCell(3).setCellValue("RPSSD");
		header.getCell(3).setCellStyle(headerStyle);
	}

	@Override
	protected void setContent(HSSFSheet sheet) {
		for (int i = 0; i < records.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			GGYRModel model = (GGYRModel)records.get(i);
			row.createCell(0).setCellValue(model.getMo());
			row.getCell(0).setCellStyle(style);
			row.createCell(1).setCellValue(model.getGgyr());
			row.getCell(1).setCellStyle(style);
			row.createCell(2).setCellValue(model.getPssd());
			row.getCell(2).setCellStyle(style);
			row.createCell(3).setCellValue(model.getRpssd());
			row.getCell(3).setCellStyle(style);
		}		
	}

	@Override
	public String getExportFileName() {
		return "GGYR.xls";
	}
}
