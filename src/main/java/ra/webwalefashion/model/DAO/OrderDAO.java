package ra.webwalefashion.model.DAO;

import ra.webwalefashion.model.entity.Order;

public interface OrderDAO extends IGenericDAO<Order,Integer>{
    Order searchUserId(Integer userId);
    void updateOrder(Integer orderId);
}
