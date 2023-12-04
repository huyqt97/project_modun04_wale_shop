package ra.webwalefashion.service;

import ra.webwalefashion.DTO.request.OrderReq;
import ra.webwalefashion.model.entity.Order;

import java.util.List;

public interface OrderService {
    void create(OrderReq orderReq,Integer cartId);
    List<Order> findAll();
//    void create(OrderReq orderReq,Integer cartId);
}
