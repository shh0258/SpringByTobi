import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
	ConnectionMaker sc = new AConnectionMaker();
	public void add(User user) throws ClassNotFoundException, SQLException {
		Connection c = sc.makeConnection();
		PreparedStatement ps = c.prepareStatement("insert into User(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2,  user.getName());
		ps.setString(3, user.getPassword());
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = sc.makeConnection();
		PreparedStatement ps = c.prepareStatement("select * from User where id = ?");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));
		
		rs.close();
		ps.close();
		c.close();
		return user;
	}
	
	public static void main (String[] args) throws ClassNotFoundException, SQLException {
		UserDao dao = new UserDao();
		User user = new User();
		user.setId("1241rf");
		user.setName("asdsad");
		user.setPassword("married");
		
		dao.add(user);
		System.out.println(user.getId()+ "등록 성공");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getName());
		
		System.out.println(user2.getId() + "조회 성공");
	}
}
