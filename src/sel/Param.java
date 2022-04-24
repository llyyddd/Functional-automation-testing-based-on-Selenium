package sel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class Param {
	@DataProvider
	public Object[][] getParam1(){
		Object[][] data= {{"London","Paris"},{"Denver","London"},{"Paris","Denver"}};
	    return data;
	}
	
	@DataProvider(name="Arr")
	public Object[][] getParam_Arr() throws Exception{
		File file = new File("F:/selenium/flights.txt");
		FileReader bytes = new FileReader(file);
		BufferedReader chars=new BufferedReader(bytes);
		int lines=0;
		while(chars.readLine()!=null)
			lines++;
		Object[][] data=new Object[lines][];
		bytes.close();
		//重新打开或把光标移到开头
		bytes = new FileReader(file);
		chars=new BufferedReader(bytes);
		String row=null;
		int i=0;
		while((row=chars.readLine())!=null) {
			String[] cols=row.split("\t");
			data[i++]=cols;
		}
		bytes.close();
		return data;
	}
	
	@DataProvider(name="List")
	public Object[][] getParam_List() throws Exception{
		File file = new File("F:/selenium/flights.txt");
		FileReader bytes = new FileReader(file);
		BufferedReader chars=new BufferedReader(bytes);
		List<String[]> res=new ArrayList<>();
		String row=null;
		while((row=chars.readLine())!=null) {
			String[] cols=row.split("\t");
			res.add(cols);
		}
		bytes.close();
		Object[][] data=new Object[res.size()][];
		for(int i=0;i<res.size();i++)
			data[i]=res.get(i);
		return data;
	}	
	@DataProvider(name="Excel_data")
	public Object[][] getParam_Excel() throws Exception{
		InputStream file=new FileInputStream("F:/selenium-package/cases.xls");
		Workbook excel=Workbook.getWorkbook(file);
		Sheet sheet=excel.getSheet(0);
		int row_c=sheet.getRows();
		int col_c=sheet.getColumns();
		Cell cell=null;
		List<String[]> list=new ArrayList<>();
		for(int i=1;i<row_c;i++) {
			String[] roei_col=new String[col_c-1];//存每行数据,第一列存的是用例的名称	 
			//从第2列开始，第一列存的是用例名称
			for(int j=1;j<col_c;j++) {
				cell=sheet.getCell(j,i);
				roei_col[j-1]=cell.getContents();
				
			}
			list.add(roei_col);
		}
		file.close();
		Object[][] data=new Object[list.size()][];
		for(int i=0;i<list.size();i++)
			data[i]=list.get(i);
		return data;
		
	}
		


}
