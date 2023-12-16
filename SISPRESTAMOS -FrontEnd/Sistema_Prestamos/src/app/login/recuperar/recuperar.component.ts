import { Component,OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RecuperarUsuario } from 'src/app/modelos/RecuperarUsuario';
import { AuthService } from 'src/app/services/auth.service';
import { UtilesService } from 'src/app/services/utiles.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-recuperar',
  templateUrl: './recuperar.component.html',
  styleUrls: ['./recuperar.component.css']
})
export class RecuperarComponent implements OnInit {

  usuario:RecuperarUsuario = new RecuperarUsuario();
  formulario:FormGroup
  correo:string

  constructor(
    private SUtiles:UtilesService,
    private SUsuario:AuthService,
    private router:Router,
    private formBuild:FormBuilder){}

  ngOnInit(): void {
    this.SUtiles.password_event();
    this.formulario=this.formBuild.group({
      correo:['',[
        Validators.required,
        Validators.email
      ]],
      key:['',[
        Validators.required,
        Validators.pattern("[A-Z\\d]{4}[-][A-Z\\d]{4}[-][A-Z\\d]{4}")
      ]],
      password:['',[
        Validators.required,
        Validators.pattern("(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{6,20}")
      ]]
    })
  }

  public enviarCorreo(){
    this.SUsuario.enviarCorreo(this.correo).subscribe(
      response =>{
        Swal.fire(response.mensaje,"Ingresar key de su correo","success")
      },
      err =>{
        Swal.fire("Error de envio",err.error.mensaje,"error")
      }
    )
  }
  public recuperar(){

    //Setteamos el correo
    this.usuario.email=this.correo

    this.SUsuario.recuperar(this.usuario).subscribe(
      response =>{
        this.router.navigate(["/login"])
        Swal.fire(response.mensaje,"Ingrese a su cuenta, nuevo password","success")
      },
      err =>{
        Swal.fire("Error de recuperación",err.error.mensaje,"error")
      }
    )
  }
}
