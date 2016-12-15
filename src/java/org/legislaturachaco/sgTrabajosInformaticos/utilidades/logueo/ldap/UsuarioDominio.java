/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo.ldap;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;

public class UsuarioDominio {
    private String distinguishedName;
    private String userPrincipal;
    private String commonName;
    private String memberof;
    private String nombre, apellido;

    public UsuarioDominio(Attributes attr) throws javax.naming.NamingException {
        try{
            userPrincipal = (String) attr.get("userPrincipalName").get();
        }catch(NullPointerException e){
            userPrincipal= "";
            System.err.println("Error para obtener \"userPrincipal\": "+e.getLocalizedMessage());
        }
        commonName = (String) attr.get("cn").get();
        distinguishedName = (String) attr.get("distinguishedName").get();
        try{
            memberof= (String) attr.get("memberOf").get();
        }catch(NullPointerException e){
            memberof= "";
            System.err.println("Error para obtener \"memberof\": "+e.getLocalizedMessage());
        }
        this.nombre= (String) attr.get("givenname").get();
        this.apellido= (String) attr.get("sn").get();
    }

    public String getUserPrincipal() {
        return userPrincipal;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getDistinguishedName() {
        return distinguishedName;
    }

    public String getMemberof() {
        return memberof;
    }
    
    public boolean perteneceAGrupo(String unGrupo){
        //System.out.println(this.getMemberof()+" /// "+unGrupo);
        return this.getMemberof().toLowerCase().contains(unGrupo.toLowerCase());
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    @Override
    public String toString() {
        String separador= "; ";
        StringBuilder sb= new StringBuilder();
        /**
        NamingEnumeration ne= attr.getAll();
        while(ne.hasMoreElements()){
            sb.append(ne.nextElement());
            sb.append("\n");
        }*/
        sb.append(this.getClass());
        sb.append(separador);
        sb.append(this.getCommonName());
        sb.append(separador);
        sb.append(this.getDistinguishedName());
        sb.append(separador);
        sb.append(this.getMemberof());
        sb.append(separador);
        sb.append(this.getUserPrincipal());
        return sb.toString();
    }
}
