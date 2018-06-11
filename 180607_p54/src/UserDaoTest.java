import java.sql.SQLException;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class UserDaoTest {
	public static void main (String[] args) throws ClassNotFoundException, SQLException {
//		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
////		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//		UserDao dao = context.getBean("userDao", UserDao.class);//오브젝트 클래스가 기본으로 리턴되는데, 두번째 인자로 제네릭 메소드방식을 통해 형을 캐스팅해줄수 있음 
//		User user = new User();
//		user.setId("124f");
//		user.setName("asdsad");
//		user.setPassword("married");
//		
//		dao.add(user);
//		System.out.println(user.getId()+ "등록 성공");
//		System.out.println(user.getName());
//		
//		User user2 = dao.get(user.getId());
//		System.out.println(user2.getName());
//		
//		System.out.println(user2.getId() + "조회 성공");
	}
	@Test
	public void addAndGet() throws SQLException, ClassNotFoundException {
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		User user = new User();
		user.setId("124f");
		user.setName("asdsad");
		user.setPassword("married");
		dao.add(user);
		User user2 = dao.get(user.getId());
		
		assertThat(user2.getName(), is(user.getName()));
//		assertThat(user2.getName(), is("test"));//에러 검증 java.lang.Asserterror 
		assertThat(user2.getPassword(), is(user.getPassword()));
	}
	
	@Test
	public void count() throws SQLException, ClassNotFoundException {
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		User user1 = new User("123", "Tom", "spring1");
		User user2 = new User("1234", "Tyler", "spring2");
		User user3 = new User("1235", "Seth", "spring3");
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));
	
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
		
	}
}
