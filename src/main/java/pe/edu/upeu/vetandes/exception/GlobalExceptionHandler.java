package pe.edu.upeu.vetandes.exception;

import java.time.LocalDateTime;
import java.util.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.security.access.AccessDeniedException;
import pe.edu.upeu.vetandes.dto.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {
 private static final Logger log=LoggerFactory.getLogger(GlobalExceptionHandler.class);
 private ResponseEntity<ErrorResponseDTO> build(HttpStatus status,String message,HttpServletRequest req,Map<String,String> fields) {
  return ResponseEntity.status(status).body(new ErrorResponseDTO(LocalDateTime.now(),status.value(),status.getReasonPhrase(),message,req.getRequestURI(),fields));
 }
 @ExceptionHandler(ReglaNegocioException.class)
 public ResponseEntity<ErrorResponseDTO> domain(ReglaNegocioException ex,HttpServletRequest req) {
  log.warn("Regla rechazada: {}",ex.getMessage()); return build(ex.getStatus(),ex.getMessage(),req,null);
 }
 @ExceptionHandler(MethodArgumentNotValidException.class)
 public ResponseEntity<ErrorResponseDTO> validation(MethodArgumentNotValidException ex,HttpServletRequest req) {
  Map<String,String> fields=new LinkedHashMap<>();
  ex.getBindingResult().getFieldErrors().forEach(e->fields.putIfAbsent(e.getField(),e.getDefaultMessage()));
  return build(HttpStatus.BAD_REQUEST,"Datos inválidos",req,fields);
 }
 @ExceptionHandler({ConstraintViolationException.class, HttpMessageNotReadableException.class, MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
 public ResponseEntity<ErrorResponseDTO> badRequest(Exception ex,HttpServletRequest req) {
  return build(HttpStatus.BAD_REQUEST,"Datos inválidos: " + ex.getClass().getSimpleName(),req,null);
 }
 @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
 public ResponseEntity<ErrorResponseDTO> integrity(org.springframework.dao.DataIntegrityViolationException ex,HttpServletRequest req) {
  log.warn("Restricción de integridad rechazada en {}",req.getRequestURI());
  return build(HttpStatus.CONFLICT,"Conflicto con una restricción de integridad de datos",req,null);
 }
 @ExceptionHandler(AccessDeniedException.class)
 public ResponseEntity<ErrorResponseDTO> forbidden(AccessDeniedException ex,HttpServletRequest req) {
  return build(HttpStatus.FORBIDDEN,"No tiene permisos para realizar esta operación",req,null);
 }
 @ExceptionHandler(Exception.class)
 public ResponseEntity<ErrorResponseDTO> general(Exception ex,HttpServletRequest req) {
  log.error("Error inesperado en {}",req.getRequestURI(),ex);
  return build(HttpStatus.INTERNAL_SERVER_ERROR,"Error interno del servidor",req,null);
 }
}
