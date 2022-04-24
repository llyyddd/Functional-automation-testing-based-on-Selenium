package sel;

import java.io.*;

import java.util.*;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;



public class FileRead {
	public static void main(String[] args) throws Exception {
		/*
		 * //从记事本读 
		 * File file = new File("F:/selenium/flights.txt"); 
		 * FileReader bytes = new FileReader(file); 
		 * BufferedReader chars = new BufferedReader(bytes); 
		 * //存一行
		 * String row=null; 
		 * while((row=chars.readLine())!=null) { 
		 *      String[] cols=row.split("\t"); 
		 *      for(String col:cols) { 
		 *          System.out.println(col); } }
		 * bytes.close();
		 */
		
		//从Excel中读
		InputStream file=new FileInputStream("F:/selenium-package/cases.xls");
		Workbook excel=Workbook.getWorkbook(file);
		Sheet sheet=excel.getSheet(0);
		int row_c=sheet.getRows();
		int col_c=sheet.getColumns();
		Cell cell=null;
		List<String[]> list=new ArrayList<>();
		for(int i=1;i<row_c;i++) {
			String[] roei_col=new String[col_c];//存每行数据
			//从第2列开始，第一列存的是用例名称
			for(int j=1;j<col_c;j++) {
				cell=sheet.getCell(j,i);
				roei_col[j]=cell.getContents();
				
			}
			list.add(roei_col);
		}
		file.close();
		
	}

}
