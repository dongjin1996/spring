package com.example.hospital.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.hospital.dto.Hospital;
import com.example.hospital.service.HospitalService;
import com.example.hospital.util.MyUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HospitalController {
	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	MyUtil myUtil;
	
	@RequestMapping(value = "/") //localhost로 접속
	public String index() {
		return "index";
	}
	
	//메인페이지 보여줌
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexed() {
		return "/index";
	}
	
	//페이지 보여줌
	@RequestMapping(value = "/created", method = RequestMethod.GET)
	public String created() {
		return "bbs/created";
	}
	
	//게시글 등록
	@RequestMapping(value = "/created", method = RequestMethod.POST)
	public String createdOK(Hospital hospital, HttpServletRequest request, Model model) {
		try {
			int maxNum = hospitalService.maxNum();
			
			hospital.setNum(maxNum + 1); // num 컬럼에 들어갈 값을 1증가 시켜준다.
			
			hospitalService.insertData(hospital);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/list";
	}
	
	//리스트 페이지 보여줌
	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(Hospital hospital, HttpServletRequest request, Model model) {
		
		try {
			
			String pageNum = request.getParameter("pageNum"); //바뀌는 페이지 번호
			
			int currentPage = 1; //현재 페이지 번호 =1
			
			if(pageNum != null) currentPage = Integer.parseInt(pageNum);
			
			String searchKey = request.getParameter("searchKey");
			String searchValue = request.getParameter("searchValue"); //검색어
			
			if(searchValue == null) {
				searchKey = "name"; //검색키워드 기본값 name
				searchValue = ""; //기본값은 빈 문자열
			} else {
				if(request.getMethod().equalsIgnoreCase("GET")) {
					//get 방식으로 request가 왔다면
					//쿼리 파라메터의 값을 디코딩 해준다.
					searchValue = URLDecoder.decode(searchValue, "UTF-8");
				}
			}
		
			
			//1. 전체 게시물의 갯수 가져오기 (페이징 처리에 필요)
			int dataCount = hospitalService.getDataCount(searchKey, searchValue);
			
			//2. 페이징 처리를 한다. (준비단계)
			int numPerPage = 5; //페이지당 보여줄 데이터 갯수
			int totalPage = myUtil.getPageCount(numPerPage, dataCount); //페이지 전체 갯수
			
			if(currentPage > totalPage) currentPage = totalPage;
			
			int start = (currentPage - 1) * numPerPage + 1; // 1 6 11 16
			int end = currentPage * numPerPage; //5, 10, 15...
			
			//3.전체 게시물 리스트 가져오기.
			List<Hospital> lists = hospitalService.getLists(searchKey, searchValue, start, end);
			
			//4. 페이징 처리를 한다.
			String param = "";
			
			if(searchValue != null && !searchValue.equals("")) {
				//검색어가 있다면
				param = "searchKey=" + searchKey;
				param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8"); //컴퓨터가 알아듣게
			}
			
			String listUrl = "/list";
			
			//list ?searchKey=name&searchValue=이름
			if(!param.equals("")) listUrl += "?" + param;
			
			String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);
			
			String articleUrl = "/article?pageNum=" + currentPage;
			
			if(!param.equals("")) {
				articleUrl += "&" + param;
				//
			}
			
			model.addAttribute("lists", lists); //DB에서 가져온 전체 게시물
			model.addAttribute("articleUrl", articleUrl); //상세페이지로 이동하기 위한 URL
			model.addAttribute("pageIndexList", pageIndexList); //하단 버튼 만드는
			model.addAttribute("dataCount", dataCount); //전체 게시물 갯수
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bbs/list";
	}
	
	//get 방식으로 Request가 들어올떄
	@RequestMapping(value = "/article", method = RequestMethod.GET)
	public String article(HttpServletRequest request, Model model) {
		
		try {
			int num = Integer.parseInt(request.getParameter("num"));
			String pageNum = request.getParameter("pageNum");
			String searchKey = request.getParameter("searchkey");
			String searchValue = request.getParameter("searchValue");
			
			if(searchValue != null) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
			
			//게시물 데이터 가져오기
			Hospital hospital = hospitalService.getReadData(num);
			
			if(hospital == null) {
				return "redirect:/list?pageNum=" + pageNum;
			}
			
			//게시글의 라인수를 구한다.
			int lineSu = hospital.getOpinion().split("\n").length;
			
			String param = "pageNum=" + pageNum;
			
			if(searchValue != null && !searchValue.equals("")) {
				//검색어가 있다면
				param += "&searchKey=" + searchKey;
				param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
			}
			
			model.addAttribute("hospital", hospital);
			model.addAttribute("params", param);
			model.addAttribute("lineSu", lineSu);
			model.addAttribute("pageNum", pageNum);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "bbs/article";
	}
	
	//수정 페이지 보여줌
	@RequestMapping(value = "/updated", method = RequestMethod.GET)
	public String updated(HttpServletRequest request, Model model) throws Exception {
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue != null) {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		}
		Hospital hospital = hospitalService.getReadData(num);
		
		if(hospital == null) {
			return "redirect:list?pageNum=" + pageNum;
		}
		
		String param = "pageNum" + pageNum;
		
		if(searchValue != null && searchValue.equals("")) {
			//검색어가 있다면
			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8"); //컴퓨터의 언어로 인코딩
		}
		
		model.addAttribute("hospital", hospital);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("params", param);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("searchValue", searchValue);
		
		return "bbs/updated";
	}
	
	//게시글 수정하는기능
	@RequestMapping(value = "/updated_ok", method = RequestMethod.POST)
	public String updatedOK(Hospital hospital, HttpServletRequest request, Model model) {
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		String param = "?pageNum=" + pageNum;
		
		try {
			hospital.setOpinion(hospital.getOpinion().replaceAll("<br/>", "\r\n"));
			hospitalService.updateData(hospital);
			
			if(searchValue != null && !searchValue.equals("")) {
				//검색어가 있다면
				param += "&searchKey=" + searchKey;
				param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8"); //컴퓨터의 언어로 인코딩
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				param += "&errorMessage=" + URLEncoder.encode("정보 수정 중 에러가 발생했습니다.", "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				
				e1.printStackTrace();
			}
		}
		
		return "redirect:list" + param;
	}
	
	//삭제하는 기능
	@RequestMapping(value = "/deleted_ok", method= {RequestMethod.GET})
	public String deleteOK(HttpServletRequest request, Model model) {
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		String param = "?pageNum" + pageNum;
		

		
		try {
			hospitalService.deleteData(num);
			
			if(searchValue != null && !searchValue.equals("")) {
				//검색어가 있다면
				param += "&searchKey=" + searchKey;
				param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8"); //컴퓨터의 언어로 인코딩
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				param += "errorMessage=" + URLEncoder.encode("게시글 삭제 중 에러가 발생했습니다.", "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				
				e1.printStackTrace();
			}
		}
		
		return "redirect:/list" + param;
	}
	
}
