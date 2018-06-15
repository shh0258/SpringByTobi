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
//		Connection c = null;
//		PreparedStatement ps = null;
//		try {
//			c = dataSource.getConnection();
//			StatementStrategy strategy = new DeleteAllStatement();
//			ps = strategy.makePreparedStatement(c);
////			ps = makeStatement(c);
//			ps.executeUpdate();
//		} catch (SQLException e) {
//			throw e;
//		} finally {
//			if(ps != null) {
//				try {
//					ps.close();// close는 만들어진 순서의 반대로 하는 것이 원칙임
//				} catch (SQLException e) {
//					
//				}
//			}
//			if(c != null) {
//				try {
//					c.close();
//				} catch (SQLException e) {
//					
//				}
//			}
//		}
		StatementStrategy st = new DeleteAllStatement();
		jdbcContextWithStatementStrategy(st);
	}
	
//	private PreparedStatement makeStatement(Connection c) throws SQLException {
//		PreparedStatement ps;
//		ps = c.prepareStatement("delete from user");
//		return ps;
//	}
	
	public int getCount() throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			c = dataSource.getConnection();
			ps = c.prepareStatement("select count(*) from user");
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch(SQLException e) {
			throw e;
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					
				}
			}
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
			if(c != null) {
				try {
					c.close();
				} catch (SQLException e) {
					
				}
			}
		}
	}
	
	public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = dataSource.getConnection();
			ps = stmt.makePreparedStatement(c);
			ps.executeUpdate();// excuteQuery 대신 이 메서드를 써야함
		} catch (SQLException e) {
			throw e;
		} finally {
			if( ps != null) {try { ps.close();} catch (SQLException e) {} }
			if( c != null) {try { c.close();} catch (SQLException e) {} }
		}
	}
	
	
}


// 이 상황에서 deleteall 메서드는 일을 수행하는 클라이언트 이고, 때문에 클라이언트는 이전에 daofactory 처럼 실제로
//일을수행하는 메서드에 의존성을 주입하고실행하는 역활을 담당해야 하는 것이지, 그 자체로서 기능을 제공하지 않게 해야 
//의존성주입의 측면에서 클래스를 사용할 수 있다. 이 제어의역전을 통해 코드 재사용성을 높이고 인터페이스화 된 contextmethod를 잘 사용할 수 있게 된다.
