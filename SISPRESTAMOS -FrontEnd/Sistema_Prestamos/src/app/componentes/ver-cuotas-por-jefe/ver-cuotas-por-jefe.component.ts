import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Cuota } from 'src/app/modelos/Cuota';
import { CuotaService } from 'src/app/services/cuota.service';
import { PrestamoService } from 'src/app/services/prestamo.service';
import { TokenService } from 'src/app/services/token.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-ver-cuotas-por-jefe',
  templateUrl: './ver-cuotas-por-jefe.component.html',
  styleUrls: ['./ver-cuotas-por-jefe.component.css']
})
export class VerCuotasPorJefeComponent implements OnInit {

  informacionPrestamos: any[] = []
  public page:number
  public esModificacion: boolean = false

  constructor(private prestamoService: PrestamoService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    let idPrestamo = this.route.snapshot.params['idPrestamo']

    if (idPrestamo) {
      this.prestamoService.obtenerInformacionPrestamosJefePrestamista(idPrestamo)
      .subscribe(data => {
        this.informacionPrestamos = data
      },
      error => {
        Swal.fire("Error", "No se pudo cargar la información del prestamo.", "error")
      })
    }
  }

  redirigirAVerPrestamos() {
    this.router.navigate(['/admin/ver-estado-prestamo']);
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
