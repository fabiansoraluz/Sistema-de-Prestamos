import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SolicitudDTO } from 'src/app/modelos/SolicitudDTO';
import { SolicitudService } from 'src/app/services/solicitud.service';
import { TokenService } from 'src/app/services/token.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listar-solicitud',
  templateUrl: './listar-solicitud.component.html',
  styleUrls: ['./listar-solicitud.component.css']
})
export class ListarSolicitudComponent implements OnInit {

  data: any = [];
  Id: number;

  private rol:String
  public solicitudDTO:SolicitudDTO = new SolicitudDTO();

  filtroForm = {
    prestatario: '',
    estado: ''
  };

  constructor(
    private SToken: TokenService,
    private SSolisitud: SolicitudService,
    private router: Router
  ) { }

  ngOnInit() {
    this.rol=this.SToken.getAuthorities()[0];
    const idUsuario = this.SToken.getIdUsuario();
    if(this.rol === 'prestamista'){
      this.listarXPrestamista(idUsuario)
    }else{
      this.listarXJefe(idUsuario)
    }
  }

  

  public listarXPrestamista(idUsuario:number) {
    this.SSolisitud.obtenerGrupodeUsuarioPrestamista(idUsuario).subscribe(
      (x) => {
        if (x) {
          this.Id = x;
          this.solicitudDTO.idGrupo=x
          this.imprimirId();
        }
      },
      (error) => {
        // Manejar errores si es necesario
        console.error("Error al obtener el valor del prestamista", error);
      }
    );
  }

  public listarXJefe(idUsuario:number){
    this.SSolisitud.obtenerGrupodeUsuario(idUsuario).subscribe(
      (response) => {
        if (response) {
          this.Id = response;
          this.solicitudDTO.idGrupo=response;
          this.imprimirId();
        }
      }
    );

  }

  imprimirId() {
    this.SSolisitud.listaSolicitudGrupo(this.Id).subscribe(x => {
      this.data = x;
    })

  }

  verSolicitud(idSolicitud: string) {
    if(this.rol === "prestamista"){
      this.router.navigate(['/admin/aprobar-solicitud', idSolicitud]);
    }else{
      Swal.fire("Error del Sistema","Permitido solo para prestamistas","error");
    }
  }

  obtenerEstadoSolicitud(numeroSolicitud: number): string {
    if(numeroSolicitud == 1){
      return 'Pendiente';
    }else if(numeroSolicitud == 2){
      return 'Aprobado';
    }else if(numeroSolicitud == 3){
      return 'Rechazado';
    }else{
      return 'Desconocido';
    }
  }
  filtrar(){
    this.SSolisitud.filtrarSolicitud(this.solicitudDTO).subscribe(
      (response)=>this.data=response,
      (err)=>this.data=[]
    )
  }
}




