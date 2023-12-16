import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DistritoDTO } from 'src/app/modelos/DistritoDTO';
import { Usuario } from 'src/app/modelos/Usuario';
import { AuthService } from 'src/app/services/auth.service';
import { UbigeoService } from 'src/app/services/ubigeo.service';
import { UtilesService } from 'src/app/services/utiles.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-registrar',
  templateUrl: './registrar.component.html',
  styleUrls: ['./registrar.component.css']
})
export class RegistrarComponent implements OnInit{
  
  public usuario:Usuario = new Usuario();
  public apellido:string
  public formulario:FormGroup

  //Configuración de ubigeo
  departamentos:string[]
  departamento:string=""
  provincias:string[]
  provincia:string=""
  distritos:DistritoDTO[]
  idUbigeo:string=""

  constructor(
    private SUtiles:UtilesService,
    private SUsuario:AuthService,
    private SUbigeo:UbigeoService,
    private router:Router,
    private formBuilder:FormBuilder
  ){}

  ngOnInit(): void {
    //Iniciar evento password
    this.SUtiles.password_event();
    //Listar departamentos
    this.SUbigeo.departamentos().subscribe(
      response => this.departamentos=response
    )
    //Construimos el formulario
    this.formulario = this.formBuilder.group({
      nombre:['',[
        Validators.required,
        Validators.pattern("(?!\\s+)[a-zA-ZáéíóúüÁÉÍÓÚÜñ\\s]{4,30}")
      ]],
      apellido:['',[
        Validators.required,
        Validators.pattern("(?!\\s+)[a-zA-ZáéíóúüÁÉÍÓÚÜñ\\s]{3,30}[\\s](?!\\s+)[a-zA-ZáéíóúüÁÉÍÓÚÜñ\\s]{3,30}")
      ]],
      correo:['',[
        Validators.required,
        Validators.email,
        Validators.pattern("(?![0-9])[a-zA-ZáéíóúüÁÉÍÓÚÜñ\\d]+[@][a-zA-ZáéíóúüÁÉÍÓÚÜñ]+[.][a-zA-Z]{2,3}")
      ]],
      username:['',[
        Validators.required,
        Validators.pattern("(?!\\s+)(?=.*[a-z])[a-zA-ZáéíóúÁÉÍÓÚñ.\\d]{6,12}")
      ]],
      password:['',[
        Validators.required,
        Validators.pattern("(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{6,20}")
      ]],
      celular:['',[
        Validators.required,
        Validators.pattern("(?![0])[\\d]{9}")
      ]],
      dni:['',[
        Validators.required,
        Validators.pattern("[\\d]{8}"),
        Validators.min(5000)
      ]]
    })
  }
  
  public listarProvincias(){
    this.provincia=""
    this.usuario.persona.ubigeo.id=""
    this.SUbigeo.provincias(this.departamento).subscribe(
      response => this.provincias=response
    )
  }

  public listarDistritos(){
    this.usuario.persona.ubigeo.id=""
    this.SUbigeo.distrito(this.provincia).subscribe(
      response => this.distritos = response
    )
  }

  public registrar(){
    //Validamos los apellidos
    if(this.apellido==null && this.getApellidos().length==0){
      Swal.fire("Error de registro","Ingresar mínimo 2 apellidos","error");
      return;
    }
    //Validamos ubigeo
    if(this.idUbigeo===''){
      Swal.fire("Error de registro","Seleccionar ubigeo","error");
      return;
    }

    //Construimos el usuario
    this.buildUser()

    //Registramos el usuario
    this.SUsuario.registrar(this.usuario).subscribe(
      response =>{
        this.router.navigate(["/login"])
        Swal.fire("Usuario registrado",'El usuario ' + response.username + ' fue registrado.',"success")
      },
      err =>{
        Swal.fire("Error de registro",err.error.mensaje,"error")
      }
    )
  }

  private getApellidos():string[]{
    let apellidos:string[]=this.formulario.get("apellido").value.trim().split(" ");
    return apellidos;
  }

  private buildUser(){
    this.usuario.correo             = this.formulario.get("correo").value
    this.usuario.password           = this.formulario.get("password").value
    this.usuario.username           = this.formulario.get("username").value
    this.usuario.persona.nombre     = this.formulario.get("nombre").value
    this.usuario.persona.paterno    = this.getApellidos()[0]
    this.usuario.persona.materno    = this.getApellidos()[1]
    this.usuario.persona.dni        = this.formulario.get("dni").value
    this.usuario.persona.celular    = this.formulario.get("celular").value
    this.usuario.persona.ubigeo.id  = this.idUbigeo
  }

}
