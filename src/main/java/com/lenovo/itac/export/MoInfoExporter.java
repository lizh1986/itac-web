package com.lenovo.itac.export;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.lenovo.itac.model.MoAndWSModel;
import com.lenovo.itac.model.MoInfoModel;

public class MoInfoExporter extends Exporter {

	public MoInfoExporter(List<MoInfoModel> models) {
		super(models);
	}
	
	@Override
	protected void setContent(HSSFSheet sheet) {
		int rowNumber = 1;
		for (int i = 0; i < records.size(); i++) {
			MoInfoModel model = (MoInfoModel)records.get(i);
			HSSFRow row = sheet.createRow(rowNumber++);
			row.createCell(0).setCellValue(model.getMo());
			row.getCell(0).setCellStyle(style);
			row.createCell(1).setCellValue("");
			row.getCell(1).setCellStyle(style);
			row.createCell(2).setCellValue(model.getQuantity());
			row.getCell(2).setCellStyle(style);
			row.createCell(3).setCellValue(model.getMtm());
			row.getCell(3).setCellStyle(style);
			row.createCell(4).setCellValue("");
			row.getCell(4).setCellStyle(style);
			row.createCell(5).setCellValue("");
			row.getCell(5).setCellStyle(style);
			row.createCell(6).setCellValue(model.getCreationTime());
			row.getCell(6).setCellStyle(style);
			row.createCell(7).setCellValue("");
			row.getCell(7).setCellStyle(style);
			row.createCell(8).setCellValue("");
			row.getCell(8).setCellStyle(style);
			
			List<MoAndWSModel> children = model.getChildren();
			for (int j = 0; j < children.size(); j++) {
				MoAndWSModel mw = children.get(j);
				HSSFRow childRow = sheet.createRow(rowNumber++);
				childRow.createCell(0).setCellValue("");
				childRow.getCell(0).setCellStyle(style);
				childRow.createCell(1).setCellValue(mw.getWs());
				childRow.getCell(1).setCellStyle(style);
				childRow.createCell(2).setCellValue("");
				childRow.getCell(2).setCellStyle(style);
				childRow.createCell(3).setCellValue("");
				childRow.getCell(3).setCellStyle(style);
				childRow.createCell(4).setCellValue(mw.getPassed());
				childRow.getCell(4).setCellStyle(style);
				childRow.createCell(5).setCellValue(mw.getPending());
				childRow.getCell(5).setCellStyle(style);
				childRow.createCell(6).setCellValue("");
				childRow.getCell(6).setCellStyle(style);
				childRow.createCell(7).setCellValue(mw.getFirstBooking());
				childRow.getCell(7).setCellStyle(style);
				childRow.createCell(8).setCellValue(mw.getLastBooking());
				childRow.getCell(8).setCellStyle(style);
			}
		}
	}

	@Override
	protected void setHeader(HSSFSheet sheet) {
		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("MO");
		header.getCell(0).setCellStyle(headerStyle);
		header.createCell(1).setCellValue("WS");
		header.getCell(1).setCellStyle(headerStyle);
		header.createCell(2).setCellValue("Quantity");
		header.getCell(2).setCellStyle(headerStyle);
		header.createCell(3).setCellValue("MTM");
		header.getCell(3).setCellStyle(headerStyle);
		header.createCell(4).setCellValue("PASS");
		header.getCell(4).setCellStyle(headerStyle);
		header.createCell(5).setCellValue("Pending & Repair");
		header.getCell(5).setCellStyle(headerStyle);
		header.createCell(6).setCellValue("Creation Time");
		header.getCell(6).setCellStyle(headerStyle);
		header.createCell(7).setCellValue("First Booking");
		header.getCell(7).setCellStyle(headerStyle);
		header.createCell(8).setCellValue("Last Booking");
		header.getCell(8).setCellStyle(headerStyle);
	}

	@Override
	protected String getSheetName() {
		return "Mo Info";
	}

	@Override
	protected void setColumnWidths() {
		if (columnWidths == null) {
			columnWidths = new int[9];
			columnWidths[0] = 20 * 256;
			columnWidths[1] = 15 * 256;
			columnWidths[2] = 10 * 256;
			columnWidths[3] = 15 * 256;
			columnWidths[4] = 15 * 256;
			columnWidths[5] = 25 * 256;
			columnWidths[6] = 25 * 256;
			columnWidths[7] = 25 * 256;
			columnWidths[8] = 25 * 256;
		}
	}

	@Override
	public String getExportFileName() {
		return "MO Info.xls";
	}
}
