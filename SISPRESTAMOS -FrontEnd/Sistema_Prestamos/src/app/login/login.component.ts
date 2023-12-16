import { Component,OnInit,Renderer2, ElementRef } from '@angular/core';
import{Router} from '@angular/router';
import { TokenService } from '../services/token.service';
import { AuthService } from '../services/auth.service';
import { LoginUsuario } from '../modelos/LoginUsuario';
import Swal from 'sweetalert2';
import { UtilesService } from '../services/utiles.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  isLogin=false
  isLoginFail=false
  user:LoginUsuario=null;
  username:string
  password:string
  roles:string[] = [];
  error_message:string;

  constructor(
    private router: Router,
    private renderer:Renderer2,
    private el: ElementRef,
    private SToken:TokenService,
    private SAuth:AuthService,
    private SUtiles:UtilesService
    ){}
  
  navigateToCustomPage(){
    this.router.navigateByUrl("/login");
  }

  ngOnInit(){

    if(this.SToken.getToken()){
      this.isLogin=true;
      this.isLoginFail=false;
      this.roles=this.SToken.getAuthorities();
    }

    //Importamos los fuentes de GoogleFonts

    const link = this.renderer.createElement('link');
    this.renderer.setAttribute(link,'href','https://fonts.googleapis.com/css2?family=Rubik:wght@300;400;500;700&family=Poppins:wght@300;400;500;700&display=swap')
    this.renderer.setAttribute(link,'rel','stylesheet')
    this.renderer.appendChild(this.el.nativeElement.ownerDocument.head, link);

    //Agregamos nuestras funciones del login.html
    this.formulario();

    //Agregamos eventos para el password
    this.SUtiles.password_event();
  }

  //Método para iniciar sesión

  onLogin():void{
    this.user = new LoginUsuario(this.username,this.password);
    this.SAuth.login(this.user).subscribe(
      data => {
        this.isLogin = true;
        this.isLoginFail = false;
        this.SToken.setToken(data.token)
        this.SToken.setUsername(data.username)
        this.SToken.setAuthorities(data.authorities)
        this.SToken.setEnlaces(data.enlaces)
        this.SToken.setIdUsuario(data.idUsuario)

        this.roles = data.authorities;
        this.router.navigate(["/admin/dashboard"])
      },
      err =>{
        this.isLogin = false
        this.isLoginFail = true
        this.error_message = "Usuario y/o contraseña incorrecta"
        Swal.fire("Error de Login",this.error_message,"error")
      }
    )
  }


  //ANIMACIONES
  formulario(){
    const inputs = document.querySelectorAll<HTMLInputElement>(".form__input");
    inputs.forEach((input: HTMLInputElement) => {
      const field = input.parentElement as HTMLElement;
      const label = input.previousElementSibling as HTMLElement;
      input.addEventListener("focus", () => {
        field.classList.add("form__field--active");
        label.classList.add("form__label--active");
      });
      input.addEventListener("blur", () => {
        const valor = input.value;
        if (valor === "") {
          field.classList.remove("form__field--active");
          label.classList.remove("form__label--active");
        }
      });
    });
  }
}
