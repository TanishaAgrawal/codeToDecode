package com.hsbc.Security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hsbc.models.SessionEntity;
import javax.servlet.http.HttpSession;

import com.hsbc.dao.OrderProcessingDAO;
import com.hsbc.daoImpl.OrderProcessingDAOImpl;
import com.hsbc.exceptions.SessionExpiredException;
import com.hsbc.exceptions.SystemSecurityException;
import com.hsbc.exceptions.UnAuthAccessException;
import com.hsbc.models.SessionEntity;

public class SessionManager {
	static final int ttl = 1200;
	private static  OrderProcessingDAO orderProcessingDAOImpl = new OrderProcessingDAOImpl();
	
	//creating a new session every time the user try to log in
    public static void createSession(HttpServletRequest request,HttpServletResponse response,int personId) throws SystemSecurityException{
    	HttpSession session = request.getSession(false);
    	if(session!=null) {
    		//invalidating the old session
    		session.invalidate();
    	}else {
    		//checking first time user
    		Cookie c[] = request.getCookies();
    		if(c==null) {
    			Cookie ck=new Cookie("user",personId+"");
    			response.addCookie(ck);
    			ck.setMaxAge(100000);
    		}
    	}
    	session = request.getSession(true);
    
    	String sessionText = personId+""+System.currentTimeMillis();
        String sessionToken = RSA.encrypt(sessionText);
        
        SessionEntity sobj = new SessionEntity(personId,sessionToken);
//        run an update command query
        orderProcessingDAOImpl.updateToken(sobj);
        session.setAttribute("sessionObject", sobj);
//        setting max lived time 
        session.setMaxInactiveInterval(ttl);
        
    }
    
//    checking the exixting session
    public static void checkToken(SessionEntity session) throws UnAuthAccessException {
    	if(session!=null) {
    		SessionEntity sessionFromDB = null;
    		SessionEntity se = orderProcessingDAOImpl.tokenFetcher(session.getPersonId());
    		
    		if(se.getSessionToken().equals(session.getSessionToken())) {
    			return;
    		}else {
    			throw new UnAuthAccessException("UnAuthorized Access");
    		}
    	}
    }
    
//    deleting the session manually
    public static void deleteSession(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	if(session!=null) {
    		session.invalidate();
    	}
    	request.getRequestDispatcher("home.html").forward(request, response);
    }
    
//    getting the SessionEntity object from session
    public static SessionEntity getSessionData(HttpServletRequest request) throws SessionExpiredException {
    	HttpSession session = request.getSession(false);
    	SessionEntity se = null;
    	if(session!=null)
    		se = (SessionEntity)session.getAttribute("sessionObject");
    	else {
    		throw new SessionExpiredException("Session No longer Available");
    	}
		return se;
    }
}





//public static SessionEntity checkSession(HttpServletRequest request/*sessionObject*/) throws SessionExpiredException, UnAuthAccessException, NotLoggedInException{
////select * from Session where customerId = session.getcustomerId;
////create publickey, privateKey, ttl variable in ddta 
////Session sessionDBObject= new Session(10,"sadfxvbnkghb");
//HttpSession serverSession = request.getSession(false);
//if(serverSession==null) {
//throw new SessionExpiredException("Session Expired due to Inactivity");
//}
//SessionEntity sessionDBObject = (SessionEntity)serverSession.getAttribute("sessionObject");
//
//if(sessionDBObject!=null) {
//if(sessionDBObject.getSessionToken().equals(sessionDBObject.getSessionToken()/*sessionObject.getToken*/)){
//	System.out.println("equal ==");
//    return sessionDBObject;
//}else {
//	throw new UnAuthAccessException("Unauthorized Access Exception");
//}
//}
//else {
//System.out.println(sessionDBObject.getSessionToken());
//throw new NotLoggedInException("Login to the System at first");
//}
//
//}
