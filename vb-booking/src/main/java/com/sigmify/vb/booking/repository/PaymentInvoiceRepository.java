package com.sigmify.vb.booking.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigmify.vb.booking.entity.PaymentInvoice;

@Transactional

public interface PaymentInvoiceRepository extends JpaRepository<PaymentInvoice, Long> {

	@Query("from PaymentInvoice where seviceOwnerId =:username and invoiceDate =:date")
	List<PaymentInvoice> findByServiceOwnerName(@Param("username") String username, @Param("date") String date);

	@Modifying
	@Query("UPDATE PaymentInvoice set amountReceivedDate=:date, dueAmountStatus='PAID' where invoiceNo =:invoiceNo")
	int updateInvoice(@Param("date") Date date, @Param("invoiceNo") String invoiceNo);

	@Query("from PaymentInvoice where dueAmountStatus='PENDING PAYMENT' and invoiceDate >:date")
	List<PaymentInvoice> findPendingInvoicePayment(@Param("date") String date);

}
