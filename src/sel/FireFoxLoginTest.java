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
	//启动浏览器
	@BeforeClass
	
	public void startExplorer() {
		//打开huohu浏览器
		System.setProperty("webdriver.gecko.driver", "F:/selenium-package/driver/geckodriver.exe");
		System.setProperty("webdriver.firefox.bin", "E:/FireFox/firefox.exe");
		driver=new FirefoxDriver();
		
		
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
	
	//测试代码：打开网站、登录测试
	
	@BeforeMethod
	@Test(description="打开网站",priority=0)
	@Parameters({"url"})
	public void openWeb(String url) {
		
		//打开网站
		
		//String url="http://localhost:1080/webtours";
		driver.get(url);
		
		///断言（检查点）：判断是否进入了初始界面
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		Boolean res_log=driver.findElement(By.tagName("body")).getText().contains("Welcome to the Web Tours site");
		Assert.assertTrue(res_log);//测试是否通过
	}
	
	//数据驱动
	@Test(description="登录",priority=1,dataProvider="Excel_data",dataProviderClass=Param.class)
	public void login(String yhm,String mm,String expect) throws InterruptedException {
        //frame里面有frameset，层层切换
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("navbar");
		//用户名
		driver.findElement(By.name("username")).sendKeys(yhm);
		//密码
		driver.findElement(By.name("password")).sendKeys(mm);
		//登录(可能有重名问题)
		driver.findElement(By.name("login")).click();
		
		Thread.sleep(3000);
		///断言（检查点）：判断是否登录成功(可能出现检查失败但实际没有问题的情况：等待时间不够，可以延长一下时间即Thread.sleep())
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		String actual=driver.findElement(By.tagName("body")).getText();
		Boolean res=driver.findElement(By.tagName("body")).getText().contains(expect);
		try {
			Assert.assertTrue(res);
		}catch(Error e){
			Assert.fail("实际结果"+actual);
			//System.out.println(actual);
		}
		
		
			
	}
	
	
	
	
}


