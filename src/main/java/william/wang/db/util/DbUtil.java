package william.wang.db.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 数据库常用工具类
 * @author 王伟
 *
 */
public class DbUtil {
	private static ApplicationContext ac;
	private static String beansUrl = "classpath:spring/applicationContext-*.xml";
	private static ThreadLocal<Connection>  
	  connectionHolders = new ThreadLocal<Connection>();
	
	public synchronized static Connection getConnection() 
			throws SQLException{
				/**
				 *  ThreadLocal的get()方法：
				 *  	以执行当前这行代码的线程做为key，返回
				 *  对应的value。
				 */
				Connection conn = connectionHolders.get();
				if(conn == null){
					conn = getConn();
					
					/**
					 * 　ThreadLocal的set方法：以执行当前这行
					 * 代码的线程做为key,保存value。
					 */
					connectionHolders.set(conn);
				}
				return conn;
			}
	
	private static Connection getConn() throws SQLException{
		/*Connection conn = null;
		try {
			Class.forName(props.getProperty("drivername"));
			conn = DriverManager.getConnection(
					props.getProperty("url"),
					props.getProperty("username"),
					props.getProperty("pwd"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}*/
		DataSource ds = (DataSource)getApplicationContext().getBean("dataSource");
		return ds.getConnection();
	}
	
	public static ApplicationContext getApplicationContext() {
		if (ac == null) {

			System.out.println("手动  - Initializing Spring root WebApplicationContext["	+ beansUrl + "]");
			ac = new ClassPathXmlApplicationContext(beansUrl);
			// jdbcTemplate.setQueryTimeout(120);
		}
		return ac;
	}
	
	public static void close() {
		Connection conn = connectionHolders.get();
		if(conn != null){
			try {
				conn.close();
				connectionHolders.set(null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public static void close(ResultSet rst,
			Statement stat, Connection conn){
		if(rst != null){
			try {
				rst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(stat != null){
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
