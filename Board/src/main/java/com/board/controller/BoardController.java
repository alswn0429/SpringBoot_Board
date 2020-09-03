package com.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.board.service.BoardService;

//@Controller : 해당 클래스가 사용자의 요청과 응답을 처리(UI 담당)
@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	//@GetMapping : 새로운 URI매핑
	@GetMapping(value = "/board/write.do")
	public String openBoardWrite(Model model) {
		
		String title = "제목";
		String content = "내용";
		String writer = "홍길동";
		
		//addAttribute : 이 메서드를 이용해서 화면(html)으로 데이터를 전달!
		//html에서는 ${ }표현식을 이용해서 전달받은 데이터에 접근
		model.addAttribute("t", title);
		model.addAttribute("c", content);
		model.addAttribute("w", writer);
		
		return "board/write";
	}
}
