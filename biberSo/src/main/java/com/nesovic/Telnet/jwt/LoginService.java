package com.nesovic.Telnet.jwt;

import java.sql.SQLException;



import java.util.Date;

import javax.ws.rs.core.Application;

import com.nesovic.Telnet.DAO.ClientsDAO;
import com.nesovic.Telnet.exception.AuthFaildException;
import com.nesovic.Telnet.model.Clients;
import com.nesovic.Telnet.model.Credentials;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class LoginService extends Application{
	ClientsDAO dao=new ClientsDAO();
	
	public String Login(Credentials crd) throws ClassNotFoundException, SQLException, AuthFaildException {
		Clients client=dao.Login(crd);
		String jwt="";
		if(client.getUsername()!="") {
			Long time=System.currentTimeMillis();
			jwt=Jwts.builder().claim("id", client.getClient_id())
					 .setSubject(String.valueOf(client.getClient_id()))
					.claim("name",client.getName())
					.claim("lastname", client.getLastname())
					.claim("role", client.getRole())
					.setExpiration(new Date(time+90000))
					.signWith(SignatureAlgorithm.HS256, "password".getBytes())
					.compact();
		//	json=Json.createObjectBuilder().add(jwt).build();
			return jwt;
		}
		return jwt;
	}
}
