package com.e_bank.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
		
		oa.setUserName(oa.getFirstName()+oa.getLastName());
		oa.setPassword(oa.getUserName()+oa.getDob().substring(0, 4));
		oa.setAccountNum(generateAccountNum());
		oa.setIfscCode("IFSCEBNK90909090");
		
		oa.setOpenDate(LocalDateTime.now());
		
		try {
			
			oa.setUserPhoto(Base64.getEncoder().encodeToString(userPhoto.getBytes()));
			oa.setUserPanPhoto(Base64.getEncoder().encodeToString(userPanPhoto.getBytes()));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String qrContent = "Full Name" + oa.getFirstName() + " "+oa.getMiddleName() + " "+oa.getLastName() + 
										"Account Number - "+oa.getAccountNum() +
										" IFSC Code"+ oa.getIfscCode() +
										"User Name - " + oa.getUserName() +
										"Adhaar Number -"+oa.getAdhaarNum() +
										"Pan Number - " + oa.getPanNum();
		
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
		
		QRCodeGenerator.generateQrCodeImage("Dilip", 200, 200, "D:\\SpringBoot Works\\E-Bank\\src\\main\\resources\\static");
		
		return "demo";
	}
}
