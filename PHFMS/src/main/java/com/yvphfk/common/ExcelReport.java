/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.common;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExcelReport
{

    public static Workbook generateReport (List objects, Report report)
    {
        Workbook workbook = buildReport(report);
        populateReport(workbook, objects, report);
        return workbook;
    }

    private static void populateReport (Workbook workbook,
                                        List objects,
                                        Report report)
    {
        DataFormat df = workbook.createDataFormat();
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(df.getFormat("d-MMM-yy"));

        CellStyle bodyCellStyle = workbook.createCellStyle();
        bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyCellStyle.setWrapText(true);

        List<Report.ReportSheet> sheetList = report.getSheets();

        int[] sheetCountList = new int[report.getSheets().size()];
        for (int m = 0; m < sheetCountList.length; m++) {
            sheetCountList[m] = 3;
        }
        // Create body

        String classType = report.getClassType();
        Class reportBaseClass = Util.loadClass(classType);
        if (reportBaseClass == null) {
            return;
        }
        for (int i = 0; i < objects.size(); i++) {
            Object form = objects.get(i);

            if (!form.getClass().equals(reportBaseClass)) {
                return;
            }

            for (int k = 0; k < sheetList.size(); k++) {
                int sheetCounter = sheetCountList[k];
                Report.ReportSheet sheet = sheetList.get(k);
                List<String> fieldPaths = sheet.getFieldPaths();
                if (!Util.nullOrEmptyOrBlank(sheet.getBaseFieldPath())) {
                    Object sheetForm = Util.getDottedFieldValue(sheet.getBaseFieldPath(), form);
                    if (sheetForm instanceof Collection) {
                        Collection collection = (Collection) sheetForm;
                        Iterator itr = collection.iterator();
                        while (itr.hasNext()) {
                            Object sheetObj = itr.next();
                            populateSheet(workbook, dateCellStyle, bodyCellStyle, sheetCounter, sheetObj, sheet, sheet.getFieldPaths());
                            sheetCounter++;
                        }
                    }
                    else {
                        String sheetClassType = sheet.getClassType();
                        if (!Util.nullOrEmptyOrBlank(sheetClassType) &&
                                !form.getClass().getName().equals(sheetClassType)) {
                            return;
                        }
                        populateSheet(workbook, dateCellStyle, bodyCellStyle, sheetCounter, sheetForm, sheet, sheet.getFieldPaths());
                        sheetCounter++;
                    }
                }
                else {
                    populateSheet(workbook, dateCellStyle, bodyCellStyle, sheetCounter, form, sheet, fieldPaths);
                    sheetCounter++;

                }
                sheetCountList[k] = sheetCounter;
            }

        }
    }

    private static void populateSheet (Workbook workbook,
                                       CellStyle dateCellStyle,
                                       CellStyle bodyCellStyle,
                                       int counter,
                                       Object form,
                                       Report.ReportSheet sheet,
                                       List<String> fieldPaths)
    {
        Sheet workingSheet = workbook.getSheet(sheet.getSheetName());

        if (workingSheet == null) {
            return;
        }

        Row regRow = workingSheet.createRow((short) counter);
        int colIndex = 0;
        for (String dottedFieldPath : fieldPaths) {
            Cell cell = regRow.createCell(colIndex);
            Object obj = Util.getDottedFieldValue(dottedFieldPath, form);
            if (obj instanceof Boolean) {
                cell.setCellValue(String.valueOf(obj));
                cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
                cell.setCellStyle(bodyCellStyle);
            }
            else if (obj instanceof Long || obj instanceof Integer) {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(Double.valueOf(String.valueOf(obj)));
                cell.setCellStyle(bodyCellStyle);
            }
            else if (obj instanceof Date) {
                cell.setCellValue((Date) obj);
                cell.setCellStyle(dateCellStyle);
            }
            else {
                if (obj != null) {
                    cell.setCellValue(String.valueOf(obj));
                    cell.setCellStyle(bodyCellStyle);
                }
                else {
                    cell.setCellValue("");
                    cell.setCellStyle(bodyCellStyle);
                }
            }
            colIndex++;
        }
    }

    private static HSSFWorkbook buildReport (Report report)
    {
        HSSFWorkbook workbook = new HSSFWorkbook();

        List<Report.ReportSheet> sheetList = report.getSheets();

        for (Report.ReportSheet sheet : sheetList) {
            HSSFSheet sheet1 = workbook.createSheet(sheet.getSheetName());
            buildReportSheet(sheet1, 0, 0, sheet.getFieldPaths(), sheet.getSheetName());
        }

        return workbook;
    }

    private static void buildReportSheet (HSSFSheet worksheet,
                                          int startRowIndex,
                                          int startColIndex,
                                          List<String> fieldNames,
                                          String sheetName)
    {
        for (int i = 0; i < fieldNames.size(); i++) {
            worksheet.setColumnWidth(i, 5000);
        }

        // Build the title and date headers
        buildTitle(worksheet, startRowIndex, startColIndex, fieldNames, sheetName);
        // Build the column headers
        buildHeaders(worksheet, startRowIndex, startColIndex, fieldNames, sheetName);
    }


    private static void buildTitle (HSSFSheet worksheet,
                                    int startRowIndex,
                                    int startColIndex,
                                    List<String> fieldPaths,
                                    String sheetName)
    {
        // Create font style for the report title
        Font fontTitle = worksheet.getWorkbook().createFont();
        fontTitle.setBoldweight(Font.BOLDWEIGHT_BOLD);
        fontTitle.setFontHeight((short) 280);

        // Create cell style for the report title
        HSSFCellStyle cellStyleTitle = worksheet.getWorkbook().createCellStyle();
        cellStyleTitle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleTitle.setWrapText(true);
        cellStyleTitle.setFont(fontTitle);

        // Create report title
        HSSFRow rowTitle = worksheet.createRow((short) startRowIndex);
        rowTitle.setHeight((short) 500);
        HSSFCell cellTitle = rowTitle.createCell(startColIndex);
        cellTitle.setCellValue(sheetName);
        cellTitle.setCellStyle(cellStyleTitle);

        // Create merged region for the report title
        worksheet.addMergedRegion(new CellRangeAddress(0, 0, 0, fieldPaths.size()));

        // Create date header
        HSSFRow dateTitle = worksheet.createRow((short) startRowIndex + 1);
        HSSFCell cellDate = dateTitle.createCell(startColIndex);
        cellDate.setCellValue("This report was generated at " + new Date());
    }

    private static void buildHeaders (HSSFSheet worksheet,
                                      int startRowIndex,
                                      int startColIndex,
                                      List<String> fieldPaths,
                                      String sheetName)
    {
        // Create font style for the headers
        Font font = worksheet.getWorkbook().createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // Create cell style for the headers
        HSSFCellStyle headerCellStyle = worksheet.getWorkbook().createCellStyle();
        headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
        headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headerCellStyle.setWrapText(true);
        headerCellStyle.setFont(font);
        headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

        // Create the column headers
        Row rowHeader = worksheet.createRow((short) startRowIndex + 2);
        rowHeader.setHeight((short) 500);

        for (int i = 0; i < fieldPaths.size(); i++) {
            Cell cell1 = rowHeader.createCell(startColIndex + i);
            cell1.setCellValue(Util.getCapitalizedFieldName(fieldPaths.get(i)));
            cell1.setCellStyle(headerCellStyle);
        }
    }
}
