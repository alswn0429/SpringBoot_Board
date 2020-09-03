package com.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class MapperTests {
	
	//BoardMapper 인터페이스 빈 주입
	@Autowired
	private BoardMapper boardMapper;
	
	//게시글 생성을 처리하는 메서드
	//BoardDTO객체 생성 후 set 메서드를 이용해 게시글 제목, 내용, 작성자를 지정
	@Test
	public void testOfInsert() {
		BoardDTO params = new BoardDTO();
		params.setTitle("1번 게시글 제목");
		params.setContent("1번 게시글 내용");
		params.setWriter("테스터");
		
		int result = boardMapper.insertBoard(params);
		System.out.println("결과는 " + result + "입니다.");
	}
	
	//try문에서 board에 저장된 게시글 정보를 Jackson라이브러를 이용해서
	//JSON문자열로 변경한 뒤에 콘솔에 출력
	@Test
	public void testOfSelectDetail() {
		BoardDTO board = boardMapper.selectBoardDetail((long) 1);
		try {
			String boardJson = new ObjectMapper().writeValueAsString(board);
			
			System.out.println("========================================");
			System.out.println(boardJson);
			System.out.println("========================================");
		} catch (JsonProcessingException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOfUpdate() {
		BoardDTO params = new BoardDTO();
		params.setTitle("1번 게시글 제목을 수정합니다.");
		params.setContent("1번 게시글 내용을 수정합니다.");
		params.setWriter("홍길동");
		params.setIdx((long) 1);
		
		int result = boardMapper.updateBoard(params);
		if (result == 1) {
			BoardDTO board = boardMapper.selectBoardDetail((long) 1);
			try {
				String boardJson = new ObjectMapper().writeValueAsString(board);
				
				System.out.println("===================================");
				System.out.println(boardJson);
				System.out.println("===================================");
			} catch (JsonProcessingException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	//값을 실제로 삭제하는 것이 아니라 삭제여부 컬럼의 상태 값만 변경하는 것 => update!!
	@Test
	public void testOfDelete() {
		int result = boardMapper.deleteBoard((long) 1);
		if (result == 1) {
			BoardDTO board = boardMapper.selectBoardDetail((long) 1);
			try {
				String boardJson = new ObjectMapper().writeValueAsString(board);
				
				System.out.println("=========================================");
				System.out.println(boardJson);
				System.out.println("=========================================");
			} catch (JsonProcessingException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testMultipleInsert() {
		for (int i = 2; i <= 50; i++) {
			BoardDTO params = new BoardDTO();
			params.setTitle(i + "번 게시글 제목");
			params.setContent(i + "번 게시글 내용");
			params.setWriter(i + "번 게시글 작성자");
			boardMapper.insertBoard(params);
		}
	}
	
	@Test
	public void testSelectList() {
		int boardTotalCount = boardMapper.selectBoardTotalCount();
		if (boardTotalCount > 0) {
			List<BoardDTO> boardList = boardMapper.selectBoardList();
			if (CollectionUtils.isEmpty(boardList) == false) {
				for (BoardDTO board : boardList) {
					System.out.println("======================================");
					System.out.println(board.getTitle());
					System.out.println(board.getContent());
					System.out.println(board.getWriter());
					System.out.println("======================================");
					{
				}
				}
			}
		}
	}
	
}
