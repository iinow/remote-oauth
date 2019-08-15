package com.ha.common;

import org.springframework.beans.factory.annotation.Autowired;

import com.ha.common.http.GithubTemplate;
import com.ha.common.http.HttpServerTemplate;
import com.ha.common.http.KakaoTemplate;

public class Define {
	
	@Autowired
	private GithubTemplate githubTemplate;
	
	@Autowired
	private KakaoTemplate kakaoTemplate;
	
	public enum Provider {
		KAKAO(1),
		GOOGLE(2),
		GITHUB(3);
		
		public final int type;
		
		Provider(int type) {
			this.type = type;
		}
		
		public static Provider findProvider(int type) {
			for(Provider resource: values()) {
				if(resource.type == type) {
					return resource;
				}
			}
			return null;
		}
		
//		public HttpServerTemplate findHttpServerTemplate(OAuthResource type) {
//			switch(type) {
//				case GITHUB:
//					return githubTemplate;
//				case KAKAO:
//					return kakaoTemplate;
//				default:
//					break;
//			}
//			return null;
//		}
	}
}
