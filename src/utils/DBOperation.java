package utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class DBOperation {

	/**
	 * 从连接池中获取连接对象
	 */

	public static Connection getConnection() {
		try {
			Properties properties = loadProperties();
			DataSource ds = BasicDataSourceFactory.createDataSource(properties);
			return ds.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 加载连接池文件
	 * 
	 */
	public static Properties loadProperties() {
		try {
			Properties properties = new Properties();
			InputStream inStream = DBOperation.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
			properties.load(inStream);
			return properties;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 注册---添加用户到数据库
	 * 
	 * @param name
	 *            用户名
	 * @param password
	 *            密码
	 * @return 插入成功与否
	 */
	public static Boolean addUser(String name, String password) {
		try {
			boolean flag = false;
			Connection connection = getConnection();
			String sqlString = "insert into user(user_name,user_password) value(?,?)";
			PreparedStatement pst = connection.prepareStatement(sqlString);
			pst.setString(1, name);
			pst.setString(2, password);
			int i = pst.executeUpdate();
			if(i > 0 ){
				System.out.println("i = "+i);
				flag = true;
			}else{
				flag = false;
			}
			return flag;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("注册失败");
			e.printStackTrace();
			
		}

		return false;
	}
	/**
	 * 修改密码
	 * @param name      用户名
	 * @param password  新密码
	 * @return          修改成功与否
	 */
	public Boolean updateUser(String name, String password) {
		
		try {
			Boolean flag = false;
			Connection connection = getConnection();
			String sql = "UPDATE user SET user_password="+password+"WHERE user_name="+name;
			PreparedStatement pst = connection.prepareStatement(sql);
			pst.execute();
			flag = true;
			return flag;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
