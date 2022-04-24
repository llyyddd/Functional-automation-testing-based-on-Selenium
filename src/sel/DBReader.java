package sel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//import sun.security.mscapi.CKeyPairGenerator.RSA;

public class DBReader {
	public static void main(String[] args) throws Exception {
		//加载数据库驱动
		Class.forName("com.mysql.jdbc.Driver");
		//连接DB
		String DBURL="jdbc:mysql://localhost:3306/test";
		String yhm="root";
		String mm="root";
		Connection conn=DriverManager.getConnection(DBURL,yhm,mm);
		//预编译形式的sql语句
		String sql="insert into users values(?,?)";
		PreparedStatement state=conn.prepareStatement(sql);
		//传参数
		for(int i=1;i<=100;i++) {
			state.setString(1,"zs"+i);
			state.setString(2,""+i);
			state.addBatch();
		}
		//执行查询SQL语句   executeBatch()批量执行
		//返回的是每次循环改变的行数（每个批次改变的行数）所构成的数组
		int n[]=state.executeBatch();
		int sum=0;
		for(int i=0;i<n.length;i++)
			sum+=n[i];
		if(sum==100)
		   System.out.println(sum+"条数据写入成功");
		//关闭DB
		state.close();
		conn.close();	
	}

}
