package com.brenda.ninjagold.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GoldController {
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index(HttpSession session, Model viewModel) {
		ArrayList<String> activity = new ArrayList<String>();
		if(session.getAttribute("userGold")==null) {
			session.setAttribute("userGold", 0);
		}
		if(session.getAttribute("activity")==null) {
			session.setAttribute("activity", activity);
		}

		viewModel.addAttribute("gold", session.getAttribute("userGold"));
		viewModel.addAttribute("activity", session.getAttribute("activity"));
		return "index.jsp";
	}
	
	@RequestMapping(value="/findgold", method=RequestMethod.POST)
	public String gold(@RequestParam("building") String building, HttpSession session) {
		Random r = new Random();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd YYYY h:mma");
		String formattedDate = formatter.format(LocalDateTime.now());
		int gold = (int)session.getAttribute("userGold");
		int goldThisTurn = 0;
		ArrayList <String> activity = (ArrayList<String>) session.getAttribute("activity");
		if (building.equals("farm")) {
			goldThisTurn = r.nextInt((20 - 10)+1)+10;
			activity.add("You have entered the farm and earned" +" "+ goldThisTurn +" "+ formattedDate);
		}
		
		else if (building.equals("cave")) {
			goldThisTurn = r.nextInt((10 - 5)+1)+5;
			activity.add("You have entered the cave and earned"+" "+ goldThisTurn +" "+ formattedDate);
		}
		
		else if (building.equals("house")) {
			goldThisTurn = r.nextInt((5 - 2)+1)+2;
			activity.add("You have entered the house and earned"+" "+ goldThisTurn +" "+ formattedDate);
		}
		
		else {
			goldThisTurn = r.nextInt((50 + 50)+1)-50;
			if( goldThisTurn < 0 ) {
				activity.add("You have entered the casino and lost" +" "+ goldThisTurn +" "+ formattedDate);

			}else {
				activity.add("You have entered the casino and won"+" "+ goldThisTurn +" "+ formattedDate);

			}

		}
		int totalGold = gold + goldThisTurn;
		session.setAttribute("userGold", totalGold);
		session.setAttribute("activity", activity);
		System.out.println(activity);
		return "redirect:/";
	}
	
}
