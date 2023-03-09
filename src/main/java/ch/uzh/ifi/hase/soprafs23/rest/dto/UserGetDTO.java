package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class UserGetDTO {

  private Long id;
  private String password;
  private String username;
  private UserStatus status;
  private boolean isLoggedin;
  private LocalDateTime createDate;
  private Date birthday;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public LocalDateTime getCreateDate(){ return createDate; }

    public void setCreateDate(LocalDateTime createDate){ this.createDate = createDate;}

  public boolean getIsLoggedIn() { return isLoggedin;}

    public void setIsLoggedin(boolean isLoggedin) {this.isLoggedin=isLoggedin;}

    public Date getBirthDay() {return birthday;}

    public void setBirthDay(Date birthday){this.birthday = birthday;}
    

  public void setUsername(String username) {
    this.username = username;
  }

  public UserStatus getStatus() {
    return status;
  }

  public void setStatus(UserStatus status) {
    this.status = status;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
