package sel;	//�޸�Ϊ��ȷ�İ���
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
 
public class TestReport extends TestListenerAdapter{
	private String reportPath;
 
	@Override
	public void onStart(ITestContext context) {
		File htmlReportDir = new File("test-output");
        if (!htmlReportDir.exists()) {
        	htmlReportDir.mkdirs();
        }
        //��������ʱ��+_result.html
        String reportName = formateDate()+"_result.html";
        reportPath = htmlReportDir+"/"+reportName;
        File report = new File(htmlReportDir,reportName);
        if(report.exists()){
            try {
                report.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //html����
        StringBuilder sb = new StringBuilder("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
				+ "<title>�Զ������Ա���</title></head><body style=\"background-color:#99FFCC;\">"
				+ "<div id=\"top\" align=\"center\"><p style=\"font-weight:bold;\">�����������н���б�</p>"			
        		+ "<table width=\"90%\" height=\"80\" border=\"1\" align=\"center\" cellspacing=\"0\" rules=\"all\" style=\"table-layout:relative;\">"
        		+ "<thead>"
        		+ "<tr>"
        		+ "<th>����������</th>"
        		+ "<th>�����������</th>"
        		+ "</tr>"
        		+ "</thead>"
        		+ "<tbody style=\"word-wrap:break-word;font-weight:bold;\" align=\"center\">");
        String res = sb.toString();
        try {  
            Files.write((Paths.get(reportPath)),res.getBytes("utf-8"));  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
	}
	
	@Override
	//���Գɹ�
	public void onTestSuccess(ITestResult result) {
		StringBuilder sb = new StringBuilder("<tr><td>");
		sb.append(result.getMethod().getRealClass()+"."+result.getMethod().getMethodName());
		sb.append("</td><td><font color=\"green\">Passed</font></td></tr>");
		String res = sb.toString();
		try {
			Files.write((Paths.get(reportPath)),res.getBytes("utf-8"),StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
	@Override
	//��������
	public void onTestSkipped(ITestResult result) {
		StringBuilder sb = new StringBuilder("<tr><td>");
		sb.append(result.getMethod().getRealClass()+"."+result.getMethod().getMethodName());
		sb.append("</td><td><font color=\"yellow\">Skipped</font>");
		sb.append("<p align=\"left\">��������<font color=\"red\">����</font>��ԭ��<br>");
		sb.append("<br><a style=\"background-color:#CCCCCC;\">");
		Throwable throwable = result.getThrowable(); 
                sb.append(throwable.getMessage()); 
                sb.append("</a></p></td></tr>");
		String res = sb.toString();
		try {
			Files.write((Paths.get(reportPath)),res.getBytes("utf-8"),StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	//����ʧ��
	public void onTestFailure(ITestResult result) {
		StringBuilder sb = new StringBuilder("<tr><td>");
        sb.append(result.getMethod().getRealClass()+"."+result.getMethod().getMethodName());
        sb.append("</td><td><font color=\"red\">Failed</font><br>");
        sb.append("<p align=\"left\">��������ִ��<font color=\"red\">ʧ��</font>��ԭ��<br>");
        sb.append("<br><a style=\"background-color:#CCCCCC;\">");
        Throwable throwable = result.getThrowable();
        sb.append(throwable.getMessage());
        sb.append("</a></p></td></tr>");
        String res = sb.toString();
        try {
            Files.write((Paths.get(reportPath)),res.getBytes("utf-8"),StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
 
	@Override
	public void onFinish(ITestContext testContext) {
		StringBuilder sb = new StringBuilder("</tbody></table><a href=\"#top\">���ض���</a></div></body>");
		sb.append("</html>");
		String msg = sb.toString();
		try {
			Files.write((Paths.get(reportPath)),msg.getBytes("utf-8"),StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
	public static String formateDate(){//���ڸ�ʽ
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd HHmmss");
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		return sf.format(date);
	}
}