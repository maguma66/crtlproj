package mistareas.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Spinner;
import android.widget.TextView;



public class resultados extends Activity{
	Vector<Vector<String>> fichero = new Vector<Vector<String>>();
	Vector<Vector<String>> tarea = new Vector<Vector<String>>();
	Vector<String> listaspinner3 = new Vector<String>();
	String texto;
	String texto1;
	String fecha;
	String[] text = new String [6];
	int posicion1 = 0;
	int trabajo_elegido = 0;
	String first,segundo,tercero;
	TextView lista;
	TextView lista1;
	TextView lista2;
	TextView lista3;
	TextView lista4;
	TextView total;
	
	int num;
	
	
	int eleccion=0;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	     
	       
	        setContentView(R.layout.listados);
	       
	         lista = (TextView)findViewById(R.id.vistas15);
			 lista1 = (TextView)findViewById(R.id.vistas16);
			 lista2 = (TextView)findViewById(R.id.vistas17);
			 lista3 = (TextView)findViewById(R.id.vistas18);
			 lista4 = (TextView)findViewById(R.id.vistas19);
			 total = (TextView)findViewById(R.id.vista05);
			
	        
	       calgar_obra();// carga el spinner
	       calgar_datos();// carga el fichero de datos guardados
	       calgar_tarea();// carga el fichero de la tarea elegida
	       ordenar();// ordenar los apuntes de la tarea
	       mostrar_lista();// mostrar la tarea elegida
				
	    }
	 


// RUTINA PARA CARGAR EL SPINNER CON LAS TAREAS EXISTENTE
	 private void calgar_obra() {
			
			primera dato1= new primera();
			listaspinner3=dato1.calgar_archivo2();
			
		
		
		
		Spinner spinner02 = (Spinner) findViewById(R.id.vista03);
			
			
			ArrayAdapter<String> spinner03 = new ArrayAdapter<String>(
					this, android.R.layout.simple_spinner_item, listaspinner3);
			spinner03.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    
			spinner02.setAdapter(spinner03);
			spinner02.setSelection(eleccion);
			spinner02.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {
				 
		        public void onItemSelected(AdapterView<?> adaptador, View v,
		        		int position, long id) {
		        	eleccion=position;
		        	calgar_tarea();
		        	ordenar();
		        	mostrar_lista();
		        	
		        }
		    
		        public void onNothingSelected(AdapterView<?> adaptador) {
		            
		        }
		 });
		}
		
//RUTINA PARA CARGAR EL FICHERO CON LOS DATOS GUARDADOS EN MEMORIA
	private void calgar_datos() {
		
		fichero.clear();
		File externalDir = Environment.getExternalStorageDirectory();
		File textFile = new File(externalDir.getAbsolutePath()
			+ File.separator + "tareas.txt");	


		try {
			texto = " "+readTextFile(textFile);
			int i = 0;
			int posicion1 = 0;
			int posicionf = texto.indexOf(";", posicion1);
		
		while (posicionf >= 0){
			int posicion2 = texto.indexOf(",", posicion1);
			int posicion3 = texto.indexOf(",", posicion2+1);
			int posicion4 = texto.indexOf(",", posicion3+1);
			int posicion5 = texto.indexOf(",", posicion4+1);
			int posicion6 = texto.indexOf(",", posicion5+1);
			
			text[0]=(texto.substring(posicion1+1, posicion2)); // nombre tarea
			text[1]=(texto.substring(posicion2+1, posicion3)); // fecha
			text[2]=(texto.substring(posicion3+1, posicion4)); // concepto
			text[3]=(texto.substring(posicion4+1, posicion5)); // cantidad
			text[4]=(texto.substring(posicion5+1, posicion6)); // precio
			text[5]=(texto.substring(posicion6+1, posicionf)); // tipo
			posicion1 = posicionf+1;
			posicionf = texto.indexOf(";", posicion1);
			
	         
			List<String> list = Arrays.asList(text);
			Vector<String> mivector = new Vector<String> (list);
			fichero.add( mivector);    
								
				i=i+1;
				
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 String readTextFile(File file) throws IOException {
		// TODO Auto-generated method stub
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
	
// RUTINA PARA CARGAR FICHERO CON LA TAREA ELEGIDA	 
	 private void calgar_tarea() {
			tarea.clear();
		 for (int i3 = 0; i3 < fichero.size(); i3=i3+1){
			 
			 if  (((Vector<String>)fichero.get(i3)).get(0).equals(listaspinner3.elementAt(eleccion))){
				 tarea.add(fichero.get(i3));
		 }
		}
		 
	 }
	 
// RUTINA PARA ORDENAR LA TAREA
	 private void ordenar() {
	 
			boolean prueba = true;

			do {prueba = false;
				for (int i=0; i<tarea.size()-1; i++){
					fecha = ((Vector<String>) tarea.elementAt(i)).elementAt(1);
					
					StringTokenizer tokens = new StringTokenizer(fecha, "."); 
					first = tokens.nextToken();
					segundo=tokens.nextToken();
					tercero=tokens.nextToken();
					
					Date date0 = new Date(Integer.parseInt(tercero),Integer.parseInt(segundo),Integer.parseInt(first));
					
					fecha = ((Vector<String>) tarea.elementAt(i+1)).elementAt(1);
					StringTokenizer tokens1 = new StringTokenizer(fecha, "."); 
					first = tokens1.nextToken();
					segundo=tokens1.nextToken();
					tercero=tokens1.nextToken();
					Date date1 = new Date(Integer.parseInt(tercero),Integer.parseInt(segundo),Integer.parseInt(first));
					
						if (date0.compareTo(date1) > 0){		
						
						tarea.add(i,tarea.get(i+1));
						if (i<tarea.size()-2){tarea.remove(i+2);}
						
						ordenar();
						}				
					}			
			}	
			while (prueba);
		}

//RUTINA PARA MOSTRAR EL LISTADO DE LA TAREA ELEGIDA
	private void mostrar_lista() {
		// TODO Auto-generated method stub
		String list="";
		String list1="";
		String list2="";
		String list3="";
		String list4="";
		int result=0;
		int total1=0;
		
		
		for (int i3 = 0; i3 < tarea.size(); i3=i3+1){
			
			
			result=	Integer.parseInt(((Vector<String>)tarea.get(i3)).get(3))*Integer.parseInt(((Vector<String>)tarea.get(i3)).get(4));		
			
			
			list=list+(((Vector<String>)tarea.get(i3)).get(1)+"\n");
			list1=list1+"   "+(((Vector<String>)tarea.get(i3)).get(2)+"\n");
			list2=list2+(((Vector<String>)tarea.get(i3)).get(3)+"\n");
			list3=list3+(((Vector<String>)tarea.get(i3)).get(4)+"\n");
			
			
			if  (((Vector<String>)tarea.get(i3)).get(5).equals("1")){
			list4=list4+String.valueOf(result)+"\n";
			total1=total1+result;}
			else{
		
			list4=list4+"- "+String.valueOf(result)+"\n";
			total1=total1-result;
			}
			
			
		}
		
		lista.setText(list);
		lista1.setText(list1);
		lista2.setText(list2);
		lista3.setText(list3);
		lista4.setText(list4);
		total.setText(String.valueOf(total1)+" €");
		
	}

	
// RUTINA PARA CREAR MENU PARA ELEGIR OPCIONES
	@Override
	  public boolean onCreateOptionsMenu (Menu me){
		String cadena="BORRAR TAREA";
		String cadena1="BORRAR ULTIMO APUNTE";
		String cadena2="CREAR PDF DE TAREA";
		  me.add(0,1,0,cadena).setIcon(R.drawable.ic_menu_tag);
		  me.add(0,2,0,cadena1).setIcon(R.drawable.ic_menu_tags);
		  me.add(0,3,0,cadena2).setIcon(R.drawable.pdfimport);
		 
		  
		  return true;
	  }
	  
	  @Override
	  public boolean onOptionsItemSelected(MenuItem it){
		  
		  switch(it.getItemId())
		  {
		  case 1:
			  borrar_tarea();
			  
			  break;
		  case 2:
			  borrar_apunte();
			 
			  break;
		  
		  case 3:
			  
			  crear_pdf();
			  
			  break;
			 
			  default:
				  break;
				  }
		  return true;
		  }

// RUTINA PARA BORRAR EL ULTIMO APUNTE DE LA TAREA ELEGIDA	  
	private void borrar_apunte() {
		if (fichero.size()==1){texto1="";}
		else {
		for (int i3 = fichero.size()-1; i3 > 0; i3=i3-1){
			if  ((fichero.get(i3)).get(0).equals(listaspinner3.elementAt(eleccion))){
				fichero.remove(i3);
				i3=0;
			}
		}
		texto1="";
		
		
			for (int i=0; i<fichero.size(); i++){
			String texto2=((Vector<String>)fichero.get(i)).get(0);
	    	String texto3=((Vector<String>)fichero.get(i)).get(1);
	    	String texto4=((Vector<String>)fichero.get(i)).get(2);
	    	String texto5=((Vector<String>)fichero.get(i)).get(3);
	    	String texto6=((Vector<String>)fichero.get(i)).get(4);
	    	String texto7=((Vector<String>)fichero.get(i)).get(5);
			String texto8=texto2+","+texto3+","+texto4+","+texto5+","+texto6+","+texto7+";"+"\n";
			texto1=texto1+texto8;
			
		}
		}
		
		
		
		File externalDir = Environment.getExternalStorageDirectory();
		File textFile = new File(externalDir.getAbsolutePath()
			+ File.separator + "tareas.txt");	
		entradas obj=new entradas();
		try {
			obj.writeTextFile(textFile, texto1);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
			
		   
	       calgar_datos();// carga el fichero de datos guardados
	       calgar_tarea();// carga el fichero de la tarea elegida
	       ordenar();// ordenar los apuntes de la tarea
	       mostrar_lista();// mostrar la tarea elegida
		}  
	
// RUTINA PARA BORRAR UNA TAREA COMPLETA
	private void borrar_tarea() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this) 
		.setIcon(android.R.drawable.ic_dialog_alert) 
		.setTitle("BORRAR TAREA")        
		.setMessage("Esta seguro de borrar esta tarea?")     
		.setPositiveButton("Si", new DialogInterface.OnClickListener()  
		{     
			@Override    
			public void onClick(DialogInterface dialog, int which) { 
				int fich=fichero.size()-1;
				for (int i3 = fich; i3>=0; i3--){
					if  ((fichero.get(i3)).get(0).equals(listaspinner3.elementAt(eleccion))){
						fichero.remove(i3);
					}
				}
				texto1="";

				for (int i=0; i<fichero.size(); i++){
					String texto2=((Vector<String>)fichero.get(i)).get(0);
			    	String texto3=((Vector<String>)fichero.get(i)).get(1);
			    	String texto4=((Vector<String>)fichero.get(i)).get(2);
			    	String texto5=((Vector<String>)fichero.get(i)).get(3);
			    	String texto6=((Vector<String>)fichero.get(i)).get(4);
			    	String texto7=((Vector<String>)fichero.get(i)).get(5);
					String texto8=texto2+","+texto3+","+texto4+","+texto5+","+texto6+","+texto7+";"+"\n";
					texto1=texto1+texto8;
					
				}
					
				File externalDir = Environment.getExternalStorageDirectory();
				File textFile = new File(externalDir.getAbsolutePath()
					+ File.separator + "tareas.txt");	
				entradas obj=new entradas();
				try {
					obj.writeTextFile(textFile, texto1);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				eleccion=0;
				   calgar_obra();// carga el spinner
			       calgar_datos();// carga el fichero de datos guardados
			       calgar_tarea();// carga el fichero de la tarea elegida
			       ordenar();// ordenar los apuntes de la tarea
			       mostrar_lista();// mostrar la tarea elegida
				}  
			})   
			.setNegativeButton("No", null)   
			.show(); 
	}
	
	// RUTINA PARA CREAR UN PDF CON LA TAREA ELEGIDA
	private void crear_pdf() {
		// TODO Auto-generated method stub
		String nombre_tarea=listaspinner3.elementAt(eleccion);
		int res;
		int totgasto=0;
		int totventa=0;
		
		 try {
			 OutputStream file = new FileOutputStream(new File("/sdcard/"+nombre_tarea+".pdf"));

	            Document document = new Document();
	            PdfWriter.getInstance(document, file);
	            
	            document.open();
	            
	            
            	
	            // NUEVA TABLA
	            PdfPTable mitabla=new PdfPTable(7);
	            mitabla.setWidthPercentage(100);
	            // TITULO
	            PdfPCell micelda =new PdfPCell (new Paragraph("INFORME:  "+nombre_tarea,FontFactory.getFont("arial",18,Font.BOLD)));
	            micelda.setColspan(7);
	            micelda.setBorder(Rectangle.NO_BORDER);
	            micelda.setHorizontalAlignment(Element.ALIGN_LEFT);
	            mitabla.addCell(micelda);
	           
	            Date fechaActual = new Date();
	            SimpleDateFormat formato = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es_ES"));
	            String cadenaFecha = formato.format(fechaActual);
	           
	           
	            // SEPARADOR DATOS
	            micelda =new PdfPCell (new Paragraph(" "));
	            micelda.setColspan(7);
	            micelda.setBorder(Rectangle.NO_BORDER);
	            mitabla.addCell(micelda);
	            // TITULOS
	            
	            micelda =new PdfPCell (new Paragraph("FECHA"));
	            micelda.setHorizontalAlignment(Element.ALIGN_CENTER);
	            mitabla.addCell(micelda);
	            
	            micelda =new PdfPCell (new Paragraph("DESCRIPCION"));
	            micelda.setColspan(3);
	            
	            micelda.setHorizontalAlignment(Element.ALIGN_CENTER);
	            mitabla.addCell(micelda);
	            micelda =new PdfPCell (new Paragraph("UNIDADES"));
	            
	            micelda.setHorizontalAlignment(Element.ALIGN_CENTER);
	            mitabla.addCell(micelda);
	            micelda =new PdfPCell (new Paragraph("PRECIO"));
	            
	            micelda.setHorizontalAlignment(Element.ALIGN_CENTER);
	            mitabla.addCell(micelda);
	            micelda =new PdfPCell (new Paragraph("TOTAL"));
	            
	            micelda.setHorizontalAlignment(Element.ALIGN_CENTER);
	            mitabla.addCell(micelda);
	            // CUERPO DE CONCEPTOS
	            for (int i3 = 0; i3 < tarea.size(); i3=i3+1){
	            	res=Integer.parseInt(((Vector<String>)tarea.get(i3)).get(3))*Integer.parseInt(((Vector<String>)tarea.get(i3)).get(4));
	            
	            	
	            	
	            micelda =new PdfPCell (new Paragraph(((Vector<String>)tarea.get(i3)).get(1)));//fecha
		        micelda.setHorizontalAlignment(Element.ALIGN_CENTER);
		        mitabla.addCell(micelda);
	            micelda =new PdfPCell (new Paragraph(((Vector<String>)tarea.get(i3)).get(2)));//descripcion
	            micelda.setColspan(3);
	            micelda.setHorizontalAlignment(Element.ALIGN_LEFT);
	            mitabla.addCell(micelda);
	            micelda =new PdfPCell (new Paragraph(((Vector<String>)tarea.get(i3)).get(3)));//unidades
	            micelda.setHorizontalAlignment(Element.ALIGN_CENTER);
	            mitabla.addCell(micelda);
	            
	            if (((Vector<String>)tarea.get(i3)).get(5).equals("1")){ 
	            
	            micelda =new PdfPCell (new Paragraph(((Vector<String>)tarea.get(i3)).get(4)));//precio
	            micelda.setHorizontalAlignment(Element.ALIGN_CENTER);
	            mitabla.addCell(micelda);
	            
	            
	            micelda =new PdfPCell (new Paragraph(String.valueOf(res)));//total
	            micelda.setHorizontalAlignment(Element.ALIGN_CENTER);
	            mitabla.addCell(micelda);
	            totgasto=totgasto+res;
	            }
	            
	            if (((Vector<String>)tarea.get(i3)).get(5).equals("2")){ 
		            
		            micelda =new PdfPCell (new Paragraph(((Vector<String>)tarea.get(i3)).get(4)));//precio
		            micelda.setHorizontalAlignment(Element.ALIGN_CENTER);
		            mitabla.addCell(micelda);
		            
		            
		            micelda =new PdfPCell (new Paragraph("- "+String.valueOf(res)));//total
		            micelda.setHorizontalAlignment(Element.ALIGN_CENTER);
		            mitabla.addCell(micelda);
		            totventa=totventa+res;
		            }	
	            
	            }
	            //TOTALES
	            micelda =new PdfPCell (new Paragraph(" "));
	            micelda.setColspan(4);
	            micelda.setBorder(Rectangle.NO_BORDER);
	            mitabla.addCell(micelda);
	            
	            micelda =new PdfPCell (new Paragraph("TOTAL GASTOS"));
	            micelda.setColspan(2);
	            micelda.setHorizontalAlignment(Element.ALIGN_CENTER);
	            mitabla.addCell(micelda);
	            
	            micelda =new PdfPCell (new Paragraph(totgasto+" €"));
	            micelda.setHorizontalAlignment(Element.ALIGN_CENTER);
	            mitabla.addCell(micelda);
	            
	            micelda =new PdfPCell (new Paragraph(" "));
	            micelda.setColspan(4);
	            micelda.setBorder(Rectangle.NO_BORDER);
	            mitabla.addCell(micelda);
	            
	            micelda =new PdfPCell (new Paragraph("TOTAL VENTAS"));
	            micelda.setColspan(2);
	            micelda.setHorizontalAlignment(Element.ALIGN_CENTER);
	            mitabla.addCell(micelda);
	            
	            micelda =new PdfPCell (new Paragraph(totventa+" €"));
	            micelda.setHorizontalAlignment(Element.ALIGN_CENTER);
	            mitabla.addCell(micelda);
	            
	            micelda =new PdfPCell (new Paragraph(" "));
	            micelda.setColspan(4);
	            micelda.setBorder(Rectangle.NO_BORDER);
	            mitabla.addCell(micelda);
	            
	            micelda =new PdfPCell (new Paragraph("TOTAL RESULTADOS"));
	            micelda.setColspan(2);
	            micelda.setHorizontalAlignment(Element.ALIGN_CENTER);
	            mitabla.addCell(micelda);
	            int tot=totgasto-totventa;
	            micelda =new PdfPCell (new Paragraph(tot+" €"));
	            micelda.setHorizontalAlignment(Element.ALIGN_CENTER);
	            mitabla.addCell(micelda);
	            
	            micelda =new PdfPCell (new Paragraph(" "));
	            micelda.setColspan(7);
	            micelda.setBorder(Rectangle.NO_BORDER);
	            mitabla.addCell(micelda);
	            
	            micelda =new PdfPCell (new Paragraph("Benalmadena-costa a  "+cadenaFecha,FontFactory.getFont("arial",15,Font.ITALIC)));
	            micelda.setColspan(7);
	            micelda.setBorder(Rectangle.NO_BORDER);
	            micelda.setHorizontalAlignment(Element.ALIGN_CENTER);
	            mitabla.addCell(micelda);
	            
	            document.add(mitabla);
	           
            	document.close();
	            file.close();

	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	        
	       
	            
	}

	
	}

