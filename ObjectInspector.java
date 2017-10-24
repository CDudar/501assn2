/*==========================================================================
File: ObjectInspector.java
Purpose:Demo Object inspector for the Asst2TestDriver

Location: University of Calgary, Alberta, Canada
Created By: Jordan Kidney
Created on:  Oct 23, 2005
Last Updated: Oct 23, 2005

***********************************************************************
If you are going to reproduce this code in any way for your asignment 
rember to include my name at the top of the file toindicate where you
got the original code from
***********************************************************************


========================================================================*/




import java.util.*;
import java.lang.reflect.*;


public class ObjectInspector
{
    public ObjectInspector() { }

    //-----------------------------------------------------------
    public void inspect(Object obj, boolean recursive)
    {
	Vector objectsToInspect = new Vector();
	Class ObjClass = obj.getClass();

	

	System.out.println("inside inspector: " + obj + " (recursive = "+recursive+")");
	
	System.out.println("------------");
	
	if(ObjClass.getSuperclass() != null){
		System.out.println("Immediate SuperClass: " + ObjClass.getSuperclass());	
	}
	
	else{
		System.out.println("This object has no superclass");
	}
	

	
	
	
	//inspect the current class
	inspectInterfaces(obj, ObjClass, objectsToInspect);
	inspectConstructors(obj, ObjClass, objectsToInspect);
	inspectFields(obj, ObjClass,objectsToInspect);
	
	if(recursive)
	    inspectFieldClasses( obj, ObjClass, objectsToInspect, recursive);
	   
    }
    
    //-----------------------------------------------------------
    
    
    
    
    
    
    //-----------------------------------------------------------
    private void inspectFieldClasses(Object obj,Class ObjClass,
				     Vector objectsToInspect,boolean recursive)
    {
	
	if(objectsToInspect.size() > 0 )
	    System.out.println("---- Inspecting Field Classes ----");
	
	Enumeration e = objectsToInspect.elements();
	while(e.hasMoreElements())
	    {
		Field f = (Field) e.nextElement();
		System.out.println("Inspecting Field: " + f.getName() );
		
		try
		    {
			System.out.println("******************");
			inspect( f.get(obj) , recursive);
			System.out.println("******************");
		    }
		catch(Exception exp) { exp.printStackTrace(); }
	    }
    }
    
    
    //-----------------------------------------------------------
    private void inspectInterfaces(Object obj, Class ObjClass, Vector objectsToInspect)
    {
    	
    	if(ObjClass.getInterfaces() != null )
    	{
    	
    		Class[] interfaces = ObjClass.getInterfaces();
    		

    		
    			for(int i = 0; i < interfaces.length; i++)
    			{
    				
    				Class inter = interfaces[i];
    				System.out.printf("Interfaces: " + inter.getName());
    				
    			}
    		System.out.printf("\n");	
    			
    			
    	}
    	else{
    		System.out.println("No interfaces");
    		
    	}
    
    
    }
    	
    	
    	
    
    
    
    
    
    //-----------------------------------------------------------
    private void inspectConstructors(Object obj, Class ObjClass, Vector objectsToInspect)
    {
    	
    	if(ObjClass.getConstructors().length >= 1)
    	{
    		
    		Constructor[] constructors  = ObjClass.getDeclaredConstructors();
    		
    		for(int i = 0; i < constructors.length; i++)
    		{
    			Constructor constructor = constructors[i];
    			Class[] parameterTypes = constructor.getParameterTypes();
    			
    			String modifiers = Modifier.toString(constructor.getModifiers());
    			
    			System.out.printf("------Constructor------"
    					+ "\nModifier(s): " + modifiers + "\n");
    			
    			System.out.printf("Parameters: ");
    			for(Class parameter:parameterTypes)
    			{
    				System.out.printf(parameter.getName() + ", ");
    				
    			}
    			System.out.println();
    			
    			
    		}
    		
    		
    		
    	}
    	
    	
    	System.out.println("------Done Constructors------");
    	
    }
    
    private void inspectMethods(Object obj, Class ObjClass, Vector objectsToInspect)
    {
    	
    	
    	
    	
    }
    
    
    //-----------------------------------------------------------
    private void inspectFields(Object obj,Class ObjClass,Vector objectsToInspect)
  
    {
	
		if(ObjClass.getDeclaredFields().length >= 1)
	    {
		
			Field[] fields = ObjClass.getDeclaredFields();
			
	
			for(int i = 0; i < fields.length; i ++){
				
				Field field = fields[i];
				field.setAccessible(true);
				
				
				if(! field.getType().isPrimitive() ) 
				    objectsToInspect.addElement( field );
				
				
				System.out.println("------Field------");
				
				try{
				System.out.println("Field: " + field.getName() + " = " + field.get(obj));
				System.out.println("Field Type: " + field.getType() );
				//System.out.println("Field Type: " + field.getGenericType() );
				System.out.println("Modifiers: " + Modifier.toString(field.getModifiers()));
				
				}
				catch(IllegalArgumentException e){
					System.out.println("This object " + ObjClass.getName()
					+ "does not specify the field" + field.getName());
				}
				catch(IllegalAccessException e1){
					System.out.println("Access not allowed for " + field.getName());
				}
				
			}
	
		if(ObjClass.getSuperclass() != null)
		    inspectFields(obj, ObjClass.getSuperclass() , objectsToInspect);
	    }
		
    }
}
