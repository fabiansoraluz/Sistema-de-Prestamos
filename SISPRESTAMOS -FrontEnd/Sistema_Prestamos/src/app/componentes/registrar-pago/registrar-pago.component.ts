import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DatosCuota } from 'src/app/modelos/DatosCuota';
import { CuotaService } from 'src/app/services/cuota.service';
import { PrestamoService } from 'src/app/services/prestamo.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-registrar-pago',
  templateUrl: './registrar-pago.component.html',
  styleUrls: ['./registrar-pago.component.css']
})
export class RegistrarPagoComponent implements OnInit {
  montoAPagar: number = 0;
  isRealizarPagoDisabled: boolean = false;
  mora: number = 0;
  objCuota: DatosCuota = {
    id_prestamo: 0,
    id_cuota: 0,
    estado:0,
    nombre: '',
    numero: '',
    monto: 0,
    monto_pagado: 0,
    fecha_vencimiento: '',
    montoTotal: 0
  };

  isUpdating: boolean = false;

  constructor(
    private cuotaService: CuotaService,
    private prestamoService: PrestamoService,
    private router: Router,
    private route: ActivatedRoute
  ) {}


  ngOnInit() {
    const idCuota = this.route.snapshot.params['actualizar'];

    if (idCuota) {
      this.isUpdating = true;

      this.cuotaService.obtenerInformacionCuota(idCuota).subscribe(
        (cuota: DatosCuota) => {
          this.objCuota = cuota;
          this.calcularMora(); // Llamar a la función para calcular la mora
          this.calcularMontoTotal(); // Llamar a la función para calcular el monto total
          this.isRealizarPagoDisabled = this.objCuota.montoTotal === 0;
        },
        (error) => {
          console.error('Error al obtener cuota por ID:', error);
          Swal.fire("Error", "No se pudo cargar la información de la cuota.", "error");
        }
      );
    }
  }

  redirigirAVerPrestamos() {
    this.router.navigate(['/admin/ver-cuotas-por-prestamista/'+this.objCuota.id_prestamo]);
  }

  calcularMontoTotal() {
    this.objCuota.montoTotal = this.objCuota.monto + this.objCuota.mora - this.objCuota.monto_pagado;
    this.objCuota.montoTotal = parseFloat(this.objCuota.montoTotal.toFixed(2));
    this.objCuota.monto = parseFloat(this.objCuota.monto.toFixed(2));
  }
  calcularMora() {
    if (this.objCuota.estado === 0 || this.objCuota.estado === 2) {
      // Obtener la fecha actual
      const fechaActual = new Date();

      // Obtener la fecha de vencimiento
      const fechaVencimiento = new Date(this.objCuota.fecha_vencimiento);

      if(fechaVencimiento<fechaActual){
        const diferenciaMilisegundos = fechaActual.getTime() - fechaVencimiento.getTime();

      // Calcular la diferencia en días
      const diferenciaDias = Math.floor(diferenciaMilisegundos / (1000 * 60 * 60 * 24));

      // Calcular la mora basada en la diferencia en días
      const porcentajeMora = 5; // 5% por día
      const mora = porcentajeMora * diferenciaDias * this.objCuota.monto / 100;

      // Actualizar el valor del input de mora
      this.objCuota.mora = mora;

      this.objCuota.mora = parseFloat(this.objCuota.mora.toFixed(2));
      }
      else{
        this.objCuota.mora = 0
      }
    }

    if (this.objCuota.estado === 1) {
      // Obtener la fecha actual
      const fechaActual = new Date();

      // Obtener la fecha de vencimiento
      const fechaVencimiento = new Date(this.objCuota.fecha_vencimiento);

      // Calcular la diferencia en milisegundos
      const diferenciaMilisegundos = fechaActual.getTime() - fechaVencimiento.getTime();

      // Calcular la diferencia en días
      const diferenciaDias = Math.floor(diferenciaMilisegundos / (1000 * 60 * 60 * 24));

      // Calcular la mora basada en la diferencia en días
      const porcentajeMora = 5; // 5% por día
      const mora = porcentajeMora * diferenciaDias * this.objCuota.monto / 100;

      // Actualizar el valor del input de mora
      this.objCuota.mora = mora;
      this.objCuota.montoTotal = 0
    }




  }
  
  
  realizarPago() {
    if (this.montoAPagar > 0 && this.montoAPagar <= this.objCuota.montoTotal) {
      // Redondear los valores antes de la comparación
      const montoAPagarRedondeado = Math.round(this.montoAPagar * 100) / 100;
      const montoTotalRedondeado = Math.round(this.objCuota.montoTotal * 100) / 100;

      
  
      // Determinar el nuevo estado según el monto a pagar
      let nuevoEstado: number;
      if (montoAPagarRedondeado < montoTotalRedondeado) {
        nuevoEstado = 2;
      } else if (montoAPagarRedondeado === montoTotalRedondeado) {
        nuevoEstado = 1;
        this.prestamoService.actualizarMoraPagada(this.objCuota.id_prestamo, this.objCuota.mora).subscribe(
          (responseMora) => {
            console.log('Mora pagada actualizada con éxito', responseMora);
          },
          (errorMora) => {
            console.error('Error al actualizar la mora pagada', errorMora);
            // Puedes manejar el error según tus necesidades
          }
        );
      }
  
      // Convertir los valores a números y realizar la suma
      const montoAPagarNumero = +this.montoAPagar;
      const montoPagadoNumero = +this.objCuota.monto_pagado;
      const nuevaCantidadTotalPagar = Math.round((montoAPagarNumero + montoPagadoNumero) * 100) / 100;

      
      this.objCuota.montoTotal = parseFloat(this.objCuota.montoTotal.toFixed(2));

  
      // Llamar al servicio para realizar el pago
      this.cuotaService.realizarPago(this.objCuota.id_cuota, nuevaCantidadTotalPagar, nuevoEstado).subscribe(
        (response) => {
          // Manejar la respuesta si es necesario
          console.log('Pago realizado con éxito', response);
  
          this.prestamoService.actualizarMontoPagado(this.objCuota.id_prestamo, this.montoAPagar).subscribe(
            (responseActualizacion) => {
              console.log('Monto pagado actualizado con éxito', responseActualizacion);
  
              // Mostrar SweetAlert y redirigir a la nueva ruta
              Swal.fire('Pago realizado con éxito', '', 'success').then(() => {
                // Redireccionar a la nueva ruta
                this.router.navigate(['/admin/ver-cuotas-por-prestamista/' + this.objCuota.id_prestamo]);
  
                // Puedes agregar lógica adicional, como actualizar la vista
              });
            },
            (errorActualizacion) => {
              console.error('Error al actualizar el monto pagado', errorActualizacion);
            }
          );
        },
        (error) => {
          console.error('Error al realizar el pago', error);
          // Puedes mostrar un mensaje de error al usuario si lo consideras necesario
        }
      );
  
    } else {
      // Muestra un mensaje al usuario indicando que el monto a pagar no es válido
      Swal.fire('Error', 'El monto a pagar no es válido', 'error');
    }
  }
  
  
  
}
