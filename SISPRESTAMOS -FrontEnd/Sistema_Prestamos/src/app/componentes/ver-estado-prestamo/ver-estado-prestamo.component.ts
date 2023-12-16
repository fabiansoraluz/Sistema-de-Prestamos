import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Cuota } from 'src/app/modelos/Cuota';
import { PrestamoService } from 'src/app/services/prestamo.service';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-ver-estado-prestamo',
  templateUrl: './ver-estado-prestamo.component.html',
  styleUrls: ['./ver-estado-prestamo.component.css']
})

export class VerEstadoPrestamoComponent implements OnInit {
  prestamos: any[] = []
  prestamosOriginales: any[] = []
  filtroPrestatario: string = '';
  public page: number
  public esModificacion: boolean = false
  idUsuario: number

  constructor(private prestamoService: PrestamoService, private router: Router, private tokenService: TokenService) {}

  ngOnInit(): void {
    this.idUsuario = this.tokenService.getIdUsuario()
    this.prestamoService.listarPrestamosPorJefePrestamista(this.idUsuario).subscribe(prestamos => {
      this.prestamos = prestamos
      this.prestamosOriginales = prestamos
    })
  }

  filtrarPorPrestatario() {
    this.prestamos = this.prestamosOriginales

    this.prestamos = this.prestamos.filter(prestamo =>
      prestamo.nombre_cliente.toLowerCase().includes(this.filtroPrestatario.toLowerCase())
    )
  }

  redireccionarAVerDetalles(idPrestamo) {
    this.router.navigate(['/admin/ver-cuotas-por-jefe', idPrestamo])
  }
}
