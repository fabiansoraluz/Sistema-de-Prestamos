import { Component, OnInit } from '@angular/core';
import { CuentaService } from 'src/app/services/cuenta.service';
import { CuentaBancaria } from 'src/app/modelos/CuentaBancaria';
import { Router } from '@angular/router';
import { TokenService } from 'src/app/services/token.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listar-cuenta',
  templateUrl: './listar-cuenta.component.html',
  styleUrls: ['./listar-cuenta.component.css']
})
export class ListarCuentaComponent implements OnInit {

  cuentas: CuentaBancaria[];
  cuentaSeleccionada: CuentaBancaria | null = null;
  page: number;
  esModificacion: boolean = false;
  mensaje:String;

  constructor(
    private SCuenta: CuentaService,
    private SToken: TokenService,
    private router: Router
  ) { }
  
  ngOnInit(): void {
    this.listarCuentas();
    console.log(this.listarCuentas);
  }

  listarCuentas() {

    let idUsuario = this.SToken.getIdUsuario();

    this.SCuenta.listarCuentasXIDUsuario(idUsuario).subscribe(
      (cuentas) => {
        this.cuentas = cuentas;
      },
      (err) => {
        this.mensaje = err.error.mensaje
      }
    );
  }

  obtenerCuentaParaActualizar(id: number) {
    this.SCuenta.buscarCuentaBancariaPorId(id).subscribe(
      (cuenta) => {
        this.cuentaSeleccionada = cuenta[0];
        this.esModificacion = true;
        this.router.navigate(['/admin/ingresar-cuenta', { cuenta: this.cuentaSeleccionada }]);
      },
      (error) => {
        console.error('Error al obtener la cuenta para actualizar:', error);
      }
    );
  }

  eliminarCuenta(id:number){
    Swal.fire({
      title: 'Eliminar Cuenta',
      text: "¿Seguro que deseas eliminar?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) { 
        this.SCuenta.eliminarCuentaBancaria(id).subscribe(
          (response)=>{
            this.listarCuentas()
            Swal.fire("Cuenta Eliminada",response.mensaje,"success")
          },
          (err) => {
            Swal.fire("Error del Sistema",err.error.mensaje,"error")
          }
    
        )
      }
    })
  }
}
