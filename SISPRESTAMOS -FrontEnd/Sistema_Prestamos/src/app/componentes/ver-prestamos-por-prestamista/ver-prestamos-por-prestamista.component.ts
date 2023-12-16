import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Cuota } from 'src/app/modelos/Cuota';
import { Prestamo } from 'src/app/modelos/Prestamo';
import { PrestamoService } from 'src/app/services/prestamo.service';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-ver-prestamos-por-prestamista',
  templateUrl: './ver-prestamos-por-prestamista.component.html',
  styleUrls: ['./ver-prestamos-por-prestamista.component.css']
})
export class VerPrestamosPorPrestamistaComponent implements OnInit {
  Prestamos: any[] = [];
  prestamosOriginales: any[] = [];
  filtroPrestatario: string = '';
  public page: number
  public esModificacion: boolean = false;
  cuota: Cuota = new Cuota();
  id_usuario: number; // Declarar la variable aquí

  constructor(private prestamoService: PrestamoService, private router: Router, private SToken: TokenService) { }

  ngOnInit(): void {
    this.id_usuario = this.SToken.getIdUsuario(); // Asignar el valor aquí
    this.prestamoService.listarPrestamos(this.id_usuario)
      .subscribe(data => {
        this.Prestamos = data;
        this.prestamosOriginales = data;
      });
  }

  filtrarPorPrestatario() {
    // Restaura la lista original antes de aplicar un nuevo filtro
    this.Prestamos = this.prestamosOriginales;

    // Filtra la lista de préstamos por el nombre del prestatario
    this.Prestamos = this.Prestamos.filter(prestamo =>
      prestamo.nombre_cliente.toLowerCase().includes(this.filtroPrestatario.toLowerCase())
    );
  }
  redireccionarAMantenimiento(id_prestamo) {
    this.prestamoService.obtenerInformacionPrestamos(this.id_usuario, id_prestamo).subscribe(
      (cuota: Cuota) => {
        this.cuota = cuota;
        this.esModificacion = true;
        this.router.navigate(['/admin/ver-cuotas-por-prestamista', id_prestamo]);
        console.log(id_prestamo)
      },
      (error) => {
        console.error('Error al buscar cuota por ID:', error);
      }
    );
    console.log(id_prestamo, this.id_usuario)
  }
}
