package com.sigmify.vb.admin.mapperImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.sigmify.vb.admin.dto.OrderDTO;
import com.sigmify.vb.admin.entity.OrderDetails;
import com.sigmify.vb.admin.mapper.Mapper;



@Component
public class OrderDetailsMapper implements Mapper<OrderDetails, OrderDTO> {

	
	
	  @Override
	  public OrderDTO convert(OrderDetails order) {
		OrderDTO dto=new OrderDTO();
		dto.setOrderId(order.getOrderNumber());
		dto.setOrderStatus(order.getOrderStatus());
	    dto.setAddress(order.getOrder().getAddress());
	    dto.setLandMark(order.getOrder().getLandMark());
	    dto.setLattitude(order.getOrder().getLattitude());
	    dto.setLongitude(order.getOrder().getLongitude());
	    dto.setFieldArea(order.getOrder().getFieldArea());
	    dto.setServiceId(order.getOrder().getServiceId().getId());
	    dto.setServiceName(order.getOrder().getServiceId().getDescription());
	    dto.setServiceSubCatName(order.getOrder().getServiceId().getServiceSubCategoryType().getDescription());
	    dto.setFarmerName(order.getOrder().getRequesterName());
	    dto.setFarmerMobileNumber(order.getOrder().getRequesterContact());
	    dto.setPaymentStatus(order.getPaymentStatus());
	    //dto.setStartDate(order.getOrder().getUpdatedAt());
	    DateFormat dateFormat=new SimpleDateFormat("dd MMM yyyy");
	    DateFormat timeFormat=new SimpleDateFormat("hh:mm aa");
	    String strDate=dateFormat.format(order.getOrder().getUpdatedAt());
	    String strTime=timeFormat.format(order.getOrderExecutionTime());
	    dto.setStartDate(strDate);
	    dto.setStartTime(strTime);
	    dto.setImagePath(order.getOrder().getServiceId().getImagePath());
	    dto.setServiceExecutorName(order.getServiceDetailsId().getServiceExecutorName());
	    dto.setServiceOwnerName(order.getServiceDetailsId().getServiceOwnerName());
	    dto.setExecutorMobileNumber(order.getOrder().getServiceOwnerMobile());
	    dto.setUom(order.getOrder().getUom().getDescription());
	    dto.setIndicativeRate(order.getOrder().getIndicativeRate());
	    dto.setServExLattitude(order.getServiceExLattitude());
	    dto.setServExLongitude(order.getServiceExLongitude());
	    return dto;
	  }

	  @Override
	  public OrderDetails toEntity(OrderDTO dto) throws Exception {
	    /*if(dto!=null) {
	      Order order=new Order();
	      order.setAddress(dto.getAddress());
	      order.setLandMark(dto.getLandMark());
	      order.setFieldArea(dto.getFieldArea());
	      order.setLattitude(dto.getLattitude());
	      order.setLongitude(dto.getLongitude());
	      return order;
	    }else {*/
	    return null;
		/* } */ 
	  }

}
