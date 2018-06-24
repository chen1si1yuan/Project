package Dao;

import org.springframework.stereotype.Service;

import Pojo.Bill;

@Service
public interface BillDao {
	
	public Integer insertBill(Bill bill);

}
