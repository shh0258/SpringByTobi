import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class JunitTest {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	@Autowired
	private ApplicationContext context;
	private UserDao dao;
	private User user1;
	private User user2;
	private User user3;
	
	@Before
	public void setUp() {
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		dao = context.getBean("userDao", UserDao.class);
		this.user1 = new User("123", "Tom", "spring1" );
		this.user2 = new User("1234", "Tyler", "spring2");
		this.user3 = new User("1235", "Seth", "spring3");
	}
	@Test
	public void addAndGet() throws SQLException, ClassNotFoundException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		dao.add(user2);
		User user4 = dao.get(user1.getId());
		User user5 = dao.get(user2.getId());
		assertThat(user1.getName(), is(user4.getName()));
		assertThat(user1.getPassword(), is(user4.getPassword()));
		assertThat(user2.getName(), is(user5.getName()));
		assertThat(user2.getPassword(), is(user5.getPassword()));
	}
	
	@Test
	public void count() throws SQLException, ClassNotFoundException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));
	
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException, ClassNotFoundException{
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		dao.get("unknown_id");
	}
}
