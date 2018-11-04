package ado;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;
import model.Msg;
import model.User;
import model.UserMsg;


public class Userdao {
	Connection con=DBManager.getDBManager().connection;
    PreparedStatement ps=null;
    ResultSet rs=null;
    String sql=null;
    
    // �û���¼
    public boolean loginuser(User bean){
    	sql="select * from user where U_LoginID=? and U_PassWord=?";
    	try {
			ps=con.prepareStatement(sql);
			ps.setString(1, bean.getU_logId());
			ps.setString(2, bean.getU_pwd());
			rs=ps.executeQuery();
			return rs.next();
			
		} catch (SQLException e) {
			System.out.println("��¼ʧ��");
			e.printStackTrace();
		}
		finally{
			close();
		}
    	return false;
    }

    
    // �û��Ƿ����
    public User getuser(User bean1){
    	sql="select * from user where U_LoginID=?";
    	try {
			ps=con.prepareStatement(sql);
			ps.setString(1, bean1.getU_logId());
			rs=ps.executeQuery();
			if( rs.next()){
				User bean=new User();
				bean.setU_logId(rs.getString("U_LoginID"));
				bean.setU_pwd(rs.getString("U_PassWord"));
				bean.setU_nickName(rs.getString("U_NickName"));
				bean.setU_signaTure(rs.getString("U_SignaTure"));
				bean.setU_sex(rs.getInt("U_Sex"));
				bean.setU_birthday(rs.getString("U_Birthday"));
				bean.setU_telephon(rs.getString("U_Telephone"));
				return bean;
			}
			
		} catch (SQLException e) {
			System.out.println("��ȡʧ��");
		}
		finally{
			close();
		}
    	return null;
    }
    
 // ����û�
  public void insertuser(User bean) {
		sql="insert into user (U_LoginID,U_NickName,U_PassWord,U_SignaTure,U_Sex,U_Birthday,U_Telephone) values(?,?,?,?,?,?,?)";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, bean.getU_logId());
			ps.setString(2, bean.getU_nickName());
			ps.setString(3,bean.getU_pwd() );
			ps.setString(4, bean.getU_signaTure());
			ps.setInt(5, bean.getU_sex());
			ps.setString(6, bean.getU_birthday());
			ps.setString(7, bean.getU_telephon());
			ps.execute();
		} catch (SQLException e) {
			System.out.println("���ʧ��");
			e.printStackTrace();
		}
		finally{
			close();
		}
	}
    
    public List<User> getAlluser(User bean1){
    	List<User> list=new ArrayList<User>();
    	sql="select * from user u where u.U_LoginID in (select f.F_id from user u,friends f where u.U_LoginID=? and f.U_id = u.U_LoginID)";
    	try {
			ps=con.prepareStatement(sql);
			ps.setString(1, bean1.getU_logId());
			rs=ps.executeQuery();
			System.out.println("444444444");
			while( rs.next()){
				System.out.println("yyouyou");
				User bean=new User();
				bean.setU_logId(rs.getString("U_LoginID"));
				bean.setU_pwd(rs.getString("U_PassWord"));
				bean.setU_nickName(rs.getString("U_NickName"));
				bean.setU_signaTure(rs.getString("U_SignaTure"));
				bean.setU_sex(rs.getInt("U_Sex"));
				bean.setU_birthday(rs.getString("U_Birthday"));
				bean.setU_telephon(rs.getString("U_Telephone"));
			  list.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("��ȡʧ��");
		}
		finally{
			close();
		}
    	return list;
    }
    
    //��ȡ������Ϣ
    public List<UserMsg> getAllMsg(User me,User friend){
    	List<UserMsg> listum=new ArrayList<UserMsg>();
    	sql="select * from msg,user where ((msg.F_id=? and msg.U_id=?)and user.U_LoginID=?)or((msg.U_id=? and msg.F_id=?)and user.U_LoginID=?)";
    	try {
			ps=con.prepareStatement(sql);
			ps.setString(1, me.getU_logId());
			ps.setString(2, friend.getU_logId());
			ps.setString(3, friend.getU_logId());
			ps.setString(4, me.getU_logId());
			ps.setString(5, friend.getU_logId());
			ps.setString(6, me.getU_logId());			
			rs=ps.executeQuery();
			while( rs.next()){
			    UserMsg um = new UserMsg();
			    um.setuId(rs.getString("U_id"));
			    um.setU_nickName(rs.getString("U_NickName"));
			    um.setMsg(rs.getString("content"));
			    listum.add(um);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("��ȡʧ��");
		}
		finally{
			close();
		}
    	return listum;
    }
    //������Ϣ
    public void sendMsg(UserMsg um){
    	sql="insert into msg (U_id,F_id,content) values (?,?,?)";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, um.getuId());
			ps.setString(2, um.getFid());
			ps.setString(3,um.getMsg());
			ps.execute();
		} catch (SQLException e) {
			System.out.println("���ʧ��");
			e.printStackTrace();
		}
		finally{
			close();
		}
    	
    }
//    
//    
//    //ɾ������Ա
//    public void deleteuser(tuser bean) {
//		sql="delete from tuser where stuid=?";
//		try {
//			ps=con.prepareStatement(sql);
//			ps.setString(1, bean.getStuid());
//			ps.execute();
//		} catch (SQLException e) {
//			System.out.println("ɾ��ʧ��");
//			e.printStackTrace();
//		}
//		finally{
//			close();
//		}
//	}
//    
//    // ���¹���Ա
//    public void updateadmin(tuser bean) {
//		sql="update tuser set name=?,sex=?,yuanxi=?,cls=?,phone=?,pwd=? where stuid=?";
//		try {
//			ps=con.prepareStatement(sql);
//			ps.setString(1, bean.getName());
//			ps.setString(2, bean.getSex());
//			ps.setString(3, bean.getYuanxi());
//			ps.setString(4, bean.getCls());
//			ps.setString(5, bean.getPhone());
//			ps.setString(6, bean.getPwd());
//			ps.setString(7, bean.getStuid());
//			ps.execute();
//		} catch (SQLException e) {
//			System.out.println("����ʧ��");
//			e.printStackTrace();
//		}
//		finally{
//			close();
//		}
//	}

    
    private void close(){
		try{
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("---------------------�ر�rs��ps��con�����쳣--------------------");
		}
	
	}
}
