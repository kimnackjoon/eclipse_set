package com.nackjoon.nj.core.model;

public class Paging {
	private boolean isAutoInit = false;
	
	//현재 페이지
	private int page;
	
	//총 레코드 수
	private int total;
	
	//첫 번째 글번호(순서정보)
	private int firstNumber;
	
	//리스트(화면)당 글 수
	private int rowCount;
	
	//페이징 그룹의 표시 페이지 수
	private int colCount;
	
	//현재 페이지 첫 번째 글번호(순서정보, DB 검색 쿼리에서 검색 페이지 구간 선별용)
	private int rowStart;
	
	//현재 페이지 마지막 글번호(순서정보, DB 검색 쿼리에서 검색 페이지 구간 선별용)
	private int rowEnd;
	
	//첫 번째 페이지 번호
	private int pageFirst;
	
	//페이징에서 첫번째 페이지 번호
	private int pageStart;
	
	//이전 페이지 번호
	private int pagePrevious;
	
	//다음 페이지 번호
	private int pageNext;
	
	//페이징에서 마지막 페이지 번호
	private int pageEnd;
	
	//마지막 페이지 번호
	private int pageLast;
	
	public Paging() {
		this(10,10);
	}
	
	public Paging(int rowCount, int colCount) {
		this.page = 1;
		this.total = 0;
		this.firstNumber = 0;
		
		this.rowCount = rowCount;
		this.colCount = colCount;
		this.rowStart = 0;
		
		this.pageFirst = 1;
		this.pageStart = 1;
		this.pagePrevious = 0;
		this.pageNext = 0;
		this.pageEnd = 1;
		this.pageLast = 1;
		
		this.init();
	}
	
	public void init() {
		rowStart = (page - 1) * rowCount + 1;
		rowEnd = rowStart + rowCount - 1;
		
		if(rowEnd > total) {
			rowEnd = total;
		}
		
		firstNumber = total - (rowCount * (page - 1));
		
		pageFirst = 1;
		pageLast = (int)Math.ceil((double)total / rowCount);
		
		if(pageLast < 1) {
			pageLast = 1;
		}
		
		pageStart = (int)Math.ceil((double)(page - colCount) / colCount) * colCount + 1;
		pageEnd = pageStart + colCount - 1;
		
		if(pageEnd > pageLast) {
			pageEnd = pageLast;
		}
		
		if(pageEnd < 1) {
			pageEnd = 1;
		}
		
		if(page > 1) {
			pagePrevious = page - 1;
		} else {
			pagePrevious = 0;
		}
		
		if(page < pageLast) {
			pageNext = page + 1;
		} else {
			pageNext = 0;
		}
	}
	
	public boolean isAutoInit() {
		return isAutoInit;
	}
	
	public void setAutoInit(boolean isAutoInit) {
		this.isAutoInit = isAutoInit;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		if(page < 1) {
			page = 1;
		}
		
		this.page = page;
		
		if(isAutoInit) {
			this.init();
		}
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		if(total < 1) {
			total = 0;
		}
		
		this.total = total;
		
		if(isAutoInit) {
			this.init();
		}
	}
	
	public int getFirstNumber() {
		return firstNumber;
	}
	
	public int getRowCount() {
		return rowCount;
	}
	
	public void setRowCount(int rowCount) {
		if(rowCount < 1) {
			rowCount = 1;
		}
		
		this.rowCount = rowCount;
		
		if(isAutoInit) {
			this.init();
		}
	}
	
	public int getColCount() {
		return colCount;
	}
	
	public void setColCount(int colCount) {
		if(colCount < 1) {
			colCount = 1;
		}
		
		this.colCount = colCount;
		
		if(isAutoInit) {
			this.init();
		}
	}
	
	public int getRowStart() {
		return rowStart;
	}
	
	public int getRowEnd() {
		return rowEnd;
	}
	
	public int getPageFirst() {
		return pageFirst;
	}
	
	public int getPageStart() {
		return pageStart;
	}
	
	public int getPagePrevious() {
		return pagePrevious;
	}
	
	public int getPageNext() {
		return pageNext;
	}
	
	public int getPageEnd() {
		return pageEnd;
	}
	
	public int getPageLast() {
		return pageLast;
	}
	
	@Override
	public String toString() {
		return "Paging [page=" + page + ", total=" + total + ", firstNumber="
				+ firstNumber + ", rowCount=" + rowCount + ", colCount=" + colCount
				+ ", rowStart=" + rowStart + ", rowEnd=" + rowEnd
				+ ", pageFirst=" + pageFirst + ", pageStart=" + pageStart
				+ ", pagePrevious=" + pagePrevious + ", pageNext=" + pageNext
				+ ", pageEnd=" + pageEnd + ", pageLast=" + pageLast + "]";
	}
}
