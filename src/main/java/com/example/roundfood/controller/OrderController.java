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
import com.example.roundfood.model.Customer;
import com.example.roundfood.model.Order;
import com.example.roundfood.model.OrderLineItem;
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
	
}
