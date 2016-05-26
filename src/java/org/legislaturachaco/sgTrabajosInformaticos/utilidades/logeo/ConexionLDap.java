/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades.logeo;

import org.legislaturachaco.sgTrabajosInformaticos.utilidades.logeo.Usuario;
import java.util.Date;
import java.util.Hashtable;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import static javax.naming.directory.SearchControls.SUBTREE_SCOPE;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 *
 * @author coperalta
 */
public class ConexionLDap {
    private static final String SERVER= "10.2.0.49:389";
    
    private static Usuario getUser(String username, LdapContext context) {
        String[] userAttributes = {
            "distinguishedName","cn","name","uid",
            "sn","givenname","memberOf","samaccountname",
            "userPrincipalName"};
        try{
            String domainName = null;
            if (username.contains("@")){
                username = username.substring(0, username.indexOf("@"));
                domainName = username.substring(username.indexOf("@")+1);
            }
            else if(username.contains("\\")){
                username = username.substring(0, username.indexOf("\\"));
                domainName = username.substring(username.indexOf("\\")+1);
            }
            else{
                String authenticatedUser = (String) context.getEnvironment().get(Context.SECURITY_PRINCIPAL);
                if (authenticatedUser.contains("@")){
                    domainName = authenticatedUser.substring(authenticatedUser.indexOf("@")+1);
                }
            }
 
            if (domainName!=null){
                String principalName = username + "@" + domainName;
                SearchControls controls = new SearchControls();
                controls.setSearchScope(SUBTREE_SCOPE);
                controls.setReturningAttributes(userAttributes);
                NamingEnumeration<SearchResult> answer = context.search( aDC(domainName), "(& (userPrincipalName="+principalName+")(objectClass=user))", controls);
                if (answer.hasMore()) {
                    Attributes attr = answer.next().getAttributes();
                    Attribute user = attr.get("userPrincipalName");
                    if (user!=null) return new Usuario(attr);
                }
            }
        }
        catch(NamingException e){
            System.err.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Obtiene un usuario con datos de los distintos grupos al cual pertenece, entre
     * otras cosas mas.
     * @param username Nombre de usuario.
     * @param password Contraseña de usuario.
     * @return Un usuario valido del dominio.
     * @throws NamingException 
     */
    public static Usuario getUser(String username, String password) throws NamingException {
        LdapContext ctx = getLdapContext(username, password);
        Usuario u= getUser(username, ctx);
        ctx.close();
        return u;
    }
    
    private static String aDC(String domainName) {
        StringBuilder buf = new StringBuilder();
        for (String token : domainName.split("\\.")) {
            if(token.length()==0) continue;
            if(buf.length()>0) buf.append(",");
            buf.append("DC=").append(token);
        }
        return buf.toString();
    }
    
    /**
     * Obtiene una conexión con el servidor.
     * @param username Un usuario para la conexión.
     * @param password Una contraseña del usuario para la conexión.
     * @return 
     * @throws NamingException
     * @throws AuthenticationException 
     */
    private static LdapContext getLdapContext(String username, String password) 
        throws NamingException, AuthenticationException{
        Hashtable<String, String> env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "Simple");
        env.put(Context.SECURITY_PRINCIPAL, username.toUpperCase() + "@legislaturachaco.local");
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.PROVIDER_URL, "ldap://"+SERVER);
        env.put(Context.REFERRAL, "follow");  
        return new InitialLdapContext(env, null);
    }
    
    public static void main(String[] args) {
        try {
            String grupoABuscar= "Soportecnicos";
            System.out.println("run: " + new Date());
            LdapContext ctx = getLdapContext("oficinasanchez", "oficina2011");
            Usuario u= getUser("coperalta", ctx);
            System.out.println(u.getCommonName());
            System.out.println(u.getDistinguishedName());
            System.out.println(u.getUserPrincipal());
            System.out.println(u.getMemberof());
            //System.out.println(u.toString());
            System.out.println(u.perteneceAGrupo(grupoABuscar));
            System.out.println(getUser("oficinasanchez", ctx).perteneceAGrupo(grupoABuscar));
            System.out.println("done: " + new Date());
            ctx.close();
        } catch (AuthenticationException ex) {
            System.err.println("Error de autenticación");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }
}
