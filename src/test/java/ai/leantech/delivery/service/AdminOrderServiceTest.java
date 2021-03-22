package ai.leantech.delivery.service;

import ai.leantech.delivery.controller.model.order.AdminOrderRequest;
import ai.leantech.delivery.controller.model.order.OrderDtoConverter;
import ai.leantech.delivery.model.Order;
import ai.leantech.delivery.model.PaymentType;
import ai.leantech.delivery.repository.OrderItemRepository;
import ai.leantech.delivery.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static ai.leantech.delivery.model.OrderAssert.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminOrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private OrderDtoConverter orderDtoConverter;

    @InjectMocks
    private AdminOrderService adminOrderService;

    @Test
    void createdOrderHasCreationDate() {
        AdminOrderRequest request = new AdminOrderRequest("cash", null, null, "ul. Pushkina, dom Kolotushkina", new ArrayList<>());
        when(orderRepository.save(any(Order.class))).then(returnsFirstArg());
        Order order = new Order();
        order.setPaymentType(PaymentType.CASH);
        order.setAddress("ul. Pushkina, dom Kolotushkina");
        order.setOrderItems(new ArrayList<>());
        when(orderDtoConverter.convertDtoToOrder(any(AdminOrderRequest.class))).thenReturn(order);
        Order createdOrder = adminOrderService.createOrder(request);
        assertThat(createdOrder).hasCreationDate();
    }

}
