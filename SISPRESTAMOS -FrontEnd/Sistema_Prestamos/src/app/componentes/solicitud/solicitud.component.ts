import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SolicitudService } from 'src/app/services/solicitud.service';
import Swal from 'sweetalert2';
import * as moment from 'moment';
import { Solicitud } from 'src/app/modelos/Solicitud';
import { TokenService } from 'src/app/services/token.service';
import { Cliente } from 'src/app/modelos/Cliente';
import { CuentaBancaria } from 'src/app/modelos/CuentaBancaria';
import { CuentaService } from 'src/app/services/cuenta.service';
import { AuthService } from 'src/app/services/auth.service';
import { ClienteService } from 'src/app/services/cliente.service';
import { Grupo } from 'src/app/modelos/Grupo';
import { Router } from '@angular/router';

@Component({
  selector: 'app-solicitud',
  templateUrl: './solicitud.component.html',
  styleUrls: ['./solicitud.component.css']
})
export class SolicitudComponent implements OnInit {

  public formulario:FormGroup
  public cuentas:CuentaBancaria[]=[]
  private cliente:Cliente
  private cuenta:CuentaBancaria
  private grupo = new Grupo()

  constructor(
    private formBuilder:FormBuilder,
    private SSolicitud:SolicitudService,
    private SCuenta:CuentaService,
    private SToken:TokenService,
    private SCliente:ClienteService,
    private router:Router
  ){}
  ngOnInit(): void {

    let id_usuario = this.SToken.getIdUsuario();

    //LISTAR CUENTAS
    this.SCuenta.listarCuentasXIDUsuario(id_usuario).subscribe(
      (response)=>this.cuentas=response
    )

    //OBTENEMOS AL CLIENTE
    this.SCliente.buscarXIDUsuario(id_usuario).subscribe(
      (response)=>this.cliente=response
    )

    //OBTENEMOS AL GRUPO
    this.SSolicitud.buscarIdGrupoXIdUsuario(id_usuario).subscribe(
      (response)=>{
        this.grupo.id = response
      }
    )

    /* CONSTRUIR FORMULARIO*/
    this.formulario = this.formBuilder.group({
      
      dias:['',[
        Validators.required
      ]],
      monto:[0,[
        Validators.required,
        Validators.pattern("([\\d]+|[\\d]+[.][\\d]{1,2})"),
        Validators.min(200)
      ]],
      observacion:['',[
        Validators.pattern("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s.]{4,200}")
      ]],
      numero:['',[
        Validators.required,
        Validators.pattern("[1-9][\\d]{11,19}")
      ]],
      interes:[0,[
        Validators.required
      ]],
      
    })

  }

  agregarCuenta(cuenta:CuentaBancaria){
    this.formulario.patchValue({
      numero:cuenta.numero
    })
    this.cuenta = cuenta
  }
  calcularInteres(){

    if (this.formulario.get("monto").valid){
      let interes_value:number
      let monto = this.formulario.get("monto").value
      let dias = this.formulario.get("dias").value
      switch(dias){
        case "10": interes_value = 5;break;
        case "15": interes_value = 7;break;
        case "20": interes_value = 10;break;
        case "25": interes_value = 12;break;
        default: interes_value = 15;
      }
      this.formulario.patchValue({
        interes:((interes_value/100)*monto).toFixed(2)
      })
    }
  }
  registrarSolicitud(){
    let bean = new Solicitud();
    bean.dias=this.formulario.get("dias").value
    bean.monto=this.formulario.get("monto").value
    bean.observacion=this.formulario.get("observacion").value
    bean.cliente = this.cliente
    bean.cuenta = this.cuenta
    bean.grupo = this.grupo
    this.SSolicitud.registrar(bean).subscribe(
      (response)=>{
        this.router.navigate(["/admin/solicitud"])
        Swal.fire("Solicitud Registrada",`Numero de Solicitud: ${response.id}`,"success")
      },
      (err)=>Swal.fire("Error del Sistema",err.error.mensaje,"error")
    )
  }
}
