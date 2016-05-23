/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;

public class Usuario {
    private Attributes attr;
    private String distinguishedName;
    private String userPrincipal;
    private String commonName;
    private String memberof;

    public Usuario(Attributes attr) throws javax.naming.NamingException {
        userPrincipal = (String) attr.get("userPrincipalName").get();
        commonName = (String) attr.get("cn").get();
        distinguishedName = (String) attr.get("distinguishedName").get();
        try{
            memberof= (String) attr.get("memberOf").get();
        }catch(NullPointerException e){
            memberof= "";
        }
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
        return this.getMemberof().contains(unGrupo);
    }

    @Override
    public String toString() {
        StringBuilder sb= new StringBuilder();        
        NamingEnumeration ne= attr.getAll();
        while(ne.hasMoreElements()){
            sb.append(ne.nextElement());
            sb.append("\n");
        }
        return sb.toString();
    }
}
