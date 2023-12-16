import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { TokenService } from '../services/token.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{

  //Para el desplazamiento de la barra menu
  @Output() sideNavToggled = new EventEmitter<boolean>();
  menuStatus: boolean = false;
  username:string="";

  constructor(private SToken:TokenService, private router:Router) {}

  ngOnInit(): void {
    this.username = this.SToken.getUsername();
  }

  //Evento para cerrar sesión
  logout():void{
    this.SToken.logOut();
    this.router.navigate(["/login"]);
    setTimeout(() => {
      window.location.reload();
    },5); 
  }

  //Barra menu
  SideNavToggle(){
    this.menuStatus = !this.menuStatus;
    this.sideNavToggled.emit(this.menuStatus);
  }
}
