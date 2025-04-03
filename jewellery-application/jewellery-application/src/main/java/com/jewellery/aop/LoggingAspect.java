package com.jewellery.aop;
 
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
 
@Aspect
@Component
public class LoggingAspect {
 
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
 
    @Pointcut("execution(* com.jewellery.service.CustomerServiceImpl.getAddressByCustomerId(..)) ||"
            + " execution(* com.jewellery.service.CustomerServiceImpl.updateCustomer(..)) ||"
            + " execution(* com.jewellery.service.CustomerServiceImpl.getCustomerById(..)) ||"
            + " execution(* com.jewellery.service.CustomerServiceImpl.getCartByCustomerId(..)) ||"
            
            + " execution(* com.jewellery.service.CategoryServiceImpl.addCategory(..)) ||"
            + " execution(* com.jewellery.service.CategoryServiceImpl.deleteCategory(..)) ||"
            + " execution(* com.jewellery.service.CategoryServiceImpl.updateCategory(..)) ||"
            + " execution(* com.jewellery.service.CategoryServiceImpl.searchCategoryByName(..)) ||"
            + " execution(* com.jewellery.service.CategoryServiceImpl.searchCategoryById(..)) ||"
            
            + " execution(* com.jewellery.service.ProductServiceImpl.addProduct(..)) ||"
            + " execution(* com.jewellery.service.ProductServiceImpl.updateProduct(..)) ||"
            + " execution(* com.jewellery.service.ProductServiceImpl.deleteProduct(..)) ||"
            + " execution(* com.jewellery.service.ProductServiceImpl.getProductById(..)) ||"
            + " execution(* com.jewellery.service.ProductServiceImpl.getAllProducts(..)) ||"
            + " execution(* com.jewellery.service.ProductServiceImpl.getProductsByCategoryName(..)) ||"
            
            + " execution(* com.jewellery.service.CartServiceImpl.addItemToCart(..)) ||"
            + " execution(* com.jewellery.service.CartServiceImpl.deleteItemFromCart(..)) ||"
            + " execution(* com.jewellery.service.CartServiceImpl.getCartItemsByCartId(..)) ||"
            + " execution(* com.jewellery.service.CartServiceImpl.calculateTotalPrice(..)) ||"
            
            + " execution(* com.jewellery.service.OrderServiceImpl.getOrdersByCustomerId(..)) ||"
            + " execution(* com.jewellery.service.OrderServiceImpl.getOrderById(..)) ||"
            + " execution(* com.jewellery.service.OrderServiceImpl.updateOrderAddress(..)) ||"
            + " execution(* com.jewellery.service.OrderServiceImpl.getOrdersByStatus(..)) ||"
            + " execution(* com.jewellery.service.OrderServiceImpl.placeOrder(..)) ||"
           
            + " execution(* com.jewellery.service.PaymentServiceImpl.getAllPayments(..)) ||"
            + " execution(* com.jewellery.service.PaymentServiceImpl.getPaymentById(..)) ||"
            + " execution(* com.jewellery.service.PaymentServiceImpl.getPaymentsByDateRange(..)) ||"
            + " execution(* com.jewellery.service.PaymentServiceImpl.getPaymentByOrderId(..)) ||"
            + " execution(* com.jewellery.service.PaymentServiceImpl.makePayment(..)) ||"
           
            + " execution(* com.jewellery.service.OrderItemServiceImpl.getOrderItemsByOrderId(..)) ||"
            
            + " execution(* com.jewellery.service.FeedbackServiceImpl.submitFeedback(..)) ||"
            + " execution(* com.jewellery.service.FeedbackServiceImpl.updateFeedback(..)) ||"
            + " execution(* com.jewellery.service.FeedbackServiceImpl.getAllFeedbacks(..)) ||"
            + " execution(* com.jewellery.service.FeedbackServiceImpl.getFeedbackById(..)) ||"
            + " execution(* com.jewellery.service.FeedbackServiceImpl.getFeedbacksByCustomerId(..)) ||"
            + " execution(* com.jewellery.service.FeedbackServiceImpl.deleteFeedback(..))")
    public void selectedMethods() {}
 
    @Before("selectedMethods()")
    public void logMethodCall(JoinPoint joinPoint) {
        logger.info("Calling method: {} with arguments: {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }
    
 
    @AfterReturning(pointcut = "selectedMethods()", returning = "result")
    public void logMethodReturn(JoinPoint joinPoint, Object result) {
        logger.info("Method: {} returned: {}", joinPoint.getSignature().getName(), result);
    }
    
 
    @AfterThrowing(pointcut = "selectedMethods()", throwing = "ex")
    public void logExceptions(JoinPoint joinPoint, Exception ex) {
        logger.error("Exception in method: {} with message: {}", joinPoint.getSignature().getName(), ex.getMessage(), ex);
    }
    
 
    @Around("selectedMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        logger.info("Method: {} executed in {} ms", joinPoint.getSignature().getName(), duration);
        return result;
    }
}