package mistareas.com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


public class primera extends Activity {
	private EditText ini1,ini2,ini3;
	
	String fecha;
	String texto;
	int nlista = 0;
	Vector<String> listaspinner = new Vector<String>();
	Vector<String> tareas = new Vector<String>();
	Vector<Vector<String>> fichero = new Vector<Vector<String>>();
	String[] vector= new String[6];
	int posicion1 = 0;
	int totalgasoil = 0;
	int totaltaller = 0;
	int totalseguro = 0;
	String first;
	String segundo;
	String tercero;
	int list;
	int eleccion;
	
	String[] slista;
	String[] archivos;
	
    
			
    /** Called when the activity is first created. 
     * @param Vector */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        setContentView(R.layout.main);
       
        ini1 = (EditText)findViewById(R.id.widget9);
		ini2 = (EditText)findViewById(R.id.widget10);
		ini3 = (EditText)findViewById(R.id.EditText01);
        
        calgar_archivo();
        calgar_obras();
        mostrar_totales();      
       
			
    }



	private void mostrar_totales() {
		// TODO Auto-generated method stub
		int total1=0;// total de tarea
		int total2=0;// total de todas las tareas
		//int valor=0;
		int result=0;
		
		
		
for (int i3 = 0; i3 < fichero.size(); i3=i3+1){
	String tarea1 = (((Vector<String>)fichero.get(i3)).get(0));
	String tarea2 = listaspinner.get(eleccion);
	result=Integer.parseInt(((Vector<String>)fichero.get(i3)).get(3))*Integer.parseInt(((Vector<String>)fichero.get(i3)).get(4));
	
	// suma o recta los valores de  todas las tareas
		if (Integer.parseInt(((Vector<String>)fichero.get(i3)).get(5))==1){
			total2=total2+result;
		} else {
			total2=total2-result;
		}
	// totaliza una tarea	

	if  (tarea1.equals(tarea2)){
			if (Integer.parseInt(((Vector<String>)fichero.get(i3)).get(5))==1){
		total1=total1+result;
			}else total1=total1-result;
			}
	
}
	
		ini1.setText(String.valueOf(total1)+" €");
		ini2.setText(String.valueOf(total2)+" €");



}
	public void calgar_archivo() {
		// TODO Auto-generated method stub
		 	File externalDir = Environment.getExternalStorageDirectory();
			File textFile = new File(externalDir.getAbsolutePath()+ File.separator + "tareas.txt");
			archivos = externalDir.list();
			if (existe(archivos,"tareas.txt")){
				
				try {
					texto = " "+readTextFile(textFile);		
				} catch (IOException e) {		
					e.printStackTrace();
				}		
			} else {try {
				inici_archivo();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
			
			int i = 0;
			int posicion1 = 0;
			int posicionf = texto.indexOf(";", posicion1);
		
		while (posicionf >= 0){
			
			int posicion2 = texto.indexOf(",", posicion1);
			int posicion3 = texto.indexOf(",", posicion2+1);
			int posicion4 = texto.indexOf(",", posicion3+1);
			int posicion5 = texto.indexOf(",", posicion4+1);
			int posicion6 = texto.indexOf(",", posicion5+1);
			
			vector[0]=(texto.substring(posicion1+1, posicion2)); // nombre tarea
			vector[1]=(texto.substring(posicion2+1, posicion3)); // fecha
			vector[2]=(texto.substring(posicion3+1, posicion4)); // concepto
			vector[3]=(texto.substring(posicion4+1, posicion5)); // cantidad
			vector[4]=(texto.substring(posicion5+1, posicion6)); // precio
			vector[5]=(texto.substring(posicion6+1, posicionf)); // tipo
			
			posicion1 = posicionf+1;
			posicionf = texto.indexOf(";", posicion1);
			           
				List<String> list = Arrays.asList(vector);
				Vector<String> mivector = new Vector<String> (list);
				fichero.add( mivector);
				
				
							
				i=i+1;
		}

	}

	 public void inici_archivo() throws IOException {		
		 File externalDir = Environment.getExternalStorageDirectory();
			File textFile = new File(externalDir.getAbsolutePath()
					+ File.separator + "tareas.txt");
	         
	        String texto1 = "";
			writeTextFile(textFile, texto1);
			calgar_archivo();
	}



	 public void writeTextFile(File file, String texto) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(texto);
		writer.close();
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



	private boolean existe(String[] archivos2, String archbusca) {
		// TODO Auto-generated method stub
		boolean bo=true;
		for (int f=0; f<archivos2.length; f++)
			if (archbusca.equals(archivos2[f])){
				bo= true;
				break;
			}else bo = false;
				
		return bo;
		

	}



	private void calgar_obras() {
		// TODO Auto-generated method stub
		
		if (fichero.size()==1){listaspinner.add(fichero.get(0).get(0));}
			for (int i4 = 1; i4 < fichero.size(); i4=i4+1){
			listaspinner.add(((Vector<String>)fichero.get(i4)).get(0));
			}
			
			HashSet<String> hs = new HashSet<String>();
			hs.addAll(listaspinner);
			listaspinner.clear();
			listaspinner.addAll(hs);
		
		Spinner spinner01 = (Spinner) findViewById(R.id.Spinner02);
		
		ArrayAdapter<String> spinner02 = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item,listaspinner);
		spinner02.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
		spinner01.setAdapter(spinner02);
		spinner01.setSelection(eleccion);
		spinner01.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {
			 
		       
		   public void onItemSelected(AdapterView<?> adaptador, View v,
	        		int position, long id) {
	        	eleccion=position;
	        	mostrar_totales();
	        	
	        }
	    
	       

			public void onNothingSelected(AdapterView<?> adaptador) {
	            // your code here
	        }
	 });
	
	}

	 public void entradas(View view){
	 		Intent intent = new Intent(primera.this, entradas.class);

	 		startActivity(intent);  	   
	    }
	    
	    public void resultado(View view){
	 	   
	 		Intent intent = new Intent(primera.this, resultados.class);
	 		
	 		startActivity(intent);  	   
	   }
	
	    public void cerrar(View view)
	    {
	    	if (ini3.getVisibility()==View.VISIBLE){
	    		ini3.setVisibility(View.INVISIBLE);
	    	}
	    	else{
	    		setResult(RESULT_OK);
	    		finish(); 
	    	}
	    		 
	    }

	    public void actualizar(View view)
	    {	finish();
	    	Intent refresh = new Intent(this, primera.class); 
	    	startActivity(refresh);
	    	 
	    }


		public Vector<String> calgar_archivo2() {
			// TODO Auto-generated method stub
			calgar_archivo();
			if (fichero.size()==1){listaspinner.add(fichero.get(0).get(0));}
			for (int i4 = 1; i4 < fichero.size(); i4=i4+1){
				listaspinner.add(((Vector<String>)fichero.get(i4)).get(0));
				}
			
			HashSet<String> hs = new HashSet<String>();
			hs.addAll(listaspinner);
			listaspinner.clear();
			listaspinner.addAll(hs);

			
			return listaspinner;
		}
		
		@Override
		  public boolean onCreateOptionsMenu (Menu me){
			String cadena="AYUDA";
			
			  me.add(0,1,0,cadena).setIcon(R.drawable.ic_menu_tag);
			 
			  return true;
		  }
		  
		  @Override
		  public boolean onOptionsItemSelected(MenuItem it){
			  
			  switch(it.getItemId())
			  {
			  case 1:
				  ini3.setVisibility(View.VISIBLE);
				  
				  break;
				 
				  default:
					  break;
					  }
			  return true;
			  }
		
		
}