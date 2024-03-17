package com.indivie.cms.service.impl;

import com.indivie.cms.dto.request.OrderRequest;
import com.indivie.cms.dto.request.SearchOrderRequest;
import com.indivie.cms.dto.request.TransactionalProductTransferRequest;
import com.indivie.cms.dto.response.OrderDetailResponse;
import com.indivie.cms.dto.response.OrderResponse;
import com.indivie.cms.entity.Outlet;
import com.indivie.cms.entity.Product;
import com.indivie.cms.entity.ProductSupplyOrder;
import com.indivie.cms.entity.ProductSupplyOrderDetail;
import com.indivie.cms.repository.ProductSupplyOrderRepository;
import com.indivie.cms.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ProductSupplyOrderRepository productSupplyOrderRepository;
    private final ProductService productService;
    private final OutletService outletService;
    private final OrderDetailService orderDetailService;
    private final ProductTransferService transferService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderResponse create(OrderRequest request) {
        Outlet outlet = outletService.getById(request.getOutletId());

        ProductSupplyOrder order = ProductSupplyOrder.builder()
                .orderDate(new Date())
                .outlet(outlet)
                .build();
        productSupplyOrderRepository.saveAndFlush(order);

        List<ProductSupplyOrderDetail> orderDetails = request.getDetailRequests().stream().map(
                detailRequest -> {
                    Product product = productService.getById(detailRequest.getProductId());

                    transferService.createOrderTransfer(TransactionalProductTransferRequest.builder()
                                    .outletId(request.getOutletId())
                                    .productId(detailRequest.getProductId())
                                    .qty(detailRequest.getQty())
                            .build());

                    return ProductSupplyOrderDetail.builder()
                            .productSupplyOrder(order)
                            .product(product)
                            .netCost(product.getNetCost())
                            .qty(detailRequest.getQty())
                            .build();
                }
        ).toList();

        orderDetailService.createBulk(orderDetails);
        order.setOrderDetails(orderDetails);

        List<OrderDetailResponse> detailResponses = orderDetails.stream().map(detail -> OrderDetailResponse.builder()
                        .id(detail.getId())
                        .orderId(detail.getProductSupplyOrder().getId())
                        .productId(detail.getProduct().getId())
                        .netCost(detail.getNetCost())
                        .qty(detail.getQty())
                        .build()
                ).toList();

        return OrderResponse.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .outletId(outlet.getId())
                .orderDetails(detailResponses)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Page<OrderResponse> getAll(SearchOrderRequest request) {
        if (request.getPage() <= 0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sort);

        Page<ProductSupplyOrder> supplyOrderPage = productSupplyOrderRepository.findAll(pageable);

        String outletId = "%%";
        String productId = "%%";
        String supplierId = "%%";

        if (request.getOutletId() != null) {
            outletId = "%" + request.getOutletId() + "%";
        }
        if (request.getProductId() != null) {
            productId = "%" + request.getProductId() + "%";
        }
        if (request.getSupplierId() != null) {
            supplierId = "%" + request.getSupplierId() + "%";
        }

        if (productSupplyOrderRepository.findAllFiltered(outletId, productId, supplierId).isPresent()) {
            List<ProductSupplyOrder> productSupplyOrders = productSupplyOrderRepository
                    .findAllFiltered(outletId, productId, supplierId)
                    .get();
            supplyOrderPage = new PageImpl<>(productSupplyOrders, pageable, productSupplyOrders.size());
        }

        return supplyOrderPage.map(order -> {
            List<OrderDetailResponse> detailResponses = order.getOrderDetails().stream().map(
                    orderDetail -> OrderDetailResponse.builder()
                            .id(orderDetail.getId())
                            .orderId(order.getId())
                            .productId(orderDetail.getProduct().getId())
                            .netCost(orderDetail.getNetCost())
                            .qty(orderDetail.getQty())
                            .build()
            ).toList();

            return OrderResponse.builder()
                    .id(order.getId())
                    .orderDate(order.getOrderDate())
                    .outletId(order.getOutlet().getId())
                    .orderDetails(detailResponses)
                    .build();
        });
    }
}
