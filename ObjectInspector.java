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
	
	HashMap<Object, Integer> ObjectsInspected = new HashMap<Object, Integer>();
	
    public ObjectInspector() { }

    //-----------------------------------------------------------
    public void inspect(Object obj, boolean recursive)
    {
	Vector objectsToInspect = new Vector();
	Class ObjClass = obj.getClass();
	
	
	ObjectsInspected.put(obj, 1);

	

	System.out.println("inside inspector: " + obj + " (recursive = "+recursive+")");
	
	System.out.println("------------");
	
	if(ObjClass.getSuperclass() != null){
		System.out.println("Immediate SuperClass: " + ObjClass.getSuperclass());	
	}
	
	else{
		System.out.println("This object has no superclass");
	}
	

	
	
	
	//inspect the current class

	inspectConstructors(obj, ObjClass, objectsToInspect);
	inspectFields(obj, ObjClass,objectsToInspect);
	inspectMethods(obj, ObjClass, objectsToInspect);	
	inspectInterfaces(obj, ObjClass, objectsToInspect);	
	
	System.out.println(objectsToInspect.size() + " objects found");
	//for(int i = 0; i < objectsToInspect.size(); i++) 
	//{
		System.out.println(objectsToInspect);
	//}
	System.out.println("DONE");
	
	//parseOutArrayContents
	
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
			
				if(!ObjectsInspected.containsKey(f.get(obj))) {
					
				System.out.println("******************");
				inspect( f.get(obj) , recursive);
				System.out.println("DONE RECURSING");
				
				System.out.println("******************");
			    
				}
				else {
					System.out.println("OBJECT ALREADY INSPECTED, SKIPPING" + f.get(obj));
				}
				
			}
		catch(Exception exp) { exp.printStackTrace(); }
	    }
    }
    
    
    //-----------------------------------------------------------
    private void inspectInterfaces(Object obj, Class ObjClass, Vector objectsToInspect)
    {
    	
    	System.out.println("=====Interfaces for: " + ObjClass.getName() + "=====");
    	
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
    	
    	
    	if(ObjClass.getSuperclass() != null) {
    		inspectInterfaces(obj, ObjClass.getSuperclass(), objectsToInspect);
    		
    	}
    	
    
    
    }
    	
    	
    	
    
    
    
    
    
    //-----------------------------------------------------------
    private void inspectConstructors(Object obj, Class ObjClass, Vector objectsToInspect)
    {
    	
    	
    	System.out.println("=====Constructors for: " + ObjClass.getName() + "=====");
    	
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
    	
    	if(ObjClass.getSuperclass() != null) {
    		inspectConstructors(obj, ObjClass.getSuperclass(), objectsToInspect);
    		
    	}
    	
    	
    	System.out.println("------Done Constructors------");
    	
    }
    
    private void inspectMethods(Object obj, Class ObjClass, Vector objectsToInspect)
    {
    	
    	System.out.println("=====Methods for: " + ObjClass.getName() + "=====");
    	
    	
    	if(ObjClass.getDeclaredMethods().length >= 1)
    	{
    		Method[] methods = ObjClass.getDeclaredMethods();
    		
    			for(int i = 0; i < methods.length; i++)
    			{
    				
    				System.out.println("------Method------");
    				
    				Method method = methods[i];
    				System.out.println("Name " + method.getName());
    				
    				Class[] exceptions = method.getExceptionTypes();
    				Class[] parameterTypes = method.getParameterTypes();
    				String modifiers = Modifier.toString(method.getModifiers());
    				Class returnType = method.getReturnType();
    				
    				
    				System.out.println("Modifiers: " + modifiers);
    				
    				System.out.printf("Parameters: ");
        			for(Class parameter:parameterTypes)
        			{
        				System.out.printf(parameter.getName() + ", ");
        				
        			}
        			System.out.println();
        			
        			System.out.printf("Exceptions: ");
        			for(Class exception: exceptions)
        			{
        				System.out.printf(exception.getName() + ", ");
        			}
        			System.out.println();
        			
        			System.out.println("Return Type: " + returnType.getName());
    				
    				
    			}
    		
    		
    	}
    	
    	if(ObjClass.getSuperclass() != null) {
    		inspectMethods(obj, ObjClass.getSuperclass(), objectsToInspect);
    		
    	}

    	
    	
    }
    
    
    //-----------------------------------------------------------
    private void inspectFields(Object obj,Class ObjClass,Vector objectsToInspect)
  
    {
    	
    	System.out.println("=====Fields for: " + ObjClass.getName() + "=====");
    	
	
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
	

	    }
	
		if(ObjClass.getSuperclass() != null)
		    inspectFields(obj, ObjClass.getSuperclass() , objectsToInspect);
    }
}
