import { Component, OnInit } from '@angular/core';
import { Cuota } from 'src/app/modelos/Cuota';
import { CuotaDTO } from 'src/app/modelos/cuotaDTO';
import { ClienteService } from 'src/app/services/cliente.service';
import { CuotaService } from 'src/app/services/cuota.service';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-listado-cuota',
  templateUrl: './listado-cuota.component.html',
  styleUrls: ['./listado-cuota.component.css']
})
export class ListadoCuotas implements OnInit {
  cuotas: CuotaDTO[] = [];
  

  public page:number

  errorLista: String

  constructor(
    private cuotaService: CuotaService,
    private SToken: TokenService,
    private SCliente: ClienteService) {}

  ngOnInit(): void {
    var id = this.SToken.getIdUsuario();
    this.SCliente.buscarXIDUsuario(id).subscribe(
      (response)=>{
        this.listar(response.id)
      }
    );
    
  }


public listar(id) {
  this.cuotaService.listarCuotasPorIdCliente(id).subscribe(
    (response) => {
      this.cuotas = response;
      console.log('Listado de Cuotas:', this.cuotas);
    },
    (err) => this.errorLista = err.error.mensaje
  );
}

  obtenerEstadoSolicitud(numeroSolicitud: number): string {
    switch (numeroSolicitud) {
      case 0:
        return 'Pendiente';
      case 1:
        return 'Pagado';
      case 2:
        return 'Parcialmente';
      default:
        return 'Desconocido';
    }
  }
    
}
