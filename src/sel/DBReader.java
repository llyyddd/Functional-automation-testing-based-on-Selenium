package sel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//import sun.security.mscapi.CKeyPairGenerator.RSA;

public class DBReader {
	public static void main(String[] args) throws Exception {
		//�������ݿ�����
		Class.forName("com.mysql.jdbc.Driver");
		//����DB
		String DBURL="jdbc:mysql://localhost:3306/test";
		String yhm="root";
		String mm="root";
		Connection conn=DriverManager.getConnection(DBURL,yhm,mm);
		//Ԥ������ʽ��sql���
		String sql="insert into users values(?,?)";
		PreparedStatement state=conn.prepareStatement(sql);
		//������
		for(int i=1;i<=100;i++) {
			state.setString(1,"zs"+i);
			state.setString(2,""+i);
			state.addBatch();
		}
		//ִ�в�ѯSQL���   executeBatch()����ִ��
		//���ص���ÿ��ѭ���ı��������ÿ�����θı�������������ɵ�����
		int n[]=state.executeBatch();
		int sum=0;
		for(int i=0;i<n.length;i++)
			sum+=n[i];
		if(sum==100)
		   System.out.println(sum+"������д��ɹ�");
		//�ر�DB
		state.close();
		conn.close();	
	}

}
