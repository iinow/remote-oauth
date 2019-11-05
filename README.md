# remote-oauth

1. auth 서버
   - token 발급 서버
2. resource 서버
   - 자원을 사용할 수 있는 서버

#### 사용법

1. auth 서버한테 아래 요청을 보낸다 Authorization 헤더에 Basic 값은 clientid:secret 으로 인코딩해서 전달한다.
```
    curl -d "grant_type=client_credentials&\
    email=jh@haha.com&\
    password=1234"
    -H "Content-Type: application/x-www-form-urlencoded"
    -H "Authorization: Basic Y2xpZW50OnNlY3JldA=="
    -X POST http://localhost:8080/oauth/token
```
