package com.example.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.mail.Session;
import javax.servlet.http.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.bean.DepartmentBeanSpring;
import com.example.bean.EmployeeBeanString;
import com.example.bean.EmployeeLeaveStatus;
import com.example.bean.LoginBeanSpring;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@Controller
@RequestMapping("/EntryExit")
public class LoginController {
	private static PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");
	private static PersistenceManager pm = PMF.getPersistenceManager();
	

	@RequestMapping(value = "/logout.htm", method = RequestMethod.POST)
	public String exitMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession x = request.getSession();
		x.invalidate();
		return "logOut";
			
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/login.htm", method = RequestMethod.POST)
	public String entryMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String loginEmployeeType = request.getParameter("loginEmployeeType");
		System.out.println("email from jsp : " + email);
		System.out.println("password from jsp is : " + password);
		System.out.println("login employee type from jsp " + loginEmployeeType);

		// loginobject.setPassword(password);
		boolean userExists = false;
		try {

			pm = PMF.getPersistenceManager();
			LoginBeanSpring searchUser = pm.getObjectById(
					LoginBeanSpring.class, email);
			System.out.println("password from database : "
					+ (searchUser.getPassword()));
			System.out.println("logintype from database : "
					+ (searchUser.getUserType()));
			if (((searchUser.getPassword()).equals(password))
					&& (searchUser.getUserType().equals(loginEmployeeType))) {
				userExists = true;
				System.out.println("matched password");
			} else {
				userExists = false;
			}
		} catch (Exception e) {
			userExists = false;
			System.out
					.println("Exception in searching of user in LoginService class : "
							+ e);
		} finally {
			pm = PMF.getPersistenceManager();
		}

		// System.out.println("Inserted successfully");
		String page = null;
		if (userExists) {
			System.out.println("1");

			HttpSession session = request.getSession();
			session.setAttribute("sessionEmail", email);
			session.setAttribute("sessionUserType", loginEmployeeType);
			if (loginEmployeeType.equals("admin")) {
				return "adminHomePage";
			} else { // Getting and setting seesion of all user General Employee
						// Details
				System.out.println("2");
				// PersistenceManager pm =
				// JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
				System.out.println("p");
				try {
					// For retirveing from Employee table
					System.out.println("q" + email);
					pm = PMF.getPersistenceManager();
					EmployeeBeanString searchUser = pm.getObjectById(
							EmployeeBeanString.class, email);
					System.out.println("test1");
					String name = searchUser.getName();
					String team = searchUser.getTeam();
					email = searchUser.getEmail();
					String contactNo = searchUser.getContact();
					String empType = searchUser.getEmployeeType();
					System.out.println("test2");
					String cleaves = searchUser.getCleaves();
					System.out.println("test3");
					String pleaves = searchUser.getPleaves();
					System.out.println("test4");
					String sleaves = searchUser.getSleaves();
					System.out
							.println("Details of employee in controller , will be set into session : "
									+ name + team + email + contactNo + empType);

					session.setAttribute("sessionTeam", team);
					session.setAttribute("sessionEmail", email);
					session.setAttribute("sessionName", name);
					session.setAttribute("sessionContactNo", contactNo);

					session.setAttribute("sessionTotalcLeaves", cleaves);
					session.setAttribute("sessionTotalpLeaves", pleaves);
					session.setAttribute("sessionTotalsLeaves", sleaves);

					session.setAttribute("sessioneType", empType);
					System.out.println("3");

					// For retirveing from Department table -the PIC/Manager's
					// name
					DepartmentBeanSpring searchReportingTo = pm.getObjectById(
							DepartmentBeanSpring.class, team);
					String managerName = searchReportingTo.getManagerName();
					String managerEmail = searchReportingTo.getManagerEmail();

					session.setAttribute("sessionManagerName", managerName);
					session.setAttribute("sessionManagerEmail", managerEmail);

				} catch (Exception e) {
					System.out
							.println("Caught in retrieve cntroller's exception "
									+ e);

				} finally {
					pm.close();
				}

				// For retrieving details for current Leave Status
				try {
					// For retirveing from Employee table
					System.out.println("Leave Status retrieval");
					pm = PMF.getPersistenceManager();
					EmployeeLeaveStatus searchUser = pm.getObjectById(
							EmployeeLeaveStatus.class, email);

					System.out.println("test11");
					String cbalanceleaves = searchUser.getBalanceCL();
					String pbalanceleaves = searchUser.getBalancePL();
					String sbalanceleaves = searchUser.getBalanceSL();
					System.out.println("test22");
					System.out
							.println("Details of balance Leaves in controller , will be set into session : "
									+ cbalanceleaves
									+ pbalanceleaves
									+ sbalanceleaves);

					session.setAttribute("sessionBalancecLeaves",
							cbalanceleaves);
					session.setAttribute("sessionBalancepLeaves",
							pbalanceleaves);
					session.setAttribute("sessionBalancesLeaves",
							sbalanceleaves);

				} catch (Exception e) {
					System.out
							.println("Caught in retrieve cntroller's exception "
									+ e);

				} finally {
					pm.close();
				}
				System.out.println("4");
				request.setAttribute("statusFromServer",
						"true");
				return "empHomePage";

			}
		} else {
			 System.out.println("5");
			 
				request.setAttribute("statusFromServer",
						"false");
			 return "logOut";
//			out.println("Username/Password mismatched");
		}

	}

}
