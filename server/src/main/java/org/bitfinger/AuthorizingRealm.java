package org.bitfinger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class AuthorizingRealm extends JdbcRealm {
	
 	//private final GenericRepository dao;
 	protected boolean permissionsLookupEnabled = false; 
	
// 	@Inject
//	public AuthorizingRealm(PersistenceManagerFactory pmf){
// 		super();
//		dao = new GenericRepository(pmf);
//	}
	
 	@Override
 	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {  
		  
 		UsernamePasswordToken upToken = (UsernamePasswordToken) token;  
		//Account a = dao.queryEntity(new RNQuery().addFilter("email", upToken.getUsername()), Account.class);
		 
	    AuthenticationInfo info = null;  
	    //info = new SimpleAuthenticationInfo(a.getEmail(), a.getPassword(), a.getEmail());
	    //return info;
	    return null;
	}
 	
 	
 	@Override
 	protected Set<String> getRoleNamesForUser(Connection conn, String username)
 			throws SQLException {
 		return new HashSet<String>(Arrays.asList("customer"));
 	}
 	
 	@Override
 	protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
 		return new SimpleAuthorizationInfo(new HashSet<String>(Arrays.asList("customer")));
 	}
 	
 	

}
