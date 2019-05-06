package de.mint.stuttgart;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.charset.Charset;
import java.io.BufferedWriter;

import com.sun.tools.javac.Main;


public class MyCompiler {


   private static class NameManager {
      private static final String packageName = "online";
      private static String className;
      private static String sessionID;
      public NameManager(String className, String sessionID) {
         this.className = className;
         this.sessionID = sessionID;
      }
      public String getFileName() {
         return packageName + "/" + className + ".java";
      }

      public String getPackageName() {
         return packageName;
      }
      public String getFullClassName() {
         return packageName + "." + className;
      }
   }

   private NameManager nm;



   private boolean compileFile (String filename) throws IOException {

      try {
         // PrintWriter(OutputStream out) 
      PrintWriter compilerErrorsOut = new PrintWriter(new FileOutputStream("compileerrors.txt"));
      int errorCode = Main.compile(
         new String[] {
            filename },
            compilerErrorsOut);
         if (errorCode != 0) {
            System.out.println(errorCode);
            return false;
         }
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }

      return true;
   }





   private boolean loadClass (String className)  {

      System.out.println("Load class " + className);
      try {
         Class<?> newClass = Class.forName(className);
         Method meth = newClass.getMethod("main", String[].class);
         System.out.println("Calling method " + meth);
         String[] params= {}; // init params accordingly
         meth.invoke(null, (Object) params);
         System.out.println("Das wars hier");
         // https://www.philipphauer.de/study/se/classloader.php
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      } catch (NoSuchMethodException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (SecurityException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (InvocationTargetException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return true;


   }



   public String doCompile (String className, String code)
         throws IOException {
      System.out.println("Print to file  " + nm.getFileName());

      if (!FileWriter.writeFile(nm.getFileName(), nm.getPackageName(),
            className, code))
         return "ERROR";

      compileFile(nm.getFileName());

      loadClass(nm.getFullClassName());

      return null;
   }




   public static void main (String[] args) throws IOException {
      MyCompiler c = new MyCompiler();
      c.nm = new NameManager(args[0],"1234");
      c.doCompile(args[0], "System.out.println(\"Es tut wirklich\");\n System.out.println(\"Yeah!\");");
   }

}
