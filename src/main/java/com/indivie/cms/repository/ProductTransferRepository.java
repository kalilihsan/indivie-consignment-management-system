package com.indivie.cms.repository;

import com.indivie.cms.entity.ProductTransfer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductTransferRepository extends JpaRepository<ProductTransfer,String> {
    @Query(nativeQuery = true,
    value = """
            SELECT pt.id, pt.transfer_date, pt.sender, pt.receiver, pt.product_id, pt.qty, pt.transfer_type
            FROM m_product_transfer AS pt
            INNER JOIN m_product AS p ON p.id = pt.product_id
            INNER JOIN m_supplier AS s ON s.id = p.supplier_id
            WHERE (pt.sender LIKE :sender OR pt.receiver LIKE :receiver)
            AND pt.product_id LIKE :productId
            AND s.id LIKE :supplierId
            """)
    Optional<List<ProductTransfer>> getFilteredProductTransfers(
            @Value("sender") String sender,
            @Value("receiver") String receiver,
            @Value("productId") String productId,
            @Value("supplierId") String supplierId
    );
}
