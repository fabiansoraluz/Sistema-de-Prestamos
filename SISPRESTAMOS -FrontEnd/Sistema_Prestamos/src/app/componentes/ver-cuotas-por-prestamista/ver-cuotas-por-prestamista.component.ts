import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Cuota } from 'src/app/modelos/Cuota';
import { InformacionPrestamo } from 'src/app/modelos/InformacionPrestamo';
import { CuotaService } from 'src/app/services/cuota.service';
import { PrestamoService } from 'src/app/services/prestamo.service';
import { TokenService } from 'src/app/services/token.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-ver-cuotas-por-prestamista',
  templateUrl: './ver-cuotas-por-prestamista.component.html',
  styleUrls: ['./ver-cuotas-por-prestamista.component.css']
})
export class VerCuotasPorPrestamistaComponent implements OnInit {
  informacionPrestamos: any[] = [];
  isUpdating: boolean = false;
  public page:number
  public esModificacion: boolean = false;
  cuota:Cuota = new Cuota();
  ultimoIndiceEstado1: number = -1;
  constructor(private prestamoService: PrestamoService, private cuotaService: CuotaService ,private router:Router,private SToken:TokenService, private route: ActivatedRoute,) {}


  
  ngOnInit(): void {
    let id_usuario = this.SToken.getIdUsuario();

      const id_prestamo = this.route.snapshot.params['idPrestamo'];

    if (id_prestamo) {
      this.isUpdating = true;

      this.prestamoService.obtenerInformacionPrestamos(id_usuario,id_prestamo)
      .subscribe(data => {
        this.informacionPrestamos = data;

        let todosSonCero = true;

        for (const prestamo of this.informacionPrestamos) {
          if (prestamo.estado !== 0 && prestamo.estado !== 2) {
            todosSonCero = false;
            break;
          }
        }

        if (todosSonCero) {
          this.informacionPrestamos[0].botonHabilitado = true;
          console.log("Se habilita el primer botón porque todos los estados son 0 o 2");
        } else {
          for (let i = 0; i < this.informacionPrestamos.length; i++) {
            const prestamo = this.informacionPrestamos[i];
            if (prestamo.estado === 1) {
              this.ultimoIndiceEstado1 = i;
            }

            console.log(`Estado del registro ${i + 1}:`, prestamo.estado);
          }

          if (this.ultimoIndiceEstado1 !== -1 && this.ultimoIndiceEstado1 < this.informacionPrestamos.length - 1) {
            const siguienteEstado = this.informacionPrestamos[this.ultimoIndiceEstado1 + 1].estado;
            if (siguienteEstado !== 1) {
              this.informacionPrestamos[this.ultimoIndiceEstado1 + 1].botonHabilitado = true;
              console.log(`Estado del registro ${this.ultimoIndiceEstado1 + 2}: Aquí se habilita`);
            }
          }
        }
      },
        (error) => {
          console.error('Error al obtener cuota por ID:', error);
          Swal.fire("Error", "No se pudo cargar la información del prestamo.", "error");
        }
      );
    }
  
  }


  
  
  redirigirAVerPrestamos() {
    this.router.navigate(['/admin/ver-prestamos-por-prestamista']);
  }
  redireccionarAMantenimiento(id_cuota) {
    this.cuotaService.obtenerInformacionCuota(id_cuota).subscribe(
      (cuota: Cuota) => {
        this.cuota = cuota;
        this.esModificacion = true;
        this.router.navigate(['/admin/registrar-pago', id_cuota]);
        console.log(id_cuota)
      },
      (error) => {
        console.error('Error al buscar cuota por ID:', error);
      }
    );
  }

  getEstadoLegible(estado: number): string {
    switch (estado) {
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

