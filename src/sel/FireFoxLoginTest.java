package sel;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.*;

@Listeners({TestReport.class})
public class FireFoxLoginTest {
	WebDriver driver;
	//���������
	@BeforeClass
	
	public void startExplorer() {
		//��huohu�����
		System.setProperty("webdriver.gecko.driver", "F:/selenium-package/driver/geckodriver.exe");
		System.setProperty("webdriver.firefox.bin", "E:/FireFox/firefox.exe");
		driver=new FirefoxDriver();
		
		
	}
	
	//���һ�����Է���ִ����֮��ر������
	@AfterClass
	public void closeExplorer() {
		//�ر���ҳ
		driver.quit();
	}
	
	//ÿ����һ�������ȴ�������
	//����ĳЩԪ�أ���Ҫ�ȴ�һ��ʱ����ܼ��س�����Ϊ�˲���Ԫ�ص��ȶ��ԣ������ӵȴ�ʱ��
	@AfterMethod
	public void wait3s() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	
	//���Դ��룺����վ����¼����
	
	@BeforeMethod
	@Test(description="����վ",priority=0)
	@Parameters({"url"})
	public void openWeb(String url) {
		
		//����վ
		
		//String url="http://localhost:1080/webtours";
		driver.get(url);
		
		///���ԣ����㣩���ж��Ƿ�����˳�ʼ����
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		Boolean res_log=driver.findElement(By.tagName("body")).getText().contains("Welcome to the Web Tours site");
		Assert.assertTrue(res_log);//�����Ƿ�ͨ��
	}
	
	//��������
	@Test(description="��¼",priority=1,dataProvider="Excel_data",dataProviderClass=Param.class)
	public void login(String yhm,String mm,String expect) throws InterruptedException {
        //frame������frameset������л�
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("navbar");
		//�û���
		driver.findElement(By.name("username")).sendKeys(yhm);
		//����
		driver.findElement(By.name("password")).sendKeys(mm);
		//��¼(��������������)
		driver.findElement(By.name("login")).click();
		
		Thread.sleep(3000);
		///���ԣ����㣩���ж��Ƿ��¼�ɹ�(���ܳ��ּ��ʧ�ܵ�ʵ��û�������������ȴ�ʱ�䲻���������ӳ�һ��ʱ�伴Thread.sleep())
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		String actual=driver.findElement(By.tagName("body")).getText();
		Boolean res=driver.findElement(By.tagName("body")).getText().contains(expect);
		try {
			Assert.assertTrue(res);
		}catch(Error e){
			Assert.fail("ʵ�ʽ��"+actual);
			//System.out.println(actual);
		}
		
		
			
	}
	
	
	
	
}


