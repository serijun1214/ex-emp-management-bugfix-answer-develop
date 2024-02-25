package com.example.paging;

/**
 * ページング情報を保持するクラス.
 */
public class Paging {
  // 現在のページ
  private int nowPage = 1;
  // 1ページに表示する件数
  private int pageSize = 5;
  // 全ページ数
  private int totalPage;
  // 開始ページ
  private int startPage = 1;

  public int getNowPage() {
    return nowPage;
  }

  public void setNowPage(int nowPage) {
    this.nowPage = nowPage;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(int totalRecord) {
    if (totalRecord % pageSize == 0) {
      this.totalPage = totalRecord / pageSize;
    } else {
      this.totalPage = totalRecord / pageSize + 1;
    }
  }

  public int getStartPage() {
    return startPage;
  }

  public void setStartPage(int startPage) {
    this.startPage = startPage;
  }

  @Override
  public String toString() {
    return "Paging [nowPage=" + nowPage + ", pageSize=" + pageSize + ", totalPage=" + totalPage + ", startPage="
        + startPage + "]";
  }

  
}
