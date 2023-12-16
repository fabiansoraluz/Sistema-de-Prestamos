import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { Cliente } from 'src/app/modelos/Cliente';
import { CuentaBancaria } from 'src/app/modelos/CuentaBancaria';
import { TipoCuenta } from 'src/app/modelos/TipoCuenta';
import { ClienteService } from 'src/app/services/cliente.service';
import { CuentaService } from 'src/app/services/cuenta.service';
import { TokenService } from 'src/app/services/token.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-ingresar-cuenta',
  templateUrl: './ingresar-cuenta.component.html',
  styleUrls: ['./ingresar-cuenta.component.css']
})
export class IngresarCuentaComponent implements OnInit {

  cliente: Cliente
  cuenta:CuentaBancaria = new CuentaBancaria();
  tipos: TipoCuenta[]
  bancos = ['BBVA Continental', 'BCP', 'Interbank'];
  monedas = ['Soles', 'Dolares', 'Euros'];
  formulario:FormGroup
  isUpdating = false

  constructor(
    private cuentaService: CuentaService,
    private tokenService: TokenService,
    private clienteService: ClienteService,
    private router: Router,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) { }


  ngOnInit(): void {

    // Listar todos los tipos
    this.listarTipos();
    


    // Construir formulario
    this.formulario = this.fb.group({

      nombre: ["",[
        Validators.required,
        Validators.pattern(/^[a-zA-Z0-9][a-zA-Z0-9\s]*$/),
        Validators.pattern(/^[^\u00A8]*$/),
      ]],
      moneda: ["",[
        Validators.required
      ]],
      banco: ["", [
        Validators.required
      ]],
      numero: ["", [
        Validators.required,
        Validators.pattern("(?!0+)[\\d]{12,20}")
      ]],
      tipo: ["", [
        Validators.required
      ]]

    })

    // Verificamos si es actualizar o registrar
    const idCuenta = this.route.snapshot.params['idCuenta'];

    // Si hay un ID en la ruta, estamos actualizando, no registrando
    if (idCuenta) {
      this.isUpdating = true;

      this.cuentaService.buscarCuentaBancariaPorId(idCuenta).subscribe(
        (response) =>{
          this.cuenta = response
          this.formulario.patchValue({
            nombre:response.nombre,
            moneda:response.moneda,
            banco:response.banco,
            numero:response.numero,
            tipo:response.tipo.id
          })
          this.cliente = response.cliente
        },
        (err)=>Swal.fire("Error del Sistema",err.error.mensaje,"error")
      )
    }else{
      // Obtén el idUsuario del TokenService
      const idUsuario = this.tokenService.getIdUsuario();

      // Setteamos al cliente
      this.clienteService.buscarXIDUsuario(idUsuario).subscribe(
        (response) => this.cliente = response
      );
    }
  }

  listarTipos(){
    this.cuentaService.listarTiposCuentas().subscribe(
      (response)=>this.tipos=response
    )
  }

  registrarCuentaBancaria() {

    this.buildCuenta();
    this.cuentaService.registrarCuentaBancaria(this.cuenta).subscribe(
      (cuentaBancaria) => {
        this.router.navigate(['/admin/listar-cuenta']);
        Swal.fire("Registro Exitoso",`${cuentaBancaria.nombre} fue registrada`,"success")
      },
      (err) => {
        Swal.fire("Error al registrar",err.error.mensaje,"error")
      }
    );
  }

  actualizarCuentaBancaria(){

    this.buildCuenta();
    
    this.cuentaService.actualizarCuentaBancaria(this.cuenta).subscribe(
      (cuentaBancaria) => {
        this.router.navigate(['/admin/listar-cuenta']);
        Swal.fire("Actualización Exitosa",`${cuentaBancaria.nombre} fue actualizada`,"success")
      },
      (err) => {
        Swal.fire("Error al registrar",err.error.mensaje,"error")
      }
    );
  }

  buildCuenta(){

    // Setteamos el tipo
    var tipo = new TipoCuenta();
    tipo.id = this.formulario.get("tipo").value;
    this.cuenta.tipo = tipo

    // Setteamos los demas valores
    this.cuenta.banco = this.formulario.get("banco").value;
    this.cuenta.moneda = this.formulario.get("moneda").value;
    this.cuenta.nombre = this.formulario.get("nombre").value;
    this.cuenta.numero = this.formulario.get("numero").value;
    this.cuenta.cliente = this.cliente
  }



}
