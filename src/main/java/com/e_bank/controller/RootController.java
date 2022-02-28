package com.e_bank.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.e_bank.registrationEntity.OpenAccount;
import com.e_bank.service.RootService;
import com.google.zxing.WriterException;

import qrCodeGeneretor.QRCodeGenerator;

@Controller
public class RootController {

	@Autowired
	private RootService service;
	private long id;
	
	  @GetMapping("/") 
	  public String home() {
	  
	  return "index"; 
	  }
	 
	
	@GetMapping("/openAccount")
	public String openAccount() {
		
		return "OpenAccount";
	}
	
	String fistName = "";
	String lastName = "";
	String dates = "";
	
	@RequestMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute OpenAccount oa,
			@RequestParam("userPassPhoto") MultipartFile userPhoto,
			@RequestParam("userPanPic") MultipartFile userPanPhoto,
			Model model
			) throws WriterException, IOException{
		
		oa.setUserName(generateUsername(oa));
		oa.setPassword(generatePassword(oa));
		oa.setAccountNum(generateAccountNum());
		oa.setIfscCode("IFSCEBNK90909090");
		oa.setAccountBalance(500000);
		oa.setCibilScore(650);
		oa.setOpenDate(LocalDateTime.now());
		
		try {
			
			oa.setUserPhoto(Base64.getEncoder().encodeToString(userPhoto.getBytes()));
			oa.setUserPanPhoto(Base64.getEncoder().encodeToString(userPanPhoto.getBytes()));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String qrContent = "Full Name" + oa.getFirstName() + " "+oa.getMiddleName() + " "+oa.getLastName() + "\n"+
										"Account Number - "+oa.getAccountNum() +"\n"+
										" IFSC Code"+ oa.getIfscCode() +"\n"+
										"User Name - " + oa.getUserName() +"\n"+
										"Adhaar Number -"+oa.getAdhaarNum() +"\n"+
										"Pan Number - " + oa.getPanNum()+"\n";
		
		byte[] qrCodeImage = QRCodeGenerator.getQRCodeImage(qrContent, 200, 200);
			
		oa.setUserQr(Base64.getEncoder().encodeToString(qrCodeImage));	
		service.openAccount(oa);
		
		
		Optional<OpenAccount> recentCustomer = service.getRecentCustomer();
		
		model.addAttribute("recent", recentCustomer.get());
		return "SuccessAccount";
	}
	
	//Generate Account Number method
	public String generateAccountNum() {
		
		Random r = new Random();
		int num = r.nextInt(9000000) + 1000000;
		String accNum = "78612"+num;
		
		return accNum;
	}
	
	public String generateUsername(OpenAccount oa) {
		
		
		Random r = new Random();
		int num = r.nextInt(999) ;
		String username = oa.getFirstName() + oa.getLastName() + num;
		System.out.println("User name: " + username);
		return username;
	}
	
	public String generatePassword(OpenAccount oa) {
		// firstCharacter
		char f = oa.getFirstName().charAt(oa.getFirstName().length() - 1);
		System.out.println(f);
		// lastCharated
		char l = oa.getLastName().charAt(0);
		System.out.println(l);
		// phone Even no find
		String[] split = oa.getPhno().split("");
		ArrayList<Integer> ph = new ArrayList<Integer>();
		for (int i = 0; i < split.length; i++) {
			ph.add(Integer.valueOf(split[i]));
		}

		List<Integer> phEven = ph.stream().filter(n -> n % 2 == 0).collect(Collectors.toList());
		int sum = (int) phEven.stream().mapToDouble(n -> n).sum();
		String evenString = "";
		for (int i = 0; i < phEven.size(); i++) {
			evenString += phEven.get(i);
		}
		// pan chard vowel no and even No
		String[] split2 = oa.getPanNum().split("");
		String vowl = "";
		String evenPan = "";
		for (int i = 0; i < split2.length; i++) {
			if (split2[i].equals("a") || split2[i].equals("i") || split2[i].equals("o") || split2[i].equals("u")
					|| split2[i].equals("e")) {
				vowl += split2[i];
			}

		}

		String password = vowl +  sum +evenPan +f+l +evenString;
		System.out.println("Password ="+password);
		return password;
	}
	

	@GetMapping("/login")
	public String loginHome() {
		
		return "index";
	}
	
	@RequestMapping("/loginVerfy")
	public String loginVerify(@RequestParam("username") String username,
			@RequestParam("password") String password,
			Model model) {
		
		List<OpenAccount> loginData = service.getLoginData();
		
		for(int i=0; i<loginData.size(); i++) {
			if(loginData.get(i).getUserName().equals(username) && loginData.get(i).getPassword().equals(password)) {
				
				Optional<OpenAccount> dataById = service.getDataById(loginData.get(i).getId());
				
				model.addAttribute("user", dataById.get());
				
				return "customerHome";
			}
		}
		
		return "errorPages/loginRejected";
	}
	
	
	@RequestMapping("/updatePassword")
	public String updatePass(@RequestParam("updateConfirmPass") String pass,
										@RequestParam("id") long id)  {
		
				OpenAccount updateById = service.updateById(id);
				updateById.setPassword(pass);
				
				service.openAccount(updateById);
				
				return "redirect:/";
	}
	@RequestMapping("/test")
	public String goTo() throws WriterException, IOException {
		
		//QRCodeGenerator.generateQrCodeImage("Dilip", 200, 200, "D:\\SpringBoot Works\\E-Bank\\src\\main\\resources\\static");
		
		return "amount";
	}
}
