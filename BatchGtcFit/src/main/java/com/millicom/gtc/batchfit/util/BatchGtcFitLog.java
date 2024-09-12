package com.millicom.gtc.batchfit.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.millicom.gtc.batchfit.config.PropertiesConfig;
import com.millicom.gtc.batchfit.dto.properties.PropDto;



@Component
public class BatchGtcFitLog {
	
	@Autowired
	PropDto properties;
	
	@Value("${gtcfit.dirlog}")
    private String dirlog;
	
	@Value("${gtcfit.namelog}")
    private String namelog;

	public  void generarArchivo(String response) throws IOException {
			
			SimpleDateFormat dtf = new SimpleDateFormat("yyyyMMdd");
	        Calendar calendar = Calendar.getInstance();
	        Date dateObj = calendar.getTime();
	        String formattedDate = dtf.format(dateObj);
	        
	        SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	        String fechaRegistro = fr.format(dateObj);
	        String nombreLog = namelog;
	        String dirLog = dirlog;
	        
	        String nombreArchivo = dirLog+formattedDate+nombreLog+".log";
	        File archivo = new File(nombreArchivo);			
			FileWriter myWriter = new FileWriter(nombreArchivo,archivo.exists());
			try {
			myWriter.write("===================================== \n");
		    myWriter.write(fechaRegistro +" : ");
		    myWriter.write("TRACE: "+response+"\n");  
		    myWriter.close();  
		
		} 
				catch (IOException e) {
	    	
		}
				catch (Exception e) {
	    	
		}
			finally {
				myWriter.close();
		}
	}
}
