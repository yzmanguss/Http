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
	 * �����ӳ��л�ȡ���Ӷ���
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
	 * �������ӳ��ļ�
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
	 * ע��---����û������ݿ�
	 * 
	 * @param name
	 *            �û���
	 * @param password
	 *            ����
	 * @return ����ɹ����
	 */
	public static Boolean addUser(String name, String password) {
		try {
			boolean flag = false;
			Connection connection = DBOperation.getConnection();
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
			System.out.println("ע��ʧ��");
			e.printStackTrace();
			
		}

		return false;
	}

}
