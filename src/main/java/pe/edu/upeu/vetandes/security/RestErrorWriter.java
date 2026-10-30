package pe.edu.upeu.vetandes.security;

import java.io.IOException;
import java.time.LocalDateTime;
import jakarta.servlet.http.*;

public final class RestErrorWriter {
 private RestErrorWriter(){}
 private static String json(String s){return s==null?"":s.replace("\\","\\\\").replace("\"","\\\"").replace("\n"," ").replace("\r"," ");}
 public static void write(HttpServletRequest req,HttpServletResponse res,int status,String mensaje) throws IOException {
  res.setStatus(status);res.setContentType("application/json;charset=UTF-8");
  String error=status==401?"Unauthorized":"Forbidden";
  res.getWriter().write("{\"timestamp\":\""+LocalDateTime.now()+"\",\"status\":"+status+
   ",\"error\":\""+error+"\",\"mensaje\":\""+json(mensaje)+"\",\"ruta\":\""+json(req.getRequestURI())+"\"}");
 }
}
