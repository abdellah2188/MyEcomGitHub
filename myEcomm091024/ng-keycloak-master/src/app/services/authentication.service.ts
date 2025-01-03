import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import {Observable} from 'rxjs';
import {Order} from '../model/Order.model';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  public host:string="https://localhost:8443";
  public authenticated: boolean = false;
  public authenticatedUser: any;
  private users=[
    {username:"admin", password:"1234",roles:['USER','ADMIN']},
    {username:"user1", password:"1234",roles:['USER']},
    {username:"user2", password:"1234",roles:['USER']}
  ]

  constructor(private http:HttpClient) {
  }

  login(username:string,password:string){
    let user;
    this.users.forEach(u=>{
      if(u.username===username && u.password===password){
        user=u;
        this.authenticatedUser={username:user.username, roles:user.roles};
        localStorage.setItem("authToken",btoa(JSON.stringify(this.authenticatedUser)));

      }
    })
    if(user){
      this.authenticated=true;
      this.authenticatedUser= user;
      //localStorage.setItem("authToken",btoa(JSON.stringify(this.authenticatedUser)));
    }
    else{
      this.authenticated=false;
    }
  }

  loadUser(){
    let t=localStorage.getItem('authToken');

    if(t){
      let user= JSON.parse(atob(t));
      this.authenticatedUser= {username:user.username, roles:user.roles}
      console.log("MMMMM", this.authenticatedUser);
      this.authenticated=true;
    }
  }


  isAdmin(){
    if(this.authenticatedUser){
      return this.authenticatedUser.roles.indexOf("ADMIN")>-1;
    }
    else return false;
  }
  /*
  isUser(){
    if(this.authenticatedUser){
      return this.authenticatedUser.roles.indexOf("User")>-1;
    }
    else return false;
  }*/

  isAuthenticated(){
    return this.authenticated;
  }
  logout(){
    this.authenticated=false;
    console.log("TTTTTTTT", "ListCaddies_"+this.authenticatedUser.username);
    localStorage.removeItem("ListCaddies_"+this.authenticatedUser.username);

    this.authenticatedUser=undefined;
    localStorage.removeItem('authToken');

  }

}
