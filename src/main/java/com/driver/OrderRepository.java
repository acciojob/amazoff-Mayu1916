package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

@Repository
public class OrderRepository {
        HashMap<String,Order> orderHashMap = new HashMap<>();
        HashMap<String,DeliveryPartner> deliveryPartnerHashMap = new HashMap<>();
        HashMap<String,String> orderPartnerHashmap = new HashMap<>();
        HashMap<String, List<String>> orderAssignTopartner = new HashMap<>();

        public String addOrder(Order order){
            orderHashMap.put(order.getId(),order);
            return "order added";
        }
        public String addPartner(String deliveryPartner){
            DeliveryPartner partner = new DeliveryPartner(deliveryPartner);
            deliveryPartnerHashMap.put(deliveryPartner,partner);
            return "partner added";
        }

        public String addOrderPartnerPair(String order,String partner){
            if(!orderHashMap.containsKey(order) || !orderPartnerHashmap.containsKey(partner)) return "not present";


            if(orderPartnerHashmap.containsKey(order)) return "already assign";
            if(!orderAssignTopartner.containsKey(partner)){
                List<String> list = new ArrayList<>();
                list.add(order);
                orderAssignTopartner.put(partner,list);
                DeliveryPartner deliveryPartner = deliveryPartnerHashMap.get(partner);
                deliveryPartner.setNumberOfOrders(list.size());
            }else{
                List<String> list = orderAssignTopartner.get(partner);
                list.add(order);
                DeliveryPartner deliveryPartner = deliveryPartnerHashMap.get(partner);
                deliveryPartner.setNumberOfOrders(list.size());
            }

            orderPartnerHashmap.put(order,partner);
            return "pair added";
        }

        public Order getOrderById(String id){
            return orderHashMap.get(id);
        }

        public DeliveryPartner getPartnerById(String id){
            return deliveryPartnerHashMap.get(id);
        }

        public int getOrderCountByPartnerId(String partner){
            List<String> list =  orderAssignTopartner.getOrDefault(partner,new ArrayList<>());
            return list.size();
        }

        public List getOrdersByPartnerId(String partner){
            return orderAssignTopartner.getOrDefault(partner,new ArrayList<>());
        }

        public List getAllOrders(){
            List<String> list = new ArrayList<>();
            for(String x : orderHashMap.keySet()){
                list.add(orderHashMap.get(x).getId());
            }
            return list;
        }

        public int getCountOfUnassignedOrders(){
            int ans =orderHashMap.size()-orderPartnerHashmap.size();
            return ans;
        }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {

        int countOfOrders = 0;
        List<String> list = orderAssignTopartner.get(partnerId);
        int deliveryTime = Integer.parseInt(time.substring(0, 2)) * 60 + Integer.parseInt(time.substring(3));
        for (String s : list) {
            Order order = orderHashMap.get(s);
            if (order.getDeliveryTime() > deliveryTime) {
                countOfOrders++;
            }
        }
        return countOfOrders;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {

        String time = "";
        List<String> list = orderAssignTopartner.get(partnerId);
        int deliveryTime = 0;
        for (String s : list) {
            Order order = orderHashMap.get(s);
            deliveryTime = Math.max(deliveryTime, order.getDeliveryTime());
        }
        int hour = deliveryTime / 60;
        String sHour = "";
        if (hour < 10) {
            sHour = "0" + String.valueOf(hour);
        } else {
            sHour = String.valueOf(hour);
        }

        int min = deliveryTime % 60;
        String sMin = "";
        if (min < 10) {
            sMin = "0" + String.valueOf(min);
        } else {
            sMin = String.valueOf(min);
        }

        time = sHour + ":" + sMin;

        return time;

    }

    public String deletePartnerById(String partnerId) {

        deliveryPartnerHashMap.remove(partnerId);

        List<String> list = orderAssignTopartner.getOrDefault(partnerId, new ArrayList<>());
        ListIterator<String> itr = list.listIterator();
        while (itr.hasNext()) {
            String s = itr.next();
            orderAssignTopartner.remove(s);
        }
        orderAssignTopartner.remove(partnerId);
        return "Deleted";
    }

    public String deleteOrderById(String orderId) {

        orderHashMap.remove(orderId);
        String partnerId = orderPartnerHashmap.get(orderId);
        orderAssignTopartner.remove(orderId);
        List<String> list = orderAssignTopartner.get(partnerId);

        ListIterator<String> itr = list.listIterator();
        while (itr.hasNext()) {
            String s = itr.next();
            if (s.equals(orderId)) {
                itr.remove();
            }
        }
        orderAssignTopartner.put(partnerId, list);

        return "Deleted";
    }



}
