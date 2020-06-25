package HibernateUtil;

import java.util.Iterator;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.mysql.cj.xdevapi.JsonArray;

import connect.Employee;
import connect.User;

public class HibernateUtil {

	static Session sessionObj;
	static SessionFactory sessionFactoryObj;

	// This Method Is Used To Create The Hibernate's SessionFactory Object
	private static SessionFactory buildSessionFactory() {
		// Creating Configuration Instance & Passing Hibernate Configuration File
		Configuration configObj = new Configuration();
		configObj.configure("hibernate.cfg.xml");

		// Since Hibernate Version 4.x, ServiceRegistry Is Being Used
		ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder()
				.applySettings(configObj.getProperties()).build();

		// Creating Hibernate SessionFactory Instance
		sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
		return sessionFactoryObj;
	}

	// Method 1: This Method Used To Create A New User Record In The Database
	// Table
//	public static void createRecord() {
//		User empObj;
//		int empAge = 26, empSal = 1000;
//		try {
//			sessionObj = buildSessionFactory().openSession();
//			sessionObj.beginTransaction();
//
//			for (int j = 101; j <= 105; j++) {
//				// Creating User Data & Saving It To The Database
//				empObj = new User();
//				empObj.setFirstName("Editor");
//				empObj.setLastName(String.valueOf(j));
//				empObj.setAge(empAge);
//				empObj.setEducation("Post Graduation");
//				empObj.setSalary(empSal);
//
//				empAge = empAge + 3;
//				empSal = empSal + 500;
//
//				sessionObj.save(empObj);
//			}
//
//			System.out.println("\n.......Records Saved Successfully In The Database.......\n");
//
//			// Committing The Transactions To The Database
//			sessionObj.getTransaction().commit();
//		} catch (Exception sqlException) {
////			if (null != sessionObj.getTransaction()) {
////				System.out.println("\n.......Transaction Is Being Rolled Back.......");
////				sessionObj.getTransaction().rollback();
////			}
//			sqlException.printStackTrace();
//		} finally {
//			if (sessionObj != null) {
//				sessionObj.close();
//			}
//		}
//	}
	public static List getByID() {
		int emp_id;
		User empObj = null;
		try {
			sessionObj = buildSessionFactory().openSession();

			// Get List User
			String sql = "SELECT * FROM user";
			SQLQuery query = sessionObj.createSQLQuery(sql);
			query.addEntity(User.class);
			List results = query.list();

		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		return null;
	}

	//object json
	public static JSONObject getUser(String id, String name, String pass, String email, String role)
			throws JSONException {
		JSONObject jso = new JSONObject();
		jso.put("id", id);
		jso.put("userName", name);
		jso.put("password", pass);
		jso.put("email", email);
		jso.put("role", role);
		return jso;
	}

	// Method 2: This Method Is Used To Display The Records From The Database Table
	public static JSONArray displayRecords() {
//		int emp_id;
		User empObj = null;
		JSONArray a = null;
		try {
			sessionObj = buildSessionFactory().openSession();

			// Get List User
			String sql = "SELECT * FROM user";
			SQLQuery query = sessionObj.createSQLQuery(sql);
			query.addEntity(User.class);
			List results = query.list();
			
			//export json
			a = new JSONArray();
			for (Iterator iterator = results.iterator(); iterator.hasNext();) {
				empObj = (User) iterator.next();
//				add data to array json use JSONObject getUser
				a.put(getUser(empObj.getId() + "", empObj.getUsername(), empObj.getPassword(), empObj.getEmail(),
						empObj.getRoleID()));
			}

//			// Get The User Details Whose Emp_Id is 1
//			emp_id = 1;
//			empObj = (User) sessionObj.get(User.class, new Integer(emp_id));
//			if (empObj != null) {
//				System.out.println("\nUser Record?= " + empObj.toString());
//			}

		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		return a;
	}
}