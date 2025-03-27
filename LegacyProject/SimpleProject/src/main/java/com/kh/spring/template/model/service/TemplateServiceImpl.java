package com.kh.spring.template.model.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TemplateServiceImpl implements TemplateService {
	private final String SERVICE_KEY = "";	//KEY
	
	@Override
	public String requestGetTemplate(int pageNo) {
		if(pageNo < 1) {
			pageNo = 1;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("");	
		sb.append("?serviceKey=" + SERVICE_KEY);
		sb.append("&pageNo=" + pageNo);
		sb.append("&numOfRows=9");
		sb.append("&resultType=json");
		
		URI uri = null;
		try {
			uri = new URI(sb.toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String apiResponseData = new RestTemplate().getForObject(uri, String.class);
		log.info("api test : {}",apiResponseData);
		return apiResponseData;
	}

}
