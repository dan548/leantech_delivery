package ai.leantech.delivery.controller;

import ai.leantech.delivery.MySpringBootTest;
import ai.leantech.delivery.controller.model.order.AdminOrderRequest;
import ai.leantech.delivery.controller.model.order.OrderItemRequest;
import ai.leantech.delivery.model.Order;
import ai.leantech.delivery.service.AdminOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MySpringBootTest
@AutoConfigureMockMvc
class AdminOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminOrderService adminOrderService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void whenValidInput_thenReturns201() throws Exception {
        AdminOrderRequest request = new AdminOrderRequest("cash", 4L, 4L,
                "ul. Pushkina, dom Kolotushkina", Arrays.asList(new OrderItemRequest(2, 4L), new OrderItemRequest(1, 3L)));
        Order savedOrder = mock(Order.class);
        when(savedOrder.getId()).thenReturn(1L);
        when(adminOrderService.createOrder(any(AdminOrderRequest.class))).thenReturn(savedOrder);
        mockMvc.perform(post("/api/admin/orders")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
        verify(adminOrderService).createOrder(any(AdminOrderRequest.class));
    }
}
