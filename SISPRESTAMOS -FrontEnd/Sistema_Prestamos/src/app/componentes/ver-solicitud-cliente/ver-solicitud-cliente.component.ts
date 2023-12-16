import { Component, OnInit } from '@angular/core';
import { TokenService } from 'src/app/services/token.service';
import { SolicitudService } from 'src/app/services/solicitud.service';
import { Solicitud } from 'src/app/modelos/Solicitud';

@Component({
  selector: 'app-ver-solicitud-cliente',
  templateUrl: './ver-solicitud-cliente.component.html',
  styleUrls: ['./ver-solicitud-cliente.component.css']
})
export class VerSolicitudClienteComponent implements OnInit {

  solicitudes: Solicitud[] = [];
  public page!:number

  constructor(
    private SToken: TokenService,
    private solicitudService: SolicitudService
  ) {}

  ngOnInit() {
    // Obtener el ID del usuario
    const idUsuario = this.SToken.getIdUsuario();

    // Llamar al servicio para obtener las solicitudes por usuario
    this.solicitudService.obtenerSolicitudesPorUsuario(idUsuario).subscribe(
      (response) => {
        this.solicitudes = response;
      },
      (error) => {
        console.error('Error al obtener solicitudes:', error);
      }
    );
  }
}
