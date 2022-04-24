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
	//启动浏览器
	@BeforeClass
	public void startExplorer() {
		//打开IE浏览器
		System.setProperty("webdriver.ie.driver", "F:/selenium-package/driver/IEDriverServer.exe");
		driver=new InternetExplorerDriver();
		
		
	}
	
	//最后一个测试方法执行完之后关闭浏览器
	@AfterClass
	public void closeExplorer() {
		//关闭网页
		driver.quit();
	}
	
	//每做完一个方法等待三秒钟
	//对于某些元素，需要等待一段时间才能加载出来，为了查找元素的稳定性，需增加等待时间
	@AfterMethod
	public void wait3s() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	
	//测试代码：打开网站、登录、订票、退出4个业务
	//@BeforeMethod
	@Test(description="打开网站",priority=0)
	public void openWeb() {
		//打开网站
		String url="http://localhost:1080/webtours";
		driver.get(url);
		
		///断言（检查点）：判断是否进入了初始界面
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		Boolean res_log=driver.findElement(By.tagName("body")).getText().contains("Welcome to the Web Tours site");
		Assert.assertTrue(res_log);//测试是否通过
	}

	@Test(description="登录",priority=1)
	public void login() {
        //frame里面有frameset，层层切换
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("navbar");
		//用户名
		driver.findElement(By.name("username")).sendKeys("lyd");
		//密码
		driver.findElement(By.name("password")).sendKeys("1");
		//登录(可能有重名问题)
		driver.findElement(By.name("login")).click();
		
		///断言（检查点）：判断是否登录成功(可能出现检查失败但实际没有问题的情况：等待时间不够，可以延长一下时间即Thread.sleep())
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		String actual=driver.findElement(By.tagName("body")).getText();
		Boolean res=driver.findElement(By.tagName("body")).getText().contains("Welcome, lyd");
		try {
			Assert.assertTrue(res);
		}catch(Error e){
			Assert.fail("实际结果"+actual);
			//System.out.println(actual);
		}
		
		
			
	}
	
	@Test(description="第一次搜票",priority=2)
	public void search1() throws Exception {
		//查看航班信息，flight没有class或id，使用xpath
		//切回主文档再进入frame
		//登录后（即跳转界面时），网页可能会有延迟，无法选出发地和目的地，因此让线程进行睡眠
		
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("navbar");
		driver.findElement(By.xpath("//img[@alt=\"Search Flights Button\"]")).click();
		
		//跳转界面时可能有延迟
		Thread.sleep(5000);
		
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		Boolean res_s=driver.findElement(By.tagName("body")).getText().contains("Departure City");
		Assert.assertTrue(res_s);
	}
	
	@Test(description="订票",priority=3,dataProvider="List",dataProviderClass=Param.class)
	public void book(String from,String to) throws Exception {		
		//出发地
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		Select s1=new Select(driver.findElement(By.name("depart")));
		s1.selectByVisibleText(from);
		//目的地
		Select s2=new Select(driver.findElement(By.name("arrive")));
		s2.selectByVisibleText(to);
		//点击continue
		driver.findElement(By.name("findFlights")).click();
		
		//选航班
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		driver.findElements(By.name("outboundFlight")).get(1).click();
		//获得航班号
		String flightnum=driver.findElements(By.name("outboundFlight")).get(1).getAttribute("value").substring(0,3);
		//点击continue
		
		driver.findElement(By.name("reserveFlights")).click();
		
		//确认个人信息，continue
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		driver.findElement(By.name("buyFlights")).click();

		///断言（检查点）：判断是否成功dingpiao
		//选不同票的时候页面信息会发生变化
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
	
	@Test(description="注销",priority=4)
	public void quit() throws Exception {
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("navbar");
		driver.findElement(By.xpath("//img[@alt=\"SignOff Button\"]")).click();
		///断言（检查点）：判断是否成功退出
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		Boolean res_quit=driver.findElement(By.tagName("body")).getText().contains("Welcome to the Web Tours site");
		Assert.assertTrue(res_quit);
	
	}
	
	
}