package com.indivie.cms.repository;

import com.indivie.cms.dto.request.SearchTransactionRequest;
import com.indivie.cms.entity.OutletTransaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OutletTransactionRepository extends JpaRepository<OutletTransaction, String > {
    @Query(nativeQuery = true,
    value = """
            SELECT ot.id, ot.transaction_date, ot.outlet_id FROM t_outlet_transaction AS ot
            INNER JOIN t_outlet_transaction_detail AS otd ON otd.transaction_id = ot.id
            INNER JOIN m_product AS p ON p.id = otd.product_id
            WHERE ot.outlet_id LIKE :outletId AND otd.product_id LIKE :productId
            """)
    Optional<List<OutletTransaction>> findAllFiltered(@Param("outletId") String outletId,
                                                      @Param("productId") String productId);
}
