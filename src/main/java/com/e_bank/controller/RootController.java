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
		
		long phn=Long.parseLong(oa.getPhno());
		ArrayList<Long> al=new ArrayList();
		while(phn>0) {
			al.add(phn%10);
			phn/=10;
		}
		for(int i=0;i<al.size();i++) {
			if(i%2==0)
				phn+=al.get(i);
		}
		int a=(int)phn%10;
		phn=phn/10;
		phn+=a;
		System.out.println(phn);
		// pan chard vowel no and even No
		
		String[] ch=oa.getPanNum().toLowerCase().split("");
		String s="";
		
		for(int i=0;i<ch.length;i++) {
			if(ch[i].equals("a")||ch[i].equals("e")||ch[i].equals("i")||
					ch[i].equals("o")||ch[i].equals("u")) {
				s+=ch[i];
			}
		}
		s=s.toUpperCase();
		char[] c=oa.getPanNum().toCharArray();
		String num="";
		for(int i=0;i<c.length;i++) {
			if(c[i]>='0' && c[i]<='9') {
				num+=c[i];
			}
		}
		long lum=Long.parseLong(num);
		System.out.println(lum);
		int n=0;
		while(lum>0) {
			int num1=(int)lum%10;
			if(num1%2==0) {
				n+=num1;
			}
			lum/=10;
		}
		System.err.println(n);
		int f1=n%10; n/=10;
		f1+=n;
		System.out.println(f1);
		
		String password = ""+l+f+phn+s+f1;
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
