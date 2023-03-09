package ch.uzh.ifi.hase.soprafs23.rest.dto;

import java.sql.Date;

public class UserPostDTO {

  private String username;

  private boolean isLoggedin;

  private String password;

  private Date birthday;


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public boolean getIsLoggedIn() { return isLoggedin; }

    public void setIsLoggedIn(boolean isLoggedIn) {this.isLoggedin = isLoggedin;}

    public Date getBirthDay() {return birthday;}

    public void setBirthDay(Date birthday){this.birthday = birthday;}
}
