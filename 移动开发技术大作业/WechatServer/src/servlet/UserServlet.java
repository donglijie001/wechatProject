package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ResponseJson;
import model.User;
import model.UserMsg;
//import ado.tadmindao;
import ado.Userdao;

import com.google.gson.Gson;

public class UserServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UserServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		ResponseJson json = new ResponseJson ();
		Userdao dao=new Userdao();
		String method=request.getParameter("method");
		System.out.println(method);
		//显示好友列表
		if(method.endsWith("getalluser")){
			String logId=request.getParameter("logId");
			System.out.println("登录名"+logId);
			User bean=new User();
			bean.setU_logId(logId);
				json.msg = "success";
				json.data=dao.getAlluser(bean);
				String res = gson.toJson(json);
				response.getWriter().write(res);
		}
//		else if(method.endsWith("deleteuser")){
//			String stuid=request.getParameter("stuid");
//			tuser bean=new tuser();
//			bean.setStuid(stuid);
//			dao.deleteuser(bean);
//		}
		else 
			//登录
		if(method.endsWith("loginuser")){
			String logId=request.getParameter("logId");
			String pwd=request.getParameter("pwd");
			User bean=new User();
			bean.setU_logId(logId);
			bean.setU_pwd(pwd);
			if(dao.loginuser(bean))
			{
				json.msg = "success";
				json.data.add(dao.getuser(bean));
			}
			else 
			json.msg="fail";
			String res = gson.toJson(json);
			response.getWriter().write(res);
			System.out.println("用户登录"+res);
		}
		//注册
		else if(method.endsWith("registeruser")){
			String logId=request.getParameter("logId");
			String pwd=request.getParameter("psw");
			User bean=new User();
			bean.setU_logId(logId);
			bean=dao.getuser(bean);
			json.msg = "success";
			if(bean!=null){
				//已经存在
				json.msg = "此账号已经存在，不能重复注册";
			}else {
				bean=new User();
				bean.setU_logId(logId);
				bean.setU_pwd(pwd);
				dao.insertuser(bean);
			}
			String res = gson.toJson(json);
			response.getWriter().write(res);
			System.out.println("user注册"+res);
			//System.out.println("admin登录"+res);
		}
		else if (method.endsWith("getallmsg")){
			String logId=request.getParameter("logId");
			String fId = request.getParameter("fId");
			User me=new User();
			me.setU_logId(logId);
			
			User friend = new User();
			friend.setU_logId(fId);
			json.msg = "success";
			json.data=dao.getAllMsg(me,friend);
			String res = gson.toJson(json);
			response.getWriter().write(res);
		}
		//发送消息
		else if(method.endsWith("sendmsg")){
			String logId=request.getParameter("logId");
			String fId = request.getParameter("fId");
			String msg = request.getParameter("msg");
			UserMsg um=new UserMsg();
			um.setuId(logId);
			um.setFid(fId);
			um.setMsg(msg);
			json.msg = "success";
				dao.sendMsg(um);
			String res = gson.toJson(json);
			response.getWriter().write(res);
			System.out.println("发送成功"+res);
			
		}
		
		
		//updateuserpwd
//		else if(method.endsWith("updateuserpwd")){
//			String stuid=request.getParameter("stuid");
//			String pwd=request.getParameter("pwd");
//			tuser bean=new tuser();
//			bean.setStuid(stuid);
//			bean=dao.getuser(bean);
//			bean.setPwd(pwd);
//			dao.updateadmin(bean);
//			//System.out.println("admin登录"+res);
//		}// 更新个人信息
//		else if(method.endsWith("updateuser")){
//			String stuid=request.getParameter("stuid");
//			String name=request.getParameter("name");
//			String sex=request.getParameter("sex");
//			String yuanxi=request.getParameter("yuanxi");
//			String cls=request.getParameter("cls");
//			String phone=request.getParameter("phone");
//			tuser bean=new tuser();
//			bean.setStuid(stuid);
//			bean=dao.getuser(bean);
//			bean.setCls(cls);
//			bean.setName(name);
//			bean.setPhone(phone);
//			bean.setYuanxi(yuanxi);
//			bean.setSex(sex);
//			dao.updateadmin(bean);
//			//System.out.println("admin登录"+res);
//		}
//		else   // 获得借阅历史
//			if(method.endsWith("getoneuser")){
//			String stuid=request.getParameter("stuid");
//			
//			json.status = 200;
//			json.msg = "success";
//			tuser bean=new tuser();
//			bean.setStuid(stuid);
//			json.data.add(dao.getuser(bean));
//			String res = gson.toJson(json);
//			response.getWriter().write(res);
//			System.out.println("获得用户"+res);
//		}
	}
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
