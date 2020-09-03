package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.board.domain.BoardDTO;

//@Mapper : XML Mapper에서 메서드의 이름과 일치하는 SQL문을 찾아 실행함
//		    즉 SQL쿼리를 호출!
@Mapper
public interface BoardMapper {
	
	//게시글을 생성하는 insert쿼리를 호출하는 메서드(params에 게시글의 정보 담김!)
	public int insertBoard(BoardDTO params);
	
	//게시글을 조회하는 select쿼리 호출(where조건으로 idx를 사용해서 특정 게시글 조회)
	public BoardDTO selectBoardDetail(Long idx);
	
	//게시글을 수정하는 update쿼리 호출
	public int updateBoard(BoardDTO params);
	
	//게시글을 삭제하는 delete쿼리 호출
	//delete_yn : 실제로 데이터를 삭제하지 않고, 컬럼의 상태값을 y, n으로 지정
	//상태값이 n으로 지정된 데이터만 노출!
	public int deleteBoard(Long idx);
	
	//게시글 목록을 조회하는 select 쿼리 호출
	public List<BoardDTO> selectBoardList();
	
	//삭제 여부가 n으로 지정된 게시글의 수를 조회하는 select쿼리문 호출(페이징 처리!)
	public int selectBoardTotalCount();
}
