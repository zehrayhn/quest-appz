package com.projectapp.questapp.security;

import com.projectapp.questapp.services.LikeService;

import com.projectapp.questapp.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {


     @Autowired
     JwtTokenProvider jwtTokenProvider;
     @Autowired
     UserDetailsServiceImpl userDetailsService;

   // @Autowired
    //public void setLikeServiceb(JwtTokenProvider jwtTokenProvider){
     //   this.jwtTokenProvider=jwtTokenProvider;
   // }

  //  @Autowired
  //  public void setLikeServicec(UserDetailsServiceImpl userDetailsService){
     //   this.userDetailsService=userDetailsService;
   // }

    //frontend tarafından backend tarafına istek geldiğinde spring security nin yaptığı bir sürü filtreleme var.
    //biz buraya eksta aşama daha ekliyoruz.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

         try{
            String jwtToken= extractJwtFromRequest(request);
            //eğer token doluysa ve validate ise bu token içerisinden user ımızı alıcaz. ve daha sonra authentication vericez
            if(StringUtils.hasText(jwtToken) && jwtTokenProvider.validateToken(jwtToken)) {
                Long id = jwtTokenProvider.getUserIdFromJwt(jwtToken);
                UserDetails user = userDetailsService.loadUserById(id);
                if (user != null) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(auth);

                }

            }
            }catch (Exception e){
               return;
         }


        filterChain.doFilter(request,response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        //Authorization header ımızı aldık.
        String bearer= request.getHeader("Authorization");
        //bearer dolu mu gelmiş diye kontrol ederiz.
       if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer "))
        return bearer.substring("Bearer".length() + 1);
       //bearer ve boşluk karakterinden sonrasını alır döndürür.

             return null;
    }
}
