package  com.hulqframe.cwgl.mapper;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.cwgl.model.ReceiptPayment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptPaymentMapper extends BaseMapper<ReceiptPayment,Integer> {
    @Delete("delete from p_receipt_payment where payment_id=#{paymentId}")
    void deleteByPaymentId(@Param("paymentId") Integer paymentId);
}
