package model.services;

import java.util.Calendar;
import java.util.Date;

import entities.Contract;
import entities.Installment;

public class ContractService {
	
	private OnlinePaymentService onlinePaymentService;

	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}

	public void processContract(Contract contract, Integer months) {
		
		double basicQuota = contract.getTotalValue() / months;
		
		for (int i = 1; i <= months; i++) {
			double subTotal = basicQuota + onlinePaymentService.interest(basicQuota, i);
			double total = subTotal + onlinePaymentService.paymentFee(subTotal);
			Date dueDate = parcela(contract.getDate(), i);
			contract.getInstallments().add(new Installment(dueDate, total));
		}
		
	}
	
	private Date parcela(Date date, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, n);
		
		return calendar.getTime();
	}
	
			
}
