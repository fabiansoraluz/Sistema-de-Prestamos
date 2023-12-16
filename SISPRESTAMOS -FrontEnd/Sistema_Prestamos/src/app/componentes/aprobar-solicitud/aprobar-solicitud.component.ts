import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Prestamo } from 'src/app/modelos/Prestamo';
import { Solicitud } from 'src/app/modelos/Solicitud';
import { CrudprestamistasService } from 'src/app/services/crudprestamistas.service';
import { SolicitudService } from 'src/app/services/solicitud.service';
import { TokenService } from 'src/app/services/token.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-aprobar-solicitud',
  templateUrl: './aprobar-solicitud.component.html',
  styleUrls: ['./aprobar-solicitud.component.css']
})
export class AprobarSolicitudComponent implements OnInit {

  prestamo: Prestamo = new Prestamo();
  solicitud: Solicitud = new Solicitud(); // Inicializa la variable con una nueva instancia de Solicitud
  id:number

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private SToken:TokenService,
    private solicitudService: SolicitudService
      ) { 
    this.SToken.getToken()
  }

  ngOnInit(): void {
    // Obtiene el ID de usuario usando STokenService
    const idUsuario = this.SToken.getIdUsuario();

    // Llama al servicio para obtener el ID del prestamista
    this.solicitudService.obtenerIdPrestamista(idUsuario).subscribe(
      idPrestamista => {
        this.id = idPrestamista
      },
      error => {
        console.error('Error al obtener ID del prestamista:', error);
      }
    );

    // Obtiene el ID de la solicitud de los parámetros de la ruta
    const idSolicitud = this.route.snapshot.params['idSolicitud'];

    // Llama al servicio para obtener la información de la solicitud
    this.solicitudService.obtenerSolicitudPorId(idSolicitud).subscribe(
      (response: Solicitud) => {
        // Asigna los datos de la solicitud a la variable
        this.solicitud = response;
      },
      error => {
        console.error('Error al obtener la solicitud por ID:', error);
      }
    );
  }

  aprobarSolicitud() {
    const prestamistaId = this.id;
    const solicitudId = this.solicitud.id;
    const grupoId = this.solicitud.grupo.id;
    this.prestamo.prestamista.id = prestamistaId
    this.prestamo.solicitud.id = solicitudId
    this.prestamo.monto= this.solicitud.monto
    this.prestamo.grupo.id=this.solicitud.grupo.id
    this.solicitudService.aprobarSolicitud(this.prestamo).subscribe(
      (response) => {
        // Mostrar un Swal al usuario después de aprobar la solicitud
        Swal.fire({
          icon: 'success',
          title: 'Solicitud Aprobada',
          text: response.mensaje,
          confirmButtonText: 'Ok'
        }).then(() => {
          // Redirigir a la lista de solicitudes o a donde desees
          this.router.navigate(['/admin/listar-solicitud']);
        });
      },
      (err) => {
        Swal.fire("Error del sistema",err.error.mensaje,"error")
      }
    );
  }

  rechazarSolicitud() {
    const idSolicitud = this.solicitud.id;
    this.solicitudService.rechazarSolicitud(idSolicitud).subscribe(
      (response) => {
        Swal.fire({
          icon: 'success',
          title: 'Solicitud Rechazada',
          text: 'La solicitud se ha rechazado correctamente.',
          confirmButtonText: 'Ok'
        }).then(() => {
          console.log('Redireccionando a /listar-solicitud');
          this.router.navigate(['/admin/listar-solicitud']);
        });
      },
      (err) => {
        Swal.fire("Error del sistema",err.error.mensaje,"error")
      }
    );
  }
}
