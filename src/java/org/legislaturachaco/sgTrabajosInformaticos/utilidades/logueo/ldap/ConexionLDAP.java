/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo.ldap;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
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
public class ConexionLDAP {

    private static ConexionLDAP conexionLDAP;

    private LdapContext ctx;

    private final String baseBusqueda, dominio, servidor, puerto;
    
    private ConexionLDAP() {
        dominio = "@legislaturachaco.local";
        baseBusqueda = "dc=LEGISLATURACHACO,dc=LOCAL";
        servidor= "10.2.0.49";
        puerto= "389";
    }
    
    public static ConexionLDAP getInstance() {
        if (conexionLDAP == null) {
            conexionLDAP = new ConexionLDAP();
        }
        return conexionLDAP;
    }

    public void crearConexion(String usuarioConexion, String passwordConexion) throws NamingException {
        String urlServidor = "ldap://" + servidor + ":" + puerto;

        Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, usuarioConexion.toUpperCase() + dominio);
        env.put(Context.SECURITY_CREDENTIALS, passwordConexion);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, urlServidor);
        //ensures that objectSID attribute values
        //will be returned as a byte[] instead of a String
        env.put("java.naming.ldap.attributes.binary", "objectSID");

        //System.out.println(urlServidor);
        // the following is helpful in debugging errors
        //env.put("com.sun.jndi.ldap.trace.ber", System.err);
        ctx = new InitialLdapContext(env, null);
    }

    public void cerrarConexion() throws NamingException {
        ctx.close();
    }
    
    private Usuario getUser(String username) throws UsuarioNoEncontradoException {
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
                String authenticatedUser = (String) ctx.getEnvironment().get(Context.SECURITY_PRINCIPAL);
                if (authenticatedUser.contains("@")){
                    domainName = authenticatedUser.substring(authenticatedUser.indexOf("@")+1);
                }
            }
 
            if (domainName!=null){
                String principalName = username + "@" + domainName;
                SearchControls controls = new SearchControls();
                controls.setSearchScope(SUBTREE_SCOPE);
                controls.setReturningAttributes(userAttributes);
                NamingEnumeration<SearchResult> answer = ctx.search( aDC(domainName), "(& (userPrincipalName="+principalName+")(objectClass=user))", controls);
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
        throw new UsuarioNoEncontradoException("el servidor "+servidor);
    }
    
    /**
     * Obtiene un usuario con datos de los distintos grupos al cual pertenece, entre
     * otras cosas mas.
     * @param nombreUsuario Nombre de usuario.
     * @return Un usuario valido del dominio.
     * @throws NamingException 
     */
    public Usuario getUsuario(String nombreUsuario) throws NamingException, UsuarioNoEncontradoException {
        Usuario u= getUser(nombreUsuario);
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
    
    public boolean buscarGrupo(String usuario, String grupo) {
        try {
            usuario += dominio;

            //System.out.println(usuario);
            StringBuilder filtro = new StringBuilder("(&");
            filtro.append("(objectClass=person)");
            filtro.append("(userPrincipalName=").append(usuario).append(")");
            filtro.append(")");

            String returnAttrs[] = {"memberOf"};
            SearchControls sCtrl = new SearchControls();
            sCtrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
            sCtrl.setReturningAttributes(returnAttrs);

            NamingEnumeration<SearchResult> answer = ctx.search(baseBusqueda, filtro.toString(), sCtrl);
            boolean encontrado = false;

            // Define the match string
            // Loop through the results and check every single value in attribute "memberOf"
            while (answer.hasMoreElements()) {
                SearchResult sr = answer.next();
                //System.out.println(sr.toString());
                String memberOfAttrValue = sr.getAttributes().get("memberOf").toString();
                //System.out.println(memberOfAttrValue);
                if (memberOfAttrValue.contains(grupo)) {
                    encontrado = true;
                    break;
                }
            }

            return encontrado;
        } catch (Exception e) {
            //System.out.println(e.getLocalizedMessage());
            return false;
        }
    }

    public void buscarMiembros(String grupo) throws NamingException {

        // record_count is the sum of all returned records
        Integer record_count = 0;
        // enabled_count is the sum of all records found enabled
        Integer enabled_count = 0;
        // The three attributes we want to display
        String display_name = "";
        String account_name = "";
        String account_control = "";

        // Here we store the returned LDAP object data
        String dn = "";
        // This will hold the returned attribute list
        Attributes attrs;

        // The entries attribute list we want to return from a search. Here we want the
        // real name, the domain account name and see if the account has been disabled.
        String[] attr_list = {"member"};

        SearchControls ctls = new SearchControls();
        ctls.setReturningAttributes(attr_list);
        ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        // The group search filter: check if a entry is a person and a contact or user,
        // and belongs to a group named here
        //String filter = "(&(objectCategory=person)(|(objectClass=contact)(objectClass=user))(memberOf=" + grupo + "))";
        String filter = "(&(objectclass=group)(cn=" + grupo + "))";

        // Search the subtree for objects using the given filter
        NamingEnumeration answer = ctx.search(baseBusqueda, filter, ctls);
        // Print the answer
        //Search.printSearchEnumeration(answer);
        while (answer.hasMoreElements()) {
            SearchResult sr = (SearchResult) answer.next();
            //System.out.println(">>>" + sr.getName());
            attrs = sr.getAttributes();

            if (null != attrs) {
                for (NamingEnumeration ae = attrs.getAll(); ae.hasMoreElements();) {
                    Attribute atr = (Attribute) ae.next();
                    String attributeID = atr.getID();
                    Enumeration vals = atr.getAll();
                    //System.out.println(attributeID);
                    while (vals.hasMoreElements()) {
                        String username = (String) vals.nextElement();
                        System.out.println("Username: " + username);
                    }
                }
            } else {
                System.out.println("No members for groups found");
            }
        }
    }

    public List<String> listarGrupos(String usuario) throws NamingException {
        List<String> resultados= new ArrayList<>();
       
        SearchControls searchCtls = new SearchControls();
        String returnedAtts[] = {"memberOf"};
        searchCtls.setReturningAttributes(returnedAtts);
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);        
        String searchFilter = "(&(objectClass=user)(sAMAccountName=" + usuario + "))";

        NamingEnumeration answer = ctx.search(baseBusqueda, searchFilter, searchCtls);

        while (answer.hasMoreElements()) {
            SearchResult sr = (SearchResult) answer.next();
            
            Attributes attrs = sr.getAttributes();
            Attribute groupMembers = attrs.get("memberOf");
            for (int i = 0; i < groupMembers.size(); i++) {
                resultados.add(groupMembers.get(i).toString());
            }
        }    
        return resultados;
    }

    public SearchResult findAccountByAccountName(String accountName) throws NamingException {

        String searchFilter = "(&(objectClass=user)(sAMAccountName=" + accountName + "))";

        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        NamingEnumeration<SearchResult> results = ctx.search(baseBusqueda, searchFilter, searchControls);

        SearchResult searchResult = null;
        if (results.hasMoreElements()) {
            searchResult = (SearchResult) results.nextElement();

            //make sure there is not another item available, there should be only 1 match
            if (results.hasMoreElements()) {
                System.err.println("Matched multiple users for the accountName: " + accountName);
                return null;
            }
        }

        return searchResult;
    }

    public String findGroupBySID(String sid) throws NamingException {

        String searchFilter = "(&(objectClass=group)(objectSid=" + sid + "))";

        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        NamingEnumeration<SearchResult> results = ctx.search(baseBusqueda, searchFilter, searchControls);

        if (results.hasMoreElements()) {
            SearchResult searchResult = (SearchResult) results.nextElement();

            //make sure there is not another item available, there should be only 1 match
            if (results.hasMoreElements()) {
                System.err.println("Matched multiple groups for the group with SID: " + sid);
                return null;
            } else {
                return (String) searchResult.getAttributes().get("sAMAccountName").get();
            }
        }
        return null;
    }

    public String getPrimaryGroupSID(SearchResult srLdapUser) throws NamingException {
        byte[] objectSID = (byte[]) srLdapUser.getAttributes().get("objectSid").get();
        String strPrimaryGroupID = (String) srLdapUser.getAttributes().get("primaryGroupID").get();

        String strObjectSid = decodeSID(objectSID);

        return strObjectSid.substring(0, strObjectSid.lastIndexOf('-') + 1) + strPrimaryGroupID;
    }

    /**
     * The binary data is in the form: byte[0] - revision level byte[1] - count
     * of sub-authorities byte[2-7] - 48 bit authority (big-endian) and then
     * count x 32 bit sub authorities (little-endian)
     *
     * The String value is: S-Revision-Authority-SubAuthority[n]...
     *
     * Based on code from here -
     * http://forums.oracle.com/forums/thread.jspa?threadID=1155740&tstart=0
     */
    private static String decodeSID(byte[] sid) {

        final StringBuilder strSid = new StringBuilder("S-");

        // get version
        final int revision = sid[0];
        strSid.append(Integer.toString(revision));

        //next byte is the count of sub-authorities
        final int countSubAuths = sid[1] & 0xFF;

        //get the authority
        long authority = 0;
        //String rid = "";
        for (int i = 2; i <= 7; i++) {
            authority |= ((long) sid[i]) << (8 * (5 - (i - 2)));
        }
        strSid.append("-");
        strSid.append(Long.toHexString(authority));

        //iterate all the sub-auths
        int offset = 8;
        int size = 4; //4 bytes for each sub auth
        for (int j = 0; j < countSubAuths; j++) {
            long subAuthority = 0;
            for (int k = 0; k < size; k++) {
                subAuthority |= (long) (sid[offset + k] & 0xFF) << (8 * k);
            }

            strSid.append("-");
            strSid.append(subAuthority);

            offset += size;
        }

        return strSid.toString();
    }

    public String getServidor() {
        return servidor;
    }

    public String getPuerto() {
        return puerto;
    }
    
    public static void main(String[] args) {

        String[] lstUs = {"oficinasanchez", "gmario", "coperalta", "jsilva", "mglopez", "aacevedo", "opallud", "oficinagersel", "sss"};

        String passConexion = "trabinfacceso2016";
        String usConexion = "accesotrabinf", unUsuario= "coperalta";
        
        boolean respuesta;
        String grupoABuscar = "trabajosinformaticos";

        ConexionLDAP con = ConexionLDAP.getInstance();
        
        Usuario u;
        
        try {
            con.crearConexion(usConexion, passConexion);
            
            System.out.println("Conexion establecida exitosamente");

            for (int i = 0; i < lstUs.length; i++) {
                System.out.println("--------------------------------------------------------");
                try {

                    System.out.println("Usuario: " + lstUs[i]);
                    //1) lookup the ldap account
                    SearchResult srLdapUser = con.findAccountByAccountName(lstUs[i]);

                    //2) get the SID of the users primary group
                    String primaryGroupSID = con.getPrimaryGroupSID(srLdapUser);

                    System.out.println("primaryGroupSID: " + primaryGroupSID);

                    //3) get the users Primary Group
                    String primaryGroupName = con.findGroupBySID(primaryGroupSID);

                    System.out.println("primaryGroupName: " + primaryGroupName);

                    respuesta = con.buscarGrupo(lstUs[i], grupoABuscar);
                    System.out.println("Resultado busqueda de " + lstUs[i] + " en " + grupoABuscar + ": " + respuesta);
                    
                    //datos usuario
                    u= con.getUsuario(lstUs[i]);
                    System.out.println(u);
                    
                } catch (Exception e) {
                    System.out.println(e.getMessage() + ". Usuario " + lstUs[i] + " no encontrador en el AD");
                }
            }
            System.out.println("--------------------------------------------------------");
            System.out.println("--------------------------------------------------------");

            con.buscarMiembros(grupoABuscar);
            
            System.out.println("--------------------------------------------------------");
            System.out.println("--------------------------------------------------------");
            
            List<String> lista= con.listarGrupos(unUsuario);
            for(String a: lista){
                System.out.println(a);
            }

            con.cerrarConexion();
        } catch (NamingException ex) {
            System.err.println(ex.getMessage() + ". Error en la Conexión LDAP.");
            ex.printStackTrace();
        }
    }
}
