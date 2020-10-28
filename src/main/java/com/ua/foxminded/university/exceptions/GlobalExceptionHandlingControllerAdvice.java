package com.ua.foxminded.university.exceptions;//package com.ua.foxminded.university.exceptions;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.dao.DataAccessException;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.ejb.NoSuchEntityException;
//import javax.servlet.http.HttpServletRequest;
//import java.sql.SQLException;
//import java.util.Date;
//
//@ControllerAdvice
//public class GlobalExceptionHandlingControllerAdvice {
//
//    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandlingControllerAdvice.class);
//
//    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public void conflict() {
//        logger.error("Request raised a DataIntegrityViolationException");
//    }
//
//    @ExceptionHandler({SQLException.class, DataAccessException.class})
//    public String databaseError(Exception exception) {
//        logger.error("Request raised " + exception.getClass().getSimpleName());
//        return "exceptions/database";
//    }
//
//    @ExceptionHandler({ServiceException.class, NoSuchEntityException.class})
//    public ModelAndView handleError(HttpServletRequest req, Exception exception)
//            throws Exception {
//
//        if (AnnotationUtils.findAnnotation(exception.getClass(),
//                ResponseStatus.class) != null)
//            throw exception;
//
//        logger.error("Request: " + req.getRequestURI() + " raised " + exception);
//
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", exception);
//        mav.addObject("url", req.getRequestURL());
//        mav.addObject("timestamp", new Date().toString());
//        mav.addObject("status", 500);
//
//        mav.setViewName("exceptions/error");
//        return mav;
//    }
//}
