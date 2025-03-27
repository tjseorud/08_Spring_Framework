package com.kh.spring.busan.model.service;

public interface BusanService {
	//1
	String requestGetBusan(int pageNo);
	
	//2
	String requestGetBusanDetail(int pk);
	
	//3(식당에 후기 달기)
	
}
