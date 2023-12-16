import { ChangeDetectorRef, Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { BreakpointObserver } from '@angular/cdk/layout'
import { TokenService } from '../services/token.service';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Enlace } from '../modelos/Enlace';


@Component({
  selector: 'app-side-nav',
  templateUrl: './side-nav.component.html',
  styleUrls: ['./side-nav.component.css']
})
export class SideNavComponent implements OnInit{
  
  @ViewChild(MatSidenav)
  sidenav! : MatSidenav;
  username:string="";
  enlaces:Array<Enlace>=new Array<Enlace>;

  constructor(private cdr:ChangeDetectorRef, private SToken:TokenService, private router:Router,private observer: BreakpointObserver) {}
  
  ngOnInit(): void {
    this.username = this.SToken.getUsername();
    this.enlaces = this.SToken.getEnlaces();
  }

  //Evento para cerrar sesión
  logout():void{
    this.SToken.logOut();
    this.router.navigate(["/login"]);
    setTimeout(() => {
      window.location.reload();
    },5); 
  }

  ngAfterViewInit() {
    // Utiliza setTimeout para retrasar la observación hasta después de la detección de cambios inicial
    setTimeout(() => {
      this.observer.observe(["(max-width: 800px)"]).subscribe((res) => {
        if (res.matches) {
          this.sidenav.mode = "over";
          this.sidenav.close();
        } else {
          this.sidenav.mode = "side";
          this.sidenav.open();
        }
        // Marca los cambios para asegurarte de que no haya errores
        this.cdr.detectChanges();
      });
    });
  }
}
