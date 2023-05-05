import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;  

ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "run.bat");
File dir = new File("C:\\Users\\rohinis\\Desktop");
pb.directory(dir);
Process p = pb.start();