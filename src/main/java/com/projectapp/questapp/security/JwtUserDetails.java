package com.projectapp.questapp.security;



import com.projectapp.questapp.entities.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class JwtUserDetails implements UserDetails{
    public Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

   private JwtUserDetails(Long id,String username,String password,Collection<? extends GrantedAuthority> authorities){
       this.id=id;
       this.username=username;
       this.password=password;
       this.authorities=authorities;


   }

   public static JwtUserDetails create(User user){
       List<GrantedAuthority> authoritiesList=new ArrayList<>();
       authoritiesList.add(new SimpleGrantedAuthority("user"));
       return new JwtUserDetails(user.getId(),user.getUserName(), user.getPassword(), authoritiesList);
    // authoritties ile yetki sahibi kullanıcıların listesini oluşturduk. bizim sadece bir kullanıcı tipimiz var o da user.
    //jwtUserDetails -> authentication için kullanacağımız user objemizi oluşturduk.
   }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //authentication sınıfı için sadece adın ve şifrenin olduğu özel bir sınıf oluştururuz.

}
