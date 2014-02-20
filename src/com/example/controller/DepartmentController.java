package com.example.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.bean.DepartmentBeanSpring;
import com.example.bean.EmployeeBeanString;
import com.example.bean.EmployeeLeaveStatus;
import com.example.bean.LoginBeanSpring;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.example.controller.MailController;

@Controller
@RequestMapping("/DepartmentOperation")
public class DepartmentController {
	@RequestMapping(value = "/add.htm", method = RequestMethod.POST)
	public String redirectToAddJSP(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return "addDepartment";
	}

	@RequestMapping(value = "/addDepartment.htm", method = RequestMethod.POST)
	public void addDepartmentMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();
		PrintWriter out = response.getWriter();
		boolean depCreation = false;

		String departmentName = request.getParameter("departmentName");
		String managerName = request.getParameter("managerName");
		String managerEmail = request.getParameter("managerEmail");
		DepartmentBeanSpring departmentBeanSpring = new DepartmentBeanSpring();
		departmentBeanSpring.setDepartmentName(departmentName);
		departmentBeanSpring.setManagerName(managerName);
		departmentBeanSpring.setManagerEmail(managerEmail);

		boolean deptExists = false;
		// Checking whether department already exists or not
		try {
			DepartmentBeanSpring searchDept = pm.getObjectById(
					DepartmentBeanSpring.class, departmentName);
			if (searchDept.getDepartmentName().equals(departmentName))
				deptExists = true;
			else
				deptExists = false;
		} catch (Exception e) {
			System.out.println("Caught in retrieve cntroller's exception " + e);

		}
		// adding new dapartments to the database
		if (!deptExists) {
			try {
				pm.makePersistent(departmentBeanSpring); // Department Table
				depCreation = true;
			} catch (Exception e) {
				depCreation = false;

			} finally {
				pm.close();
			}
		}
		if (depCreation) {

			String subject;
			String body;

			MailController mailController = new MailController();

			// call mail method service
			// Mail to Manager (account created)

			HttpSession session = request.getSession();

			System.out.println("Mail to Manager : ");
			System.out.println("Manager's Email is " + managerEmail);
			subject = "Department Created Successfully";
			body = "You are the PIC for the new Department " + departmentName
					+ " that has been created.";

			mailController.mailMethod(managerName, managerEmail, subject, body);

			// call mail method service
			// Mail to Admin (account created)
			String adminEmail = "chandan.gupta@a-cti.com";
			body = " You have successfully created the following : \n Department Name : "
					+ departmentName
					+ "\nPIC : "
					+ managerName
					+ "\n Email Address : " + managerEmail;

			System.out.println("Mail to Admin : ");
			System.out.println("Admin Name : " + "Administrator");
			System.out.println("Admin Email address : " + adminEmail);
			mailController.mailMethod("Administrator", adminEmail, subject,
					body);
			out.println("Department " + departmentName
					+ "  Created Successfully.");
		} else {
			if (deptExists)
				out.println("Department already exists");
			else
				out.println("Department cannot be created");
		}

	}

	@RequestMapping(value = "/editDepartment.htm", method = RequestMethod.POST)
	public String editDepartmentMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();

		out.println("<h2> View Page</h2>");

		// for retrieving all records from database
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();
		Query q = pm.newQuery(DepartmentBeanSpring.class);
		try {
			List<DepartmentBeanSpring> results = (List<DepartmentBeanSpring>) q
					.execute();
			int i = 0;
			if (!results.isEmpty()) {
				for (DepartmentBeanSpring p : results) {
					// Process result psys
					i++;

				}
				request.setAttribute("passedObject", results);
				return "editDepartment";
			} else {
				// Handle "no results" case
				System.out.println("no results found");
			}
		} finally {
			q.closeAll();
		}
		return "error";
	}

	@RequestMapping(value = "/RetrieveDepartmentDetails.htm", method = RequestMethod.POST)
	public String retrieveDepartmentDetailsMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();

		String departmentName = request.getParameter("departmentName");
		try {
			PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
					"transactions-optional").getPersistenceManager();
			DepartmentBeanSpring searchDept = pm.getObjectById(
					DepartmentBeanSpring.class, departmentName);

			JSONObject jobj = new JSONObject();

			String managerName = searchDept.getManagerName();
			String managerEmail = searchDept.getManagerEmail();

			jobj.put("managerName", managerName);
			jobj.put("managerEmail", managerEmail);
			jobj.put("departmentName", departmentName);
			out.println(jobj);

		} catch (Exception e) {
			System.out.println("Caught in retrieve cntroller's exception " + e);

		}
		return null;
	}

	@RequestMapping(value = "/updateDepartment.htm", method = RequestMethod.POST)
	public void updateDepartmentMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String departmentName = request.getParameter("departmentName");
		String managerName = request.getParameter("managerName");
		String managerEmail = request.getParameter("managerEmail");

		String temp = departmentName + managerName + managerEmail;
		boolean updateStatus = false;
		// EmployeeBeanString employeeRegistration = new EmployeeBeanString();
		JSONObject jobj = new JSONObject();
		try {
			PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
					"transactions-optional").getPersistenceManager();
			DepartmentBeanSpring updateDepartmentObject = pm.getObjectById(
					DepartmentBeanSpring.class, departmentName);
			updateDepartmentObject.setManagerName(managerName);
			updateDepartmentObject.setManagerEmail(managerEmail);

			jobj.put("departmentName", departmentName);
			jobj.put("managerName", managerName);
			jobj.put("managerEmail", managerEmail);

			updateStatus = true;
		} catch (Exception e) {
			System.out.println("Exception while updating Department is : " + e);
			updateStatus = false;

		}
		if (updateStatus) {
			MailController mailController = new MailController();
			String subject = "", body = "";
			// call mail method service
			// Mail to Manager (account created)

			HttpSession session = request.getSession();

			System.out.println("Mail to Manager : ");
			System.out.println("Manager's Email is " + managerEmail);
			subject = "Department " + departmentName + " Modified Successfully";
			body = " The department "
					+ departmentName
					+ " has been modified by the Admin. \n Updated detail are as follows :\n Department Name : "
					+ departmentName + "\nPIC : " + managerName
					+ "\n Email Address : " + managerEmail;
			;

			mailController.mailMethod(managerName, managerEmail, subject, body);

			// call mail method service
			// Mail to Admin (account created)
			String adminEmail = "chandan.gupta@a-cti.com";
			body = " You have successfully modified the department "
					+ departmentName
					+ "\n Updated detail are as follows  : \n Department Name : "
					+ departmentName + "\nPIC : " + managerName
					+ "\n Email Address : " + managerEmail;

			System.out.println("Mail to Admin : ");
			System.out.println("Admin Name : " + "Administrator");
			System.out.println("Admin Email address : " + adminEmail);
			mailController.mailMethod("Administrator", adminEmail, subject,
					body);
			out.println(jobj);

		} else {
			out.println("Cannot update " + departmentName);
		}
	}

	@RequestMapping(value = "/RetrieveDepartmentName.htm", method = RequestMethod.POST)
	public void retrieveDepartmentName(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<h2> View Page</h2>");
		// for retrieving all records from database
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();
		Query q = pm.newQuery(DepartmentBeanSpring.class);
		try {
			List<DepartmentBeanSpring> results = (List<DepartmentBeanSpring>) q
					.execute();
			int i = 0;
			if (!results.isEmpty()) {
				for (DepartmentBeanSpring p : results) {
					i++;

				}
				out.println("passedObject");
				// return "homepageAdmin";
			} else {
				// Handle "no results" case
				System.out.println("no results found");
			}
		} finally {
			q.closeAll();
		}
		// return "error";
	}

	@RequestMapping(value = "/deleteDepartment.htm", method = RequestMethod.POST)
	public void deleteDepartmentMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		boolean deletedStatus = false;
		String departmentName = request.getParameter("deprecord");
		String managerName = "";
		String managerEmail = "";

		List<String> deptList = Arrays.asList(departmentName.split(","));
		for (String s : deptList) {
			try {
				System.out.println("recieved : " + s);
				PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
						"transactions-optional").getPersistenceManager();
				// Deletion from DepartmentBEanSpring Table
				DepartmentBeanSpring departmentBeanSpring = pm.getObjectById(
						DepartmentBeanSpring.class, s);
				managerName = departmentBeanSpring.getManagerName();
				managerEmail = departmentBeanSpring.getManagerEmail();

				pm.deletePersistent(departmentBeanSpring);
				deletedStatus = true;
			} catch (Exception e) {
				deletedStatus = false;
			}
			if (deletedStatus) {
				MailController mailController = new MailController();
				String subject = "", body = "";
				// call mail method service
				// Mail to Manager (account created)

				HttpSession session = request.getSession();

				System.out.println("Mail to Manager : ");
				System.out.println("Manager's Email is " + managerEmail);
				subject = "Department Deleted Successfully";
				body = " The department " + departmentName
						+ " has been deleted by the Admin";

				mailController.mailMethod(managerName, managerEmail, subject,
						body);

				// call mail method service
				// Mail to Admin (account created)
				String adminEmail = "chandan.gupta@a-cti.com";
				body = " You have successfully deleted the following : \n Department Name : "
						+ departmentName
						+ "\nPIC : "
						+ managerName
						+ "\n Email Address : " + managerEmail;

				System.out.println("Mail to Admin : ");
				System.out.println("Admin Name : " + "Administrator");
				System.out.println("Admin Email address : " + adminEmail);
				mailController.mailMethod("Administrator", adminEmail, subject,
						body);
				out.println("  Deleted Successfully ");
			} else {
				out.println("Error in Deleting ");
			}

		}
	}

	@RequestMapping(value = "/deleteSingleDepartment.htm", method = RequestMethod.POST)
	public void deleteSingleDepartmentMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		boolean deletedStatus = false;
		String departmentName = request.getParameter("departmentName");
		String managerName = "";
		String managerEmail = "";

		try {
			System.out.println("recieved : " + departmentName);
			PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
					"transactions-optional").getPersistenceManager();
			// Deletion from DepartmentBEanSpring Table
			DepartmentBeanSpring departmentBeanSpring = pm.getObjectById(
					DepartmentBeanSpring.class, departmentName);
			managerName = departmentBeanSpring.getManagerName();
			managerEmail = departmentBeanSpring.getManagerEmail();

			pm.deletePersistent(departmentBeanSpring);
			deletedStatus = true;
		} catch (Exception e) {
			deletedStatus = false;
		}
		if (deletedStatus) {
			MailController mailController = new MailController();
			String subject = "", body = "";
			// call mail method service
			// Mail to Manager (account created)

			HttpSession session = request.getSession();

			System.out.println("Mail to Manager : ");
			System.out.println("Manager's Email is " + managerEmail);
			subject = "Department Deleted Successfully";
			body = " The department " + departmentName
					+ " has been deleted by the Admin";

			mailController.mailMethod(managerName, managerEmail, subject, body);

			// call mail method service
			// Mail to Admin (account created)
			String adminEmail = "chandan.gupta@a-cti.com";
			body = " You have successfully deleted the following : \n Department Name : "
					+ departmentName
					+ "\nPIC : "
					+ managerName
					+ "\n Email Address : " + managerEmail;

			System.out.println("Mail to Admin : ");
			System.out.println("Admin Name : " + "Administrator");
			System.out.println("Admin Email address : " + adminEmail);
			mailController.mailMethod("Administrator", adminEmail, subject,
					body);
			out.println("  Deleted Successfully ");
		} else {
			out.println("Error in Deleting ");
		}

	}

	
	@RequestMapping(value = "/viewAllDepartmentDelete.htm", method = RequestMethod.POST)
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String viewDepartmentDeleteMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		System.out
				.println("to retireve all department fro delete method entry");

		out.println("<h2> View Page</h2>");

		// for retrieving all records from database
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();
		Query q = pm.newQuery(DepartmentBeanSpring.class);
		try {
			List<DepartmentBeanSpring> results = (List<DepartmentBeanSpring>) q
					.execute();
			int i = 0;
			if (!results.isEmpty()) {
				for (DepartmentBeanSpring p : results) {
					i++;
				}
				request.setAttribute("passedObject", results);
				return "deleteDepartment";
			} else {
				// Handle "no results" case
			}
		} finally {
			q.closeAll();
		}
		return "error";

	}

	@RequestMapping(value = "/viewAllDepartmentOnly.htm", method = RequestMethod.POST)
	public String viewDepartmentOnlyMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		out.println("<h2> View Page</h2>");
		// for retrieving all records from database
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();
		Query q = pm.newQuery(DepartmentBeanSpring.class);
		try {
			List<DepartmentBeanSpring> results = (List<DepartmentBeanSpring>) q
					.execute();
			int i = 0;
			if (!results.isEmpty()) {
				for (DepartmentBeanSpring p : results) {
					i++;
				}
				request.setAttribute("passedObject", results);
				return "viewAllDepartment";
			} else {
				// Handle "no results" case
			}
		} finally {
			q.closeAll();
		}
		return "error";

	}

}
