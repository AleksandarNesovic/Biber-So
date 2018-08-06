package com.nesovic.Telnet.filter;

import java.io.IOException;


import java.security.Principal;

import javax.annotation.Priority;
import javax.json.Json;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;

import com.nesovic.Telnet.annotations.Secured;


@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter{

	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException{
		String authorizationHeader =  requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
	
		if (authorizationHeader == null) {
			System.out.println("No JWT token !");
			requestContext.setProperty("auth-failed", true);
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
			return;
			}
		
		
		String jwt=authorizationHeader.trim();
		try {
		final String username =validateToken(jwt);
		
		final SecurityContext securityContext = requestContext.getSecurityContext();
		if (username != null) {
			requestContext.setSecurityContext(new SecurityContext() {
				@Override
				public Principal getUserPrincipal() {
					return new Principal() {
						@Override
						public String getName() {
							System.out.println("Returning custom Principal - " + username);
							return username;
						}
					};
				}
				@Override
				public boolean isUserInRole(String role) {
					return securityContext.isUserInRole(role);
				}

				@Override
				public boolean isSecure() {
					return securityContext.isSecure();
				}

				@Override
				public String getAuthenticationScheme() {
					return securityContext.getAuthenticationScheme();
				}
			});
		}
		}catch (Exception e) {
			System.out.println("JWT validation failed");
			System.out.println("Token je istekao");
			requestContext.setProperty("auth-failed", true);
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(Json.createValue("Token je istekao")).build());
		}
	
	}
	private String validateToken(String jwt) throws InvalidJwtException {
		String username = "";
		String secret="password";
		JwtConsumer jwtConsumer = new JwtConsumerBuilder()
				.setRequireSubject() // the JWT must have a subject claim
				.setVerificationKey(new HmacKey(secret.getBytes()))
				 .setRelaxVerificationKeyValidation() 
				 .setSkipSignatureVerification()
				.build(); // create the JwtConsumer instance
		
			try {
				JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
					System.out.println(NumericDate.now());
					System.out.println(jwtClaims.getExpirationTime());
					username=(String)jwtClaims.getClaimValue("sub");
					System.out.println("JWT validation succeeded!");
			} catch (MalformedClaimException e) {
				e.printStackTrace();
			}
		return username;
	}
}
