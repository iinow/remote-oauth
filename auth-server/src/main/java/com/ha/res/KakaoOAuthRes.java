package com.ha.res;

import java.io.Serializable;

import lombok.Data;


/**
 * {
    "access_token": "arw_1spARcGziVVJydGayoBRPZHlhQK3as5Txgo9dNkAAAFsasMgYQ",
    "token_type": "bearer",
    "refresh_token": "6Bzy8jNO75etOc83gMNa2eKPc_56esjqNj7VyAo9dNkAAAFsasMgYA",
    "expires_in": 21599,
    "scope": "profile",
    "refresh_token_expires_in": 5183999
}
 * */
@Data
public class KakaoOAuthRes implements Serializable {
	private static final long serialVersionUID = 602246858940420725L;

	private String accessToken;
	private String tokenType;
	private String refreshToken;
	private int expiresIn;
	private String scope;
	private int refreshTokenExpiresIn;
}
