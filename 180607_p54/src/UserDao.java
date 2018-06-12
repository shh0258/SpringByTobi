import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

public class UserDao {
	private ConnectionMaker cm;
	private DataSource dataSource;
	
	public void setConnectionMaker(ConnectionMaker connectionMaker) {
		this.cm = connectionMaker;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void add(User user) throws ClassNotFoundException, SQLException {
		Connection c = dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement("insert into User(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2,  user.getName());
		ps.setString(3, user.getPassword());
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement("select * from User where id = ?");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		User user = new User();
		if(rs.next()) {
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}
		
		rs.close();
		ps.close();
		c.close();
		
		if(user == null) throw new EmptyResultDataAccessException(1);
		return user;
	}
	
	public void deleteAll() throws SQLException {
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement("delete from user");
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
	public int getCount() throws SQLException {
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement("select count(*) from user");
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		
		rs.close();
		ps.close();
		c.close();
		
		return count;
	}
}
