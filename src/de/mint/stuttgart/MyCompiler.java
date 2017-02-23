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

public class MyCompiler {

   private static final String packagename = "online";

   private boolean writeFile (String filename, String className, String code) throws IOException {

      		Path p = FileSystems.getDefault().getPath(filename);
      		BufferedWriter output = Files.newBufferedWriter(p,Charset.forName("UTF-8"));
            //PrintStream output = new PrintStream(new FileOutputStream(filename));
            //output.println("package " + packagename + ";");
            output.write("package " + packagename + ";");
            output.newLine();
            output.write("public class " + className + " {");
            output.newLine();
            output.write("   public static void main  (String[] args) {");
            output.newLine();
            output.write(code);
            output.newLine();
            output.write("   }");
            output.newLine();
            output.write("}");
            output.flush();

            output.close();

         return true;
   }


   private boolean compileFile (String filename) throws IOException {

            PrintWriter compilerErrorsOut = new PrintWriter(new FileOutputStream("compileerrors.txt"));
            int errorCode = com.sun.tools.javac.Main.compile(new String[] {
                  //"-classpath", ".",
                  //"-d", packagename,
                  filename }, compilerErrorsOut);
            if (errorCode != 0) {
               System.out.println(errorCode);
            }

            return true;
   }


   private boolean loadClass (String className)  {

      System.out.println("load class " + className);
      try {
         Class<?> newClass = Class.forName(className); 
         System.out.println(newClass);
         //newClass.main(args);
         java.lang.reflect.Method meth = newClass.getMethod("main", String[].class);
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
      } catch (IllegalAccessException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IllegalArgumentException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (InvocationTargetException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return true;


   }



   public String doCompile (String className, String code) throws IOException {
      System.out.println(className);
      System.out.println(code);

      String filename = packagename + "/" + className + ".java";
      System.out.println("Print to file  " + filename);

      if (!writeFile(filename, className, code))
         return "ERROR";

      compileFile(filename);

      loadClass(packagename + "." + className);

      return null;
   }




   public static void main (String[] args) throws IOException {
      MyCompiler c = new MyCompiler();

      c.doCompile(args[0], "System.out.println(\"Es tut wirklich\");");
   }

}
