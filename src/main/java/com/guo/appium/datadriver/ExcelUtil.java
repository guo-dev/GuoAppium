package com.guo.appium.datadriver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	private XSSFSheet ExcelWSheet;
	private XSSFWorkbook ExcelWBook;
	private XSSFCell Cell;
	private XSSFRow Row;
	private String filePath;

	// 设定要操作的 Excel 的文件路径和 Excel 文件中的 sheet 名称
	// 在读写excel的时候，均需要先调用此方法，设定要操作的 excel 文件路径和要操作的 sheet 名称
	public ExcelUtil(String filepath) throws Exception {
		FileInputStream ExcelFile;
		try {
			// 实例化 excel 文件的 FileInputStream 对象
			ExcelFile = new FileInputStream(filepath);
			// 实例化 excel 文件的 XSSFWorkbook 对象
			ExcelWBook = new XSSFWorkbook(ExcelFile);

		} catch (Exception e) {
			throw (e);
		}
		this.filePath = filepath;
	}

	// 读取 excel 文件指定单元格的函数，此函数只支持后缀为xlsx的 excel 文件
	public String getCellData(int RowNum, int ColNum, String sheetName) throws Exception {
		ExcelWSheet = ExcelWBook.getSheet(sheetName);
		try {
			// 通过函数参数指定单元格的行号和列号，获取指定的单元格对象
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			// 如果单元格的内容为字符串类型，则使用 getStringCellValue 方法获取单元格的内容
			// 如果单元格的内容为数字类型，则使用 getNumericCellValue() 方法获取单元格的内容
			String CellData = "";
			if (Cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
				CellData = Cell.getStringCellValue();
			} else if (Cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
				DecimalFormat df = new DecimalFormat("0");
				CellData = df.format(Cell.getNumericCellValue());
			} else {
				throw new RuntimeException("no support data type");
			}

			return CellData;

		} catch (Exception e) {
			throw new RuntimeException("get excel data error-->" + e.getMessage());
		}

	}

	// 在 excel 文件的执行单元格中写入数据，此函数只支持后缀为xlsx的 excel 文件写入
	public void setCellData(int RowNum, int ColNum, String resultValue, String sheetName, boolean testFlag)
			throws Exception {
		ExcelWSheet = ExcelWBook.getSheet(sheetName);
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
				Cell.setCellValue(resultValue);

			} else {
				// 单元格中有内容，则可以直接调用单元格对象的 setCellValue 方法设定单元格的值
				Cell.setCellValue(resultValue);
			}

			Cell.setCellStyle(style);
			// 实例化写入 excel 文件的文件输出流对象
			FileOutputStream fileOut = new FileOutputStream(filePath);
			// 将内容写入 excel 文件中
			ExcelWBook.write(fileOut);
			// 调用flush 方法强制刷新写入文件
			fileOut.flush();
			// 关闭文件输出流对象
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("set excel data error-->" + e.getMessage());
		}
	}

	// 从 excel 文件获取测试数据的静态方法
	public Object[][] getTestData(String sheetName) throws IOException {
		// 通过 sheetName 参数,生成 sheet 对象
		Sheet Sheet = ExcelWBook.getSheet(sheetName);
		int rowCount = Sheet.getLastRowNum() - Sheet.getFirstRowNum();

		List<Object[]> records = new ArrayList<Object[]>();
		for (int i = 1; i <= rowCount; i++) {
			// 使用 getRow 方法获取行对象
			Row row = Sheet.getRow(i);
			String fields[] = new String[row.getLastCellNum() - 2];
			if (row.getCell(row.getLastCellNum() - 2).getStringCellValue().equalsIgnoreCase("y")) {
				for (int j = 0; j < row.getLastCellNum() - 2; j++) {
					if (row.getCell(j).getCellType() == XSSFCell.CELL_TYPE_STRING) {
						fields[j] = row.getCell(j).getStringCellValue();
					} else if (row.getCell(j).getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						DecimalFormat df = new DecimalFormat("0");
						fields[j] = df.format(row.getCell(j).getNumericCellValue());
					} else {
						throw new RuntimeException("cell value format is error");
					}
				}
				records.add(fields);
			}

		}

		// 定义函数返回值，即 Object[][]
		// 将存储测试数据的 list 转换为一个 Object 的二维数组
		// {{“”，“”，“”}，{“”，“”，“”}，{“”，“”，“”}，{“”，“”，“”}}
		Object[][] results = new Object[records.size()][];
		// 设置二维数组每行的值，每行是个object对象
		for (int i = 0; i < records.size(); i++) {
			results[i] = records.get(i);
		}
		// 关闭 excel 文件
		if(!(results.length>0)){
			throw new RuntimeException("dataprovider data is empty");
		}
		return results;
	}

	public static void main(String[] args) {
		try {
			ExcelUtil excel = new ExcelUtil("configs/douban.xlsx");
			// eu.setCellData(2, eu.getLastColumnNum(),"测试执行失败");
			Object[][] ob = excel.getTestData("login");
			for (int i = 0; i < ob.length; i++) {
				Object[] obl = ob[i];
				System.out.println();
				for (int j = 0; j < obl.length; j++) {
					System.out.print(obl[j]+"\t");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
