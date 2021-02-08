package com.example.roundfood.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.roundfood.controller.collectdata.CustomerDataHandler;
import com.example.roundfood.controller.collectdata.OrderDataHandler;
import com.example.roundfood.controller.collectdata.OrderLineItemDataHandler;
import com.example.roundfood.controller.collectdata.PaymentOptionDataHandler;
import com.example.roundfood.model.Customer;
import com.example.roundfood.model.Order;
import com.example.roundfood.model.OrderLineItem;
import com.example.roundfood.model.PaymentOption;
import com.example.roundfood.service.DateAndTime;

@Controller
public class OrderController {

	@Autowired
	OrderDataHandler orderDataHandler;
	
	@Autowired
	OrderLineItemDataHandler orderLineItemDataHandler;
	
	@Autowired
	CustomerDataHandler customerDataHandler;
	
	@Autowired
	DateAndTime dateAndTime;
	
	@Autowired
	PaymentOptionDataHandler paymentOptionDataHandler;
	
	@RequestMapping(value = "/ordering", method = RequestMethod.GET)
    public String renderCartPage(Model model,
    							HttpServletRequest httpServletRequest) {
		Long customerId = (Long) httpServletRequest.getSession().getAttribute("customer_id");
        String customerName = (String) httpServletRequest.getSession().getAttribute("customer_name");
        Long openedorderId = (Long) httpServletRequest.getSession().getAttribute("openedorder_id");
        Integer numberOfOrderItems = (Integer) httpServletRequest.getSession().getAttribute("number_of_order_items");
        
        Customer customer = customerDataHandler.getCustomerById(customerId);
        
        if (openedorderId != null) {
            Order order = orderDataHandler.getOrderById(openedorderId);
            double totalPrice = orderDataHandler.getTotalPrice(order);
            
            model.addAttribute("order", order);
            model.addAttribute("totalPrice", totalPrice);
            HashMap<String, List<Date>> choosableShippingDatesAndTimes = dateAndTime.getChoosableShippingDatesAndTimes();
            model.addAttribute("choosableDays", choosableShippingDatesAndTimes.get("choosableDays"));
            model.addAttribute("choosableShippingDates", choosableShippingDatesAndTimes.get("choosableShippingDates"));
            
            List<PaymentOption> paymentOptions = paymentOptionDataHandler.getAllPaymentOptions();
            model.addAttribute("paymentOptions", paymentOptions);
        } else {
        	model.addAttribute("empty", true);
        }
        
        model.addAttribute("loggedIn", customerId != null);
        model.addAttribute("customername", customerName);
        model.addAttribute("customer", customer);
        httpServletRequest.getSession().setAttribute("openedorder_id", openedorderId);
		httpServletRequest.getSession().setAttribute("number_of_order_items", numberOfOrderItems);
        
        return "ordering";
    }
	
	@RequestMapping(value = "/ordering/delete-item", method = RequestMethod.POST)
	public String deleteOrderLineItem(Model model,
									HttpServletRequest httpServletRequest,
									@RequestParam Map<String,String> allRequestParams) {
		Long customerId = (Long) httpServletRequest.getSession().getAttribute("customer_id");
        String customerName = (String) httpServletRequest.getSession().getAttribute("customer_name");
        Long openedorderId = (Long) httpServletRequest.getSession().getAttribute("openedorder_id");
        Integer numberOfOrderItems = (Integer) httpServletRequest.getSession().getAttribute("number_of_order_items");
        
		Long orderId = Long.parseLong(allRequestParams.get("orderId"));
        Long orderLineItemiId = Long.parseLong(allRequestParams.get("orderLineItemiId"));
        
        if (openedorderId != null) {
            Order order = orderDataHandler.getOrderById(orderId);
            OrderLineItem orderLineItem = orderLineItemDataHandler.getOrderLineItemById(orderLineItemiId);
            
            List<OrderLineItem> orderLineItems = order.getOrderLineItems();
            orderLineItems.remove(orderLineItem);
            order.setOrderLineItems(orderLineItems);
            orderDataHandler.updateOrder(order);
            orderLineItemDataHandler.deleteOrderLineItem(orderLineItem);
            
            if (orderLineItems.isEmpty()) {
            	orderDataHandler.deleteOrder(order);
            	model.addAttribute("empty", true);
            	httpServletRequest.getSession().setAttribute("openedorder_id", null);
        		httpServletRequest.getSession().setAttribute("number_of_order_items", null);
            } else {	
                double totalPrice = orderDataHandler.getTotalPrice(order);
                model.addAttribute("order", order);
                model.addAttribute("totalPrice", totalPrice);
                numberOfOrderItems = order.getOrderLineItems().size();
                httpServletRequest.getSession().setAttribute("openedorder_id", orderId);
        		httpServletRequest.getSession().setAttribute("number_of_order_items", numberOfOrderItems);
        		
        		HashMap<String, List<Date>> choosableShippingDatesAndTimes = dateAndTime.getChoosableShippingDatesAndTimes();
                model.addAttribute("choosableDays", choosableShippingDatesAndTimes.get("choosableDays"));
                model.addAttribute("choosableShippingDates", choosableShippingDatesAndTimes.get("choosableShippingDates"));
                
                List<PaymentOption> paymentOptions = paymentOptionDataHandler.getAllPaymentOptions();
                model.addAttribute("paymentOptions", paymentOptions);
            }
            
        } else {
        	model.addAttribute("empty", true);
        }
        
        Customer customer = customerDataHandler.getCustomerById(customerId);
        model.addAttribute("customer", customer);
        
        model.addAttribute("loggedIn", customerId != null);
        model.addAttribute("customername", customerName);
        
		return "ordering";
	}
	
	@RequestMapping(value = "/ordering/delete-order", method = RequestMethod.POST)
	public String deleteOrder(Model model,
							HttpServletRequest httpServletRequest,
							@RequestParam Map<String,String> allRequestParams) {
		
		Long customerId = (Long) httpServletRequest.getSession().getAttribute("customer_id");
        String customerName = (String) httpServletRequest.getSession().getAttribute("customer_name");
        Long openedorderId = (Long) httpServletRequest.getSession().getAttribute("openedorder_id");
        Integer numberOfOrderItems = (Integer) httpServletRequest.getSession().getAttribute("number_of_order_items");
        
        Long orderId = Long.parseLong(allRequestParams.get("orderId"));
        
        if (orderId != null) {
        	
            boolean deletionSucceeded = orderDataHandler.deleteOrderAndLineItemsByOrderId(orderId);
            
            if (deletionSucceeded) {
            	model.addAttribute("empty", true);
            	httpServletRequest.getSession().setAttribute("openedorder_id", null);
        		httpServletRequest.getSession().setAttribute("number_of_order_items", null);
            } else {
            	Order order = orderDataHandler.getOrderById(orderId);
            	model.addAttribute("deletionError", true);
            	double totalPrice = orderDataHandler.getTotalPrice(order);
                model.addAttribute("order", order);
                model.addAttribute("totalPrice", totalPrice);
            	httpServletRequest.getSession().setAttribute("openedorder_id", orderId);
        		httpServletRequest.getSession().setAttribute("number_of_order_items", numberOfOrderItems);
        		
        		HashMap<String, List<Date>> choosableShippingDatesAndTimes = dateAndTime.getChoosableShippingDatesAndTimes();
                model.addAttribute("choosableDays", choosableShippingDatesAndTimes.get("choosableDays"));
                model.addAttribute("choosableShippingDates", choosableShippingDatesAndTimes.get("choosableShippingDates"));
                
                List<PaymentOption> paymentOptions = paymentOptionDataHandler.getAllPaymentOptions();
                model.addAttribute("paymentOptions", paymentOptions);
            }
            
        } else {
        	model.addAttribute("empty", true);
        }
        
        Customer customer = customerDataHandler.getCustomerById(customerId);
        model.addAttribute("customer", customer);
        
        model.addAttribute("loggedIn", customerId != null);
        model.addAttribute("customername", customerName);
        
		return "ordering";
	}
	
	@RequestMapping(value = "/ordering/sending-order", method = RequestMethod.POST)
	public String saveOrder(Model model,
			HttpServletRequest httpServletRequest,
			@RequestParam Map<String,String> allRequestParams) {
		Long customerId = (Long) httpServletRequest.getSession().getAttribute("customer_id");
        String customerName = (String) httpServletRequest.getSession().getAttribute("customer_name");
        Long openedorderId = (Long) httpServletRequest.getSession().getAttribute("openedorder_id");
        Integer numberOfOrderItems = (Integer) httpServletRequest.getSession().getAttribute("number_of_order_items");
        
        Long orderId = Long.parseLong(allRequestParams.get("orderId"));
        Date selectedOrderDateAndTimeDate = dateAndTime.convertStringToDateAndTime(allRequestParams.get("selectedDateAndTime"));
        Long selectedPaymentOptionId = Long.parseLong(allRequestParams.get("paymentRadios"));
        
        boolean savingSucceeded = orderDataHandler.finalizeOrder(orderId, selectedOrderDateAndTimeDate, selectedPaymentOptionId);
        
		Customer customer = customerDataHandler.getCustomerById(customerId);
        model.addAttribute("customer", customer);
        
        model.addAttribute("loggedIn", customerId != null);
        model.addAttribute("customername", customerName);
	
		if (!savingSucceeded) {
			model.addAttribute("savingError", true);
			Order order = orderDataHandler.getOrderById(orderId);
			double totalPrice = orderDataHandler.getTotalPrice(order);
			
            model.addAttribute("order", order);
            model.addAttribute("totalPrice", totalPrice);
        	httpServletRequest.getSession().setAttribute("openedorder_id", orderId);
    		httpServletRequest.getSession().setAttribute("number_of_order_items", numberOfOrderItems);
    		
    		HashMap<String, List<Date>> choosableShippingDatesAndTimes = dateAndTime.getChoosableShippingDatesAndTimes();
            model.addAttribute("choosableDays", choosableShippingDatesAndTimes.get("choosableDays"));
            model.addAttribute("choosableShippingDates", choosableShippingDatesAndTimes.get("choosableShippingDates"));
            
            List<PaymentOption> paymentOptions = paymentOptionDataHandler.getAllPaymentOptions();
            model.addAttribute("paymentOptions", paymentOptions);
            
			return "ordering";
		}
		
		httpServletRequest.getSession().removeAttribute("openedorder_id");
		httpServletRequest.getSession().removeAttribute("number_of_order_items");
		
		return "ordering-succeeded";
	}
	
	@RequestMapping(value = "/ordering/listing-orders", method = RequestMethod.GET)
	public String listOrders(Model model,
							HttpServletRequest httpServletRequest,
							@RequestParam Map<String,String> allRequestParams) {
		Long customerId = (Long) httpServletRequest.getSession().getAttribute("customer_id");
        String customerName = (String) httpServletRequest.getSession().getAttribute("customer_name");
        Long openedorderId = (Long) httpServletRequest.getSession().getAttribute("openedorder_id");
        Integer numberOfOrderItems = (Integer) httpServletRequest.getSession().getAttribute("number_of_order_items");
        
        Customer customer = customerDataHandler.getCustomerById(customerId);
        Order openedOrder = orderDataHandler.getOpenedOrderByCustomer(customer);
        List<Order> finalizedOrders = orderDataHandler.getFinalizedOrdersByCustomer(customer);
        
        model.addAttribute("openedOrder", openedOrder);
        model.addAttribute("previousOrders", finalizedOrders);
        
        model.addAttribute("loggedIn", customerId != null);
        model.addAttribute("customername", customerName);
        httpServletRequest.getSession().setAttribute("openedorder_id", openedorderId);
		httpServletRequest.getSession().setAttribute("number_of_order_items", numberOfOrderItems);
        
        return "orderlist";
	}
	
	@RequestMapping(value = "/ordering/reorder", method = RequestMethod.POST)
	public String reorder(Model model,
							HttpServletRequest httpServletRequest,
							@RequestParam Map<String,String> allRequestParams) {
		Long customerId = (Long) httpServletRequest.getSession().getAttribute("customer_id");
        String customerName = (String) httpServletRequest.getSession().getAttribute("customer_name");
        Long openedorderId = (Long) httpServletRequest.getSession().getAttribute("openedorder_id");
        Integer numberOfOrderItems = (Integer) httpServletRequest.getSession().getAttribute("number_of_order_items");
        
        Long reorderedOrderId = Long.parseLong(allRequestParams.get("orderId"));
        
        Map<String, Object> megaPack = orderDataHandler.reorderByOrderId(reorderedOrderId, openedorderId);
        boolean succeeded = (boolean) megaPack.get("succeeded");
        Map<String, String> responseMap = (Map<String, String>) megaPack.get("responseMap");
        
        if (!succeeded) {
        	model.addAttribute("reorderingFailed", true);
        } else {
        	openedorderId = Long.parseLong(responseMap.get("orderId"));
        	numberOfOrderItems = Integer.parseInt(responseMap.get("numberOfOrderItems"));
        }
        
        Customer customer = customerDataHandler.getCustomerById(customerId);
        Order openedOrder = orderDataHandler.getOpenedOrderByCustomer(customer);
        List<Order> finalizedOrders = orderDataHandler.getFinalizedOrdersByCustomer(customer);
        
        model.addAttribute("openedOrder", openedOrder);
        model.addAttribute("previousOrders", finalizedOrders);
        
        model.addAttribute("loggedIn", customerId != null);
        model.addAttribute("customername", customerName);
        httpServletRequest.getSession().setAttribute("openedorder_id", openedorderId);
		httpServletRequest.getSession().setAttribute("number_of_order_items", numberOfOrderItems);
        
        return "orderlist";
	}
	
}