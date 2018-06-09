import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class UserDaoTest {
	public static void main (String[] args) throws ClassNotFoundException, SQLException {
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao = context.getBean("userDao", UserDao.class);//오브젝트 클래스가 기본으로 리턴되는데, 두번째 인자로 제네릭 메소드방식을 통해 형을 캐스팅해줄수 있음 
		User user = new User();
		user.setId("124f");
		user.setName("asdsad");
		user.setPassword("married");
		
		dao.add(user);
		System.out.println(user.getId()+ "등록 성공");
		System.out.println(user.getName());
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		
		System.out.println(user2.getId() + "조회 성공");
		
	}
}
