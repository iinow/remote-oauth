package com.ha.security;

import java.util.Map;

import com.ha.common.AuthDefine.PROVIDER;
import com.ha.dto.FacebookOAuth2UserInfo;
import com.ha.dto.GithubOAuth2UserInfo;
import com.ha.dto.GoogleOAuth2UserInfo;
import com.ha.dto.KakaoOAuth2UserInfo2;
import com.ha.dto.OAuth2UserDto;
import com.ha.exception.OAuth2AuthenticationProcessingException;

public class OAuth2UserInfoFactory {
	public static OAuth2UserDto getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(PROVIDER.GOOGLE.toString().toLowerCase())) {
            return new GoogleOAuth2UserInfo(attributes);
        }else if (registrationId.equalsIgnoreCase(PROVIDER.FACEBOOK.toString().toLowerCase())) {
            return new FacebookOAuth2UserInfo(attributes);
        }else if (registrationId.equalsIgnoreCase(PROVIDER.GITHUB.toString().toLowerCase())) {
        	//github은 Email Api 따로 요청해야함 https://api.github.com/user/emails
            return new GithubOAuth2UserInfo(attributes);
        }else if(registrationId.equalsIgnoreCase(PROVIDER.KAKAO.toString().toLowerCase())) {
        	return new KakaoOAuth2UserInfo2(attributes);
        }else {
        	throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
