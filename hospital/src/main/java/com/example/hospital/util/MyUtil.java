package com.example.hospital.util;

import org.springframework.stereotype.Service;

@Service
public class MyUtil {
	//전체 페이지의 갯수를 구한다.
	public int getPageCount(int numPerPage, int dataCount) {
		int pageCount = 0;
		pageCount = dataCount / numPerPage;
		
		if(dataCount % numPerPage != 0) {
			pageCount++;
		}
		
		return pageCount;
	}
	
	public String pageIndexList(int currentPage, int totalPage, String listUrl) {
		StringBuffer sb = new StringBuffer(); //문자열 데이터를 자주 추가하거나 삭제할때는 메모리낭비를 줄여준다
		int numPerBlock = 5; //이전과 다음사이 숫자를 몇개 표시 할건지
		int currentPageSetup; // 이전버튼에 들어갈 값
		int page; //그냥 페이지 숫자를 클릭했을때 들어갈 값
		
		if(currentPage == 0 || totalPage == 0) return "";
		
		//검색어가 있을떄 : 
		if(listUrl.indexOf("?") != -1) {
			// '?'가 들어있다면 (쿼리스트링이 있다면)
			listUrl += "&";
		} else {
			listUrl += "?";
		}
		
		//1.이전 버튼 만들기
		
		currentPageSetup = (currentPage / numPerBlock) * numPerBlock;
		
		//currenPage가 5 10 15 20 일때 currenPageSetup 0 5 10 15..
		if(currentPage % numPerBlock == 0) {
			currentPageSetup = currentPageSetup - numPerBlock;
		}
		
		if(totalPage > numPerBlock && currentPageSetup > 0) {
			sb.append("<a href=\"" + listUrl + "pageNum=" + currentPageSetup + "\">◀이전</a>&nbsp;");
			
			//<a href="" > 이전</a> 모양을 만든다.
		}
		
		//2. 그냥 페이지 ( 6 7 8 9 10)이동 버튼 만들기
		
		page = currentPageSetup + 1; //1 6 11 16;;
		
		while(page <= totalPage && page <= (currentPageSetup + numPerBlock)) {
			if(page == currentPage) {
				sb.append("<font color=\"red\">" + page + "</font>&nbsp;");
			}else {
				//현재 선택반 페이지가 아니라면
				sb.append("<a href=\"" + listUrl + "pageNum=" + page + "\">" + page + "</a>&nbsp;");
				// <a href="list?pageNum=7">7</a>&nbsp;
			}
			
			page++;
		}
		
		//3. 다음 버튼 만들기
		
		if(totalPage - currentPageSetup > numPerBlock) {
			sb.append("<a href=\"" + listUrl + "pageNum=" + currentPageSetup + "\">다음▶</a>&nbsp;" );
		}
		
		//4. 버튼 합쳐서 문자열로 리턴
		System.out.println(sb.toString());
		
		return sb.toString();
	}
	
	
}
