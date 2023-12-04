package ra.webwalefashion.service.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.webwalefashion.DTO.request.OrderReq;
import ra.webwalefashion.model.DAO.*;
import ra.webwalefashion.model.entity.CartItem;
import ra.webwalefashion.model.entity.Order;
import ra.webwalefashion.model.entity.OrderDetail;
import ra.webwalefashion.model.entity.Product;
import ra.webwalefashion.service.OrderService;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderDetailDAO orderDetailDAO;
    @Autowired
    private CartItemDAO cartItemDAO;
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private SizeDAO sizeDAO;
    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private BranDAO branDAO;
    @Autowired
    private CartDAO cartDAO;

    @Override
    public void create(OrderReq orderReq, Integer cartId) {
        ModelMapper modelMapper = new ModelMapper();
        orderDAO.save(modelMapper.map(orderReq, Order.class));
        for (CartItem c : cartItemDAO.findALl()) {
            if (c.getCartId() == cartId) {
                OrderDetail orderDetail = new OrderDetail();
                Product p = productDAO.findById(c.getProductId());
                orderDetail.setOrderId(orderIdMax());
                orderDetail.setProductName(p.getName());
                orderDetail.setProductSize(sizeDAO.findById(p.getSizeId()).getSizeName());
                orderDetail.setProductCategory(categoryDAO.findById(p.getCategoryId()).getCategoryName());
                orderDetail.setProductBrand(branDAO.findById(p.getBrand()).getNameBrand());
                orderDetail.setQuantity(c.getQuantity());
                orderDetail.setPrice(c.getPrice());
                orderDetailDAO.save(orderDetail);
                orderDAO.updateOrder(orderDetail.getOrderId());
                cartItemDAO.delete(c.getId());
                cartDAO.updateCart(c.getCartId());
            }
        }
    }

    public Integer orderIdMax() {
        int max = 0;
        for (Order o : orderDAO.findALl()) {
            if (o.getOrder_id() > max) {
                max = o.getOrder_id();
            }
        }
        return max;
    }

    @Override
    public List<Order> findAll() {
        return orderDAO.findALl();
    }

}
