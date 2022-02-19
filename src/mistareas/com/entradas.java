package mistareas.com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;




import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;



public class entradas extends Activity {
	private EditText et1,et2,et3,et4;
	String ets1,ets2,ets3,ets4,ets5,ets6;
	String[] spinner01 = new String[4];
	Vector<String> listaspinner2 = new Vector<String>();
	
	String entrada="";
	String texto_salida="";
	int eleccion;
	boolean validar=false;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	     
	       
	        setContentView(R.layout.entradas);
	        
	        et1 = (EditText)findViewById(R.id.seg7); // fecha
			et2 = (EditText)findViewById(R.id.seg9); // concepto
			et3 = (EditText)findViewById(R.id.seg5); // valor
			et4 = (EditText)findViewById(R.id.seg3); // cantidad
	        
	        
			calgar_fecha();
	       calgar_tarea();
	            
	       
				
	    }

	
	public void entrada_trabajo(View view) throws IOException {
		// TODO Auto-generated method stub
	     
	        File externalDir = Environment.getExternalStorageDirectory();
			File textFile = new File(externalDir.getAbsolutePath()+ File.separator + "tareas.txt");
			
				ets1=listaspinner2.get(eleccion);// nombre tarea
				ets2=et1.getText().toString();// fecha
				ets3=et2.getText().toString();// concepto
				ets4=et4.getText().toString();// cantidad
				ets5=et3.getText().toString();// valor
				ets6="1";					   // asiento de trabajo
		
				
				if (listaspinner2.get(eleccion).equals("nueva tarea")){
					listaspinner2.add(ets3);
					calgar_tarea();
					et2.setText("tarea añadida");
					}
				
				
				else {
					entrada = ets1+","+ets2+","+ets3+","+ets4+","+ets5+","+ets6+";"+"\n";
				
			String texto = readTextFile(textFile);
	        String texto1 = texto+entrada;     
			writeTextFile(textFile, texto1);	
		
			et2.setText("apunte realizado");
			}
				 
				 
	}
	
	


	public void entrada_efectivo(View view) throws IOException {
		// TODO Auto-generated method stub
	     
	        File externalDir = Environment.getExternalStorageDirectory();
			File textFile = new File(externalDir.getAbsolutePath()+ File.separator + "tareas.txt");
			
				ets1=listaspinner2.get(eleccion);// nombre tarea
				ets2=et1.getText().toString();// fecha
				ets3=et2.getText().toString();// concepto
				ets4=et4.getText().toString();// cantidad
				ets5=et3.getText().toString();// valor
				ets6="2";					   // asiento de trabajo
		
				
				
				if (listaspinner2.get(eleccion).equals("nueva tarea")){
					listaspinner2.add(ets3);
					calgar_tarea();
					et2.setText("tarea añadida");
					
				} 
				
				else {
					entrada = ets1+","+ets2+","+ets3+","+ets4+","+ets5+","+ets6+";"+"\n";
				
			String texto = readTextFile(textFile);
	        String texto1 = texto+entrada;     
			writeTextFile(textFile, texto1);	
		
			 et2.setText("apunte realizado");
			}
				
				 
	}
	
	public void writeTextFile(File textFile, String texto) throws IOException {
		// TODO Auto-generated method stub
		BufferedWriter writer = new BufferedWriter(new FileWriter(textFile));
		writer.write(texto);
		writer.close();
	}
	private void calgar_fecha() {
		// TODO Auto-generated method stub
		Date today = new Date();
		 int dia_actual = today.getDate();
		 int mes_actual = today.getMonth()+1;
		 int año_actual = today.getYear()+1900;
		 String fecha_actual = dia_actual+"."+mes_actual+"."+año_actual;
		  
		 et1.setText(fecha_actual); 
	}
	
	
	private void calgar_tarea() {
		
		
		if(listaspinner2.size()==0)	{
		
		primera dato1= new primera();
		listaspinner2=dato1.calgar_archivo2();
		
		listaspinner2.add("nueva tarea");
		 }
	
		Spinner spinner01 = (Spinner) findViewById(R.id.Spinner01);
		
		ArrayAdapter<String> spinner02 = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, listaspinner2);
		spinner02.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
		spinner01.setAdapter(spinner02);
		spinner01.setSelection(eleccion);
		spinner01.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {
			 
		       
		   public void onItemSelected(AdapterView<?> adaptador, View v,
	        		int position, long id) {
	        	eleccion=position;
	        	
	        }
	    
			public void onNothingSelected(AdapterView<?> adaptador) {
	            // your code here
	        }
	 });
	
}

	String readTextFile(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		StringBuilder texto = new StringBuilder();
		String line;
		while ((line = reader.readLine())!=null){
			texto.append(line);
			texto.append("\n");
		
		}
		reader.close();
		return texto.toString();
	}
}