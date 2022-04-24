package sel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.*;

@Listeners({TestReport.class})
public class bookTest {
	WebDriver driver;
	//���������
	@BeforeClass
	public void startExplorer() {
		//��IE�����
		System.setProperty("webdriver.ie.driver", "F:/selenium-package/driver/IEDriverServer.exe");
		driver=new InternetExplorerDriver();
		
		
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
	
	//���Դ��룺����վ����¼����Ʊ���˳�4��ҵ��
	//@BeforeMethod
	@Test(description="����վ",priority=0)
	public void openWeb() {
		//����վ
		String url="http://localhost:1080/webtours";
		driver.get(url);
		
		///���ԣ����㣩���ж��Ƿ�����˳�ʼ����
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		Boolean res_log=driver.findElement(By.tagName("body")).getText().contains("Welcome to the Web Tours site");
		Assert.assertTrue(res_log);//�����Ƿ�ͨ��
	}

	@Test(description="��¼",priority=1)
	public void login() {
        //frame������frameset������л�
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("navbar");
		//�û���
		driver.findElement(By.name("username")).sendKeys("lyd");
		//����
		driver.findElement(By.name("password")).sendKeys("1");
		//��¼(��������������)
		driver.findElement(By.name("login")).click();
		
		///���ԣ����㣩���ж��Ƿ��¼�ɹ�(���ܳ��ּ��ʧ�ܵ�ʵ��û�������������ȴ�ʱ�䲻���������ӳ�һ��ʱ�伴Thread.sleep())
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		String actual=driver.findElement(By.tagName("body")).getText();
		Boolean res=driver.findElement(By.tagName("body")).getText().contains("Welcome, lyd");
		try {
			Assert.assertTrue(res);
		}catch(Error e){
			Assert.fail("ʵ�ʽ��"+actual);
			//System.out.println(actual);
		}
		
		
			
	}
	
	@Test(description="��һ����Ʊ",priority=2)
	public void search1() throws Exception {
		//�鿴������Ϣ��flightû��class��id��ʹ��xpath
		//�л����ĵ��ٽ���frame
		//��¼�󣨼���ת����ʱ������ҳ���ܻ����ӳ٣��޷�ѡ�����غ�Ŀ�ĵأ�������߳̽���˯��
		
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("navbar");
		driver.findElement(By.xpath("//img[@alt=\"Search Flights Button\"]")).click();
		
		//��ת����ʱ�������ӳ�
		Thread.sleep(5000);
		
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		Boolean res_s=driver.findElement(By.tagName("body")).getText().contains("Departure City");
		Assert.assertTrue(res_s);
	}
	
	@Test(description="��Ʊ",priority=3,dataProvider="List",dataProviderClass=Param.class)
	public void book(String from,String to) throws Exception {		
		//������
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		Select s1=new Select(driver.findElement(By.name("depart")));
		s1.selectByVisibleText(from);
		//Ŀ�ĵ�
		Select s2=new Select(driver.findElement(By.name("arrive")));
		s2.selectByVisibleText(to);
		//���continue
		driver.findElement(By.name("findFlights")).click();
		
		//ѡ����
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		driver.findElements(By.name("outboundFlight")).get(1).click();
		//��ú����
		String flightnum=driver.findElements(By.name("outboundFlight")).get(1).getAttribute("value").substring(0,3);
		//���continue
		
		driver.findElement(By.name("reserveFlights")).click();
		
		//ȷ�ϸ�����Ϣ��continue
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		driver.findElement(By.name("buyFlights")).click();

		///���ԣ����㣩���ж��Ƿ�ɹ�dingpiao
		//ѡ��ͬƱ��ʱ��ҳ����Ϣ�ᷢ���仯
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		Boolean res_book=driver.findElement(By.tagName("body")).getText().contains("Flight "+flightnum+" leaves "+from+" for "+to);
		Assert.assertTrue(res_book);
		
		//book another
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		driver.findElement(By.name("Book Another")).click();	
	}
	
	@Test(description="ע��",priority=4)
	public void quit() throws Exception {
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("navbar");
		driver.findElement(By.xpath("//img[@alt=\"SignOff Button\"]")).click();
		///���ԣ����㣩���ж��Ƿ�ɹ��˳�
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		Boolean res_quit=driver.findElement(By.tagName("body")).getText().contains("Welcome to the Web Tours site");
		Assert.assertTrue(res_quit);
	
	}
	
	
}