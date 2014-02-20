package com.example.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.Persistent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jsp.ah.searchDocumentBody_jsp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.bean.DepartmentBeanSpring;
import com.example.bean.EmployeeBeanString;
import com.example.bean.EmployeeLeave;
import com.example.bean.EmployeeLeaveStatus;
import com.example.bean.LoginBeanSpring;
import com.example.bean.MessageBean;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.example.controller.MailController;

@Controller
@RequestMapping("/EmployeeOperation")
public class AdminController {
	@RequestMapping(value = "/addAdminAccount.htm", method = RequestMethod.POST)
	public String addAdminAccountMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		boolean adminCreation = false;
		System.out.println("entered in add admin controller");

		String adminEmail = "chandan.gupta@a-cti.com";
		String adminPassword = "admin";
		String userType = "admin";

		LoginBeanSpring loginbeanspring = new LoginBeanSpring();
		loginbeanspring.setEmail(adminEmail);
		loginbeanspring.setPassword(adminPassword);
		loginbeanspring.setUserType(userType);
		// adding admnin to DataStore
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();

		try {

			pm.makePersistent(loginbeanspring); // Registration Table
			// pm.makePersistent(loginBean); // Login Table
			adminCreation = true;
		} catch (Exception e) {
			adminCreation = false;
			System.out.println("Exception : " + e);

		} finally {
			pm.close();
		}

		if (adminCreation) {
			request.setAttribute("name", adminEmail);
			request.setAttribute("message",
					" Administrator Account Created Successfully. Check your mail");
			return "message";
		} else {
			request.setAttribute("errorMessage",
					"Administrator Account Creation Failed");
			return "error";
		}

	}

	@RequestMapping(value = "/addEmployee.htm", method = RequestMethod.POST)
	public void addEmployeeRecordMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();

		PrintWriter out = response.getWriter();
		boolean empCreation = false;
		System.out.println("entered in servlet");

		String employeeName = request.getParameter("employeeName");
		String employeeTeam = request.getParameter("team");
		// String employeeTeam = "Developer";
		String reportingTo = request.getParameter("reportingTo");
		String emailAddress = request.getParameter("emailAddress");
		String contactNo = request.getParameter("contactNo");
		String employeeType = request.getParameter("employeeType");
		System.out.println("got all values from request object");
		String temp = employeeName + employeeTeam + reportingTo + emailAddress
				+ contactNo + employeeType;
		System.out.println("values are : " + temp);

		String cleaves = request.getParameter("cleaves");
		String pleaves = request.getParameter("pleaves");
		String sleaves = request.getParameter("sleaves");

		EmployeeBeanString employeeRegistration = new EmployeeBeanString();
		employeeRegistration.setEmail(emailAddress);
		employeeRegistration.setContact(contactNo);
		employeeRegistration.setName(employeeName);
		employeeRegistration.setTeam(employeeTeam);
		employeeRegistration.setReportingTo(reportingTo);
		employeeRegistration.setEmployeeType(employeeType);
		employeeRegistration.setCleaves(cleaves);
		employeeRegistration.setPleaves(pleaves);
		employeeRegistration.setSleaves(sleaves);

		boolean empExists = false;
		// Checking whether department already exists or not
		try {
			LoginBeanSpring searchEmp = pm.getObjectById(
					LoginBeanSpring.class, emailAddress);
			if (searchEmp.getEmail().equals(emailAddress)) {
				System.out.println("Emloyee already exists");
				empExists = true;
			} else {
				System.out.println("Emloyee doest not exists");
			}

		} catch (Exception e) {
			System.out.println("Caught in retrieve cntroller's exception " + e);
		}

		LoginBeanSpring loginbeanspring = new LoginBeanSpring();
		loginbeanspring.setEmail(emailAddress);

		// Auto generated password :

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String temp1 = dateFormat.format(cal.getTime()).substring(15, 16);
		String temp2 = dateFormat.format(cal.getTime()).substring(17, 19);
		String temp3 = dateFormat.format(cal.getTime()).substring(1, 2);
		String password = employeeName.substring(0, 3) + temp1 + temp2 + temp3;
		loginbeanspring.setPassword(password);
		loginbeanspring.setUserType("general");
		EmployeeLeaveStatus employeeLeaveStatus = new EmployeeLeaveStatus();
		employeeLeaveStatus.setEmail(emailAddress);
		employeeLeaveStatus.setBalanceCL(cleaves);
		employeeLeaveStatus.setBalancePL(pleaves);
		employeeLeaveStatus.setBalanceSL(sleaves);

		// to add new user to UserDetails , Login Table, emp Table Bean JDO
		if (!empExists) {
			try {

				pm.makePersistent(employeeRegistration); // Registration Table
				pm.makePersistent(loginbeanspring); // Login Table
				pm.makePersistent(employeeLeaveStatus); // Employee Current

				String subject;
				String body;

				MailController mailController = new MailController();

				// call mail method service
				// ` to Employee (account created)

				subject = " Account Created Successfully for Leave Module ";
				body = "Please Login" + "\n" + "Userid : " + emailAddress
						+ "\nPassword : " + password;
				System.out.println("Mail to employee : ");
				System.out.println("Reciever Name " + employeeName);
				System.out.println("Receiever Email address : " + emailAddress);
				mailController.mailMethod(employeeName, emailAddress, subject,
						body);

				// call mail method service
				// Mail to Manager (account created)

				HttpSession session = request.getSession();
				String managerEmail = "chandaneswar.sengupta@yahoo.in";
				System.out.println("Mail to Manager : ");
				System.out.println("Manager's Email is " + managerEmail);
				System.out.println("Manager's Name " + reportingTo);
				body = " 1 employee added with emp Id : " + emailAddress
						+ " in your department ";
				mailController.mailMethod(reportingTo, managerEmail, subject,
						body);

				// call mail method service
				// Mail to Admin (account created)
				String adminEmail = "chandan.gupta@a-cti.com";
				String userDetail = "ID : " + emailAddress + "\n" + "Name : "
						+ employeeName + "\nTeam : " + employeeTeam
						+ "\n Manager's Name : " + reportingTo
						+ "\nCasual Leaves : " + cleaves
						+ "\n Personal Leaves : " + pleaves
						+ "\nSick Leaves : " + sleaves;

				body = " You have successfully created an Employee :\n"
						+ userDetail;

				System.out.println("Mail to Admin : ");
				System.out.println("Admin Name : " + "Administrator");
				System.out.println("Admin Email address : " + adminEmail);
				mailController.mailMethod("Administrator", adminEmail, subject,
						body);
				empCreation = true;
			} catch (Exception e) {
				empCreation = false;
				System.out.println("Exception : " + e);
			} finally {
				pm.close();
			}
		}

		if (empCreation) {
			out.println("Employee with  " + emailAddress
					+ "  Created Successfully.");
		} else {
			if (empExists)
				out.println("Employee already exists");
			else
				out.println("Employee cannot be created. Retry");
		}
	}

	@RequestMapping(value = "/deleteSingleEmployee.htm", method = RequestMethod.POST)
	public void deleteSingleEmployee(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		PrintWriter out = response.getWriter();
		boolean deletedStatus = false;
		System.out.println(" entered in to be deleted controller");
		String emailDelete = request.getParameter("email");
		try {
			MailController mailController = new MailController();
			PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
					"transactions-optional").getPersistenceManager();
			// Deletion from Employee Master
			EmployeeBeanString searchUser1 = pm.getObjectById(
					EmployeeBeanString.class, emailDelete);

			// getting name
			String employeeName = searchUser1.getName();
			String emailAddress = searchUser1.getEmail();
			String team = searchUser1.getTeam();

			// getting Team's manager's Name / reportingTo

			DepartmentBeanSpring departmentBeanSpring = pm.getObjectById(
					DepartmentBeanSpring.class, team);
			String managerEmail = departmentBeanSpring.getManagerEmail();
			String reportingTo = departmentBeanSpring.getManagerName();

			pm.deletePersistent(searchUser1);
			// Deletion from Login Table
			LoginBeanSpring searchUser2 = pm.getObjectById(
					LoginBeanSpring.class, emailDelete);
			pm.deletePersistent(searchUser2);
			// Deletion from LeaveStatus
			EmployeeLeaveStatus searchUser3 = pm.getObjectById(
					EmployeeLeaveStatus.class, emailDelete);
			pm.deletePersistent(searchUser3);

			// Deletion of each user

			String subject = "", body = "";

			subject = " Deletion of your leaverequest system account ";
			body = " Hello,\n"
					+ "Your leaverequestsystem account has been deleted by Admin \n"
					+ "\n\n Regards, \n Admin";

			System.out.println("Mail to employee : ");
			System.out.println("Reciever Name " + employeeName);
			System.out.println("Receiever Email address : " + emailAddress);
			mailController
					.mailMethod(employeeName, emailAddress, subject, body);

			// Mail to Manager (account created)

			HttpSession session = request.getSession();
			System.out.println("Mail to Manager : ");
			System.out.println("Manager's Email is " + managerEmail);
			System.out.println("Manager's Name " + reportingTo);

			subject = " Deletion of " + emailAddress
					+ "'s leaverequest system account ";
			body = " Hello,\n" + " Account of " + emailAddress
					+ " has been deleted by Admin " + "\n\n Regards, \n Admin";
			mailController.mailMethod(reportingTo, managerEmail, subject, body);

			// call mail method service
			// Mail to Admin (account created)
			String adminEmail = "chandan.gupta@a-cti.com";
			body = " You have successfully deleted an Employee with email Address :\n"
					+ emailAddress;

			System.out.println("Mail to Admin : ");
			System.out.println("Admin Name : " + "Administrator");
			System.out.println("Admin Email address : " + adminEmail);
			mailController.mailMethod("Administrator", adminEmail, subject,
					body);

			deletedStatus = true;
		} catch (Exception e) {
			System.out.println("Exception : " + e);
			deletedStatus = false;
		}
		if (deletedStatus) {

			// Mail to Employee (account created)

			out.println("<h3>  Deleted Successfully </h3>");
		} else {
			out.println("<h3>  Error in Deleting </h3>");
		}
	}

	@RequestMapping(value = "/deleteEmployee.htm", method = RequestMethod.POST)
	public void deleteEmployee(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		PrintWriter out = response.getWriter();
		boolean deletedStatus = false;
		System.out.println(" entered in to be deleted controller");
		String emailDelete = request.getParameter("rowrecord");
		List<String> empList = Arrays.asList(emailDelete.split(","));
		for (String eachUserEmail : empList) {
			try {
				MailController mailController = new MailController();
				PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
						"transactions-optional").getPersistenceManager();
				// Deletion from Employee Master
				EmployeeBeanString searchUser1 = pm.getObjectById(
						EmployeeBeanString.class, eachUserEmail);

				// getting name
				String employeeName = searchUser1.getName();
				String emailAddress = searchUser1.getEmail();
				String team = searchUser1.getTeam();

				// getting Team's manager's Name / reportingTo

				DepartmentBeanSpring departmentBeanSpring = pm.getObjectById(
						DepartmentBeanSpring.class, team);
				String managerEmail = departmentBeanSpring.getManagerEmail();
				String reportingTo = departmentBeanSpring.getManagerName();

				pm.deletePersistent(searchUser1);
				// Deletion from Login Table
				LoginBeanSpring searchUser2 = pm.getObjectById(
						LoginBeanSpring.class, eachUserEmail);
				pm.deletePersistent(searchUser2);
				// Deletion from LeaveStatus
				EmployeeLeaveStatus searchUser3 = pm.getObjectById(
						EmployeeLeaveStatus.class, eachUserEmail);
				pm.deletePersistent(searchUser3);

				// Deletion of each user

				String subject = "", body = "";

				subject = " Deletion of your leaverequest system account ";
				body = " Hello,\n"
						+ "Your leaverequestsystem account has been deleted by Admin \n"
						+ "\n\n Regards, \n Admin";

				System.out.println("Mail to employee : ");
				System.out.println("Reciever Name " + employeeName);
				System.out.println("Receiever Email address : " + emailAddress);
				mailController.mailMethod(employeeName, emailAddress, subject,
						body);

				// Mail to Manager (account created)

				HttpSession session = request.getSession();
				System.out.println("Mail to Manager : ");
				System.out.println("Manager's Email is " + managerEmail);
				System.out.println("Manager's Name " + reportingTo);

				subject = " Deletion of " + emailAddress
						+ "'s leaverequest system account ";
				body = " Hello,\n" + " Account of " + emailAddress
						+ " has been deleted by Admin "
						+ "\n\n Regards, \n Admin";
				mailController.mailMethod(reportingTo, managerEmail, subject,
						body);

				// call mail method service
				// Mail to Admin (account created)
				String adminEmail = "chandan.gupta@a-cti.com";
				body = " You have successfully deleted an Employee with email Address :\n"
						+ emailAddress;

				System.out.println("Mail to Admin : ");
				System.out.println("Admin Name : " + "Administrator");
				System.out.println("Admin Email address : " + adminEmail);
				mailController.mailMethod("Administrator", adminEmail, subject,
						body);

				deletedStatus = true;
			} catch (Exception e) {
				System.out.println("Exception : " + e);
				deletedStatus = false;
			}
			if (deletedStatus) {

				// Mail to Employee (account created)

				out.println("<h3>  Deleted Successfully </h3>");
			} else {
				out.println("<h3>  Error in Deleting </h3>");
			}

			// PrintWriter out = response.getWriter();
			// boolean deletedStatus = false;
			// System.out.println(" entered in to be deleted controller");
			// String emailDelete = request.getParameter("rowrecord");
			// try {
			// PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
			// "transactions-optional").getPersistenceManager();
			// // Deletion from Employee Master
			// EmployeeBeanString searchUser1 = pm.getObjectById(
			// EmployeeBeanString.class, emailDelete);
			// pm.deletePersistent(searchUser1);
			// // Deletion from Login Table
			// LoginBeanSpring searchUser2 = pm.getObjectById(
			// LoginBeanSpring.class, emailDelete);
			// pm.deletePersistent(searchUser2);
			// // Deletion from LeaveStatus
			// EmployeeLeaveStatus searchUser3 = pm.getObjectById(
			// EmployeeLeaveStatus.class, emailDelete);
			// pm.deletePersistent(searchUser3);
			// deletedStatus = true;
			// } catch (Exception e) {
			// System.out.println("Exception : " + emailDelete);
			// deletedStatus = false;
			// }
			// if (deletedStatus)
			// out.println("<h3>  Deleted Successfully </h3>");
			// else
			// out.println("<h3>  Error in Deleting </h3>");
		}
	}

	@RequestMapping(value = "/editEmployee.htm", method = RequestMethod.POST)
	public String editEmployeeRecord(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		PrintWriter out = response.getWriter();
		// for retrieving all records from database
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();

		// For getting al the department name from database
		Query q = pm.newQuery(DepartmentBeanSpring.class);
		try {
			List<DepartmentBeanSpring> results = (List<DepartmentBeanSpring>) q
					.execute();
			int i = 0;
			if (!results.isEmpty()) {
				for (DepartmentBeanSpring p : results) {
					System.out.println("record found , i = " + i);
					i++;
					System.out.println(p.getDepartmentName());
				}
				request.setAttribute("allDepartments", results);

			} else {
				System.out.println("no results found");
			}
		} finally {
			q.closeAll();
		}

		// For getting the records of all the employees
		q = pm.newQuery(EmployeeBeanString.class);
		try {
			List<EmployeeBeanString> results = (List<EmployeeBeanString>) q
					.execute();
			int i = 0;
			if (!results.isEmpty()) {
				for (EmployeeBeanString p : results) {
					// Process result psys
					System.out.println("record found , i = " + i);
					i++;
					System.out.println(p.getEmail());
				}
				request.setAttribute("passedObject", results);
				return "editEmployee";
			} else {
				// Handle "no results" case
				System.out.println("no results found");
			}
		} finally {
			q.closeAll();
		}
		return "error";

	}

	@RequestMapping(value = "/RetrieveEmpDetails.htm", method = RequestMethod.POST)
	public void displayEmployeeRecord(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String emailAddress = request.getParameter("emailAddress");
		System.out.println("Email Address from getParamater is : "
				+ emailAddress);
		try {
			PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
					"transactions-optional").getPersistenceManager();
			EmployeeBeanString searchUser = pm.getObjectById(
					EmployeeBeanString.class, emailAddress);

			JSONObject jobj = new JSONObject();

			String name = searchUser.getName();
			String team = searchUser.getTeam();
			String reportingTo = searchUser.getReportingTo();
			emailAddress = searchUser.getEmail();
			String contactNo = searchUser.getContact();
			String empType = searchUser.getEmployeeType();
			String cleaves = searchUser.getCleaves();
			String pleaves = searchUser.getPleaves();
			String sleaves = searchUser.getSleaves();
			System.out.println(name + team + reportingTo + emailAddress
					+ contactNo + empType);

			jobj.put("name", name);
			jobj.put("team", team);
			jobj.put("reportingTo", reportingTo);
			jobj.put("emailAddress", emailAddress);
			jobj.put("contactNo", contactNo);
			jobj.put("empType", empType);

			jobj.put("cleaves", cleaves);
			jobj.put("pleaves", pleaves);
			jobj.put("sleaves", sleaves);

			System.out.println("all set, before redirecting json object");
			out.println(jobj);

		} catch (Exception e) {
			System.out.println("Caught in retrieve cntroller's exception " + e);

		}

	}

	@RequestMapping(value = "/saveEmployee.htm", method = RequestMethod.POST)
	public void saveEmployeeRecord(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		// System.out.println("<h2> Saving Controller</h2>");

		String name = "", empType = "";

		String employeeName = request.getParameter("employeeName");
		String employeeTeam = request.getParameter("team");
		String reportingTo = request.getParameter("reportingTo");
		String emailAddress = request.getParameter("emailAddress");
		String contactNo = request.getParameter("contactNo");
		String employeeType = request.getParameter("employeeType");
		String cleavesnew = request.getParameter("cleaves");
		String pleavesnew = request.getParameter("pleaves");
		String sleavesnew = request.getParameter("sleaves");

		// System.out.println("got all values from request object");
		String temp = employeeName + employeeTeam + reportingTo + emailAddress
				+ contactNo + employeeType;
		// System.out.println("in updating Controller : values are : " + temp);

		boolean updateStatus = false;
		// EmployeeBeanString employeeRegistration = new EmployeeBeanString();
		JSONObject jobj = new JSONObject();
		try {
			PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
					"transactions-optional").getPersistenceManager();
			EmployeeBeanString updateUser = pm.getObjectById(
					EmployeeBeanString.class, emailAddress);

			String cleavesold = updateUser.getCleaves();
			String pleavesold = updateUser.getPleaves();
			String sleavesold = updateUser.getSleaves();

			// Difference in all leaves
			int diffincleaves = Integer.parseInt(cleavesnew)
					- Integer.parseInt(cleavesold);
			int incinpleaves = Integer.parseInt(pleavesnew)
					- Integer.parseInt(pleavesold);
			int incinsleaves = Integer.parseInt(sleavesnew)
					- Integer.parseInt(sleavesold);

			// to save the EmployeeBean Spring Table (Main Customer Table)
			updateUser.setName(employeeName);
			updateUser.setTeam(employeeTeam);
			updateUser.setReportingTo(reportingTo);
			updateUser.setEmail(emailAddress);
			updateUser.setContact(contactNo);
			updateUser.setEmployeeType(employeeType);
			updateUser.setCleaves(cleavesnew);
			updateUser.setPleaves(pleavesnew);
			updateUser.setSleaves(sleavesnew);
			pm.makePersistent(updateUser);
			// System.out.println("1");
			updateStatus = true;

			emailAddress = updateUser.getEmail();
			name = updateUser.getName();
			employeeTeam = updateUser.getTeam();
			reportingTo = updateUser.getReportingTo();
			contactNo = updateUser.getContact();
			empType = updateUser.getEmployeeType();
			cleavesnew = updateUser.getCleaves();
			pleavesnew = updateUser.getPleaves();
			sleavesnew = updateUser.getSleaves();

			// System.out.println(name + team + reportingTo + emailAddress
			// + contactNo + empType);

			// To save the current leave status table

			EmployeeLeaveStatus updateCurrentStatus = pm.getObjectById(
					EmployeeLeaveStatus.class, emailAddress);
			String a, b, c;

			if ((Integer.parseInt(updateCurrentStatus.getBalanceCL()) + diffincleaves) < 0)
				a = "0";
			else
				a = String.valueOf(Integer.parseInt(updateCurrentStatus
						.getBalanceCL()) + diffincleaves);

			if ((Integer.parseInt(updateCurrentStatus.getBalancePL()) + incinpleaves) < 0)
				b = "0";
			else
				b = String.valueOf(Integer.parseInt(updateCurrentStatus
						.getBalancePL()) + incinpleaves);

			if ((Integer.parseInt(updateCurrentStatus.getBalanceSL()) + incinsleaves) < 0)
				c = "0";
			else
				c = String.valueOf(Integer.parseInt(updateCurrentStatus
						.getBalanceSL()) + incinsleaves);

			System.out.println("");

			updateCurrentStatus.setBalanceCL(a);
			updateCurrentStatus.setBalancePL(b);
			updateCurrentStatus.setBalanceSL(c);
			pm.makePersistent(updateCurrentStatus);

			jobj.put("name", name);
			jobj.put("team", employeeTeam);
			jobj.put("reportingTo", reportingTo);
			jobj.put("emailAddress", emailAddress);
			jobj.put("contactNo", contactNo);
			jobj.put("empType", empType);

			jobj.put("cleaves", cleavesnew);
			jobj.put("pleaves", pleavesnew);
			jobj.put("sleaves", sleavesnew);

			// System.out.println("all set, before redirecting json object");

		} catch (Exception e) {
			// System.out.println("2");
			// System.out.println("Exception while updfating is : " + e);
			updateStatus = false;

		}
		System.out.println("3");
		if (updateStatus) {

			String subject;
			String body;

			MailController mailController = new MailController();

			// Mail to Employee (account created)

			subject = " Your account has been modified by Admin ";
			body = "Your modified detail of account is as follows \n"
					+ "Name : " + name + "Email : " + emailAddress + "Team : "
					+ employeeTeam + "Reporting To : " + reportingTo
					+ "Contact No : " + contactNo + "Employee Type : "
					+ empType + "Casual Leaves : " + cleavesnew
					+ "Personal Leaves : " + pleavesnew + "Sick Leaves : "
					+ sleavesnew;
			//
			// System.out.println("Mail to employee : ");
			// System.out.println("Reciever Name " + employeeName);
			// System.out.println("Receiever Email address : " + emailAddress);
			mailController
					.mailMethod(employeeName, emailAddress, subject, body);

			// Mail to Manager (account created)

			HttpSession session = request.getSession();
			String managerEmail = "chandaneswar.sengupta@yahoo.in";
			System.out.println("Mail to Manager : ");
			System.out.println("Manager's Email is " + managerEmail);
			System.out.println("Manager's Name " + reportingTo);

			subject = " Account of " + emailAddress
					+ " has been modified by Admin ";
			body = "Modified detail of account is as follows \n" + "Name : "
					+ name + "Email : " + emailAddress + "Team : "
					+ employeeTeam + "Reporting To : " + reportingTo
					+ "Contact No : " + contactNo + "Employee Type : "
					+ empType + "Casual Leaves : " + cleavesnew
					+ "Personal Leaves : " + pleavesnew + "Sick Leaves : "
					+ sleavesnew;
			mailController.mailMethod(reportingTo, managerEmail, subject, body);

			// call mail method service
			// Mail to Admin (account created)
			String adminEmail = "chandan.gupta@a-cti.com";
			String userDetail = "Name : " + name + "Email : " + emailAddress
					+ "Team : " + employeeTeam + "Reporting To : "
					+ reportingTo + "Contact No : " + contactNo
					+ "Employee Type : " + empType + "Casual Leaves : "
					+ cleavesnew + "Personal Leaves : " + pleavesnew
					+ "Sick Leaves : " + sleavesnew;
			body = " You have successfully modified an Employee :\n"
					+ userDetail;

			// System.out.println("Mail to Admin : ");
			// System.out.println("Admin Name : " + "Administrator");
			// System.out.println("Admin Email address : " + adminEmail);
			mailController.mailMethod("Administrator", adminEmail, subject,
					body);
			out.println(jobj);
		} else {
			// System.out.println("cannot uodate");
			out.println("Cannot Update Record  of " + emailAddress);

		}
		// System.out.println("4");

	}

	@RequestMapping(value = "/viewAllEmployeeDelete.htm", method = RequestMethod.POST)
	public String displayEmployeeRecord1(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		// for retrieving all records from database
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();
		Query q = pm.newQuery(EmployeeBeanString.class);
		try {
			List<EmployeeBeanString> results = (List<EmployeeBeanString>) q
					.execute();
			int i = 0;
			if (!results.isEmpty()) {
				for (EmployeeBeanString p : results) {
					// Process result psys
					// System.out.println("record found , i = " + i);
					i++;
					// System.out.println(p.getEmail());

				}
				request.setAttribute("passedObject", results);
				return "deleteEmployee";
			} else {
				// Handle "no results" case
				// System.out.println("no results found");
			}
		} finally {
			q.closeAll();
		}
		return "error";
	}

	@RequestMapping(value = "/viewAllEmployeeOnly.htm", method = RequestMethod.POST)
	public String displayEmployeeRecord2(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		// for retrieving all records from database
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();
		Query q = pm.newQuery(EmployeeBeanString.class);
		try {
			List<EmployeeBeanString> results = (List<EmployeeBeanString>) q
					.execute();
			int i = 0;
			if (!results.isEmpty()) {
				for (EmployeeBeanString p : results) {
					// Process result psys
					System.out.println("record found , i = " + i);
					i++;
					System.out.println(p.getEmail());

				}
				request.setAttribute("passedObject", results);
				return "viewAllEmployee";
			} else {
				// Handle "no results" case
				System.out.println("no results found");
			}
		} finally {
			q.closeAll();
		}
		return "error";
	}

	@RequestMapping(value = "/retrieveManagerName.htm", method = RequestMethod.GET)
	public void retrieveManagerNameMethod(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		// out.println("<h2> View Page</h2>");
		String deptName = request.getParameter("team");
		// for retrieving the Manager Name
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();
		try {
			DepartmentBeanSpring searchdept = pm.getObjectById(
					DepartmentBeanSpring.class, deptName);

			out.println(searchdept.getManagerName());
		} catch (Exception e) {
			System.out.println("Caught in Retrieving Manager Name exception : "
					+ e);
		}

	}

	@RequestMapping(value = "/retrieveAllDepartmentName.htm", method = RequestMethod.GET)
	public String retrieveAllDepartmentNameMethod(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();
		Query q = pm.newQuery(DepartmentBeanSpring.class);
		try {
			List<DepartmentBeanSpring> results = (List<DepartmentBeanSpring>) q
					.execute();
			int i = 0;
			if (!results.isEmpty()) {
				for (DepartmentBeanSpring p : results) {
					System.out.println("record found , i = " + i);
					i++;
					System.out.println(p.getDepartmentName());
				}
				request.setAttribute("passedObject", results);

			} else {
				System.out.println("no results found");
			}
		} finally {
			q.closeAll();
		}
		return "addEmployee";
	}

	@RequestMapping(value = "/UpdatePassword.htm", method = RequestMethod.GET)
	public void UpdatePasswordMethod(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();
		String emailAddress = request.getParameter("emailAddress");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword1 = request.getParameter("newPassword1");
		String temp = emailAddress + oldPassword + newPassword1;
		boolean updateStatus = false;
		boolean oldPswdValidationSuccess = false;
		try {
			LoginBeanSpring updateUser = pm.getObjectById(
					LoginBeanSpring.class, emailAddress);
			if (oldPassword.equals(updateUser.getPassword())) {
				updateUser.setPassword(newPassword1);
				pm.makePersistent(updateUser);
				updateStatus = true;
				oldPswdValidationSuccess = true;
			}

		} catch (Exception e) {
		}
		if (updateStatus) {
			MailController mailController = new MailController();

			HttpSession session = request.getSession();
			String employeeName = (String) session.getAttribute("sessionName");

			// to Employee (Password change acknowledgment)

			String subject = " Password change successfull ";
			String body = "You have successfully changed your pasword for leaverequest account :  "
					+ emailAddress + "\nYour new Password : " + newPassword1;
			mailController
					.mailMethod(employeeName, emailAddress, subject, body);

			out.println("Password Updated successfully");
		} else {
			if (oldPswdValidationSuccess == false)
				out.println("Old Password Verification Failure");
			else
				out.println("Password updation failed");
		}
		// System.out.println("4");
	}

	@RequestMapping(value = "/ResetPassword.htm", method = RequestMethod.GET)
	public void ResetPasswordMethod(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();
		String emailAddress = request.getParameter("emailAddress");
		String newPassword = request.getParameter("newPassword");
		String temp = emailAddress + newPassword;
		boolean updateStatus = false;
		try {
			LoginBeanSpring updateUser = pm.getObjectById(
					LoginBeanSpring.class, emailAddress);
			updateUser.setPassword(newPassword);
			pm.makePersistent(updateUser);
			updateStatus = true;
		} catch (Exception e) {
		}
		if (updateStatus) {
			MailController mailController = new MailController();

			// HttpSession session = request.getSession();
			// String employeeName = (String)
			// session.getAttribute("sessionName");

			// to Employee (Password change acknowledgment)

			String subject = " Password Reset successfull ";
			String body = "You have successfully reset your pasword for leaverequest account :  "
					+ emailAddress + "\nYour new Password : " + newPassword;
			mailController.mailMethod("User", emailAddress, subject, body);

			out.println("Password Updated successfully");
		} else {
			out.println("Password updation failed");

		}
		// System.out.println("4");
	}
	
	@RequestMapping(value = "/sendMessageToAdmin.htm", method = RequestMethod.POST)
	public void sendMessageToAdminMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();

		PrintWriter out = response.getWriter();
		boolean messageSentStatus = false;
		System.out.println("entered in servlet");

		String messageSubject = request.getParameter("messageSubject");
		String messageBody = request.getParameter("messageBody");
		String emailAddress = request.getParameter("emailAddress");
		String status = "unread";
		String reciever ="";
		HttpSession session = request.getSession();
		String managerEmail = (String) session.getAttribute("sessionEmail");

		Query q = pm.newQuery(LoginBeanSpring.class);
		q.setFilter("userType == uType");
		q.declareParameters("String uType");

		// System.out.println("Befor entering try of checking incoming requests");
		try {
			// System.out.println("in try blcokk, of retrieving the leave details controller");
			List<LoginBeanSpring> results = (List<LoginBeanSpring>) q.execute("admin");
			int i = 0;
			if (!results.isEmpty()) {
				for (LoginBeanSpring p : results) {
					// Process result psys
					 System.out.println("record found , i = " + i);
					i++;
					 System.out.println(p.getUserType());
					 
					 if(p.getUserType().equals("admin"))
					 {
						 System.out.println("1");
						 reciever = p.getEmail();
						 System.out.println("2");
						 break;
					 }
						 
					System.out.println("Reciever's name : " + reciever);
				}
			} else {
				System.out.println("no results found");
			}
		} finally {
			q.closeAll();
		}

		String sender =emailAddress;
		
		
		
//		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try
		{
			
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		
		String dateToday = dateFormat.format(cal.getTime());
		
		String temp1 = dateFormat.format(cal.getTime()).substring(15, 16);
		
		
		String temp2 = dateFormat.format(cal.getTime()).substring(17, 19);
		String temp3 = dateFormat.format(cal.getTime()).substring(1, 2);
		// System.out.println("1");
		String msgId;
		msgId = sender.substring(0, 3) + temp1 + temp2 + temp3;
				

		MessageBean newMessage = new MessageBean();
		newMessage.setSender(sender);
		
		
		newMessage.setMessageSubject(messageSubject);
		newMessage.setMessageBody(messageBody);
		newMessage.setMsgId(msgId);
		newMessage.setDate(dateToday);
		newMessage.setStatus(status);
		newMessage.setReciever(reciever);
		
		newMessage.setSender(emailAddress);
		pm.makePersistent(newMessage);
		messageSentStatus =true;
		}
		catch(Exception e)
		{
			System.out.println("exception : " +e);
			messageSentStatus =false;
		}
		
		if(	messageSentStatus)
			out.println("true");
		else
			out.println("false");
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
