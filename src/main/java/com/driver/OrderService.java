package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository = new OrderRepository();

    public String addOrder(Order order){
        return orderRepository.addOrder(order);
    }
    public String addPartner(String deliveryPartner){
        return orderRepository.addPartner(deliveryPartner);
    }

    public String addOrderPartnerPair(String order,String partner){
        return orderRepository.addOrderPartnerPair(order,partner);
    }

    public Order getOrderById(String id){
        return orderRepository.getOrderById(id);
    }

    public DeliveryPartner getPartnerById(String id){
        return orderRepository.getPartnerById(id);
    }

    public int getOrderCountByPartnerId(String partner){
        return orderRepository.getOrderCountByPartnerId(partner);
    }

    public List getOrdersByPartnerId(String partner){
        return getOrdersByPartnerId(partner);
    }

    public List getAllOrders(){
        return getAllOrders();
    }

    public int getCountOfUnassignedOrders(){
        return orderRepository.getCountOfUnassignedOrders();
    }

    public int getOrdersLeftAfterTime(String time,String partnerId){
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
    }

    public String getLastDeliveryTime(String partnerId){
        return orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }

    public void deletePartnerById(String partnerId){
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId){
        orderRepository.deleteOrderById(orderId);
    }


}
