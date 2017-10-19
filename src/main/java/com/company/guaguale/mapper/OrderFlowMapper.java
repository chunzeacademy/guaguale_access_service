package com.company.guaguale.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.company.guaguale.domain.OrderFlow;
@Mapper
public interface OrderFlowMapper {

	@Insert(" insert into order_flow (partitionId, orderId, ownerKey," + "stepId, flowId, createTime,"
			+ " updateTime, data_key, context_data," + " retry_times, currentStatus, ret_code, ret_msg)"
			+ " values (#{partitionid}, #{orderid}, #{ownerkey},"
			+ " #{stepid}, #{flowid}, #{createtime,jdbcType=TIMESTAMP}," + " #{updatetime}, #{dataKey}, #{contextData},"
			+ " #{retryTimes}, #{currentstatus}, #{retCode}, #{retMsg})")
	int insert(OrderFlow record);

	@Update(" UPDATE order_flow SET partitionId=#{partitionid}, ownerKey=#{ownerkey},"
			+ "stepId=#{stepid}, flowId=#{flowid}, createTime=#{createtime},"
			+ " updateTime=#{updatetime}, data_key=#{dataKey}, context_data=#{contextData},"
			+ " retry_times=#{retryTimes}, currentStatus=#{currentstatus}, ret_code=#{retCode}, ret_msg=#{retMsg}"
			+ " where partitionId=#{partitionid} and orderId=#{orderid}")
	int update(OrderFlow orderFlow);
	
	@Delete("delete order_flow where where partitionId=#{partitionid} and orderId=#{orderid}")
	int delete(OrderFlow orderFlow);

	
}