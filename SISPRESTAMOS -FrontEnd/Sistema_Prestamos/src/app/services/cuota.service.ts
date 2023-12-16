import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Cuota } from '../modelos/Cuota';
import { Prestamo } from '../modelos/Prestamo';

@Injectable({
  providedIn: 'root',
})
export class CuotaService {
  private apiUrl = 'http://localhost:8091/api/cuotas'; 

  constructor(private http: HttpClient) {}

  // Método para obtener la información de una cuota por su ID
  obtenerInformacionCuota(idCuota: number): Observable<any> {
    const url = `${this.apiUrl}/obtenerInformacion?idCuota=${idCuota}`;
    return this.http.get(url);
  }

  realizarPago(idCuota: number, monto: number, nuevoEstado: number): Observable<any> {
    const url = `${this.apiUrl}/pagarCuota?idCuota=${idCuota}&nuevoMontoPagado=${monto}&nuevoEstado=${nuevoEstado}`;
    return this.http.put(url, null); // Puedes enviar algún cuerpo en la solicitud si es necesario
  }

  listar(): Observable<Cuota[]> {
    return this.http.get<Cuota[]>(`${this.apiUrl}/listar`);
  }
  
  obtenerIdPrestamoPorCuota(idCuota: number): Observable<number> {
    const url = `${this.apiUrl}/findPrestamoIdByCuotaId?idCuota=${idCuota}`;
    return this.http.get<number>(url);
  }

  listarCuotasPorIdCliente(idCliente: number): Observable<any> {
    const url = `${this.apiUrl}/listarXIDCliente/${idCliente}`;
    return this.http.get(url);
  }

}