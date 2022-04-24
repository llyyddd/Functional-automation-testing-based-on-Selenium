package sel;

import java.io.*;

import java.util.*;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;



public class FileRead {
	public static void main(String[] args) throws Exception {
		/*
		 * //�Ӽ��±��� 
		 * File file = new File("F:/selenium/flights.txt"); 
		 * FileReader bytes = new FileReader(file); 
		 * BufferedReader chars = new BufferedReader(bytes); 
		 * //��һ��
		 * String row=null; 
		 * while((row=chars.readLine())!=null) { 
		 *      String[] cols=row.split("\t"); 
		 *      for(String col:cols) { 
		 *          System.out.println(col); } }
		 * bytes.close();
		 */
		
		//��Excel�ж�
		InputStream file=new FileInputStream("F:/selenium-package/cases.xls");
		Workbook excel=Workbook.getWorkbook(file);
		Sheet sheet=excel.getSheet(0);
		int row_c=sheet.getRows();
		int col_c=sheet.getColumns();
		Cell cell=null;
		List<String[]> list=new ArrayList<>();
		for(int i=1;i<row_c;i++) {
			String[] roei_col=new String[col_c];//��ÿ������
			//�ӵ�2�п�ʼ����һ�д������������
			for(int j=1;j<col_c;j++) {
				cell=sheet.getCell(j,i);
				roei_col[j]=cell.getContents();
				
			}
			list.add(roei_col);
		}
		file.close();
		
	}

}
