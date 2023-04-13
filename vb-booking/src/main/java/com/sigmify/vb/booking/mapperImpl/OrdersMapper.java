package com.sigmify.vb.booking.mapperImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.sigmify.vb.booking.dto.OrderDTO;
import com.sigmify.vb.booking.entity.Order;
import com.sigmify.vb.booking.entity.OrderDetails;
import com.sigmify.vb.booking.entity.ServiceDetails;
import com.sigmify.vb.booking.mapper.Mapper;
import com.sigmify.vb.booking.repository.OrderDetailsTypeRepository;
import com.sigmify.vb.booking.repository.ServiceDetailsRepository;

@Component
public class OrdersMapper implements Mapper<OrderDetails, OrderDTO> {
	
	@Autowired
	OrderDetailsTypeRepository orderDetailsTypeRepository;
	
	@Autowired
	private ServiceDetailsRepository serviceDetailsRepository;
	
	
	  /**
	 *
	 */
	@Override
	  public OrderDTO convert(OrderDetails order) {
		OrderDTO dto=new OrderDTO();
		dto.setOrderId(order.getOrder().getOrderId());
		dto.setOrderStatus(order.getOrder().getOrderStatus());
	    dto.setAddress(order.getOrder().getAddress());
	    dto.setLandMark(order.getOrder().getLandMark());
	    dto.setLattitude(order.getOrder().getLattitude());
	    dto.setLongitude(order.getOrder().getLongitude());
	    dto.setFieldArea(order.getOrder().getFieldArea());
	    dto.setServiceId(order.getOrder().getServiceId().getId());
	    dto.setServiceName(order.getOrder().getServiceId().getName());
	    dto.setFarmerName(order.getOrder().getRequesterName());
	    dto.setFarmerMobileNumber(order.getOrder().getRequesterContact());
	    dto.setServiceSubCatName(order.getOrder().getServiceId().getServiceSubCategoryType().getDescription());
	    dto.setImagePath("/"+order.getOrder().getServiceId().getImagePath());
	    dto.setPinCode(order.getOrder().getPinCode());
	    //OrderDetails orderDetails=null;
	    List<ServiceDetails> sdList=null;
	    if(order.getOrder().getOrderStatus().equals("NEW_ORDER_CREATED") || order.getOrder().getOrderStatus().equals("ORDER_CANCELLED")) {
	    	Pageable pageable=PageRequest.of(0, 1);
	    	sdList=serviceDetailsRepository.findByPincodeAndServiceName(dto.getServiceName(),dto.getPinCode(),pageable);
            dto.setServiceOwnerName(sdList.get(0).getServiceOwnerName());
            dto.setServiceExecutorName(sdList.get(0).getServiceExecutorName());
            dto.setIndicativeRate(sdList.get(0).getOnSeasonPrice());
            dto.setExecutorMobileNumber(sdList.get(0).getPhoneNo());
            dto.setServExLattitude(sdList.get(0).getLattitude());
            dto.setServExLongitude(sdList.get(0).getLongitude());
	    }else {
		//if(order.getOrderStatus().equals("ORDER_ACCEPTED") || order.getOrderStatus().equals("ORDER_COMPLETED")) {
		//orderDetails=orderDetailsTypeRepository.findByOrderNum(order.getOrderId());
			dto.setServExLongitude(order.getServiceExLongitude());
		dto.setServExLattitude(order.getServiceExLattitude());
		//}else {
			//dto.setServExLattitude("");
			//dto.setServExLongitude("");
		//}
		String description = order.getOrder().getServiceId().getName();
	   // OrderDetails odObj=orderDetailsTypeRepository.findByOrderUserName(order.getCreatedBy(), order.getOrderId());
		String userName=order.getServiceExecutorUserId();
		//String exeName=odObj.getServiceExecutorName();
		Long serviceDetailsId=order.getServiceDetailsId().getId();
		ServiceDetails serviceDetails = serviceDetailsRepository
				.getByDescriptionAndServiceOwnerUserName(description,userName,serviceDetailsId);
		order.getOrder().setIndicativeRate(serviceDetails.getOnSeasonPrice());
		order.getOrder().setServiceExecutorName(serviceDetails.getServiceExecutorName());
		order.getOrder().setServiceOwnerName(serviceDetails.getServiceOwnerName());
		order.getOrder().setServiceOwnerMobile(serviceDetails.getPhoneNo());
		/*Integer offSeasonPrice = serviceDetails.getOffSeasonPrice();
			Integer onSeasonPrice = serviceDetails.getOnSeasonPrice();
			if (offSeasonPrice > onSeasonPrice) {
				order.setIndicativeRate(offSeasonPrice);
			} else {
				order.setIndicativeRate(onSeasonPrice);
			}*/
	    if(order.getOrder().getServiceExecutorName() == null) {
	    	dto.setServiceExecutorName("");
	    }else {
	    	dto.setServiceExecutorName(order.getOrder().getServiceExecutorName());
	    }
	    if(order.getOrder().getServiceOwnerName() == null) {
	    	dto.setServiceOwnerName("");
	    }else {
	    	dto.setServiceOwnerName(order.getOrder().getServiceOwnerName());
	    }
	    if(order.getOrder().getServiceOwnerMobile() == null) {
	    	dto.setExecutorMobileNumber("");
	    }else {
	    	dto.setExecutorMobileNumber(order.getOrder().getServiceOwnerMobile());
	    }
	    if(order.getOrder().getIndicativeRate() == null) {
	    	dto.setIndicativeRate(0);
	    }else {
	    	dto.setIndicativeRate(order.getOrder().getIndicativeRate());
	    }
	    }
	    if(order.getOrder().getIsRunning() == false) {
	    	dto.setIsRunning(order.getOrder().getIsRunning());
	    }else {
	    	dto.setIsRunning(order.getOrder().getIsRunning());
	    }
	    DateFormat dateFormat=new SimpleDateFormat("dd MMM yyyy");
	    DateFormat timeFormat=new SimpleDateFormat("hh:mm aa");
	    //orderDetails=orderDetailsTypeRepository.findByOrderNum(order.getOrderId());
	    String strDate=dateFormat.format(order.getOrderExecutionTime());
	    String strTime=timeFormat.format(order.getOrderExecutionTime());
	    dto.setStartDate(strDate);
	    dto.setStartTime(strTime);
	    dto.setUom(order.getOrder().getUom().getDescription());
	    dto.setServiceDesc(order.getOrder().getServiceId().getDescription());
	    return dto;
	  }

	  @Override
	  public OrderDetails toEntity(OrderDTO dto) throws Exception {
	    if(dto!=null) {
	      OrderDetails order=new OrderDetails();
	      order.getOrder().setAddress(dto.getAddress());
	      order.getOrder().setLandMark(dto.getLandMark());
	      order.getOrder().setFieldArea(dto.getFieldArea());
	      order.getOrder().setLattitude(dto.getLattitude());
	      order.getOrder().setLongitude(dto.getLongitude());
	      return order;
	    }else {
	    return null;
	  }  
	  }

}
