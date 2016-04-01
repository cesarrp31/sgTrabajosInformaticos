/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades;

/**
 *
 * @author coperalta
 */
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Jesús Alberto Sánchez Tecalco
 */
@SessionScoped
@Named(value = "reportBean")
public class InformesController implements Serializable {
    /*
    Ver
    http://www.adictosaltrabajo.com/tutoriales/java-bean-datasource/
    http://curiotecnology.blogspot.com.ar/2015/03/primefaces-jasperreports-reportes.html
    */
    private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    private String number;

    public void generateReport() {
        try {
            List<Object> countries = getListCountriesDummy();

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("fecha", Calendar.getInstance().getTime().toString());

            outputStream = JasperReportUtil.getOutputStreamFromReport(countries, map, getPathFileJasper());
            media = JasperReportUtil.getStreamContentFromOutputStream(outputStream, "application/pdf", getNameFilePdf());
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    public String getPathFileJasper() {
        return "/reportes/graficoTorta.jasper";
    }

    public String getNameFilePdf() {
        return "reporte_dummy.pdf";
    }

    public void downloadFile() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=" + getNameFilePdf());
            
            OutputStream output = response.getOutputStream();
            output.write(outputStream.toByteArray());
            output.close();
            
            facesContext.responseComplete();
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
    
    private List<Object> getListCountriesDummy() {
        List<Object> countries = new ArrayList<Object>();
        int max = Integer.parseInt(number);
        for (int i = 1; i <= max; i++) {
            //countries.add(new Country("ID" + i, "Pais " + i));
        }
        return countries;
    }

 public StreamedContent getMedia() {
        return media;
    }

    public void setMedia(StreamedContent media) {
        this.media = media;
    }
 
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}