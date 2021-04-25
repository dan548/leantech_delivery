package ai.leantech.delivery.controller;

import ai.leantech.delivery.controller.model.order.AdminOrderRequest;
import ai.leantech.delivery.controller.model.order.OrderResponse;
import ai.leantech.delivery.entity.Order;
import ai.leantech.delivery.objectmother.AdminOrderRequestMother;
import ai.leantech.delivery.objectmother.OrderResponseMother;
import ai.leantech.delivery.service.AdminOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
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
    void whenValidInputCreate_thenReturns201() throws Exception {
        Order savedOrder = mock(Order.class);
        when(savedOrder.getId()).thenReturn(1L);
        when(adminOrderService.createOrder(any(AdminOrderRequest.class))).thenReturn(savedOrder);
        mockMvc.perform(post("/api/admin/orders")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(AdminOrderRequestMother.complete().build())))
                .andExpect(status().isCreated());
        verify(adminOrderService).createOrder(any(AdminOrderRequest.class));
    }

    @Test
    void whenAnonymousCreate_thenReturns403() throws Exception {
        mockMvc.perform(post("/api/admin/orders")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(AdminOrderRequestMother.complete().build())))
                .andExpect(status().isForbidden());
        verify(adminOrderService, never()).createOrder(any(AdminOrderRequest.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void whenDelete_thenReturns200() throws Exception {
        mockMvc.perform(delete("/api/admin/orders/666"))
                .andExpect(status().isOk());
        verify(adminOrderService).deleteOrder(eq(666L));
    }

    @Test
    @WithMockUser(username = "adihit", roles = {"DELIVERY", "CLIENT"})
    void whenNotAdminDelete_thenReturns403() throws Exception {
        mockMvc.perform(delete("/api/admin/orders/666"))
                .andExpect(status().isForbidden());
        verify(adminOrderService, never()).deleteOrder(any(Long.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void whenGetOrders_thenReturns200() throws Exception {
        mockMvc.perform(get("/api/admin/orders"))
                .andExpect(status().isOk());
        verify(adminOrderService).getOrders(isNull(), isNull(), isNull(), isNull(), eq(0), eq(25), eq("DESC"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void whenEditOrder_thenReturns200() throws Exception {
        JsonPatch patch = new JsonPatch(Collections.emptyList());
        OrderResponse resp = OrderResponseMother.complete().build();
        mockMvc.perform(patch("/api/admin/orders/666")
                .contentType("application/json-patch+json")
                .content(objectMapper.writeValueAsString(patch)))
                .andExpect(status().isOk());
        when(adminOrderService.updateOrder(any(), any())).thenReturn(resp);
        verify(adminOrderService).updateOrder(eq(666L), any(JsonPatch.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void whenGetOrder_thenReturns200() throws Exception {
        OrderResponse resp = OrderResponseMother.complete().build();
        mockMvc.perform(get("/api/admin/orders/666"))
                .andExpect(status().isOk());
        when(adminOrderService.getOrderById(any(Long.class))).thenReturn(resp);
        verify(adminOrderService).getOrderById(eq(666L));
    }

}
