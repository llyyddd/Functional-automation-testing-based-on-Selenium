# Functional-automation-testing-based-on-Selenium
基于selenium的功能自动化测试

基于java selenium 对webtours订票网站进行功能测试

### 配置环境
1、jdk 1.8  
2、idea/eclipse

### 实现的功能
1、数据驱动  
2、使用textng进行并发测试  
3、使用testng.xml传递参数  
4、自动生成测试报告并存储在test-output文件夹中  

### 详细说明
1、数据驱动：  
使用dataprovider访问参数，从而实现数据驱动。测试数据存储在Excel和TXT中，从而减少代码重复且数据所属的测试用例失败，不会影响到其他测试数据对应的测试用例  
本框架中登录测试用例存储在cases.xls中，订票测试用例存储在flights.txt中。   
2、对用户登录进行并发测试：  
IELoginTest.java实现在IE浏览器上进行登录自动化测试，FireFoxLoginTest.java实现在FireFox浏览器上进行登录自动化测试。使用textng的测试套件同时在两个浏览器上进行登录测试，可用于兼容性测试。  
3、对通过对testng.xml修改传递参数。  
4、自动生成测试报告，并存储在test-output文件夹中，其中测试报告的文件名包含运行日期信息，文件内容包含测试用例名、测试用例执行结果和错误信息等。    
