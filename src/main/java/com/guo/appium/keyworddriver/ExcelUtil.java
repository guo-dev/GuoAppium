package com.guo.appium.keyworddriver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//本类主要实现后缀为 xlsx 的 excel  文件操作  
public class ExcelUtil {

	private XSSFSheet ExcelWSheet;
	private XSSFWorkbook ExcelWBook;
	private XSSFCell Cell;
	private XSSFRow Row;

	// 设定要操作的 Excel 的文件路径
	// 在读写excel的时候，需要先设定要操作的 excel 文件路径
	public ExcelUtil(String Path) {

		FileInputStream ExcelFile;
		try {
			// 实例化 excel 文件的 FileInputStream 对象
			ExcelFile = new FileInputStream(Path);
			System.out.println(Path);
			// 实例化 excel 文件的 XSSFWorkbook 对象
			ExcelWBook = new XSSFWorkbook(ExcelFile);
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			System.out.println("Excel 路径设定失败xxxxxx");
			e.printStackTrace();
		}
	}

	// 读取 excel 文件指定单元格的函数，此函数只支持后缀为 xlsx 的 excel 文件
	public String getCellData(String SheetName, int RowNum, int ColNum) {
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		try {
			// 通过函数参数指定单元格的行号和列号，获取指定的单元格对象
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			// 如果单元格的内容为字符串类型，则使用 getStringCellValue 方法获取单元格的内容
			// 如果单元格的内容为数字类型，则使用 getNumericCellValue() 方法获取单元格的内容
			// String CellData = Cell.getCellType() == XSSFCell.CELL_TYPE_STRING
			// ? Cell.getStringCellValue() :
			// String.valueOf(Math.round(Cell.getNumericCellValue()));
			String CellData = "";
			if (Cell != null) {
				if (Cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
					CellData = Cell.getStringCellValue();
				} else if (Cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					DecimalFormat df = new DecimalFormat("0");
					CellData = df.format(Cell.getNumericCellValue());
				}else if(Cell.getCellType()==XSSFCell.CELL_TYPE_BLANK) {
					CellData="";
				}
			} else {
				System.out.println("第" + RowNum + "行" + "第" + ColNum + "Lie");
			}

			// 函数返回指定单元格的字符串内容
			return CellData;

		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
			// 读取遇到异常，则返回空字符串
			return "";
		}
	}

	// //获取 Excel 文件最后一行的行号
	// public int getLastRowNum() {
	// //函数返回 sheet 中最后一行的行号
	// return ExcelWSheet.getLastRowNum();
	// }
	// 获取指定 sheet 中的数据总行数
	public int getRowCount(String SheetName) {
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		int number = ExcelWSheet.getLastRowNum();
		return number;
	}

	// 在excel 的指定sheet 中，获取第一次包含指定测试用例序号文字的行号
	public int getFirstRowContainsTestCaseID(String sheetName, String testCaseName) {
		int i;
		try {

			ExcelWSheet = ExcelWBook.getSheet(sheetName);
			int rowCount = ExcelWSheet.getLastRowNum();
			for (i = 1; i < rowCount; i++) {
				// 使用循环的方法遍历测试用例序号列的所有行，判断是否包含某个测试用例序号关键字
				// System.out.println(getCellData(sheetName,i,colNum));
				if (getCellData(sheetName, i, 0).equalsIgnoreCase(testCaseName)) {
					// 如果包含，则退出 for 循环，并返回包含测试用例序号关键字的行号
					break;
				}
			}
			System.out.println(rowCount);
			if (rowCount < i) {
				return 0;
			}
			return i;
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			return 0;
		}

	}

	// 获取指定 sheet 中，某个测试用例最后的行号
	public int getTestCaseLastStepRow(String SheetName, String testCaseID, int testCaseStartRowNumber) {
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			/*
			 * 从包含指定测试用例序号的第一行开始逐行遍历，直到某一行不出现指定测试用例序号， 此时的遍历次数就是次测试用例步骤的个数
			 */
			for (int i = testCaseStartRowNumber; i < ExcelWSheet.getLastRowNum(); i++) {
				// System.out.println(ExcelUtil.getCellData(SheetName,i,
				// Constants.Col_TestCaseID));

				try {
					if (!testCaseID.equals(getCellData(SheetName, i, Constants.Col_TestCaseID))) {
						int number = i-1;
						return number;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				System.out.println(i + "xxxxxxxxxxxxxxxxxxx");

			}
			int number = ExcelWSheet.getLastRowNum();
			return number;
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
			return 0;
		}
	}

	// 在 excel 文件的执行单元格中写入数据，此函数只支持后缀为 xlsx 的 excel 文件写入
	public void setCellData(String SheetName, int RowNum, int ColNum, String Result,boolean testFlag) {
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		try {
			// 获取 excel文件中的行对象
			Row = ExcelWSheet.getRow(RowNum);
			// 如果单元格为空，则返回 Null
			Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
			XSSFCellStyle style = ExcelWBook.createCellStyle();
			if (testFlag) {
				style.setFillForegroundColor(IndexedColors.GREEN.index);

			} else {
				style.setFillForegroundColor(IndexedColors.RED.index);
			}
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			if (Cell == null) {
				// 当单元格对象是 null 的时候，则创建单元格
				// 如果单元格为空，无法直接调用单元格对象的 setCellValue 方法设定单元格的值
				Cell = Row.createCell(ColNum);
				// 创建单元格后可以调用单元格对象的 setCellValue 方法设定单元格的值
				Cell.setCellValue(Result);

			} else {
				// 单元格中有内容，则可以直接调用单元格对象的 setCellValue 方法设定单元格的值
				Cell.setCellValue(Result);

			}
			Cell.setCellStyle(style);
			// 实例化写入 excel 文件的文件输出流对象
			FileOutputStream fileOut = new FileOutputStream(Constants.Path_ExcelFile);
			// 将内容写入 excel 文件中
			ExcelWBook.write(fileOut);
			// 调用flush 方法强制刷新写入文件
			fileOut.flush();
			// 关闭文件输出流对象
			fileOut.close();

		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
		}
	}

	public Object[][] getKeywordData(String sheetName) {
		ExcelWSheet = ExcelWBook.getSheet(sheetName);
		int rowCount=ExcelWSheet.getLastRowNum();
		System.out.println(rowCount);
		List<Object[]> records=new ArrayList<Object[]>();
		for(int i=1;i<=rowCount;i++) {
			if(getCellData(sheetName, i, 2).equalsIgnoreCase("y")) {
				System.out.println();
				Object[] rowFields=new Object[3];
				for(int j=0;j<3;j++) {
					String cellData = getCellData(sheetName, i, j);
					System.out.print(cellData+"\t");
					rowFields[j]=cellData;
				}
				records.add(rowFields);
			}
		}
		Object[][] obj=new Object[records.size()][];
		for(int i=0;i<records.size();i++) {
			obj[i]=records.get(i);
		}
		return obj;
	}

	public Object[][] getCaseData(String sheetName) {
		ExcelWSheet = ExcelWBook.getSheet(sheetName);
		int rowCount=ExcelWSheet.getLastRowNum();
		System.out.println(rowCount);
		List<Object[]> records=new ArrayList<Object[]>();
		for(int i=1;i<=rowCount;i++) {
			System.out.println();
			Object[] rowFields=new Object[3];
			for(int j=1;j<=3;j++) {
				String cellData = getCellData(sheetName, i, j);
				System.out.print(cellData+"\t");
				rowFields[j-1]=cellData;
			}
			records.add(rowFields);
		}
		Object[][] obj=new Object[records.size()][];
		for(int i=0;i<records.size();i++) {
			obj[i]=records.get(i);
		}
		return obj;
	}

	public static void main(String[] args) {
		ExcelUtil util = new ExcelUtil("configs/DONGKEYWORD.xlsx");
		util.getCaseData("登录");
		// System.out.println(getCellData("知乎",1,1));
	//	System.out.println(util.getFirstRowContainsTestCaseID("DONG", "退出"));
//		System.out.println(util.getTestCaseLastStepRow("DONG", "退出",8));
	}
}
