package app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import entities.Contract;
import entities.Installment;
import model.services.ContractService;
import model.services.OnlinePaymentService;
import model.services.PaypalService;

public class Program {

	public static void main(String[] args) throws ParseException {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		OnlinePaymentService ps = new PaypalService();
		
		System.out.println("Enter contract data");
		System.out.print("Number: ");
		Integer number = sc.nextInt();
		System.out.print("Date (dd/MM/yyyy)");
		Date date = sdf.parse(sc.next());
		System.out.print("Contract value: ");
		Double contractValue = sc.nextDouble();
		
		Contract contract = new Contract(number, date, contractValue);
		
		ContractService contractService = new ContractService(ps);
		System.out.print("Enter number of installments: ");
		number = sc.nextInt();
		contractService.processContract(contract, number);
		
		System.out.println("Installments:");

		for (Installment obj : contract.getInstallments()) {
			System.out.println(sdf.format(obj.getDueDate()) + " - " + obj.getAmount());
		}
		
		sc.close();
	}
	
}
