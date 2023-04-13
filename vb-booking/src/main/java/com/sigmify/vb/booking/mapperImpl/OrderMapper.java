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
public class OrderMapper implements Mapper<Order, OrderDTO> {
	
	@Autowired
	OrderDetailsTypeRepository orderDetailsTypeRepository;
	
	@Autowired
	private ServiceDetailsRepository serviceDetailsRepository;
	
	
	  @Override
	  public OrderDTO convert(Order order) {
		OrderDTO dto=new OrderDTO();
		dto.setOrderId(order.getOrderId());
		dto.setOrderStatus(order.getOrderStatus());
	    dto.setAddress(order.getAddress());
	    dto.setLandMark(order.getLandMark());
	    dto.setLattitude(order.getLattitude());
	    dto.setLongitude(order.getLongitude());
	    dto.setFieldArea(order.getFieldArea());
	    dto.setServiceId(order.getServiceId().getId());
	    dto.setServiceName(order.getServiceId().getName());
	    dto.setFarmerName(order.getRequesterName());
	    dto.setFarmerMobileNumber(order.getRequesterContact());
	    dto.setServiceSubCatName(order.getServiceId().getServiceSubCategoryType().getDescription());
	    dto.setImagePath("/"+order.getServiceId().getImagePath());
	    dto.setPinCode(order.getPinCode());
	    DateFormat dateFormat=new SimpleDateFormat("dd MMM yyyy");
	    DateFormat timeFormat=new SimpleDateFormat("hh:mm aa");
	    //orderDetails=orderDetailsTypeRepository.findByOrderNum(order.getOrderId());
	    String strDate=dateFormat.format(order.getOrderExecutionTime());
	    String strTime=timeFormat.format(order.getOrderExecutionTime());
	    dto.setStartDate(strDate);
	    dto.setStartTime(strTime);
	    dto.setUom(order.getUom().getDescription());
	    dto.setServiceDesc(order.getServiceId().getDescription());
		dto.setIsRunning(order.getIsRunning());
	    Pageable pageable=PageRequest.of(0, 1);
		List<OrderDetails> orderDetails=orderDetailsTypeRepository.findByOrderByNew(dto.getOrderId(), pageable);
		Pageable pageable1=PageRequest.of(0, 1);
		List<ServiceDetails> serviceDetails=serviceDetailsRepository.findByPincodeAndServiceName(dto.getServiceName(), dto.getPinCode(), pageable1);
		if(order.getOrderStatus().equals("NEW_ORDER_CREATED")){
			dto.setServExLattitude(serviceDetails.get(0).getLattitude());
			dto.setServExLongitude(serviceDetails.get(0).getLattitude());
			dto.setExecutorMobileNumber(serviceDetails.get(0).getPhoneNo());
			dto.setServiceExecutorName(serviceDetails.get(0).getServiceExecutorName());
			dto.setServiceOwnerName(serviceDetails.get(0).getServiceOwnerName());
			dto.setIndicativeRate(serviceDetails.get(0).getOnSeasonPrice());
		}else {
		dto.setServExLattitude(orderDetails.get(0).getServiceDetailsId().getLattitude());
		dto.setServExLongitude(orderDetails.get(0).getServiceDetailsId().getLongitude());
		dto.setExecutorMobileNumber(orderDetails.get(0).getServiceDetailsId().getPhoneNo());
		dto.setServiceExecutorName(orderDetails.get(0).getServiceDetailsId().getServiceExecutorName());
		dto.setServiceOwnerName(orderDetails.get(0).getServiceDetailsId().getServiceOwnerName());
		dto.setIndicativeRate(orderDetails.get(0).getServiceDetailsId().getOnSeasonPrice());
		}
		/*String description = order.getServiceId().getName();
	    OrderDetails odObj=orderDetailsTypeRepository.findByOrderUserName(order.getCreatedBy(), order.getOrderId());
		String userName=odObj.getServiceExecutorUserId();
		//String exeName=odObj.getServiceExecutorName();
		Long serviceDetailsId=odObj.getServiceDetailsId().getId();
		ServiceDetails serviceDetails = serviceDetailsRepository
				.getByDescriptionAndServiceOwnerUserName(description,userName,serviceDetailsId);
		order.setIndicativeRate(serviceDetails.getOnSeasonPrice());
		order.setServiceExecutorName(serviceDetails.getServiceExecutorName());
		order.setServiceOwnerName(serviceDetails.getServiceOwnerName());
		order.setServiceOwnerMobile(serviceDetails.getServiceOwnerName());
		/*Integer offSeasonPrice = serviceDetails.getOffSeasonPrice();
			Integer onSeasonPrice = serviceDetails.getOnSeasonPrice();
			if (offSeasonPrice > onSeasonPrice) {
				order.setIndicativeRate(offSeasonPrice);
			} else {
				order.setIndicativeRate(onSeasonPrice);
			}*/
	    /*if(order.getServiceExecutorName() == null) {
	    	dto.setServiceExecutorName("");
	    }else {
	    	dto.setServiceExecutorName(order.getServiceExecutorName());
	    }
	    if(order.getServiceOwnerName() == null) {
	    	dto.setServiceOwnerName("");
	    }else {
	    	dto.setServiceOwnerName(order.getServiceOwnerName());
	    }
	    if(order.getIsRunning() == false) {
	    	dto.setIsRunning(order.getIsRunning());
	    }else {
	    	dto.setIsRunning(order.getIsRunning());
	    }
	    if(order.getServiceOwnerMobile() == null) {
	    	dto.setExecutorMobileNumber("");
	    }else {
	    	dto.setExecutorMobileNumber(order.getServiceOwnerMobile());
	    }
	    
	    if(order.getIndicativeRate() == null) {
	    	dto.setIndicativeRate(0);
	    }else {
	    	dto.setIndicativeRate(order.getIndicativeRate());
	    }*/
	    return dto;
	  }

	  @Override
	  public Order toEntity(OrderDTO dto) throws Exception {
	    if(dto!=null) {
	      Order order=new Order();
	      order.setAddress(dto.getAddress());
	      order.setLandMark(dto.getLandMark());
	      order.setFieldArea(dto.getFieldArea());
	      order.setLattitude(dto.getLattitude());
	      order.setLongitude(dto.getLongitude());
	      return order;
	    }else {
	    return null;
	  }  
	  }

}
