package com.example.controller;

import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.IOException;
import java.io.PrintWriter;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.bean.EmployeeBeanString;
import com.example.bean.EmployeeLeave;
import com.example.bean.EmployeeLeaveStatus;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@Controller
@RequestMapping("/LeaveOperation")
public class LeaveRequestController {

	private static PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");
	private static PersistenceManager pm = PMF.getPersistenceManager();

	@RequestMapping(value = "/CheckLeaveStatus.htm", method = RequestMethod.GET)
	public void checkLeaveStatusMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String emailAddress = request.getParameter("emailAddress");
		System.out.println("Email Address from getParamater is : "
				+ emailAddress);
		try {
			PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
					"transactions-optional").getPersistenceManager();
			EmployeeLeaveStatus searchUser = pm.getObjectById(
					EmployeeLeaveStatus.class, emailAddress);

			JSONObject jobj = new JSONObject();

			String cleaves = searchUser.getBalanceCL();
			String pleaves = searchUser.getBalancePL();
			String sleaves = searchUser.getBalanceSL();

			// System.out.println("casual : " + cleaves + "personal : " +
			// pleaves
			// + " Sick : " + sleaves + "Email : " + emailAddress);

			jobj.put("cleaves", cleaves);
			jobj.put("pleaves", pleaves);
			jobj.put("sleaves", sleaves);

			// System.out.println("all set, before redirecting json object");
			out.println(jobj);

		} catch (Exception e) {
			System.out.println("Caught in retrieve cntroller's exception " + e);

		}
	}

	@RequestMapping(value = "/checkforIncomingRequests.htm", method = RequestMethod.GET)
	public void checkforIncomingRequestsMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();
		PrintWriter out = response.getWriter();
		System.out.println("checkforIncomingRequests");
		HttpSession session = request.getSession();
		String managerEmail = (String) session.getAttribute("sessionEmail");

		Query q = pm.newQuery(EmployeeLeave.class);
		q.setFilter("picEmail == managerEmail");
		q.declareParameters("String managerEmail");

		// System.out.println("Befor entering try of checking incoming requests whetehere present ");
		try {
			// System.out.println("in try blcokk, of retrieving the leave details controller");
			List<EmployeeLeave> results = (List<EmployeeLeave>) q
					.execute(managerEmail);
			int i = 0;
			if (!results.isEmpty()) {
				for (EmployeeLeave p : results) {
					// Process result psys
					System.out.println("record found , i = " + i);
					i++;
					System.out.println(p.getKey());

				}
				request.setAttribute("passedObject", results);
				out.println("true");
				System.out.println("yes");
			} else {
				// Handle "no results" case
				out.println("true");
				System.out.println("no");
			}
		} finally {
			q.closeAll();
		}

	}

	@RequestMapping(value = "/RequestForLeave.htm", method = RequestMethod.GET)
	public void leaveRequestfromemptoManagerMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		// String email = request.getParameter(arg0);

		// var queryString = "todayDate=" + todayDate + "&startingDate=" +
		// startingDate + "&endingDate=" + endingDate + "&daysOfLeaves=" +
		// daysOfLeaves +
		// "&leaveType=" + leaveType + "&comment=" + comment;
		//
		//
		HttpSession x = request.getSession();
		String email = (String) x.getAttribute("sessionEmail");
		String appliedDate = request.getParameter("todayDate");
		String applicant = (String) x.getAttribute("sessionName");
		String startDate = request.getParameter("startingDate");
		String endDate = request.getParameter("endingDate");
		String noOfDays = request.getParameter("daysOfLeaves");
		String leaveType = request.getParameter("leaveType");
		String status = "NA";
		String picEmail = (String) x.getAttribute("sessionManagerEmail");
		String appRejOn = "NA";
		String comment = request.getParameter("comment");

		System.out.println("Applicant's email : " + email);
		System.out.println("Applied on: " + appliedDate);
		System.out.println("Applicant's Name : " + applicant);
		System.out.println("Start Date : " + startDate);
		System.out.println("End Date : " + endDate);
		System.out.println("No. of Days : " + noOfDays);
		System.out.println("Leave Type : " + leaveType);
		System.out.println("Status : " + status);
		System.out.println("Manager's Email : " + picEmail);
		System.out.println("Approved/Rejected on : " + appRejOn);
		System.out.println("Comments : " + comment);

		// Adding to LEave Database
		boolean recordAdded = false;

		EmployeeLeave employeeLeave = new EmployeeLeave();
		employeeLeave.setEmail(email);
		employeeLeave.setAppliedDate(appliedDate);
		employeeLeave.setApplicant(applicant);
		employeeLeave.setStartDate(startDate);
		employeeLeave.setEndDate(endDate);
		employeeLeave.setNoOfDays(noOfDays);
		employeeLeave.setLeaveType(leaveType);
		employeeLeave.setStatus(status);
		employeeLeave.setPicEmail(picEmail);
		employeeLeave.setAppRejOn(appRejOn);
		employeeLeave.setComment(comment);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String temp1 = dateFormat.format(cal.getTime()).substring(15, 16);
		String temp2 = dateFormat.format(cal.getTime()).substring(17, 19);
		String temp3 = dateFormat.format(cal.getTime()).substring(1, 2);
		// System.out.println("1");
		String key = applicant.substring(0, 3) + temp1 + temp2 + temp3
				+ leaveType.substring(0, 3);
		// System.out.println("2");
		// System.out.println("Unique Key : " + key);
		// System.out.println("3");
		employeeLeave.setKey(key);

		// Entry in Leave Database
		try {
			pm = PMF.getPersistenceManager();
			// System.out.println("4");
			pm.makePersistent(employeeLeave); // Registration Table
			// System.out.println("5");
			recordAdded = true;
		} catch (Exception e) {
			recordAdded = false;
			System.out.println("Exception : " + e);

		} finally {
			pm.close();
		}

		// Update in Current Status Database

		// email -> email address of appicant
		// noOfDays
		// leaveType

		boolean updateStatus = false;
		try {
			pm = PMF.getPersistenceManager();
			EmployeeLeaveStatus searchUser = pm.getObjectById(
					EmployeeLeaveStatus.class, email);

			// System.out.println(leaveType);
			// Personal leave calculation
			if (leaveType.equals("Personal Leaves")) {
				// System.out.println("Personal");
				int bal = Integer.parseInt(searchUser.getBalancePL());
				// System.out.println("in db , Personal Leaves : " + bal);
				int req = Integer.parseInt(noOfDays);
				// System.out.println("Requested for : " + req);
				int res = bal - req;
				// System.out.println("New Leav balance :" + res);
				searchUser.setBalancePL(String.valueOf(res));
				x.setAttribute("sessionBalancepLeaves", String.valueOf(res));
			}
			// Casual Leave Calculation
			if (leaveType.equals("Casual Leaves")) {
				// System.out.println("Casual");
				int bal = Integer.parseInt(searchUser.getBalanceCL());
				// System.out.println("in db , Personal Leaves : " + bal);
				int req = Integer.parseInt(noOfDays);
				// System.out.println("Requested for : " + req);
				int res = bal - req;
				// System.out.println("New Leav balance :" + res);
				searchUser.setBalanceCL(String.valueOf(res));
				x.setAttribute("sessionBalancecLeaves", String.valueOf(res));
			}
			// Sick Leave Calculation
			if (leaveType.equals("Sick Leaves")) {
				// System.out.println("Sick");
				int bal = Integer.parseInt(searchUser.getBalanceSL());
				// System.out.println("in db , Personal Leaves : " + bal);
				int req = Integer.parseInt(noOfDays);
				// System.out.println("Requested for : " + req);
				int res = bal - req;
				// System.out.println("New Leav balance :" + res);
				searchUser.setBalanceSL(String.valueOf(res));
				x.setAttribute("sessionBalancesLeaves", String.valueOf(res));
			}

			pm.makePersistent(searchUser);
			updateStatus = true;
		} catch (Exception e) {
			System.out
					.println("Exception while updating the Current Leave Status is : "
							+ e);
			updateStatus = false;

		} finally {
			pm.close();

		}
		System.out.println("updated in CurrentLeave Status : " + updateStatus);
		JSONObject jobj = new JSONObject();
		try {

			if (recordAdded) {

				jobj.put("leaveApplicationId", key);
				jobj.put("status", true);

				// Mail Sending to the Employee
				String subject;
				String body;

				MailController mailController = new MailController();

				// call mail method service
				// to Employee (account created)

				subject = " Leave Request Sent to Respective Manager ("
						+ picEmail + ")";
				body = "Your request for Leave Application has been successfully sent to  :  "
						+ picEmail
						+ "\n Leave Application Detail : "
						+ "\n Unique Application Id  : "
						+ key
						+ "\n Application Date : "
						+ appliedDate
						+ "\n Starting Date : "
						+ startDate
						+ "\n Ending Date : "
						+ endDate
						+ "\n Leave Type : "
						+ leaveType
						+ "\n Duration : "
						+ noOfDays
						+ "\n\n Please wait for approval from the respective Manager . . ."
						+ "\n\n";

				mailController.mailMethod(applicant, email, subject, body);

				// call mail method service
				// to Maneger (account created)

				subject = " Leave Request Recieved from " + email;
				body = " You have recieved a Leave Request Application from  :  "
						+ email
						+ "\n Leave Application Detail : "
						+ "\n Unique Application Id  : "
						+ key
						+ "\n Application Date : "
						+ appliedDate
						+ "\n Starting Date : "
						+ startDate
						+ "\n Ending Date : "
						+ endDate
						+ "\n Leave Type : "
						+ leaveType
						+ "\n Duration : "
						+ noOfDays
						+ "\n\n Follow link : http://leaverequest-ad.appspot.com/ , and either Approve/Reject accordingly . . ."
						+ "\n\n";

				mailController.mailMethod("Manager", picEmail, subject, body);

				out.println(jobj);
			} else {
				jobj.put("status", false);
				out.println(jobj);
			}
		} catch (Exception e) {

		}
	}

	@RequestMapping(value = "/CheckForNewLeaveRequests.htm", method = RequestMethod.POST)
	public void acceptLeaveRequestMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		out.println("Check for New Leave Requests Controller");

	}

	@RequestMapping(value = "/CheckStatus.htm", method = RequestMethod.POST)
	public String checkLeaveStatus(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();
		PrintWriter out = response.getWriter();
		out.println("check Leave Status");
		HttpSession session = request.getSession();
		String sessionEMail = (String) session.getAttribute("sessionEmail");

		Query q = pm.newQuery(EmployeeLeave.class);
		q.setFilter("email == sessionEMail");
		q.declareParameters("String sessionEMail");

		// System.out.println("Befor entering try");
		try {
			System.out
					.println("in try blcokk, of retrieving the leave details controller");
			List<EmployeeLeave> results = (List<EmployeeLeave>) q
					.execute(sessionEMail);
			System.out.println("1");
			int i = 0;
			if (!results.isEmpty()) {
				for (EmployeeLeave p : results) {
					// Process result psys
					System.out.println("record found , i = " + i);
					i++;
					System.out.println(p.getKey());

				}
				System.out.println("2");
				request.setAttribute("passedObject", results);
				System.out.println("3");
				request.setAttribute("statusFromServer", "true");
				System.out.println("4");
				return "allLeaveRequestStatus";

			} else {
				System.out.println("5");
				// Handle "no results" case
				// System.out.println("no results found");
			}
		} finally {
			q.closeAll();
		}
		System.out.println("6");
		request.setAttribute("statusFromServer", "false");
		System.out.println("7");
		return "allLeaveRequestStatus";
	}

	@RequestMapping(value = "/incomingLeaveRequest.htm", method = RequestMethod.POST)
	public String incomingRequestMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();
		PrintWriter out = response.getWriter();
		out.println("check Leave Status");
		HttpSession session = request.getSession();
		String managerEmail = (String) session.getAttribute("sessionEmail");

		Query q = pm.newQuery(EmployeeLeave.class);
		q.setFilter("picEmail == managerEmail");
		// q.setOrdering("height desc");
		q.declareParameters("String managerEmail");

		// System.out.println("Befor entering try of checking incoming requests");
		try {
			// System.out.println("in try blcokk, of retrieving the leave details controller");
			List<EmployeeLeave> results = (List<EmployeeLeave>) q
					.execute(managerEmail);
			int i = 0;
			if (!results.isEmpty()) {
				for (EmployeeLeave p : results) {
					// Process result psys
					// System.out.println("record found , i = " + i);
					i++;
					// System.out.println(p.getKey());

				}
				request.setAttribute("passedObject", results);
				request.setAttribute("statusFromServer", "true");
				System.out.println("4");
				return "allIncomingLeaveRequestStatus";
			} else {
				// Handle "no results" case
				System.out.println("no results found");
			}
		} finally {
			q.closeAll();
		}

		request.setAttribute("statusFromServer", "false");
		return "allIncomingLeaveRequestStatus";
	}

	@RequestMapping(value = "/retrieveParticularLeaveRecord.htm", method = RequestMethod.POST)
	public void retrieveParticularRecordMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();
		PrintWriter out = response.getWriter();
		// out.println("particular a application id");
		// System.out.println(request.getParameter("key"));
		String key = request.getParameter("key");
		// System.out.println("Unique key from jsp is : " + key);
		try {
			EmployeeLeave searchUser = pm.getObjectById(EmployeeLeave.class,
					key);
			JSONObject jobj = new JSONObject();
			String applicantId = searchUser.getKey();
			String employeeName = searchUser.getApplicant();
			String employeeEmail = searchUser.getEmail();
			String appliedDate = searchUser.getAppliedDate();
			String startDate = searchUser.getStartDate();
			String endDate = searchUser.getEndDate();
			String noOfDays = searchUser.getNoOfDays();
			String leaveType = searchUser.getLeaveType();
			String status = searchUser.getStatus();
			String appRejDate = searchUser.getAppRejOn();
			jobj.put("applicantId", applicantId);
			jobj.put("employeeName", employeeName);
			jobj.put("employeeEmail", employeeEmail);
			jobj.put("appliedDate", appliedDate);
			jobj.put("startDate", startDate);
			jobj.put("endDate", endDate);
			jobj.put("noOfDays", noOfDays);

			jobj.put("leaveType", leaveType);
			jobj.put("status", status);
			jobj.put("appRejDate", appRejDate);

			// System.out.println("all set, before redirecting json object");
			out.println(jobj);

		} catch (Exception e) {
			// System.out.println("Caught in retrieve cntroller's exception " +
			// e);

		}

	}

	@RequestMapping(value = "/ApproveLeaveRequest.htm", method = RequestMethod.GET)
	public void approveLeaveRequestMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory(
				"transactions-optional").getPersistenceManager();
		PrintWriter out = response.getWriter();
		// out.println("particular a application id");
		String key = request.getParameter("key");
		// System.out.println("Unique key from jsp is :" + key);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		// System.out.println(dateFormat.format(date));
		boolean approvedSuccess = false;
		try {
			EmployeeLeave searchLeave = pm.getObjectById(EmployeeLeave.class,
					key);
			searchLeave.setAppRejOn(dateFormat.format(date));
			searchLeave.setStatus("Approved");
			pm.makePersistent(searchLeave);
			approvedSuccess = true;
		} catch (Exception e) {
			System.out.println("Caught in retrieve cntroller's exception " + e);
			approvedSuccess = false;
		}
		if (approvedSuccess)
			out.println("Approved Successful");
		else
			out.println("Approve Failure.Please Retry.");
	}

	@RequestMapping(value = "/RejectLeaveRequest.htm", method = RequestMethod.GET)
	public void rejectLeaveRequestMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();

		String key = request.getParameter("key");
		System.out.println("Unique key from jsp is :" + key);

		String emailAddress = request.getParameter("emailAddress");
		System.out.println("EmaiAdress is :" + emailAddress);

		String noOfDays = request.getParameter("noOfDays");
		System.out.println("noOfDays is :" + noOfDays);

		String leaveType = request.getParameter("leaveType");
		System.out.println("leaveType is :" + leaveType);

		HttpSession x = request.getSession();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		boolean rejectedSuccess = false;
		boolean leaveBalanceUpdatedafterRejection = false;
		try {
			pm = PMF.getPersistenceManager();

			System.out.println("1");

			// getting the total leave in main table - employeebeanspring
			EmployeeBeanString searchUserfromEmployeeBeanString = pm
					.getObjectById(EmployeeBeanString.class, emailAddress);
			String orgpleaves = searchUserfromEmployeeBeanString.getPleaves();
			System.out.println(orgpleaves);
			String orgcleaves = searchUserfromEmployeeBeanString.getCleaves();
			System.out.println(orgcleaves);
			String orgsleaves = searchUserfromEmployeeBeanString.getSleaves();
			System.out.println(orgsleaves);
			System.out.println("2");

			EmployeeLeave searchLeave = pm.getObjectById(EmployeeLeave.class,
					key);
			searchLeave.setAppRejOn(dateFormat.format(date));
			searchLeave.setStatus("Rejected");
			pm.makePersistent(searchLeave);

			rejectedSuccess = true;
			// To revert the days back to the employee again

			EmployeeLeaveStatus searchUser = pm.getObjectById(
					EmployeeLeaveStatus.class, emailAddress);

			System.out.println(leaveType);
			// Personal leave calculation
			if (leaveType.equals("Personal Leaves")) {
				System.out.println("Personal");
				int bal = Integer.parseInt(searchUser.getBalancePL());

				System.out.println("in db , Personal Leaves : " + bal);
				int return_back = Integer.parseInt(noOfDays);
				System.out.println("To return back : " + return_back);
				int res = bal + return_back;
				System.out.println("New Leav balance :" + res);

				if (res > Integer.parseInt(orgpleaves)) {
					searchUser.setBalancePL(orgpleaves);
					x.setAttribute("sessionBalancepLeaves",
							String.valueOf(orgpleaves));
				} else {
					searchUser.setBalancePL(String.valueOf(res));
					x.setAttribute("sessionBalancepLeaves", String.valueOf(res));
				}

			}
			// Casual Leave Calculation
			if (leaveType.equals("Casual Leaves")) {
				System.out.println("Casual");
				int bal = Integer.parseInt(searchUser.getBalanceCL());
				System.out.println("in db , Personal Leaves : " + bal);
				int return_back = Integer.parseInt(noOfDays);
				System.out.println("To return back : " + return_back);
				int res = bal + return_back;
				System.out.println("New Leav balance :" + res);

				if (res > Integer.parseInt(orgcleaves)) {
					searchUser.setBalanceCL(orgcleaves);
					x.setAttribute("sessionBalancecLeaves",
							String.valueOf(orgcleaves));
				} else {
					searchUser.setBalanceCL(String.valueOf(res));
					x.setAttribute("sessionBalancecLeaves", String.valueOf(res));
				}

			}
			// Sick Leave Calculation
			if (leaveType.equals("Sick Leaves")) {
				System.out.println("Sick");
				int bal = Integer.parseInt(searchUser.getBalanceSL());
				System.out.println("in db , Personal Leaves : " + bal);
				int return_back = Integer.parseInt(noOfDays);
				System.out.println("To return back : " + return_back);
				int res = bal + return_back;
				System.out.println("New Leav balance :" + res);

				if (res > Integer.parseInt(orgsleaves)) {
					searchUser.setBalanceSL(orgsleaves);
					x.setAttribute("sessionBalancesLeaves",
							String.valueOf(orgsleaves));
				} else {
					searchUser.setBalanceSL(String.valueOf(res));
					x.setAttribute("sessionBalancesLeaves", String.valueOf(res));
				}

			}

			pm.makePersistent(searchUser);
			leaveBalanceUpdatedafterRejection = true;

		} catch (Exception e) {
			System.out.println("Caught in retrieve cntroller's exception " + e);
			rejectedSuccess = false;
		}
		if (rejectedSuccess && leaveBalanceUpdatedafterRejection)
			out.println("Rejected Successful");
		else
			out.println("Rejection Failure.Please Retry.");

	}

}
