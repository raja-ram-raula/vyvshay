package com.sigmify.vb.booking.mapperImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.sigmify.vb.booking.dto.PaymentDto;
import com.sigmify.vb.booking.entity.PaymentDetails;
import com.sigmify.vb.booking.mapper.Mapper;

@Component
public class PaymentDetailsMapper implements Mapper<PaymentDetails, PaymentDto> {

	@Override
	public PaymentDto convert(PaymentDetails payment) {
		PaymentDto dto=new PaymentDto();
		dto.setOrderId(payment.getOrder());
		dto.setTotalAmount(payment.getTotalAmount());
		dto.setAdvanceAmount(payment.getAdvanceAmount());
		dto.setBalanceAmount(payment.getBalanceAmount());
		DateFormat dateFormat=new SimpleDateFormat("dd MMM yyyy");
	    DateFormat timeFormat=new SimpleDateFormat("hh:mm aa");
	    String strDate=dateFormat.format(payment.getCreatedAt());
	    String strTime=timeFormat.format(payment.getCreatedAt());
	    dto.setStartDate(strDate);
	    dto.setStartTime(strTime);
		return dto;
	}

	@Override
	public PaymentDetails toEntity(PaymentDto dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
