package com.ha.common;

public class Define {
	
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
	}
}
