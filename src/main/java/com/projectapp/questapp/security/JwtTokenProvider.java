package com.projectapp.questapp.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    //token ımızı oluştutuken kullandığımız özel bir key

    @Value("${questapp.app.secret}")
    private String APP_SECRET;

    //kaç saniyede bu tokenlar geçerliliğini yitiriyor
    @Value("${questapp.expires.in}")
    private long EXPIRES_IN;

    public String generateJwtToken(Authentication auth){
        JwtUserDetails userDetails= (JwtUserDetails) auth.getPrincipal();
        //principal aslında authenticate edeceğimiz user demek.
        Date expireDate=new Date(new Date().getTime() + EXPIRES_IN);


        //authenticate edeceğimiz user ın id sini verdik. oluşturulma zamanı, anahtarı oluşturmak için seçtiğimiz algoritmayı verdik.
        //bu key i oluştururken getusername ile de oluşturabilirdik.
        return  Jwts.builder().setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,APP_SECRET).compact();

    }

    public String generateJwtTokenByUserId(Long userId) {
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder().setSubject(Long.toString(userId))
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
    }
//userıd ile oluşturduğumuz key den userıd yi çözüyor olmamız lazım bunun için aşağıdaki metodu kullanıcaz.
    Long getUserIdFromJwt(String token) {
        Claims claims=Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
//oluşturalan token ı validate  etmemiz gerekir.
//(frontend tarafından bize bir istek geldiğinde user bize bir tokenla gelir. Bu token doğru mu yani bizim oluşturduğumuz token mı diye kontrol etmemiz gerekir. veya süresi dolmuş mu ?)
    boolean validateToken(String token){

        try{

            //bu app_secret i kullanarak parse edebiliyorsak bu bizim uygulamamızın oluşturduğu bir keydir.
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);

            return !isTokenExpired(token); //expired olduysa false döner
        }
        catch (SignatureException e){

            return false;
        }
        catch (MalformedJwtException e){

            return false;
        }
        catch (ExpiredJwtException e){
            log.error("Token parsing failed: {}", e.getMessage());
            return false;
        }

        catch (UnsupportedJwtException e){
            log.error("Token parsing failed: {}", e.getMessage());
            return false;
        }
        catch (IllegalArgumentException e){
            log.error("Token parsing failed: {}", e.getMessage());
            return false;
        }
        catch (JwtException e){
            log.error("Token parsing failed: {}", e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) {

        //bu token in ne zaman expire olacağı(expiration)
        Date expiration=Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date()); //expiration bugunun tarihinden önce mi -> önce ise true dönecek değil ise false

    }
}
