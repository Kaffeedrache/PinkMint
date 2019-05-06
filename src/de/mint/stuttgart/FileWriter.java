package de.mint.stuttgart;

import java.io.PrintWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.charset.Charset;
import java.io.BufferedWriter;


public class FileWriter {


   private static String tab = "\t";


   private static void writeLine(BufferedWriter out, int tabs, String line)
         throws IOException {
      String mytabs = "";
      for (int i=0; i<tabs; i++) {
         mytabs += tab;
      }
      out.write(mytabs + line);
      out.newLine();
   }


   private static void writeLine(BufferedWriter out, String line)
         throws IOException {
      writeLine(out, 0, line);
   }


   public static boolean writeFile (String filename, String packagename,
         String className, String code)
         throws IOException {

		Path p = FileSystems.getDefault().getPath(filename);
		BufferedWriter output = Files.newBufferedWriter(p,Charset.forName("UTF-8"));

      writeLine(output, "package " + packagename + ";");
      writeLine(output, "public class " + className + " {");
      writeLine(output, 1, "public static void main  (String[] args) {");

      String[] codelines = code.split("\n");

      for (String line : codelines) {
         writeLine(output, 2, line);
      }

      writeLine(output, 1, "}");
      writeLine(output, "}");

      output.flush();
      output.close();

      return true;
   }







}
